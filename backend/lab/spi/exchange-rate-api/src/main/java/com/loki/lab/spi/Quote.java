package com.loki.lab.spi;

import java.time.LocalDate;

public class Quote {
    private String currency;
    private LocalDate date;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
