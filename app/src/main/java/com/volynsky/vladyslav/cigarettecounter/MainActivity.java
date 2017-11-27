package com.volynsky.vladyslav.cigarettecounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;



public class MainActivity extends AppCompatActivity implements OnClickListener{

    int setupnorm, availableToday, smokedToday, flag = 0;
    int allSmoked, tarGained, alltar = 0;
    int daysSmoked = 1;
    float moneySpent, nicotineGained, allnicotine, price, percentNorm, averagePercent, grn, cent = 0;
    long baseTime, prevTime = 0;

    TextView availableForTodayValue;
    TextView smokedForTodayValue;

    Button smokeButton;
    Button resetButton;
    Button aboutButton;
    Button statisticsButton;
    Button setupButton;

    CheckBox newDayCheckBox;

    Chronometer lastSmokeTimeChro;

    SharedPreferences savePref;
    SharedPreferences.Editor editor;

    final String SAVE_TIME = "Previous timer time: ";
    final String SAVE_SMOKED = "Smoked count: ";
    final String SAVE_AVAILABLE = "Available count: ";
    final String SAVE_NORM = "Cigarett norm: ";
    final String SAVE_FLAG = "Setup is changed";
    final String SAVE_AllSMOKED = "The count of all the smoked cigarettes";
    final String SAVE_PRICEG = "Cigarett priceG: ";
    final String SAVE_PRICEC = "Cigarett priceC: ";
    final String SAVE_NICOTINE = "Cigarett nicotine: ";
    final String SAVE_TAR = "Cigarett tar: ";
    final String SAVE_DAYSSMOKED = "Days count";
    final String SAVE_MONEYSPENT = "Money spent on cigarettes";
    final String SAVE_PERCENT = "Percent of norm smoked";
    final String SAVE_ALLNICOTINE = "Nicotine amount";
    final String SAVE_ALLTAR = "Tar amount";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setDefaultScreen();
    }

    public void initializeViews() {
        availableForTodayValue = findViewById(R.id.AvailableForTodayValue);
        smokedForTodayValue = findViewById(R.id.SmokedForTodayValue);
        smokeButton = findViewById(R.id.SmokeButton);
        resetButton = findViewById(R.id.ResetButton);
        lastSmokeTimeChro = findViewById(R.id.LastSmokeTimeChro);
        aboutButton = findViewById(R.id.AboutButton);
        statisticsButton = findViewById(R.id.StatButton);
        newDayCheckBox = findViewById(R.id.NewDayCheckBox);
        setupButton = findViewById(R.id.SetupButton);
    }

    public void setDefaultScreen() {
        buttonDisable(smokeButton);
        buttonDisable(resetButton);
        smokeButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);
        aboutButton.setOnClickListener(this);
        statisticsButton.setOnClickListener(this);
        setupButton.setOnClickListener(this);
        loadValue();
        if (setupnorm != 0){
            setScreenAfterLoad();
        }
        loadTimer();
    }

    public void setScreenAfterLoad (){
        buttonEnable(smokeButton);
        buttonEnable(resetButton);
        if (flag == 1){
            availableToday = setupnorm;
            availableForTodayValue.setText(String.valueOf(availableToday));
            savePref = getSharedPreferences("CigarettePref",MODE_PRIVATE);
            editor = savePref.edit();
            flag = 0;
            editor.putInt(SAVE_FLAG, flag);
            editor.apply();
            colorCondit();
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

    public void smokeClicked (){
        availableToday--;
        smokedToday++;
        allSmoked++;
        alltar = alltar + tarGained;
        allnicotine = allnicotine + nicotineGained;
        moneySpent = moneySpent+price;
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
        availableToday = setupnorm;
        availableForTodayValue.setText(String.valueOf(availableToday));
        setColorNorm(availableForTodayValue);
        setColorNorm(smokedForTodayValue);
        if (newDayCheckBox.isChecked()){
            daysSmoked++;
            percents();
            smokedToday = 0;
        }
        smokedToday = 0;
        smokedForTodayValue.setText(String.valueOf(smokedToday));
    }

    public void statisticsClicked (){
        saveValue();
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

    public void setupClicked (){
        Intent intent = new Intent(this, SetupActivity.class);
        startActivity(intent);
    }

    public void onClick(View v){
            switch (v.getId()){
                case R.id.SmokeButton:
                    smokeClicked();
                    break;
                case R.id.ResetButton:
                    resetClicked();
                    break;
                case R.id.AboutButton:
                    aboutClicked();
                    break;
                case R.id.StatButton:
                    statisticsClicked();
                    break;
                case R.id.SetupButton:
                    setupClicked();
                    break;
            }
    }

    public void percents (){
        percentNorm = smokedToday*100/setupnorm;
        averagePercent = (averagePercent + percentNorm)/daysSmoked;
    }

    public void saveTimer (){
        savePref = getSharedPreferences("CigarettePref",MODE_PRIVATE);
        editor = savePref.edit();
        editor.putLong(SAVE_TIME, Calendar.getInstance().getTimeInMillis());
        editor.apply();
    }

    public void loadTimer (){
        savePref = getSharedPreferences("CigarettePref",MODE_PRIVATE);
        prevTime = savePref.getLong(SAVE_TIME, 0);
        if (prevTime != 0){
            baseTime = prevTime - Calendar.getInstance().getTimeInMillis() + SystemClock.elapsedRealtime();
            lastSmokeTimeChro.setBase(baseTime);
            lastSmokeTimeChro.start();
        }
    }

    public void saveValue (){
        savePref = getSharedPreferences("CigarettePref",MODE_PRIVATE);
        editor = savePref.edit();
        editor.putInt(SAVE_SMOKED, smokedToday);
        editor.putInt(SAVE_AVAILABLE, availableToday);
        editor.putInt(SAVE_AllSMOKED, allSmoked);
        editor.putFloat(SAVE_MONEYSPENT, moneySpent);
        editor.putFloat(SAVE_PERCENT, averagePercent);
        editor.putInt(SAVE_DAYSSMOKED, daysSmoked);
        editor.putFloat(SAVE_ALLNICOTINE, allnicotine);
        editor.putInt(SAVE_ALLTAR, alltar);
        editor.apply();
    }

    public void loadValue () {
        savePref = getSharedPreferences("CigarettePref",MODE_PRIVATE);
        setupnorm = savePref.getInt(SAVE_NORM,0);
        smokedToday = savePref.getInt(SAVE_SMOKED,0);
        availableToday = savePref.getInt(SAVE_AVAILABLE,0);
        availableForTodayValue.setText(String.valueOf(availableToday));
        smokedForTodayValue.setText(String.valueOf(smokedToday));
        flag = savePref.getInt(SAVE_FLAG,0);
        allSmoked = savePref.getInt(SAVE_AllSMOKED,0);
        grn = (float) savePref.getInt(SAVE_PRICEG,0);
        cent = (float) savePref.getInt(SAVE_PRICEC,0);
        price = (grn + (cent/100))/20;
        moneySpent = savePref.getFloat(SAVE_MONEYSPENT,0);
        averagePercent = savePref.getFloat(SAVE_PERCENT,0);
        daysSmoked = savePref.getInt(SAVE_DAYSSMOKED,0);
        nicotineGained = savePref.getFloat(SAVE_NICOTINE,0);
        tarGained = savePref.getInt(SAVE_TAR,0);
        allnicotine = savePref.getFloat(SAVE_ALLNICOTINE,0);
        alltar = savePref.getInt(SAVE_ALLTAR,0);
        colorCondit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveValue();
    }

}

