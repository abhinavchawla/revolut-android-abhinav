package com.example.currencyexchange;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements CurrencyExchangeContract.View {
    CurrencyExchangeDisplayAdapter mAdapter;
    PresenterImpl mPresenter;
    long REFRESH_TIME_PERIOD = 1000; //1 second

    private Timer timer = new Timer();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new PresenterImpl(this, this);
        setContentView(R.layout.activity_main);

        manageAdapter();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                mPresenter.sendRequestToServer();
            }
        }, 0, REFRESH_TIME_PERIOD);
    }

    @Override
    public void onGetDataSuccess(JSONObject response) {
        CurrencyExchangeRatesCollection collection = new Gson().fromJson(response.toString(), CurrencyExchangeRatesCollection.class);
        CurrencyRatesModel.getInstance().refreshModel();
        for (Map.Entry<String, Double> entry : collection.getRates().entrySet()) {
            CurrencyRatesModel.getInstance().addNewCurrency(entry.getKey(), entry.getValue());
        }
        mAdapter.setCurrencyExchangeList(CurrencyRatesModel.getInstance().getRates());
        mAdapter.notifyDataSetChanged();
        Log.i(MainActivity.class.getSimpleName(), response.toString());
    }

    @Override
    public void onGetDataFailure(VolleyError error) {
        Toast errorToast = Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT);
        errorToast.show();
        Log.i(MainActivity.class.getSimpleName(), error.toString());
    }

    @Override
    public void manageAdapter() {
        CurrencyExchangeDisplayAdapter.ClickListener clickListener = new CurrencyExchangeDisplayAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CurrencyRatesModel.getInstance().setBaseCurrency(CurrencyRatesModel.getInstance().getRates().get(position).first);
            }
        };
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mAdapter = new CurrencyExchangeDisplayAdapter();
        mAdapter.setOnItemClickListener(clickListener);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}
