package com.volynsky.vladyslav.cigarettecounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class StatisticsActivity extends AppCompatActivity implements View.OnClickListener {

    Button backButton;

    TextView smokedAllValue;
    TextView averageNormDispValue;
    TextView moneySpentValue;
    TextView nicotineGainValue;
    TextView tarGainValue;

    SharedPreferences savePref;

    String statSmoked, statMoney, statPercent, statNic, statTar = "0";

    final String SAVE_AllSMOKED = "The count of all the smoked cigarettes";
    final String SAVE_MONEYSPENT = "Money spent on cigarettes";
    final String SAVE_PERCENT = "Percent of norm smoked";
    final String SAVE_ALLNICOTINE = "Nicotine amount";
    final String SAVE_ALLTAR = "Tar amount";

    DecimalFormat percentformat = new DecimalFormat ("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        initializeViews();
        setDefaultScreen();
    }

    public void initializeViews() {
        backButton = findViewById(R.id.BackButton);
        smokedAllValue = findViewById(R.id.SmokedAllValue);
        averageNormDispValue = findViewById(R.id.AverageNormDispValue);
        moneySpentValue = findViewById(R.id.MoneySpentValue);
        nicotineGainValue = findViewById(R.id.NicotineGainValue);
        tarGainValue = findViewById(R.id.TarGainValue);
    }

    public void setDefaultScreen() {
        backButton.setOnClickListener(this);
        loadValue();
        if (statSmoked != null){
            smokedAllValue.setText(statSmoked);
        }
        if (statPercent != null){
            statPercent = statPercent + "%";
            averageNormDispValue.setText(statPercent);
        }
        if (statMoney != null){
            moneySpentValue.setText(statMoney);
        }
        if (statNic != null){
            nicotineGainValue.setText(statNic);
        }
        if (statTar != null){
            tarGainValue.setText(statTar);
        }
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void loadValue () {
        savePref = getSharedPreferences("CigarettePref",MODE_PRIVATE);
        statSmoked = String.valueOf(savePref.getInt(SAVE_AllSMOKED,0));
        statPercent = String.valueOf(percentformat.format(savePref.getFloat(SAVE_PERCENT,0)));
        if (Float.valueOf(statPercent) >= 100) {
            averageNormDispValue.setTextColor(getResources().getColor(R.color.colorAvailableBad));
        }
        else {
            averageNormDispValue.setTextColor(getResources().getColor(R.color.colorAvailableNorm));
        }
        statMoney = String.valueOf(percentformat.format(savePref.getFloat(SAVE_MONEYSPENT,0)));
        statNic = String.valueOf(percentformat.format(savePref.getFloat(SAVE_ALLNICOTINE,0)));
        statTar = String.valueOf(savePref.getInt(SAVE_ALLTAR,0));
    }
}
