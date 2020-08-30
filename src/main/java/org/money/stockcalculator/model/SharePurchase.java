package org.money.stockcalculator.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Данной моделью является любая покупка акции у Tinkoff брокера
 *
 * @author Matvey Konoplyov
 */
@Entity
@Data
@NoArgsConstructor
public class SharePurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    /**
     * Тикер акции (MSFT, AAPL)
     */
    public String ticker;

    public int quantity;

    /**
     * Цена покупки одной акции в рублях на момент покупки
     * Общая сумма покупки не описывается, вместо этого в класс добавлено поле
     * с количеством купленных акций
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
     * Создание нового объекта покупки акции
     * @param ticker тикер акции
     * @param quantity количество купленных акций
     * @param price цена за одну акцию на момент попкупки
     * @param commission комиссия сделки
     */
    public SharePurchase(String ticker, int quantity, double price, double commission) {
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
        this.commission = commission;
    }
}
