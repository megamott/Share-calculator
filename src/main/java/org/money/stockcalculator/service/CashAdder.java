package org.money.stockcalculator.service;

import org.money.stockcalculator.dao.DollarPurchaseRepo;
import org.money.stockcalculator.model.DollarPurchase;
import org.money.stockcalculator.service.utilities.ValueRounder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Matvey Konoplyov
 */
@Service
public class CashAdder implements Adder {

    private final DollarPurchaseRepo dollarPurchaseRepo;
    private final ValueRounder valueRounder;

    public CashAdder(DollarPurchaseRepo dollarPurchaseRepo, ValueRounder valueRounder) {
        this.dollarPurchaseRepo = dollarPurchaseRepo;
        this.valueRounder = valueRounder;
    }

    /**
     * Подсчёт суммы, на которую была куплена долларовая валюта с учётом коммиссии
     *
     * @return
     */
    private double BuyingCurrencyCashAdder() {
        List<DollarPurchase> dollarPurchases = (List<DollarPurchase>) dollarPurchaseRepo.findAll();
        double sum = 0;
        for (DollarPurchase dollarPurchase : dollarPurchases) {
            sum += dollarPurchase.price - dollarPurchase.commission;
        }
        return valueRounder.roundValue(sum);
    }


    @Override
    public double getSum() {
        return 0;
    }
}
