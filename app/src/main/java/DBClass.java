package com.example.parkj.dbtest;

import org.json.*;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    final String dbURL = "http://10.0.2.2:8080/mDB/JsonTest.jsp?";
    HttpURLConnection urlConnection = null;
    String msg = null;

    EditText editText;
    TextView textView;

    private String[] getJsonData = {"", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        textView = (TextView) findViewById(R.id.textView);
    }

    class CustomTask extends AsyncTask<String, Void, String[]> {
        @Override
        protected String[] doInBackground(String... strings) {
            try {
                URL url = new URL(dbURL);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlConnection.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;charset=UTF-8");

                OutputStreamWriter osw = new OutputStreamWriter(urlConnection.getOutputStream());

                osw.write(msg);
                osw.flush();

                if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK)
                    Log.i("DB", "url connection error");
                else {
                    BufferedReader bufreader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));

                    String line = null;
                    String page = "";

                    while ((line = bufreader.readLine()) != null) {
                        page += line;
                    }
                    String data = "";

                    if (strings[0].equals("SELECT")) {
                        try {
                            JSONObject json = new JSONObject(page);
                            JSONArray jArr = json.getJSONArray("response");

                            for (int i = 0; i < jArr.length(); i++) {
                                data += "\n";
                                json = jArr.getJSONObject(i);
                                Iterator<?> iter = json.keys();
                                while(iter.hasNext())
                                {
                                    String temp = iter.next().toString();

                                    data += temp + " " + json.getString(temp) + "\n";
                                }
                            }
                        } catch (JSONException e) {
                            data = page;
                        }
                    } else {
                        data = page;
                    }

                    textView.setText(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            String[] temp = {"asd", "123"};
            return temp;
        }
    }

    public void onClickButton(View v) {
        try {
            String[] result = null;
            CustomTask task = new CustomTask();

            switch (v.getId()) {
                case R.id.buttonSelect:
                    msg = "{\"query\":[{\"Type\": \"SELECT\", \"Table\" : \"mUSER\", \"Col\" : [\"Id\", \"Pw\", \"Name\"]}]}";
//                    msg = "{\"query\":[{\"Type\": \"SELECT\", \"Table\" : \"mUSER\", \"Cond\" : [\"Id='test2'\"]}]}";
                    result = task.execute("SELECT", "").get();
                    break;
                case R.id.buttonDelete:
                    msg = "{\"query\":[{\"Type\": \"DELETE\", \"Table\" : \"mUSER\", \"Cond\" : [\"Id='test1'\"]}]}";
                    result = task.execute("DELETE", "").get();
                    break;
                case R.id.buttonInsert:
                    msg = "{\"query\":[{\"Type\": \"INSERT\", \"Table\" : \"mUSER\", \"Col\" : [\"Id\", \"Pw\", \"Sid\", \"Name\", \"Nick\"], \"Value\" : [\"'test1'\", \"'1234'\", \"'1234512345'\", \"'test1'\", \"'test1'\"]}]}";
                    result = task.execute("INSERT", "").get();
                    break;
                case R.id.buttonUpdate:
                    msg = "{\"query\":[{\"Type\": \"UPDATE\", \"Table\" : \"mUSER\", \"Value\" : [\"Pw='asdasd'\"], \"Cond\" : [\"Id='test2'\"]}]}";
                    result = task.execute("UPDATE", "").get();
                    break;
            }

            for (int i = 0; i < result.length; i++)
                Log.i("clickResult", result[i]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
