package com.loki.lab.spi;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class ExchangeRate {
    public static List<ExchangeRateProvider> providers() {
        List<ExchangeRateProvider> services = new ArrayList<>();
        ServiceLoader<ExchangeRateProvider> loader = ServiceLoader.load(ExchangeRateProvider.class);
        loader.forEach(exchangeRateProvider -> {
            services.add(exchangeRateProvider);
        });
        return services;
    }

}
