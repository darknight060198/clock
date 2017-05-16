package anchovy.net.clock.other;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TimePicker;

import anchovy.net.clock.AddAlarmActivity;
import anchovy.net.clock.R;


/**
 * Created by DarKnight98 on 4/24/2017.
 */

public class TimePickerDialog {


    public void showDialog(final AddAlarmActivity activity){
        final SharedPreferences sp = activity.getSharedPreferences("timeAlarm", Context.MODE_PRIVATE);
        String hour = sp.getString("hour", "");
        String minute = sp.getString("minute", "");

        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.time_picker_alarm);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final TimePicker timePicker = (TimePicker)dialog.findViewById(R.id.alarm_timePicker);
        if (hour.isEmpty()) {
            timePicker.setCurrentHour(0);
        } else {
            timePicker.setCurrentHour(Integer.parseInt(hour));
        }

        if (minute.isEmpty()) {
            timePicker.setCurrentMinute(0);
        } else {
            timePicker.setCurrentMinute(Integer.parseInt(minute));
        }

        Button dialogBtn_cancel = (Button) dialog.findViewById(R.id.save_button);
        dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("hour", Integer.toString(timePicker.getCurrentHour()));
                edit.putString("minute", Integer.toString(timePicker.getCurrentMinute()));
                edit.apply();
                activity.updateText();
                dialog.dismiss();
            }
        });

        Button dialogBtn_okay = (Button) dialog.findViewById(R.id.cancel_button);
        dialogBtn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
