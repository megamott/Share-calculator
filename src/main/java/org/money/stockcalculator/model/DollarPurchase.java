package org.money.stockcalculator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Данной моделью является любая покупка доллара за рубль у Tinkoff брокера
 * @author Matvey Konoplyov
 */
@Entity
@Data
@NoArgsConstructor
public class DollarPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public int quantity;

    /**
     * Цена покупки одного доллара в рублях
     * Общая сумма покупки не описывается, вместо этого в класс добавлено поле
     * с количеством купленных долларов
     * @see #quantity
     */
    public double price;

    /**
     * Брокерская комиссия, взымаемая с общей суммы сделки составляет 0.3%
     * Данное поле для каждого объекта считается при помощи вызова и передачи
     * в качестве аргумента конструктора функции подсчёта комиссии
     * @link Commission#getMyCommission(int quantity, double price)
     */
    public double commission;

    /**
     * Создание нового объекта покупки доллара за рубль
     * @param quantity количество купленных долларов
     * @param price цена одного доллара на момент покупки
     */
    public DollarPurchase(int quantity, double price, double commission) {
        this.quantity = quantity;
        this.price = price;
        this.commission = commission;
    }
}
