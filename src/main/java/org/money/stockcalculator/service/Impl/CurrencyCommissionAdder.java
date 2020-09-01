package org.money.stockcalculator.service.Impl;

import org.money.stockcalculator.dao.DollarPurchaseRepo;
import org.money.stockcalculator.model.DollarPurchase;
import org.money.stockcalculator.service.Adder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс сложения комиссий с покупки доллара
 *
 * @author Matvey Konoplyov
 */
@Service
public class CurrencyCommissionAdder implements Adder {

    private final DollarPurchaseRepo dollarPurchaseRepo;

    public CurrencyCommissionAdder(DollarPurchaseRepo dollarPurchaseRepo) {
        this.dollarPurchaseRepo = dollarPurchaseRepo;
    }

    @Override
    public double getSum() {
        List<DollarPurchase> dollarPurchases = (List<DollarPurchase>) dollarPurchaseRepo.findAll();
        double sum = 0;
        for (DollarPurchase dollarPurchase : dollarPurchases) {
            sum += dollarPurchase.commission;
        }
        return roundValue(sum);
    }

    /**
     * Функция округления числа до 4 знака
     */
    private double roundValue(double value){
        BigDecimal currentDecimal = BigDecimal.valueOf(value);
        return currentDecimal.setScale(4, BigDecimal.ROUND_CEILING).doubleValue();
    }
}
