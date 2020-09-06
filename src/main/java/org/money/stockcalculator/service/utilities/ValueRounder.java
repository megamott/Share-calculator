package org.money.stockcalculator.service.utilities;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * TODO Добавить везде вместо классовых методов
 * Округление всех чисел с плавающей точкй до 4 знакоа после запятой
 *
 * @author Matvey Konoplyov
 */
@Service
public class ValueRounder {

    /**
     * Функция округления числа с плавающей точкой до 4 знака
     */
    public double roundValue(double value){
        BigDecimal currentDecimal = BigDecimal.valueOf(value);
        return currentDecimal.setScale(4, BigDecimal.ROUND_CEILING).doubleValue();
    }
}
