package com.example.test.moappteam;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;


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
        Spinner catSpinner = view.findViewById(R.id.speakFavorSpinner);
        String[] interest = getResources().getStringArray(R.array.favor);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.favor,
                //R.layout.spinner_interest);
                android.R.layout.simple_spinner_dropdown_item);
        //android.R.layout.simple_spinner_item );
        catSpinner.setAdapter(arrayAdapter);

        catSpinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("MainActivity.spinner", "pos = " + position + ", id = " + id);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.i("MainActivity.spinner", "선택 없음");
                    }
                }
        );


        ImageButton btn = view.findViewById(R.id.writeButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), WriteActivity.class);
                startActivity(intent);
            }
        });



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
