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
    private ReplyListViewAdapter adapter = new ReplyListViewAdapter();
    private boolean flag = true;
    private ListView replyListView;

    private String Qno = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak_view);
        replyListView = findViewById(R.id.replyList);

        View header = getLayoutInflater().inflate(R.layout.activity_speak_view_header, null, false);
        View footer = getLayoutInflater().inflate(R.layout.activity_speak_view_footer, null, false);
        replyListView.addHeaderView(header);
        replyListView.addFooterView(footer);
        speakTitle = findViewById(R.id.speakViewTitle);
        speakUser = findViewById(R.id.speakViewWho);
        speakTime = findViewById(R.id.speakViewTime);
        speakText = findViewById(R.id.speakViewText);
        speakLikeButton = (ImageButton) findViewById(R.id.speakLikeButton);
        speakLikeNum = (TextView) findViewById(R.id.speakViewLikeNum);

        adapter = new ReplyListViewAdapter();
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


        speakLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int like = Integer.parseInt(""+speakLikeNum.getText().toString());
                Log.i("myLike", String.valueOf(like));
                speakLikeNum.setText(String.valueOf(like+1));
                upLike();
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
            Qno = jsonObject.getString("Qno");
        } catch (Exception e) {
            Log.e("error", "parsing error");
        }

        replyView();

        replyText = (EditText) findViewById(R.id.newReply);
        replyConfirm =(ImageButton)findViewById(R.id.replyBtn);

        replyConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    JSONObject obj = new JSONObject();
                    JSONArray arr = new JSONArray();

                    obj.put("Type", "INSERT");
                    obj.put("Table", "mCOMMENT");
                    arr.put("Content");
                    obj.put("Col", arr);
                    arr = new JSONArray();
                    arr.put("'"+replyText.getText().toString()+"'");
                    obj.put("Value", arr);

                    arr = new JSONArray();
                    arr.put(obj);

                    obj = new JSONObject();
                    obj.put("query", arr);

                    ReplyCustomTask insertReply = new ReplyCustomTask();

                    insertReply.execute(obj.toString());

                    obj = new JSONObject();
                    arr = new JSONArray();

                    obj.put("Type", "CUSTOM");
                    obj.put("Query", "SELECT Cnumber FROM mCOMMENT WHERE Content='"+replyText.getText().toString()+"'");

                    arr = new JSONArray();
                    arr.put(obj);

                    obj = new JSONObject();
                    obj.put("query", arr);

                    ReplyCustomTask searchReply = new ReplyCustomTask();

                    String Cnum = searchReply.execute(obj.toString()).get();

                    obj = new JSONObject();
                    arr = new JSONArray();

                    obj.put("Type", "INSERT");
                    obj.put("Table", "mANSWER");
                    arr.put("Uid");
                    arr.put("Cno");
                    obj.put("Col", arr);
                    arr = new JSONArray();
                    arr.put("'"+StaticVariables.sLoginid+"'");
                    arr.put(Cnum.substring(8));
                    obj.put("Value", arr);

                    arr = new JSONArray();
                    arr.put(obj);

                    obj = new JSONObject();
                    obj.put("query", arr);

                    ReplyCustomTask insertAnswer = new ReplyCustomTask();

                    insertAnswer.execute(obj.toString());

                    obj = new JSONObject();
                    arr = new JSONArray();

                    obj.put("Type", "INSERT");
                    obj.put("Table", "mREPLY");
                    arr.put("Qno");
                    arr.put("Cno");
                    obj.put("Col", arr);
                    arr = new JSONArray();
                    arr.put(Qno);
                    arr.put(Cnum.substring(8));
                    obj.put("Value", arr);

                    arr = new JSONArray();
                    arr.put(obj);

                    obj = new JSONObject();
                    obj.put("query", arr);

                    ReplyCustomTask mergeReply = new ReplyCustomTask();

                    mergeReply.execute(obj.toString());



                } catch (Exception e) {

                }

            }
        });

    }

    private void replyView() {
        try {
            JSONObject obj = new JSONObject();
            JSONArray arr = new JSONArray();

            obj.put("Type", "CUSTOM");
            obj.put("Query", "SELECT * FROM Clist WHERE Qno=" + jsonObject.getString("Qno"));

            arr.put(obj);

            obj = new JSONObject();
            obj.put("query", arr);

            SpeakViewActivity.ReplyViewTask viewTextList = new SpeakViewActivity.ReplyViewTask();
            viewTextList.execute(obj.toString());
            Log.e("RESULT", obj.toString());
        } catch (JSONException e) {
            Log.i("게시판리스트 json", e.getStackTrace().toString());
        }
    }

    class ReplyViewTask extends AsyncTask<String, Void, JSONArray> {

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
            adapter.resetItem();
            for (int i = 0; i < arr.length(); i++) {
                try {
                    adapter.addItem(arr.getJSONObject(i));
                    Log.i("arr",arr.getJSONObject(i).toString() );
                } catch (Exception e) {
                    Log.e("Error", "erer");
                }
            }
            Log.i("notify","리프레시");
            adapter.notifyDataSetChanged();
        }
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
            replyText.setText("");
            replyView();
        }
    }

    public void upLike() {
        try {
            JSONObject obj = new JSONObject();
            JSONArray arr = new JSONArray();

            obj.put("Type", "UPDATE");
            obj.put("Table", "mQUESTION");
            arr.put("Good=Good+1");
            obj.put("Value", arr);
            arr = new JSONArray();
            arr.put("Qnumber=" + Qno);
            obj.put("Cond", arr);

            arr = new JSONArray();
            arr.put(obj);

            obj = new JSONObject();
            obj.put("query", arr);

            ReplyCustomTask upGood = new ReplyCustomTask();

            upGood.execute(obj.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}