package anchovy.net.clock.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import anchovy.net.clock.R;
import anchovy.net.clock.adapter.AlarmRecyclerViewAdapter;

/**
 * Created by DarKnight98 on 5/12/2017.
 */

public class AlarmFragment extends Fragment {

    public AlarmFragment() {
        //REQUIRED
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_alarm, container, false);

        RecyclerView rv = (RecyclerView) rootView.findViewById(R.id.alarm_list_recycler_view);
        rv.setHasFixedSize(true);

//        ArrayList<HashMap<String,String>> dataSet = null;
//
//        try {
//            FileInputStream fileInputStream  = new FileInputStream("list.item.alarm");
//            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//
//            dataSet = (ArrayList) objectInputStream.readObject();
//            objectInputStream.close();
//        } catch (ClassNotFoundException | IOException e) {
//            e.printStackTrace();
//        }
//
//        if (dataSet == null) {
//            AlarmRecyclerViewAdapter adapter = new AlarmRecyclerViewAdapter(new ArrayList<HashMap<String, String>>());
//            rv.setAdapter(adapter);
//        } else {
//            AlarmRecyclerViewAdapter adapter = new AlarmRecyclerViewAdapter(dataSet);
//            rv.setAdapter(adapter);
//        }

        ArrayList<HashMap<String,String>> dataSet = new ArrayList<>();
        HashMap<String,String> a = new HashMap<>();
        a.put("title", "TITLE");
        a.put("hour", "15");
        a.put("minute", "8");
        a.put("status", "on");
        HashMap<String,String> b = new HashMap<>();
        b.put("title", "TITLE2");
        b.put("hour", "5");
        b.put("minute", "25");
        b.put("status", "off");
        dataSet.add(a);
        dataSet.add(b);

        AlarmRecyclerViewAdapter adapter = new AlarmRecyclerViewAdapter(dataSet);
        rv.setAdapter(adapter);


        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        return rootView;
    }
}
