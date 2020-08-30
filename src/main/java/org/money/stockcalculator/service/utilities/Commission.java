package org.money.stockcalculator.service.utilities;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author Matvey Konoplyov
 */
@Service
public class Commission {

    /**
     * Функция считает комиссию с покупки доллара или акций в размере 0.03%
     * @param quantity количество купленных долларов
     * @param price цена одного доллара
     * @return комиссия, число округлено до 4 знака полсе запятой
     */
    public double getMyCommission(int quantity, double price) {
        BigDecimal currentDecimal = BigDecimal.valueOf(quantity * price * 0.003);
        return currentDecimal.setScale(4, BigDecimal.ROUND_CEILING).doubleValue();
    }
}
