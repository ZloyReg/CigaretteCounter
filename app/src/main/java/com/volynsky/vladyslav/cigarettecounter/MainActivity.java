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

public class MainActivity extends AppCompatActivity {

    int norm, availableToday, smokedToday = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDefaultScreen();
    }

    public void setDefaultScreen (){
        final TextView normPerDayValue = (TextView) findViewById(R.id.NormPerDayValue);
        final TextView availableForTodayText = (TextView) findViewById(R.id.AvailableForTodayText);
        final TextView smokedForTodayText = (TextView) findViewById(R.id.SmokedForTodayText);
        final Button plusB = (Button) findViewById(R.id.PlusB);
        final Button minusB = (Button) findViewById(R.id.MinusB);
        final Button freezeB = (Button) findViewById(R.id.FreezeButton);
        final Button unfreezeB = (Button) findViewById(R.id.UnFreezeButton);
        final Button smokeButton = (Button) findViewById(R.id.SmokeButton);
        final Button resetButton = (Button) findViewById(R.id.ResetButton);
        final Chronometer lastSmokeTimeChro = (Chronometer) findViewById(R.id.LastSmokeTimeChro);
        minusB.setEnabled(false);
        smokeButton.setEnabled(false);
        freezeB.setEnabled(false);
        unfreezeB.setEnabled(false);
        resetButton.setEnabled(false);

        OnClickListener plusClick = new OnClickListener(){
            public void onClick(View v) {
                    norm++;
                    normPerDayValue.setText(Integer.toString(norm));
                    minusB.setEnabled(true);
                    freezeB.setEnabled(true);
                if (norm == 40) {
                    plusB.setEnabled(false);
                }
            }
        };

        OnClickListener minusClick = new OnClickListener(){
            public void onClick(View v) {
                norm--;
                normPerDayValue.setText(Integer.toString(norm));
                plusB.setEnabled(true);
                if (norm < 1){
                    minusB.setEnabled(false);
                    freezeB.setEnabled(false);
                    smokeButton.setEnabled(false);
                }
            }
        };
        OnClickListener freezeClick = new OnClickListener(){
            public void onClick(View v) {
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
            }
        };
        OnClickListener unfreezeClick = new OnClickListener(){
            public void onClick(View v) {
                minusB.setEnabled(true);
                plusB.setEnabled(true);
                freezeB.setEnabled(true);
                unfreezeB.setVisibility(View.INVISIBLE);
                unfreezeB.setEnabled(false);
            }
        };
        OnClickListener smokeClick = new OnClickListener(){
            public void onClick(View v) {
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
            }
        };
        OnClickListener resetClick = new OnClickListener(){
            public void onClick(View v) {
                availableToday = norm;
                smokedToday = 0;
                availableForTodayText.setText("Можно выкурить за сегодня: "+Integer.toString(availableToday));
                smokedForTodayText.setText("Выкуренно за сегодня: "+Integer.toString(smokedToday));
                availableForTodayText.setTextColor(Color.BLACK);
            }
        };
        plusB.setOnClickListener(plusClick);
        minusB.setOnClickListener(minusClick);
        freezeB.setOnClickListener(freezeClick);
        unfreezeB.setOnClickListener(unfreezeClick);
        smokeButton.setOnClickListener(smokeClick);
        resetButton.setOnClickListener(resetClick);

    }
}
