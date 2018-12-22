package com.example.test.moappteam;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.moappteam.DBpkg.DBClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Iterator;

public class SpeakViewActivity extends AppCompatActivity {
    private JSONObject jsonObject;
    private EditText replyText;
    private ImageButton replyConfirm;
    private TextView speakTitle;
    private TextView speakUser;
    private TextView speakTime;
    private TextView speakText;
    private ImageButton speakLikeButton;
    private TextView speakLikeNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak_view);

        ListView replyListView = findViewById(R.id.replyList);
        View header = getLayoutInflater().inflate(R.layout.activity_speak_view_header, null, false);
        View footer = getLayoutInflater().inflate(R.layout.activity_speak_view_footer, null, false);
        replyListView.addHeaderView(header);
        replyListView.addFooterView(footer);
        speakTitle = findViewById(R.id.speakViewTitle);
        speakUser = findViewById(R.id.speakViewWho);
        speakTime = findViewById(R.id.speakViewTime);
        speakText = findViewById(R.id.speakViewText);
        speakLikeButton = findViewById(R.id.speakLikeButton);
        speakLikeNum = findViewById(R.id.speakViewLikeNum);

        ReplyListViewAdapter adapter = new ReplyListViewAdapter();
        replyListView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar)findViewById(R.id.speakToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            jsonObject = new JSONObject(getIntent().getStringExtra("JSON_OBJ"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            speakTitle.setText(jsonObject.getString("Title"));
            speakText.setText(jsonObject.getString("Content"));
            speakUser.setText(jsonObject.getString("Uid"));
            speakTime.setText(jsonObject.getString("Qtime"));
            speakLikeNum.setText(jsonObject.getString("Good"));
        }catch (Exception e) {
            Log.e("error", "parsing error");
        }

        // adapter.addItem("sdf", "user", "12:00:00", 23);

        replyText = (EditText) findViewById(R.id.newReply);
        replyConfirm =(ImageButton)findViewById(R.id.replyBtn);

        replyConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    JSONObject obj = new JSONObject();
                    JSONArray arr = new JSONArray();

                    obj.put("Type", "CUSTOM");

                    obj.put("Query", "INSERT INTO mCOMMENT('Content') VALUES ('"+replyText.getText().toString() +"')");

                    arr = new JSONArray();
                    arr.put(obj);

                    obj = new JSONObject();
                    obj.put("query", arr);

                    ReplyCustomTask replyTest = new ReplyCustomTask();

                    replyTest.execute(obj.toString());

                    Log.e("REPLY_RESULT", obj.toString());

                } catch (Exception e) {

                }

            }
        });

    }


    class ReplyCustomTask extends AsyncTask<String, Void, String> {

        JSONObject json = null;
        String data = "";

        DBClass mDB;

        @Override
        protected String doInBackground(String... strings) {
            try {
                mDB = new DBClass(StaticVariables.ipAddress);
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
            Log.i("REPLY_RESULT",s);
        }
    }


}