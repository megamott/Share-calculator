package org.money.stockcalculator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StockCalculatorApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(StockCalculatorApplication.class, args);
    }
}
