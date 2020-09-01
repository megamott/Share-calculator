package org.money.stockcalculator.service.utilities;


import org.money.stockcalculator.dao.SharePurchaseRepo;
import org.money.stockcalculator.model.SharePurchase;
import org.money.stockcalculator.service.SharesParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий функцию сравнения катировки акции при покупке и катировки на данный момент
 *
 * @author Matvey Konoplyov
 */
@Service
public class UsSharesComparison {

    private final SharePurchaseRepo sharePurchaseRepo;
    private final SharesParser sharesParser;

    public UsSharesComparison(SharePurchaseRepo sharePurchaseRepo, @Qualifier("usSharesParser") SharesParser sharesParser) {
        this.sharePurchaseRepo = sharePurchaseRepo;
        this.sharesParser = sharesParser;
    }

    /**
     * Функция ищет нынешнюю катировку акции по тикеру акции и сравнивает её с той, которая была при покупке
     *
     * @param share рассматриваемая акция
     * @return разница цен
     */
    private double compareCurrentShare(SharePurchase share) {
        double currentPrice = 0;
        try {
            currentPrice = sharesParser.getMapShares().get(share.ticker);
        } catch (Exception e) {
            System.out.println("Проблема в считывании словаря акций");
        }
        double purchasePrice = share.price;

        return currentPrice - purchasePrice;
    }

    /**
     * Сравнение цен покупок всех акций из базы данных с нынешними катировками
     *
     * @return упорядоченный список разниц
     */
    public List<Double> getComparisonsList() {
        List<SharePurchase> allShares = (List<SharePurchase>) sharePurchaseRepo.findAll();
        List<Double> differences = new ArrayList<>();
        for (SharePurchase allShare : allShares) {
            double difference = compareCurrentShare(allShare);
            differences.add(roundValue(difference));
        }
        return differences;
    }

    /**
     * Функция округления числа до 4 знака
     */
    private double roundValue(double value){
        BigDecimal currentDecimal = BigDecimal.valueOf(value);
        return currentDecimal.setScale(4, BigDecimal.ROUND_CEILING).doubleValue();
    }
}
