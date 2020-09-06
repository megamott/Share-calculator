package org.money.stockcalculator.service.Impl;

import org.money.stockcalculator.dao.DollarPurchaseRepo;
import org.money.stockcalculator.dao.SharePurchaseRepo;
import org.money.stockcalculator.model.DollarPurchase;
import org.money.stockcalculator.model.SharePurchase;
import org.money.stockcalculator.service.Adder;
import org.money.stockcalculator.service.utilities.Commission;
import org.money.stockcalculator.service.utilities.UsSharesComparison;
import org.money.stockcalculator.service.utilities.ValueRounder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO Переписать весь говнокод
 * Класс, определяющий суммарную доходность портфеля с учётом налогов и коммиссий
 *
 * @author Matvey Konoplyov
 */
@Service
public class CashAdder implements Adder {

    private final DollarPurchaseRepo dollarPurchaseRepo;
    private final SharePurchaseRepo sharePurchaseRepo;
    private final ValueRounder valueRounder;
    private final UsSharesComparison usSharesComparison;
    private final Commission commission;
    private final UsdRubParser usdRubParser;

    public CashAdder(DollarPurchaseRepo dollarPurchaseRepo,
                     SharePurchaseRepo sharePurchaseRepo,
                     ValueRounder valueRounder,
                     UsSharesComparison usSharesComparison,
                     Commission commission,
                     UsdRubParser usdRubParser) {
        this.dollarPurchaseRepo = dollarPurchaseRepo;
        this.sharePurchaseRepo = sharePurchaseRepo;
        this.valueRounder = valueRounder;
        this.usSharesComparison = usSharesComparison;
        this.commission = commission;
        this.usdRubParser = usdRubParser;
    }

    /**
     * Функция, определяющая сумму, на которую может быть продан портфель при продаже акций прямо сейчас в рублях
     * Данное число определяется по следующей формуле:
     * Цена акции на данный момент в рублях- комиссия на продажу акции в размере 0.3% - налоговый вычет в размере 13% с дохода
     * Налоговый вычет считается в {@link CashAdder#getTaxInRuble}
     */
    @Override
    public double getSum() {
        List<SharePurchase> allPurchases = (List<SharePurchase>) sharePurchaseRepo.findAll();
        double income = 0;

        for (int i = 0; i < allPurchases.size(); i++) {
            double currentPrice = getCurrentSharePriceWithoutCommissionInRuble(allPurchases.get(i), i);
            income += currentPrice - commission.getMyCommission(currentPrice)
                    - getTaxInRuble(allPurchases.get(i), i);
        }

        return valueRounder.roundValue(income);
    }

    /**
     * Функция, опередляющая доходность портфеля, как:
     * Цена продажи портфеля на данный момент {@link CashAdder#getSum()} - цена покупки портфеля с учётом комиссии при покупке
     */
    public double getIncome() {
        return valueRounder.roundValue(getSum() - getSpending());
    }

    /**
     * Определение цены продажи портфеля без учёта налогов и комиссии
     */
    public double getSharesPrice(){
        List<SharePurchase> allPurchases = (List<SharePurchase>) sharePurchaseRepo.findAll();
        double fullPrice = 0;
        for (int i = 0; i < allPurchases.size(); i++) {
            fullPrice += getCurrentSharePriceWithoutCommissionInRuble(allPurchases.get(i), i);
        }
        return valueRounder.roundValue(fullPrice);
    }

    /**
     * Подсчёт налогового вычета для одной акции считается по формуле:
     * (Цена акции на данный момент в рублях - комиссия 0.3% с продажи акции - комиссия 0.3% с покупки акции - цена покупки акции в рублях) * 13%
     *
     * @param sharePurchase данная акция
     * @param i             номер акции в базе данных
     * @return налоговый вычет
     */
    private double getTaxInRuble(SharePurchase sharePurchase, int i) {
        double sellingCommission = commission.getMyCommission(getCurrentSharePriceWithoutCommissionInRuble(sharePurchase, i));
        double sharePriceWithCommissionInRuble = sharePurchase.quantity * sharePurchase.price * usdRubParser.getQuote()
                + sharePurchase.commission * usdRubParser.getQuote();
        return (getCurrentSharePriceWithoutCommissionInRuble(sharePurchase, i) - sellingCommission - sharePriceWithCommissionInRuble) * 0.13;
    }

    /**
     * Определение цены акции на данный момент без учёта комиссии
     */
    private double getCurrentSharePriceWithoutCommissionInRuble(SharePurchase sharePurchase, int i) {
        return sharePurchase.price * sharePurchase.quantity * usdRubParser.getQuote() + usSharesComparison.getComparisonsList().get(i) * usdRubParser.getQuote()
                + sharePurchase.commission * usdRubParser.getQuote();
    }

    /**
     * Определение цены покупки портфеля с учётом комиссии при покупке доллара и коммиссии при покупке рубля
     *
     * Баг (можно исправить): комиссия переводится в рубли по нынешнему курсу
     */
    public double getSpending(){
        List<DollarPurchase> dollarPurchases = (List<DollarPurchase>) dollarPurchaseRepo.findAll();
        List<SharePurchase> sharePurchases = (List<SharePurchase>) sharePurchaseRepo.findAll();
        double sum = 0;
        for (DollarPurchase dollarPurchase : dollarPurchases) {
            sum += dollarPurchase.price * dollarPurchase.quantity + dollarPurchase.commission;
        }
        for(SharePurchase sharePurchase : sharePurchases){
            sum += sharePurchase.commission * usdRubParser.getQuote();
        }

        return valueRounder.roundValue(sum);
    }

}
