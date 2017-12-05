package com.circle.payments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;


public class SellFragment extends Fragment {

    public static android.support.v4.app.Fragment newInstance() {
        SellFragment fragment = new SellFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_sell, container, false);

        Map<String, Button> map = new HashMap<String, Button>();
        map.put("buttonZero", (Button) view.findViewById(R.id.buttonZero));
        map.put("buttonOne", (Button) view.findViewById(R.id.buttonOne));
        map.put("buttonTwo", (Button) view.findViewById(R.id.buttonTwo));
        map.put("buttonThree", (Button) view.findViewById(R.id.buttonThree));
        map.put("buttonFour", (Button) view.findViewById(R.id.buttonFour));
        map.put("buttonFive", (Button) view.findViewById(R.id.buttonFive));
        map.put("buttonSix", (Button) view.findViewById(R.id.buttonSix));
        map.put("buttonSeven", (Button) view.findViewById(R.id.buttonSeven));
        map.put("buttonEight", (Button) view.findViewById(R.id.buttonEight));
        map.put("buttonNine", (Button) view.findViewById(R.id.buttonNine));
        map.put("buttonComma", (Button) view.findViewById(R.id.buttonComma));

        for (Map.Entry<String, Button> entry : map.entrySet()) {
            final String key = entry.getKey();
            final Button value = entry.getValue();
            value.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View clickview) {
                    TextView txt = (TextView) view.findViewById(R.id.editText);
                    String baseText = txt.getText().toString().equals("R$ 0") && !key.equals("buttonComma") ? "R$ " : txt.getText().toString();
                    if(!baseText.contains(",") || (key.equals("buttonComma") && !baseText.contains(",")) || (!key.equals("buttonComma") && baseText.contains(",")))
                        txt.setText(String.format("%s%s", baseText, value.getText().toString()));
                }
            });
        }

        Button back = (Button) view.findViewById(R.id.buttonBack);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickview) {
                TextView txt = (TextView) view.findViewById(R.id.editText);
                boolean canErase = !txt.getText().toString().equals("R$ 0");

                if (canErase) {
                    String baseText = txt.getText().subSequence(0, txt.getText().length()-1).toString();
                    baseText = baseText.equals("R$ ") ? "R$ 0" : baseText;
                    txt.setText(baseText);
                }
            }
        });

        Button qtd = (Button) view.findViewById(R.id.buttonQtd);

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

        Button go = (Button) view.findViewById(R.id.buttonGo);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View clickview) {
                //TODO
//                Intent i = new Intent(getActivity().getBaseContext(), SellFragment.class);
                TextView txtView = (TextView)view.findViewById(R.id.editText);
//                i.putExtra("value", txtView.getText().subSequence(3, txtView.getText().length()));

                ExchangeFragment ldf = new ExchangeFragment();
                Bundle args = new Bundle();
                args.putString("value", txtView.getText().subSequence(3, txtView.getText().length()).toString());
                ldf.setArguments(args);

                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.changeToExchange();
            }
        });

        return view;
    }

}
