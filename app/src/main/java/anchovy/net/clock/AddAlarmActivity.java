package anchovy.net.clock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import anchovy.net.clock.other.AlarmReceiver;
import anchovy.net.clock.other.TimePickerDialog;

public class AddAlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private String time, hour, minute;;
    private TextView timeText;
    private EditText title, description;
    private static AlarmManager alarmManager;
    private static PendingIntent pendingIntent;
    private Calendar calendar;
    private static final String ALARM_TAG = "AlarmItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //initialize alarm manager
        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        //create an instance of calendar
        calendar = Calendar.getInstance();

        time = "";
        hour = "";
        minute = "";

        timeText = (TextView)findViewById(R.id.show_time_alarm);
        LinearLayout pickTime = (LinearLayout)findViewById(R.id.select_time_alarm);
        pickTime.setOnClickListener(this);

        Button save = (Button)findViewById(R.id.add_alarm_save_button);
        save.setOnClickListener(this);
        Button cancel = (Button)findViewById(R.id.add_alarm_cancel_button);
        cancel.setOnClickListener(this);

        title = (EditText)findViewById(R.id.add_alarm_title_input);
        description = (EditText)findViewById(R.id.add_alarm_description_input);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.select_time_alarm :
                TimePickerDialog dialog = new TimePickerDialog();
                dialog.showDialog(this);
                break;
            case R.id.add_alarm_cancel_button :
                onBackPressed();
                break;
            case R.id.add_alarm_save_button :
                System.out.println("pressed");
                addAlarm();
                startAlarm();
                onBackPressed();
                break;
        }
    }

    public void updateText () {
        SharedPreferences sp = getSharedPreferences("timeAlarm", Context.MODE_PRIVATE);
        String hour = sp.getString("hour", "");
        String minute = sp.getString("minute", "");
        this.hour = hour;
        this.minute = minute;
        if (!hour.isEmpty() && !minute.isEmpty()) {
            if (Integer.parseInt(minute) < 10) {
                minute = "0" + minute;
            }
            time = hour + " : " + minute;
        }
        timeText.setText(time);
    }

    public void startAlarm() {
        SharedPreferences sp = getSharedPreferences("timeAlarm", Context.MODE_PRIVATE);
        String hour = sp.getString("hour", "");
        String minute = sp.getString("minute", "");
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
        Intent newIntent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 1234, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void startAlarm(Activity activity, String hour, String minute) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
        Intent newIntent = new Intent(activity, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(activity, 1234, newIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager = (AlarmManager)activity.getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public static void stopAlarm() {
        alarmManager.cancel(pendingIntent);
        Log.e("DELETE", "ALARM");
    }

    private void addAlarm () {
        ArrayList<HashMap<String,String>> dataSet;

        dataSet = getDataFromSharedPreferences();

        if (dataSet == null) {
            dataSet = new ArrayList<>();
        }

        HashMap<String,String> a = new HashMap<>();
        a.put("title", title.getText().toString());
        a.put("hour", hour);
        a.put("minute", minute);
        a.put("status", "on");
        a.put("description", description.getText().toString());
        dataSet.add(a);

        setDataFromSharedPreferences(dataSet);
    }

    private void setDataFromSharedPreferences(ArrayList<HashMap<String,String>> dataSet){
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(dataSet);

        SharedPreferences sharedPref = getSharedPreferences(ALARM_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(ALARM_TAG, jsonCurProduct);
        editor.apply();
    }

    private ArrayList<HashMap<String,String>> getDataFromSharedPreferences(){
        Gson gson = new Gson();
        ArrayList<HashMap<String,String>> productFromShared;
        SharedPreferences sharedPref = getSharedPreferences(ALARM_TAG, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(ALARM_TAG, "");

        Type type = new TypeToken<ArrayList<HashMap<String,String>>>() {}.getType();
        productFromShared = gson.fromJson(jsonPreferences, type);

        return productFromShared;
    }
}
