package org.money.stockcalculator.service.Impl;

import org.money.stockcalculator.service.Adder;
import org.money.stockcalculator.service.utilities.UsSharesComparison;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Интерфейс сложения difference акций
 *
 * @author Matvey Konoplyov
 */
@Service
public class ShareAdder implements Adder {

    private final UsSharesComparison usSharesComparison;

    public ShareAdder(UsSharesComparison usSharesComparison) {
        this.usSharesComparison = usSharesComparison;
    }

    @Override
    public double getSum() {
        return roundValue(usSharesComparison.getComparisonsList().stream().mapToDouble(a -> a).sum());
    }

    /**
     * Функция округления числа до 4 знака
     */
    private double roundValue(double value){
        BigDecimal currentDecimal = BigDecimal.valueOf(value);
        return currentDecimal.setScale(4, BigDecimal.ROUND_CEILING).doubleValue();
    }
}
