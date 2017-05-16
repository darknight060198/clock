package anchovy.net.clock.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anchovy.net.clock.AddAlarmActivity;
import anchovy.net.clock.R;
import anchovy.net.clock.adapter.AlarmRecyclerViewAdapter;

/**
 * Created by DarKnight98 on 5/12/2017.
 */

public class AlarmFragment extends Fragment {

    private View view;
    private static final String ALARM_TAG = "AlarmItem";

    public AlarmFragment() {
        //REQUIRED
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_alarm, container, false);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.alarm_list_recycler_view);
        rv.setHasFixedSize(true);

//        ArrayList<HashMap<String,String>> dataSet = new ArrayList<>();
//        HashMap<String,String> a = new HashMap<>();
//        a.put("title", "TITLE");
//        a.put("hour", "15");
//        a.put("minute", "8");
//        a.put("status", "on");
//        a.put("description", "this is description");
//        HashMap<String,String> b = new HashMap<>();
//        b.put("title", "TITLE2");
//        b.put("hour", "5");
//        b.put("minute", "25");
//        b.put("status", "off");
//        b.put("description", "this is description this is description this is description this is description this is description this is description ");
//        dataSet.add(a);
//        dataSet.add(b);
//
//        setDataFromSharedPreferences(dataSet);

        final ArrayList<HashMap<String,String>> dataSet = getDataFromSharedPreferences();

        if (dataSet == null) {
            AlarmRecyclerViewAdapter adapter = new AlarmRecyclerViewAdapter(new ArrayList<HashMap<String, String>>(), new AlarmRecyclerViewAdapter.ClickCallback() {
                @Override
                public void onItemClick(int position) {

                }
            });
            rv.setAdapter(adapter);
        } else {
            AlarmRecyclerViewAdapter adapter = new AlarmRecyclerViewAdapter(dataSet, new AlarmRecyclerViewAdapter.ClickCallback() {
                @Override
                public void onItemClick(int position) {
                    AddAlarmActivity addAlarmActivity = new AddAlarmActivity();
                    addAlarmActivity.startAlarm(getActivity(), dataSet.get(position).get("hour"), dataSet.get(position).get("minute"));
                }
            });
            rv.setAdapter(adapter);
        }

//        ArrayList<HashMap<String,String>> dataSet = new ArrayList<>();
//        HashMap<String,String> a = new HashMap<>();
//        a.put("title", "TITLE");
//        a.put("hour", "15");
//        a.put("minute", "8");
//        a.put("status", "on");
//        HashMap<String,String> b = new HashMap<>();
//        b.put("title", "TITLE2");
//        b.put("hour", "5");
//        b.put("minute", "25");
//        b.put("status", "off");
//        dataSet.add(a);
//        dataSet.add(b);
//
//        AlarmRecyclerViewAdapter adapter = new AlarmRecyclerViewAdapter(dataSet);
//        rv.setAdapter(adapter);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return view;
    }

    @Override
    public void onStart() {
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.alarm_list_recycler_view);
        rv.setHasFixedSize(true);

        final ArrayList<HashMap<String,String>> dataSet = getDataFromSharedPreferences();

        if (dataSet == null) {
            AlarmRecyclerViewAdapter adapter = new AlarmRecyclerViewAdapter(new ArrayList<HashMap<String, String>>(), new AlarmRecyclerViewAdapter.ClickCallback() {
                @Override
                public void onItemClick(int position) {

                }
            });
            rv.setAdapter(adapter);
        } else {
            AlarmRecyclerViewAdapter adapter = new AlarmRecyclerViewAdapter(dataSet, new AlarmRecyclerViewAdapter.ClickCallback() {
                @Override
                public void onItemClick(int position) {
                    AddAlarmActivity addAlarmActivity = new AddAlarmActivity();
                    addAlarmActivity.startAlarm(getActivity(), dataSet.get(position).get("hour"), dataSet.get(position).get("minute"));
                }
            });
            rv.setAdapter(adapter);
        }

        super.onStart();
    }
//
//    /* Checks if external storage is available for read and write */
//    public boolean isExternalStorageWritable() {
//        String state = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(state)) {
//            return true;
//        }
//        return false;
//    }

    private void setDataFromSharedPreferences(ArrayList<HashMap<String,String>> dataSet){
        Gson gson = new Gson();
        String jsonCurProduct = gson.toJson(dataSet);

        SharedPreferences sharedPref = getActivity().getSharedPreferences(ALARM_TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(ALARM_TAG, jsonCurProduct);
        editor.apply();
    }

    private ArrayList<HashMap<String,String>> getDataFromSharedPreferences(){
        Gson gson = new Gson();
        ArrayList<HashMap<String,String>> productFromShared;
        SharedPreferences sharedPref = getActivity().getSharedPreferences(ALARM_TAG, Context.MODE_PRIVATE);
        String jsonPreferences = sharedPref.getString(ALARM_TAG, "");

        Type type = new TypeToken<ArrayList<HashMap<String,String>>>() {}.getType();
        productFromShared = gson.fromJson(jsonPreferences, type);

        return productFromShared;
    }

}
