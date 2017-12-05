package com.circle.payments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.DecimalFormat;


public class ExchangeFragment extends android.support.v4.app.Fragment {

    public static android.support.v4.app.Fragment newInstance() {
        ExchangeFragment fragment = new ExchangeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_exchange, container, false);

//        Intent intent = getActivity().getIntent();
//        String value = intent.getStringExtra("value");
        String value = getArguments().getString("value");

        TextView txtView = (TextView) view.findViewById(R.id.BRL);
        txtView.setText("R$ " + value);

        Button back = (Button) view.findViewById(R.id.buttonBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickview) {
//                Intent myIntent = new Intent(ExchangeFragment.this, SellFragment.class);
//                startActivity(myIntent);
//                finish();
            }
        });

        Button go = (Button) view.findViewById(R.id.buttonGo);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickview) {
                RequestQueue mRequestQueue = JsonComunication.getInstance(getActivity().getApplicationContext()).getRequestQueue();
                // Start the queue
                mRequestQueue.start();

                Intent intent = getActivity().getIntent();
                String value = intent.getStringExtra("value");

                RadioGroup group = view.findViewById(R.id.coins);
                int checkedId = group.getCheckedRadioButtonId();
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                String str = checkedRadioButton.getText().toString();

                boolean nothing = value.isEmpty() ? true : false;
                String modifier = "";

                if (str.contains("LTC")){
                    modifier = "LTC";
                }
                else if(str.contains("XMR")){
                    modifier ="XMR";
                }
                else if (str.contains("ETH")){
                    modifier = "ETH";
                }
                else if (str.contains("DSH")){
                    modifier = "DSH";
                }
                else
                    nothing = true;

                if (!nothing) {
                    String url = "https://backcircle.herokuapp.com/payment/" + value + "/" + modifier + "/12345";

                    // Formulate the request and handle the response.
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    int value = 2;
                                    // Do something with the response
                                    //                            TextView txtView = (TextView) findViewById(R.id.editText);
                                    //                            String txt = txtView.getText().subSequence(3, txtView.getText().length()).toString();
                                    //                            Intent myIntent = new Intent(ExchangeFragment.this, NextActivity.class);
                                    //                            myIntent.putExtra("value", txt); //Optional parameters
                                    //                            startActivity(myIntent);
                                    //                            finish();
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // Handle error
                                    int value = 1;
                                }
                            });

                    // Add the request to the RequestQueue.
                    mRequestQueue.add(stringRequest);
                }
            }
        });

        TextView brlText = view.findViewById(R.id.BRL);
        final TextView cptText = view.findViewById(R.id.CPT);

        RadioGroup radioGroup = view.findViewById(R.id.coins);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = group.findViewById(checkedId);
                String str = checkedRadioButton.getText().toString();

                boolean nothing = str.isEmpty() ? true : false;
                double d = 1;
                String modifier = "";

                if (str.contains("LTC")){
                    d = 41899;
                    modifier = "LTC";
                }
                else if(str.contains("XMR")){
                    d =200;
                    modifier ="XMR";
                }
                else if (str.contains("ETH")){
                    d = 1200;
                    modifier = "ETH";
                }
                else if (str.contains("DSH")){
                    d = 1400;
                    modifier = "DSH";
                }
                else
                    nothing = true;

                if (nothing)
                    cptText.setText("First choose a cripto currency");
                else {
                    TextView brlText = view.findViewById(R.id.BRL);
                    DecimalFormat formater = new DecimalFormat("0.########");
                    double x = Double.parseDouble(brlText.getText().subSequence(3, brlText.getText().length()).toString()) / d;
                    cptText.setText(formater.format(x).toString() + " " + modifier);
                }

            }
        });
        return view;
    }
}
