package com.example.currencyexchange;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class PresenterImpl implements CurrencyExchangeContract.Presenter {
    private final String BASE_URL = "https://revolut.duckdns.org/latest?base=";
    private RequestQueue mQueue;
    private CurrencyExchangeContract.View mView;


    public PresenterImpl(CurrencyExchangeContract.View view, Context context) {
        this.mView = view;
        mQueue = Volley.newRequestQueue(context);

    }

    @Override
    public void sendRequestToServer() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, BASE_URL + CurrencyRatesModel.getInstance().getBaseCurrency(), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mView.onGetDataSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mView.onGetDataFailure(error);
                    }
                });
        mQueue.add(jsonObjectRequest);
    }
}
