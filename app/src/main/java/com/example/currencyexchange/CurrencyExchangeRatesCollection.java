package com.example.currencyexchange;

import java.util.Map;

public class CurrencyExchangeRatesCollection {

    private String date;


    private String base;

    private Map<String, Double> rates;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

}


