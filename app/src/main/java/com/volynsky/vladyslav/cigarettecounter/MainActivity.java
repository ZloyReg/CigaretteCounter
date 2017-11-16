package com.volynsky.vladyslav.cigarettecounter;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Chronometer;
import android.widget.TextView;

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
    }

    public void setDefaultScreen() {
        minusB.setEnabled(false);
        smokeButton.setEnabled(false);
        freezeB.setEnabled(false);
        unfreezeB.setEnabled(false);
        resetButton.setEnabled(false);
        plusB.setOnClickListener(this);
        minusB.setOnClickListener(this);
        freezeB.setOnClickListener(this);
        unfreezeB.setOnClickListener(this);
        smokeButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    public void onClick(View v){
            switch (v.getId()){
                case R.id.PlusB:
                    norm++;
                    normPerDayValue.setText(Integer.toString(norm));
                    minusB.setEnabled(true);
                    freezeB.setEnabled(true);
                    if (norm == 40) {
                        plusB.setEnabled(false);
                    }
                    break;
                case R.id.MinusB:
                norm--;
                normPerDayValue.setText(Integer.toString(norm));
                plusB.setEnabled(true);
                if (norm < 1){
                    minusB.setEnabled(false);
                    freezeB.setEnabled(false);
                    smokeButton.setEnabled(false);
                }
                    break;
                case R.id.FreezeButton:
                    smokeButton.setEnabled(true);
                    resetButton.setEnabled(true);
                    availableToday = norm;
                    availableForTodayText.setText("Можно выкурить за сегодня: "+Integer.toString(availableToday));
                    smokedForTodayText.setText("Выкуренно за сегодня: "+Integer.toString(smokedToday));
                    minusB.setEnabled(false);
                    plusB.setEnabled(false);
                    freezeB.setEnabled(false);
                    unfreezeB.setVisibility(View.VISIBLE);
                    unfreezeB.setEnabled(true);
                    availableForTodayText.setTextColor(Color.BLACK);
                    freezeB.setVisibility(View.INVISIBLE);
                    freezeB.setEnabled(false);
                    break;
                case R.id.UnFreezeButton:
                    minusB.setEnabled(true);
                    plusB.setEnabled(true);
                    freezeB.setEnabled(true);
                    freezeB.setVisibility(View.VISIBLE);
                    freezeB.setEnabled(true);
                    unfreezeB.setVisibility(View.INVISIBLE);
                    unfreezeB.setEnabled(false);
                    break;
                case R.id.SmokeButton:
                    lastSmokeTimeChro.setBase(SystemClock.elapsedRealtime());
                    availableToday--;
                    smokedToday++;
                    availableForTodayText.setText("Можно выкурить за сегодня: "+Integer.toString(availableToday));
                    smokedForTodayText.setText("Выкуренно за сегодня: "+Integer.toString(smokedToday));
                    if (availableToday < 0){
                        availableForTodayText.setTextColor(Color.RED);
                    }
                    else{
                        availableForTodayText.setTextColor(Color.BLACK);
                    }
                    lastSmokeTimeChro.start();
                    break;
                case R.id.ResetButton:
                    availableToday = norm;
                    smokedToday = 0;
                    availableForTodayText.setText("Можно выкурить за сегодня: "+Integer.toString(availableToday));
                    smokedForTodayText.setText("Выкуренно за сегодня: "+Integer.toString(smokedToday));
                    availableForTodayText.setTextColor(Color.BLACK);
                    break;
            }
    }
}

