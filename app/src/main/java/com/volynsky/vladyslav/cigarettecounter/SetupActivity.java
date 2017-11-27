package com.volynsky.vladyslav.cigarettecounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class SetupActivity extends AppCompatActivity implements View.OnClickListener{

    Button saveButton;
    Button backButton;
    Button plusB;
    Button minusB;
    Button plusB1;
    Button minusB1;
    Button plusB2;
    Button minusB2;
    Button plusB3;
    Button minusB3;
    Button plusB4;
    Button minusB4;

    TextView normPerDayValue;
    TextView cigarettPriceValue;
    TextView cigarettPriceCentValue;
    TextView nicotineValue;
    TextView tarValue;


    int norm, priceG, priceC, tar, saveflag = 0;
    float nicotine = 0;

    final String SAVE_NORM = "Cigarett norm: ";
    final String SAVE_PRICEG = "Cigarett priceG: ";
    final String SAVE_PRICEC = "Cigarett priceC: ";
    final String SAVE_NICOTINE = "Cigarett nicotine: ";
    final String SAVE_TAR = "Cigarett tar: ";
    final String SAVE_FLAG = "Setup is changed";

    SharedPreferences savePref;
    SharedPreferences.Editor editor;

    DecimalFormat nicotineformat = new DecimalFormat ("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        initializeViews();
        setDefaultScreen();
    }

    public void initializeViews() {
        saveButton = findViewById(R.id.SaveButton);
        backButton = findViewById(R.id.BackButton);
        plusB = findViewById(R.id.PlusB);
        minusB = findViewById(R.id.MinusB);
        plusB1 = findViewById(R.id.PlusB1);
        minusB1 = findViewById(R.id.MinusB1);
        plusB2 = findViewById(R.id.PlusB2);
        minusB2 = findViewById(R.id.MinusB2);
        plusB3 = findViewById(R.id.PlusB3);
        minusB3 = findViewById(R.id.MinusB3);
        plusB4 = findViewById(R.id.PlusB4);
        minusB4 = findViewById(R.id.MinusB4);
        normPerDayValue = findViewById(R.id.NormPerDayValue);
        cigarettPriceValue = findViewById(R.id.CigarettPriceValue);
        cigarettPriceCentValue = findViewById(R.id.CigarettPriceCentValue);
        nicotineValue = findViewById(R.id.NicotineValue);
        tarValue = findViewById(R.id.TarValue);
    }

    public void setDefaultScreen() {
        saveButton.setOnClickListener(this);
        backButton.setOnClickListener(this);
        plusB.setOnClickListener(this);
        minusB.setOnClickListener(this);
        plusB1.setOnClickListener(this);
        minusB1.setOnClickListener(this);
        plusB2.setOnClickListener(this);
        minusB2.setOnClickListener(this);
        plusB3.setOnClickListener(this);
        minusB3.setOnClickListener(this);
        plusB4.setOnClickListener(this);
        minusB4.setOnClickListener(this);
        loadValue();
        conditState(norm, minusB);
        conditState(priceG, minusB1);
        conditState(priceC, minusB2);
        conditState(nicotine, minusB3);
        conditState(tar, minusB4);
    }

    public void conditState (int flag, Button button){
        if (flag != 0){
            buttonEnable(button);
        }
        else {
            buttonDisable(button);
        }
    }

    public void conditState (float flag, Button button){
        if (flag != 0){
            buttonEnable(button);
        }
        else {
            buttonDisable(button);
        }
    }

    public void plusClicked (){
        norm++;
        normPerDayValue.setText(String.valueOf(norm));
        buttonEnable(minusB);
        if (norm == 40) {
            buttonDisable(plusB);
        }
    }

    public void minusClicked (){
        norm--;
        normPerDayValue.setText(String.valueOf(norm));
        buttonEnable(plusB);
        if (norm < 1) {
            buttonDisable(minusB);
        }
    }

    public void plus1Clicked (){
        priceG++;
        cigarettPriceValue.setText(String.valueOf(priceG));
        buttonEnable(minusB1);
    }

    public void minus1Clicked (){
        priceG--;
        cigarettPriceValue.setText(String.valueOf(priceG));
        if (priceG < 1) {
            buttonDisable(minusB1);
        }
    }

    public void plus2Clicked (){
        priceC+= 5;
        cigarettPriceCentValue.setText(String.valueOf(priceC));
        buttonEnable(minusB2);
    }

    public void minus2Clicked (){
        priceC-= 5;
        cigarettPriceCentValue.setText(String.valueOf(priceC));
        if (priceC < 1) {
            buttonDisable(minusB2);
        }
    }

    public void plus3Clicked (){
        nicotine+= 0.1;
        nicotineValue.setText(String.valueOf(nicotineformat.format(nicotine)));
        buttonEnable(minusB3);
    }

    public void minus3Clicked (){
        nicotine-= 0.1;
        nicotineValue.setText(String.valueOf(nicotineformat.format(nicotine)));
        if (nicotine <= 0.1) {
            buttonDisable(minusB3);
        }
    }

    public void plus4Clicked (){
        tar++;
        tarValue.setText(String.valueOf(tar));
        buttonEnable(minusB4);
    }

    public void minus4Clicked (){
        tar--;
        tarValue.setText(String.valueOf(tar));
        if (tar < 1) {
            buttonDisable(minusB4);
        }
    }

    public void saveClicked (){
        saveValue();
    }

    public void backClicked (){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SaveButton:
                saveClicked();
                break;
            case R.id.BackButton:
                backClicked();
                break;
            case R.id.PlusB:
                plusClicked();
                break;
            case R.id.MinusB:
                minusClicked();
                break;
            case R.id.PlusB1:
                plus1Clicked();
                break;
            case R.id.MinusB1:
                minus1Clicked();
                break;
            case R.id.PlusB2:
                plus2Clicked();
                break;
            case R.id.MinusB2:
                minus2Clicked();
                break;
            case R.id.PlusB3:
                plus3Clicked();
                break;
            case R.id.MinusB3:
                minus3Clicked();
                break;
            case R.id.PlusB4:
                plus4Clicked();
                break;
            case R.id.MinusB4:
                minus4Clicked();
                break;
        }
    }

    public void buttonDisable (Button button){
        button.setEnabled(false);
        button.setTextColor(getResources().getColor(R.color.colorDisabled));
    }

    public void buttonEnable (Button button){
        button.setEnabled(true);
        button.setTextColor(getResources().getColor(R.color.colorButtonText));
    }

    public void saveValue (){
        savePref = getSharedPreferences("CigarettePref",MODE_PRIVATE);
        editor = savePref.edit();
        saveflag = 1;
        editor.putInt(SAVE_NORM, norm);
        editor.putInt(SAVE_PRICEG, priceG);
        editor.putInt(SAVE_PRICEC, priceC);
        editor.putFloat(SAVE_NICOTINE, nicotine);
        editor.putInt(SAVE_TAR, tar);
        editor.putInt(SAVE_FLAG, saveflag);
        editor.apply();
    }

    public void loadValue () {
        savePref = getSharedPreferences("CigarettePref",MODE_PRIVATE);
        norm = savePref.getInt(SAVE_NORM,0);
        normPerDayValue.setText(String.valueOf(norm));
        priceG = savePref.getInt(SAVE_PRICEG,0);
        cigarettPriceValue.setText(String.valueOf(priceG));
        priceC = savePref.getInt(SAVE_PRICEC,0);
        cigarettPriceCentValue.setText(String.valueOf(priceC));
        nicotine = savePref.getFloat(SAVE_NICOTINE,0);
        nicotineValue.setText(String.valueOf(nicotine));
        tar = savePref.getInt(SAVE_TAR,0);
        tarValue.setText(String.valueOf(tar));
    }
}
