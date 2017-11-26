package com.volynsky.vladyslav.cigarettecounter;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;



public class MainActivity extends AppCompatActivity implements OnClickListener{

    int norm, availableToday, smokedToday = 0;
    long baseTime, prevTime = 0;
    TextView normPerDayValue;
    TextView availableForTodayValue;
    TextView smokedForTodayValue;
    Button plusB;
    Button minusB;
    Button freezeB;
    Button unfreezeB;
    Button smokeButton;
    Button resetButton;
    Button aboutButton;
    Chronometer lastSmokeTimeChro;
    SharedPreferences savePref;
    SharedPreferences.Editor editor;

    final String SAVE_TIME = "Previous timer time: ";
    final String SAVE_NORM = "Cigarett norm: ";
    final String SAVE_SMOKED = "Smoked count: ";
    final String SAVE_AVAILABLE = "Available count: ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setDefaultScreen();
    }

    public void initializeViews() {
        normPerDayValue = findViewById(R.id.NormPerDayValue);
        availableForTodayValue = findViewById(R.id.AvailableForTodayValue);
        smokedForTodayValue = findViewById(R.id.SmokedForTodayValue);
        plusB = findViewById(R.id.PlusB);
        minusB = findViewById(R.id.MinusB);
        freezeB = findViewById(R.id.FreezeButton);
        unfreezeB = findViewById(R.id.UnFreezeButton);
        smokeButton = findViewById(R.id.SmokeButton);
        resetButton = findViewById(R.id.ResetButton);
        lastSmokeTimeChro = findViewById(R.id.LastSmokeTimeChro);
        aboutButton = findViewById(R.id.AboutButton);
    }

    public void setDefaultScreen() {
        buttonDisable(minusB);
        buttonDisable(smokeButton);
        buttonDisable(freezeB);
        unfreezeB.setEnabled(false);
        unfreezeB.setVisibility(View.INVISIBLE);
        buttonDisable(resetButton);
        plusB.setOnClickListener(this);
        minusB.setOnClickListener(this);
        freezeB.setOnClickListener(this);
        unfreezeB.setOnClickListener(this);
        smokeButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
        loadValue();
        if (norm != 0){
            setScreenAfterLoad();
        }
        loadTimer();
    }

    public void setScreenAfterLoad (){
        buttonDisable(minusB);
        buttonDisable(plusB);
        freezeB.setVisibility(View.INVISIBLE);
        freezeB.setEnabled(false);
        buttonEnable(unfreezeB);
        unfreezeB.setVisibility(View.VISIBLE);
        buttonEnable(smokeButton);
        buttonEnable(resetButton);

    }

    public void buttonDisable (Button button){
        button.setEnabled(false);
        button.setTextColor(getResources().getColor(R.color.colorDisabled));
    }

    public void minusButtonEnable (Button button){
        button.setEnabled(true);
        button.setTextColor(getResources().getColor(R.color.colorAvailableNorm));
    }

    public void plusButtonEnable (Button button){
        button.setEnabled(true);
        button.setTextColor(getResources().getColor(R.color.colorAvailableBad));
    }

    public void buttonEnable (Button button){
        button.setEnabled(true);
        button.setTextColor(getResources().getColor(R.color.colorButtonText));
    }

    public void setColorNorm (TextView textView){
        textView.setTextColor(getResources().getColor(R.color.colorAvailableNorm));
    }

    public void setColorBad (TextView textView){
        textView.setTextColor(getResources().getColor(R.color.colorAvailableBad));
    }

    public void colorCondit (){
        if (availableToday < 0){
            setColorBad(availableForTodayValue);
            setColorBad(smokedForTodayValue);
        }
        else{
            setColorNorm(availableForTodayValue);
            setColorNorm(smokedForTodayValue);
        }
    }

    public void plusClicked (){
        norm++;
        normPerDayValue.setText(String.valueOf(norm));
        minusButtonEnable(minusB);
        buttonEnable(freezeB);
        if (norm == 40) {
            buttonDisable(plusB);
        }
    }

    public void minusClicked (){
        norm--;
        normPerDayValue.setText(String.valueOf(norm));
        plusButtonEnable(plusB);
        if (norm < 1) {
            buttonDisable(minusB);
            buttonDisable(freezeB);
            buttonDisable(smokeButton);
        }
    }

    public void freezeClicked (){
        buttonEnable(smokeButton);
        buttonEnable(resetButton);
        availableToday = norm;
        availableForTodayValue.setText(String.valueOf(availableToday));
        smokedForTodayValue.setText(String.valueOf(smokedToday));
        buttonDisable(minusB);
        buttonDisable(plusB);
        freezeB.setEnabled(false);
        buttonEnable(unfreezeB);
        unfreezeB.setVisibility(View.VISIBLE);
        setColorNorm(availableForTodayValue);
        setColorNorm(smokedForTodayValue);
        freezeB.setVisibility(View.INVISIBLE);
        freezeB.setEnabled(false);
    }

    public void unfreezeClicked (){
        minusButtonEnable(minusB);
        plusButtonEnable(plusB);
        buttonEnable(freezeB);
        freezeB.setVisibility(View.VISIBLE);
        unfreezeB.setVisibility(View.INVISIBLE);
        unfreezeB.setEnabled(false);
    }

    public void smokeClicked (){
        availableToday--;
        smokedToday++;
        availableForTodayValue.setText(String.valueOf(availableToday));
        smokedForTodayValue.setText(String.valueOf(smokedToday));
        colorCondit();
        lastSmokeTimeChro.setBase(SystemClock.elapsedRealtime());
        lastSmokeTimeChro.start();
        saveTimer();
    }

    public void aboutClicked () {
        Toast.makeText(this, R.string.aboutText, Toast.LENGTH_LONG).show();
    }

    public void resetClicked (){
        availableToday = norm;
        smokedToday = 0;
        availableForTodayValue.setText(String.valueOf(availableToday));
        smokedForTodayValue.setText(String.valueOf(smokedToday));
        setColorNorm(availableForTodayValue);
        setColorNorm(smokedForTodayValue);
    }

    public void onClick(View v){
            switch (v.getId()){
                case R.id.PlusB:
                    plusClicked ();
                    break;
                case R.id.MinusB:
                    minusClicked ();
                    break;
                case R.id.FreezeButton:
                    freezeClicked();
                    break;
                case R.id.UnFreezeButton:
                    unfreezeClicked();
                    break;
                case R.id.SmokeButton:
                    smokeClicked();
                    break;
                case R.id.ResetButton:
                    resetClicked();
                    break;
                case R.id.AboutButton:
                    aboutClicked();
                    break;
            }
    }
    public void saveTimer (){
        savePref = getPreferences(MODE_PRIVATE);
        editor = savePref.edit();
        editor.putLong(SAVE_TIME, Calendar.getInstance().getTimeInMillis());
        editor.apply();
    }

    public void loadTimer (){
        savePref = getPreferences(MODE_PRIVATE);
        prevTime = savePref.getLong(SAVE_TIME, 0);
        if (prevTime != 0){
            baseTime = prevTime - Calendar.getInstance().getTimeInMillis() + SystemClock.elapsedRealtime();
            lastSmokeTimeChro.setBase(baseTime);
            lastSmokeTimeChro.start();
        }
    }

    public void saveValue (){
        savePref = getPreferences(MODE_PRIVATE);
        editor = savePref.edit();
        editor.putInt(SAVE_NORM, norm);
        editor.putInt(SAVE_SMOKED, smokedToday);
        editor.putInt(SAVE_AVAILABLE, availableToday);
        editor.apply();
    }

    public void loadValue () {
        savePref = getPreferences(MODE_PRIVATE);
        norm = savePref.getInt(SAVE_NORM,0);
        smokedToday = savePref.getInt(SAVE_SMOKED,0);
        availableToday = savePref.getInt(SAVE_AVAILABLE,0);
        normPerDayValue.setText(String.valueOf(norm));
        availableForTodayValue.setText(String.valueOf(availableToday));
        smokedForTodayValue.setText(String.valueOf(smokedToday));
        colorCondit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveValue();
    }

}

