package org.money.stockcalculator.service;

import org.springframework.stereotype.Service;

/**
 * Интерфейс сложения списка
 *
 * @author Matvey Konoplyov
 */
@Service
public interface Adder {
    public double getSum();
}
