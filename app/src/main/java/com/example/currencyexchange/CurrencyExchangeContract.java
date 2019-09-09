package com.example.currencyexchange;

import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface CurrencyExchangeContract {
    interface View {
        void onGetDataSuccess(JSONObject response);

        void onGetDataFailure(VolleyError error);

        void manageAdapter();
    }

    interface Presenter {
        void sendRequestToServer();
    }
}

