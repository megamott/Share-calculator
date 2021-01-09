package org.money.stockcalculator.service.utilities;

import org.money.stockcalculator.dao.DollarPurchaseRepo;
import org.money.stockcalculator.model.DollarPurchase;
import org.money.stockcalculator.service.CurrencyParser;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий функцию сравнения катировки валюты при покупке и катировки на данный момент
 *
 * @author Matvey Konoplyov
 */
@Service
public class DollarCurrencyComparison {

    private final DollarPurchaseRepo dollarPurchaseRepo;
    private final CurrencyParser currencyParser;
    private final Commission commission;

    public DollarCurrencyComparison(DollarPurchaseRepo dollarPurchaseRepo, @Qualifier("usdRubParser") CurrencyParser currencyParser, Commission commission) {
        this.dollarPurchaseRepo = dollarPurchaseRepo;
        this.currencyParser = currencyParser;
        this.commission = commission;
    }

    /**
     * Сравнение цен покупок доллара из базы данных с нынешними катировками
     * с учётом коммиссии при покупке
     *
     * @return упорядоченный список разниц
     */
    public List<Double> getComparisonsList() {
        List<DollarPurchase> allCurrencies = (List<DollarPurchase>) dollarPurchaseRepo.findAll();
        List<Double> differences = new ArrayList<>();
        for (DollarPurchase dollarPurchase : allCurrencies) {
            double buyingPriceWithCommission = dollarPurchase.price * dollarPurchase.quantity +
                    commission.getMyCommission(dollarPurchase.quantity, dollarPurchase.price);
            differences.add(roundValue(currencyParser.getQuote() * dollarPurchase.quantity - buyingPriceWithCommission));
        }

        return differences;
    }

    /**
     * Функция округления числа до 4 знака
     */
    private double roundValue(double value) {
        BigDecimal currentDecimal = BigDecimal.valueOf(value);
        return currentDecimal.setScale(4, BigDecimal.ROUND_CEILING).doubleValue();
    }

}
