package com.loki.lab.spi;

import java.time.LocalDate;
import java.util.List;

public class MainApp {
    public static void main(String[] args) {
        ExchangeRate.providers().forEach(provider -> {
            System.out.println("Retreiving USD quotes from provider :" + provider);
            List<Quote> quotes = provider.create().getQuotes("USD", LocalDate.now());
            System.out.println("----------------------------------------");
            quotes.forEach(quote -> {
                System.out.println("USD --> " + quote.getCurrency());
            });
        });
    }
}
