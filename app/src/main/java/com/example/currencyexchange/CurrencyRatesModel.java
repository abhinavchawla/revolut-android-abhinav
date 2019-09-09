package com.example.currencyexchange;

import androidx.core.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class CurrencyRatesModel {
    private static CurrencyRatesModel INSTANCE = null;
    private static List<Pair<String, Double>> rates;
    private static String baseCurrency;
    private static Double baseAmount;
    private static final String EUROPE_ISO = "EUR"; //The intial base currency

    public void addNewCurrency(String currency, Double rate) {
        CurrencyRatesModel.rates.add(new Pair<>(currency, rate));
    }

    public void setBaseCurrency(String baseCurrency) {
        CurrencyRatesModel.baseCurrency = baseCurrency;
    }

    public void setBaseAmount(Double baseAmount) {
        CurrencyRatesModel.baseAmount = baseAmount;
    }

    public List<Pair<String, Double>> getRates() {
        return rates;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public Double getBaseAmount() {
        return baseAmount;
    }

    public void refreshModel() {
        rates = new ArrayList<>();
        rates.add(new Pair<>(baseCurrency, 1.0));
    }

    private CurrencyRatesModel() {
    }

    public static CurrencyRatesModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CurrencyRatesModel();
            rates = new ArrayList<>();
            baseCurrency = EUROPE_ISO;
            baseAmount = 1.0;
        }
        return (INSTANCE);
    }
}
