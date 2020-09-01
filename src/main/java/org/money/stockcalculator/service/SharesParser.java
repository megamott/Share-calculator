package org.money.stockcalculator.service;

import java.util.Map;

/**
 * Парсер катировок акций
 *
 * @author Matvey Konoplyov
 */
public interface SharesParser {
    /**
     * @return словарь из тикеров акций и соответствующих им катировок
     * @throws Exception выбрасывается, когда размеры листа тикеров и массива катировок не совпадают
     * Это ошибка может возникнуть из-за ошибки в реализации метода
     */
    public Map<String, Double> getMapShares() throws Exception;
}
