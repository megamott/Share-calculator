package org.money.stockcalculator.service.Impl;

import org.money.stockcalculator.service.Adder;
import org.money.stockcalculator.service.utilities.DollarCurrencyComparison;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Сложение differences акций
 *
 * @author Matvey Konoplyov
 */
@Service
public class CurrencyAdder implements Adder {

    private final DollarCurrencyComparison dollarCurrency;

    public CurrencyAdder(DollarCurrencyComparison dollarCurrency) {
        this.dollarCurrency = dollarCurrency;
    }

    @Override
    public double getSum() {
        return roundValue(dollarCurrency.getComparisonsList().stream().mapToDouble(a -> a).sum());
    }

    /**
     * Функция округления числа до 4 знака
     */
    private double roundValue(double value){
        BigDecimal currentDecimal = BigDecimal.valueOf(value);
        return currentDecimal.setScale(4, BigDecimal.ROUND_CEILING).doubleValue();
    }
}
