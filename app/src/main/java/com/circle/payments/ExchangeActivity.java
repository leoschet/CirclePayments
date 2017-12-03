package com.circle.payments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.DecimalFormat;


public class ExchangeActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    // Nothing happens, users stays in SellActivity
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bnav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Intent intent = getIntent();
        String value = intent.getStringExtra("value");

        TextView txtView = (TextView) findViewById(R.id.BRL);
        txtView.setText("R$ " + value);

        Button back = (Button) findViewById(R.id.buttonBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ExchangeActivity.this, SellActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        Button go = (Button) findViewById(R.id.buttonGo);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue mRequestQueue = JsonComunication.getInstance(getApplicationContext()).getRequestQueue();
                // Start the queue
                mRequestQueue.start();

                Intent intent = getIntent();
                String value = intent.getStringExtra("value");

                RadioGroup group = (RadioGroup)  findViewById(R.id.coins);
                int checkedId = group.getCheckedRadioButtonId();
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
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
                                    //                            Intent myIntent = new Intent(ExchangeActivity.this, NextActivity.class);
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

        TextView brlText = findViewById(R.id.BRL);
        final TextView cptText = findViewById(R.id.CPT);

        RadioGroup radioGroup = findViewById(R.id.coins);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton) group.findViewById(checkedId);
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
                    TextView brlText = findViewById(R.id.BRL);
                    DecimalFormat formater = new DecimalFormat("0.########");
                    double x = Double.parseDouble(brlText.getText().subSequence(3, brlText.getText().length()).toString()) / d;
                    cptText.setText(formater.format(x).toString() + " " + modifier);
                }

            }
        });
    }
}
