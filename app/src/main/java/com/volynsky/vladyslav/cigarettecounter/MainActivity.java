package com.volynsky.vladyslav.cigarettecounter;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    int norm, availableToday, smokedToday = 0;
    TextView normPerDayValue;
    TextView availableForTodayText;
    TextView smokedForTodayText;
    Button plusB;
    Button minusB;
    Button freezeB;
    Button unfreezeB;
    Button smokeButton;
    Button resetButton;
    Button aboutButton;
    Chronometer lastSmokeTimeChro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setDefaultScreen();
    }

    public void initializeViews() {
        normPerDayValue = findViewById(R.id.NormPerDayValue);
        availableForTodayText = findViewById(R.id.AvailableForTodayText);
        smokedForTodayText = findViewById(R.id.SmokedForTodayText);
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
        minusB.setEnabled(false);
        minusB.setTextColor(getResources().getColor(R.color.colorDisabled));
        smokeButton.setEnabled(false);
        smokeButton.setTextColor(getResources().getColor(R.color.colorDisabled));
        freezeB.setEnabled(false);
        freezeB.setTextColor(getResources().getColor(R.color.colorDisabled));
        unfreezeB.setEnabled(false);
        unfreezeB.setVisibility(View.INVISIBLE);
        resetButton.setEnabled(false);
        resetButton.setTextColor(getResources().getColor(R.color.colorDisabled));
        plusB.setOnClickListener(this);
        minusB.setOnClickListener(this);
        freezeB.setOnClickListener(this);
        unfreezeB.setOnClickListener(this);
        smokeButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
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
        availableForTodayText.setText(R.string.availableTodayText);
        availableForTodayText.append(String.valueOf(availableToday));
        smokedForTodayText.setText(R.string.smokedTodayText);
        smokedForTodayText.append(String.valueOf(smokedToday));
        buttonDisable(minusB);
        buttonDisable(plusB);
        freezeB.setEnabled(false);
        buttonEnable(unfreezeB);
        unfreezeB.setVisibility(View.VISIBLE);
        availableForTodayText.setTextColor(getResources().getColor(R.color.colorAvailableNorm));
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
        lastSmokeTimeChro.setBase(SystemClock.elapsedRealtime());
        availableToday--;
        smokedToday++;
        availableForTodayText.setText(R.string.availableTodayText);
        availableForTodayText.append(String.valueOf(availableToday));
        smokedForTodayText.setText(R.string.smokedTodayText);
        smokedForTodayText.append(String.valueOf(smokedToday));
        if (availableToday < 0){
            availableForTodayText.setTextColor(getResources().getColor(R.color.colorAvailableBad));
        }
        else{
            availableForTodayText.setTextColor(getResources().getColor(R.color.colorAvailableNorm));
        }
        lastSmokeTimeChro.start();
    }

    public void aboutClicked () {
        Toast.makeText(this, R.string.aboutText, Toast.LENGTH_LONG).show();
    }

    public void resetClicked (){
        availableToday = norm;
        smokedToday = 0;
        availableForTodayText.setText(R.string.availableTodayText);
        availableForTodayText.append(String.valueOf(availableToday));
        smokedForTodayText.setText(R.string.smokedTodayText);
        smokedForTodayText.append(String.valueOf(smokedToday));
        availableForTodayText.setTextColor(getResources().getColor(R.color.colorAvailableNorm));
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
}

