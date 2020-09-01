package org.money.stockcalculator.service.Impl;

import org.apache.commons.lang3.ArrayUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.money.stockcalculator.service.CurrencyParser;

import java.io.IOException;
import java.net.URL;

/**
 * Парсер катировки покупки доллара за рубль (@link @link https://smart-lab.ru/q/usa/)
 * Реализация интерфейска получения катировки покупки валюты {@link CurrencyParser}
 *
 * @author Matvey Konoplyov
 */
public class UsdRubParser implements CurrencyParser {

    /**
     * Получение html кода страницы по ссылке
     * @return объект Document с html кодом страницы
     */
    private Document getPage() {
        String url = "https://smart-lab.ru/q/currencies/";
        Document page = null;
        try {
            page = Jsoup.parse(new URL(url), 3000);
        } catch (IOException e) {
            System.out.println("Сайт не отвечает");
        }
        return page;
    }

    /**
     * Функиия получения списка катировок покупки и продажи валют представленных на рынке из html кода страницы
     * Выборка проводилась при помощи регулярных выражений методом тыка,
     * потому может быть оптимизирована
     * Реализация интерфейса требует всего одно значение, а не список, так что данная выборка может быть
     * оптимизирована и улучшена также с этой стороны
     * @return массив катировок покупки и продажи различных валют
     */
    private double[] getDoubleQuoteArray() {
        Document page = getPage();
        Element tableQuote = page.select("table[class=simple-little-table trades-table]").first();
        String values = tableQuote.select("td").text()
                .replaceAll("[a-zA-ZА-Яа-я]", "")
                .replaceAll("\\d+:\\d+:\\d+", "")
                .replaceAll("([+\\-])\\d+\\.\\d*%", "")
                .replaceAll("\\+", "")
                .replaceAll("_", "")
                .replaceAll(" \\d{5,}", "")
                .replaceAll("\\d*%", "");

        String[] stringValues = values.split(" +");

        double[] doubleValues = new double[6];
        for (int i = 1; i < 6; i++) {
            doubleValues[i] = Double.parseDouble(stringValues[i]);
        }

        doubleValues = ArrayUtils.remove(doubleValues, 0);

        return doubleValues;
    }

    public double getQuote(){
        double[] quotes = getDoubleQuoteArray();
        return quotes[0];
    }
}
