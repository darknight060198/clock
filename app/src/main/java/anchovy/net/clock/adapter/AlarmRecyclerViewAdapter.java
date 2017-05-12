package anchovy.net.clock.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import anchovy.net.clock.R;

/**
 * Created by DarKnight98 on 5/12/2017.
 */

public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.AlarmViewHolder> {

    private ArrayList<HashMap<String, String>> dataSet;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class AlarmViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView title, time;
        ImageButton status;

        AlarmViewHolder(View v) {
            super(v);

            this.view = v;
            title = (TextView) v.findViewById(R.id.alarm_title);
            time = (TextView) v.findViewById(R.id.alarm_time);
            status = (ImageButton)v.findViewById(R.id.alarm_status_button);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public AlarmRecyclerViewAdapter(ArrayList<HashMap<String, String >> dataSet) {
        this.dataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public AlarmRecyclerViewAdapter.AlarmViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.alarm_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        AlarmViewHolder vh = new AlarmViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {
        holder.title.setText(dataSet.get(position).get("title"));
        int hour = Integer.parseInt(dataSet.get(position).get("hour"));
        int min = Integer.parseInt(dataSet.get(position).get("minute"));
        String time = "";
        if (hour > 12) {
            time += (hour-12) + "";
        } else {
            time += hour + "";
        }

        time += " : ";

        if (min < 10) {
            time += "0" + min;
        } else {
            time += min + "";
        }

        if (hour > 12) {
            time += " PM";
        } else {
            time += " AM";
        }

        holder.time.setText(time);

        if (dataSet.get(position).get("status").equals("on")) {
            holder.status.setImageResource(R.drawable.ic_alarm_on_black_24dp);
            holder.status.setBackgroundResource(R.drawable.alarm_status_backgrond_on);
        } else {
            holder.status.setImageResource(R.drawable.ic_alarm_off_black_24dp);
            holder.status.setBackgroundResource(R.drawable.alarm_status_backgrond_off);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
