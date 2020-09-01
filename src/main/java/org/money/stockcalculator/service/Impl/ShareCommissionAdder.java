package org.money.stockcalculator.service.Impl;

import org.money.stockcalculator.dao.SharePurchaseRepo;
import org.money.stockcalculator.model.SharePurchase;
import org.money.stockcalculator.service.Adder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Интерфейс сложения коммиссий с покупки акций
 *
 * @author Matvey Konoplyov
 */
@Service
public class ShareCommissionAdder implements Adder {

    private final SharePurchaseRepo sharePurchaseRepo;

    public ShareCommissionAdder(SharePurchaseRepo sharePurchaseRepo) {
        this.sharePurchaseRepo = sharePurchaseRepo;
    }

    @Override
    public double getSum() {
        List<SharePurchase> sharePurchases = (List<SharePurchase>) sharePurchaseRepo.findAll();
        double sum = 0;
        for (SharePurchase sharePurchase: sharePurchases) {
            sum += sharePurchase.commission;
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
