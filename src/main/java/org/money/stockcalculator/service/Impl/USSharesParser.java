package org.money.stockcalculator.service.Impl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.money.stockcalculator.service.SharesParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Парсер катировок акций США (@link https://smart-lab.ru/q/usa/)
 * Реализация интерфейса парсера акций {@link org.money.stockcalculator.service.SharesParser}
 *
 * @author Matvey Konoplyov
 */
@Service
public class USSharesParser implements SharesParser {

    /**
     * Получение html кода страницы по ссылке
     * @return объект Document с html кодом страницы
     */
    private Document getPage(String link) {
        String url = link;
        Document document = null;

        try {
            document = Jsoup.parse(new URL(url), 3000);
        } catch (IOException e) {
            System.out.println("Сайт не найден!");
        }

        return document;
    }

    /**
     * Функиия получения списка тикеров акций представленных на рынке из html кода страницы
     * Выборка проводилась при помощи регулярных выражений методом тыка,
     * потому может быть оптимизирована
     * @return список тикеров
     */
    private List<String> getTickerList() {
        Document page = getPage("https://smart-lab.ru/q/usa/");

        String tableQuotes = page.select("table[class=simple-little-table trades-table]")
                                 .first()
                                 .select("td")
                                 .text()
                                 .replaceAll("\\d+ \\d+:\\d+:\\d+", "");

        List<String> tickerList = getInfoByPattern(tableQuotes,
                "(([A-Z]{1,}   |[A-Z]{1,}\\.[A-Z]{1,}   )|          )"
        );

        for (int i = 0; i < tickerList.size(); i++) {
            tickerList.set(i, tickerList.get(i).replaceAll(" ", ""));
        }

        return tickerList;
    }

    /**
     * Функиия получения массива катировок акций представленных на рынке из html кода страницы
     * Выборка проводилась при помощи регулярных выражений методом тыка,
     * потому может быть оптимизирована
     * @return массив double из катировок акций
     */
    private double[] getQuoteList() {
        Document page = getPage("https://smart-lab.ru/q/usa/");

        String tableQuotes = page.select("table[class=simple-little-table trades-table]")
                                 .first()
                                 .select("td")
                                 .text()
                                 .replaceAll("\\d+ \\d+:\\d+:\\d+", "")
                                 .replaceAll(" 0 0 ", "")
                                 .replaceAll("Phillips 66", "");


        List<String> list = getInfoByPattern(tableQuotes, "( \\d+[^A-Z]( |.\\d* )|          )");

        for (int i = 0; i < list.size(); i++) {
            list.set(i, list.get(i).replaceAll(" ", ""));
        }

        return getDoubleArrayFromList(list);
    }

    /**
     * Преобразование списка в массив double
     * @param list лист строк (катировок акций)
     * @return массив double из катировок акций
     */
    private double[] getDoubleArrayFromList(List<String> list){
        double[] doubleValues = new double[list.size()];

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals("")) {
                doubleValues[i] = 0.0; // первый элемент списка был 0.0, убрать этот баг было сложнее
            } else {
                doubleValues[i] = Double.parseDouble(list.get(i));
            }
        }

        return doubleValues;
    }

    /**
     * Выборка из строки подстрок удовлетворяющих шаблону
     * @param parsedString исходная строка для выборки
     * @param myPattern шаблон
     * @return список подстрок, удовлетворяющих нужному шаблону
     */
    private List<String> getInfoByPattern(String parsedString, String myPattern) {
        Pattern pattern = Pattern.compile(myPattern);
        Matcher matcher = pattern.matcher(parsedString);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < parsedString.length(); i++) {
            if (matcher.find()) {
                list.add(matcher.group());
            }
        }
        return list;
    }

    /**
     * Получение словаря из тикеров акций США и соответствующих им катировок из листа тикеров {@link #getTickerList()}
     * и массива катировок {@link #getQuoteList()}
     * @return словарь из тикеров акций и соответствующих им катировок
     * @throws Exception выбрасывается, когда размеры листа тикеров и массива катировок не совпадают
     */
    public Map<String, Double> getMapShares() throws Exception {
        Map<String, Double> sharesUS = new HashMap<>();
        List<String> tickerList = getTickerList();
        double[] quotes = getQuoteList();

        if (tickerList.size() == quotes.length) {
            for (int i = 0; i < tickerList.size(); i++) {
                sharesUS.put(tickerList.get(i), quotes[i]);
            }
        } else throw new Exception("Lengths are not equal!");

        return sharesUS;
    }

}
