package com.example.currencyexchange;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class CurrencyExchangeDisplayAdapter extends RecyclerView.Adapter<CurrencyExchangeDisplayAdapter.ViewHolder> {

    List<Pair<String, Double>> rates;
    Double base;
    private static ClickListener clickListener;


    public CurrencyExchangeDisplayAdapter() {
        rates = new ArrayList<>();
    }

    public void setCurrencyExchangeList(List<Pair<String, Double>> rates) {
        this.rates = rates;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.currency_exchange_row, parent, false);
        final ViewHolder holder = new ViewHolder(v, new MyCustomEditTextListener());
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClick(v, holder.getAdapterPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        base = CurrencyRatesModel.getInstance().getBaseAmount();

        if (rates.size() > 0) {
            holder.myCustomEditTextListener.updatePosition(position);
            holder.currencyText.setText(rates.get(position).first);
            holder.currencyFullName.setText(Currency.getInstance(rates.get(position).first).getDisplayName());
            holder.exchangeRate.setText(String.format("%.2f", base * rates.get(position).second));
        }
    }


    @Override
    public int getItemCount() {
        return rates.size();
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        CurrencyExchangeDisplayAdapter.clickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView currencyText;
        public TextView currencyFullName;
        public EditText exchangeRate;
        public MyCustomEditTextListener myCustomEditTextListener;


        public ViewHolder(@NonNull View itemView, MyCustomEditTextListener listener) {
            super(itemView);
            itemView.setOnClickListener(this);
            currencyFullName = itemView.findViewById(R.id.currency_full_name);
            currencyText = itemView.findViewById(R.id.currency);
            exchangeRate = itemView.findViewById(R.id.rate);
            myCustomEditTextListener = listener;
            exchangeRate.addTextChangedListener(myCustomEditTextListener);
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {

        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i2, int i3) {

            if (position != 0)
                return;
            if (!base.equals(Double.valueOf(s.toString()))) {
                CurrencyRatesModel.getInstance().setBaseAmount(Double.valueOf(s.toString()));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);
    }
}
