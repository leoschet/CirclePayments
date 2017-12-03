package com.circle.payments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    Intent myIntent = new Intent(MainActivity.this, SellActivity.class);
//                    myIntent.putExtra("key", value); //Optional parameters
                    startActivity(myIntent);
                    finish();
                    return true;
                case R.id.navigation_dashboard:
//                    String[] arrays = {"teste","teste"};
//                    JsonComunication.performOnBackgroundThread(new JsonComunication());

//                    try {
//                        new JsonComunication().connect(arrays);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    return true;
                case R.id.navigation_notifications:
                    String[] arrai = {"teste","teste"};
//                    try {
//                        new JsonComunication().connect(arrai);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bnav);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        EditText brlText = findViewById(R.id.BRL);
        final TextView cptText = findViewById(R.id.CPT);

        RadioGroup radioGroup = findViewById(R.id.coins);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                // This will get the radiobutton that has changed in its check state
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                String str = checkedRadioButton.getText().toString();

                boolean nothing = str.isEmpty() ? true : false;
                double d = 1;

                if (str.equals("BTC"))
                    d = 41899;
                else if (str.equals("LTC"))
                    d = 200;
                else if (str.equals("ETH"))
                    d = 1200;
                else
                    nothing = true;

                if (nothing)
                    cptText.setText("First choose a cripto currency");
                else {
                    EditText brlText = findViewById(R.id.BRL);

                    double x = Double.parseDouble(brlText.getText().toString()) / d;
                    cptText.setText(Double.toString(x));
                }

            }
        });

        TextWatcher inputTextWatcher = new TextWatcher() {
            public void afterTextChanged(Editable s) {
                RadioGroup radioGroup = findViewById(R.id.coins);
                int radioButtonID = radioGroup.getCheckedRadioButtonId();

                boolean nothing = false;
                double d = 1;
                String str = "";

                if (radioButtonID == -1)
                    nothing = true;
                else {
                    RadioButton rb = radioGroup.findViewById(radioButtonID);

                    str = rb.getText().toString();

                    if (str.equals("BTC"))
                        d = 41899;
                    else if (str.equals("LTC"))
                        d = 200;
                    else if (str.equals("ETH"))
                        d = 1200;
                    else
                        nothing = true;
                }

                if (nothing)
                    cptText.setText("First choose a cripto currency");
                else {
                    double x = Double.parseDouble(s.toString()) / d;
                    cptText.setText(Double.toString(x));
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        };

        brlText.addTextChangedListener(inputTextWatcher);
    }

}
