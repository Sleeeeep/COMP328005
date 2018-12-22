package com.example.test.moappteam;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test.moappteam.DBpkg.DBClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpeakFragment extends Fragment {
    private ListView speakListView;
    private SpeakListViewAdapter adapter = new SpeakListViewAdapter();
    private Spinner catSpinner;
    private Fragment frag = this;
    private boolean flag = true;

    public SpeakFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_speak, container, false);
        speakListView = view.findViewById(R.id.speakListView);
        speakListView.setAdapter(adapter);
        catSpinner = view.findViewById(R.id.speakFavorSpinner);
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

        if(flag) {
            try {
                adapter.resetItem();
                JSONObject obj = new JSONObject();
                JSONArray arr = new JSONArray();

                obj.put("Type", "CUSTOM");
                obj.put("Query", "SELECT * FROM Qlist");

                arr.put(obj);

                obj = new JSONObject();
                obj.put("query", arr);

                SpeakFragment.CustomTask viewTextList = new SpeakFragment.CustomTask();
                viewTextList.execute(obj.toString());
                Log.e("RESULT", obj.toString());

            } catch (JSONException e) {
                Log.i("게시판리스트 json", e.getStackTrace().toString());
            }
        } else {
            flag = true;
            Log.i("refresh", "refreshFragment");
        }

        // example
        //adapter.addItem("title", "user", "12:00:00", "일반", 1, 1);
        //adapter.addItem("title2", "user2", "12:00:56", "특수", 0, 3);

        speakListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject mainText = new JSONObject();
                ListView listView = (ListView) parent;
                Intent intent = new Intent(getActivity(), SpeakViewActivity.class);
                intent.putExtra("MAIN_TEXT", mainText.toString());
                startActivity(intent);
                String item = (String) listView.getItemAtPosition(position);
            }
        });

        return view;
    }

    class CustomTask extends AsyncTask<String, Void, JSONArray> {

        JSONObject json = null;

        DBClass mDB;

        @Override
        protected JSONArray doInBackground(String... strings) {
            JSONArray jArr = new JSONArray();
            try {
                mDB = new DBClass(StaticVariables.ipAddress);
                mDB.setURL();

                Log.i("url", strings[0]);
                if (mDB.writeURL(strings[0]) != HttpURLConnection.HTTP_OK)
                    Log.i("DB", "url connection error");
                else {
                    if (strings[0].contains("SELECT") || strings[0].contains("CUSTOM")) {
                        json = mDB.getData();

                        jArr = json.getJSONArray("response");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jArr;
        }

        @Override
        protected void onPostExecute(JSONArray arr) {
            Log.i("RESULT", arr.toString());
            super.onPostExecute(arr);
            for(int i = arr.length() - 1; i >= 0; i--) {
                try {
                    adapter.addItem(arr.getJSONObject(i));
                } catch (Exception e) {
                    Log.e("Error", "erer");
                }
            }
            flag = false;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(frag).attach(frag).commit();
        }
    }
}
