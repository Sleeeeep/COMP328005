package com.example.test.moappteam;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpeakFragment extends Fragment {

    public SpeakFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_speak, container, false);
        ListView speakListView = view.findViewById(R.id.speakListView);
        SpeakListViewAdapter adapter = new SpeakListViewAdapter();
        speakListView.setAdapter(adapter);

        // example
        adapter.addItem("title", "user", "12:00:00", "일반", 1, 1);
        adapter.addItem("title2", "user2", "12:00:56", "특수", 0, 3);

        speakListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                Intent intent = new Intent(getActivity(), SpeakViewActivity.class);
                startActivity(intent);
                String item = (String) listView.getItemAtPosition(position);
            }
        });

        return view;
    }
}
