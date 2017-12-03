package com.circle.payments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


public class SellActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_sell);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bnav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Map<String, Button> map = new HashMap<String, Button>();
        map.put("buttonZero", (Button) findViewById(R.id.buttonZero));
        map.put("buttonOne", (Button) findViewById(R.id.buttonOne));
        map.put("buttonTwo", (Button) findViewById(R.id.buttonTwo));
        map.put("buttonThree", (Button) findViewById(R.id.buttonThree));
        map.put("buttonFour", (Button) findViewById(R.id.buttonFour));
        map.put("buttonFive", (Button) findViewById(R.id.buttonFive));
        map.put("buttonSix", (Button) findViewById(R.id.buttonSix));
        map.put("buttonSeven", (Button) findViewById(R.id.buttonSeven));
        map.put("buttonEight", (Button) findViewById(R.id.buttonEight));
        map.put("buttonNine", (Button) findViewById(R.id.buttonNine));
        map.put("buttonComma", (Button) findViewById(R.id.buttonComma));

        for (Map.Entry<String, Button> entry : map.entrySet()) {
            final String key = entry.getKey();
            final Button value = entry.getValue();
            value.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    TextView txt = (TextView) findViewById(R.id.editText);
                    String baseText = txt.getText().toString().equals("R$ 0") && !key.equals("buttonComma") ? "R$ " : txt.getText().toString();
                    if(!baseText.contains(",") || (key.equals("buttonComma") && !baseText.contains(",")) || (!key.equals("buttonComma") && baseText.contains(",")))
                        txt.setText(baseText + value.getText().toString());
                }
            });
        }

        Button back = (Button) findViewById(R.id.buttonBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txt = (TextView) findViewById(R.id.editText);
                boolean canErase = txt.getText().toString().equals("R$ 0") ? false : true;

                if (canErase) {
                    String baseText = txt.getText().subSequence(0, txt.getText().length()-1).toString();
                    baseText = baseText.equals("R$ ") ? "R$ 0" : baseText;
                    txt.setText(baseText);
                }
            }
        });

        Button qtd = (Button) findViewById(R.id.buttonQtd);

//        qtd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                TextView txt = (TextView) findViewById(R.id.editText);
//                boolean canErase = txt.getText().toString().equals("R$ 0") ? false : true;
//
//                if (canErase) {
//                    String baseText = txt.getText().subSequence(0, txt.getText().length()).toString();
//                    baseText = baseText.equals("R$ ") ? "R$ 0" : baseText;
//                    txt.setText(baseText);
//                }
//            }
//        });

        Button go = (Button) findViewById(R.id.buttonGo);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView txtView = (TextView) findViewById(R.id.editText);
                String txt = txtView.getText().subSequence(3, txtView.getText().length()).toString();
                Intent myIntent = new Intent(SellActivity.this, ExchangeActivity.class);
                myIntent.putExtra("value", txt); //Optional parameters
                startActivity(myIntent);
                finish();
            }
        });
    }
}
