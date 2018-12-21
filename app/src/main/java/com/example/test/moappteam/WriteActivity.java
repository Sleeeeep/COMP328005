package com.example.test.moappteam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.test.moappteam.DBpkg.DBClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Iterator;

public class WriteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ImageButton confirmBtn;

    private EditText wTitle;
    private EditText wTxt;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);


        toolbar = (Toolbar)findViewById(R.id.writeToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("글쓰기");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_arrow);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirmBtn = (ImageButton)findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        spinner = (Spinner) findViewById(R.id.writeSpinner);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.favor,
                //R.layout.spinner_interest);
                android.R.layout.simple_spinner_dropdown_item);
        //android.R.layout.simple_spinner_item );

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("MainActivity.spinner", "pos = " + position + ", id = " + id);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.i("WriteActivity.spinner", "선택 없음");
                    }
                }
        );


    }


    public void startWite(){

        wTitle = (EditText)findViewById(R.id.writeTitle);
        wTxt = (EditText)findViewById(R.id.inputTxt);


    }



    class CustomTask extends AsyncTask<String, Void, String> {

        JSONObject json = null;
        String data = "";

        DBClass mDB;

        @Override
        protected String doInBackground(String... strings) {
            try {
                mDB = new DBClass("http://155.230.84.186:8080/mDB/JsonTest.jsp?");
                mDB.setURL();

                if (mDB.writeURL(strings[0]) != HttpURLConnection.HTTP_OK)
                    Log.i("DB", "url connection error");
                else {
                    if (strings[0].contains("SELECT") || strings[0].contains("CUSTOM")) {
                        json = mDB.getData();

                        JSONArray jArr = json.getJSONArray("response");

                        for (int i = 0; i < jArr.length(); i++) {
                            data += "\n";
                            json = jArr.getJSONObject(i);
                            Iterator<?> iter = json.keys();
                            while (iter.hasNext()) {
                                String temp = iter.next().toString();
                                data += temp + " " + json.getString(temp) + "\n";
                            }
                        }
                    } else
                        data = mDB.getData().getString("response");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("RESULT",s);
        }
    }

}
