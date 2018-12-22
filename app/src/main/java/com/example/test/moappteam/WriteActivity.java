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

        //완료 버튼 클릭시 서버연동 작업 수행
        confirmBtn = (ImageButton)findViewById(R.id.confirmBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWrite();
                //finish();
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

    public void startWrite(){

        wTitle = (EditText)findViewById(R.id.writeTitle);
        wTxt = (EditText)findViewById(R.id.inputTxt);
        Log.i("스피너 값",spinner.getSelectedItem().toString());
        int cNum = 0;

        String cName = spinner.getSelectedItem().toString();


        if (cName.equals("알고리즘")){
            cNum = 1;
        }else if (cName.equals("자료구조")){
            cNum = 2;
        }else if (cName.equals("네트워크")){
            cNum = 3;
        }else if (cName.equals("컴퓨터구조")){
            cNum = 4;
        }else if (cName.equals("자바")){
            cNum = 5;
        }else if (cName.equals("운영체제")){
            cNum = 6;
        }else if (cName.equals("시스템")){
            cNum = 7;
        }else if (cName.equals("데이터베이스")){
            cNum = 8;
        }else if (cName.equals("인공지능")){
            cNum = 9;
        }else if (cName.equals("빅데이터")){
            cNum = 10;
        }else if (cName.equals("웹")){
            cNum = 11;
        }else if (cName.equals("보안")){
            cNum = 12;
        }else if (cName.equals("로봇")){
            cNum = 13;
        }else if (cName.equals("소프트웨어설계")){
            cNum = 14;
        }else if (cName.equals("미디어아트")){
            cNum = 15;
        }else if (cName.equals("병렬컴퓨팅")){
            cNum = 16;
        }else if (cName.equals("모바일앱프로그래밍")){
            cNum = 17;
        }



        try {

            JSONObject obj = new JSONObject();
            JSONArray arr = new JSONArray();

            obj.put("Type", "CUSTOM");

            obj.put("Query", "INSERT INTO mQUESTION('Title','Cname','Content') " +
                    "VALUES ('"+wTitle +"',"+cNum+",'"+wTxt+"')");

            arr = new JSONArray();
            arr.put(obj);

            obj = new JSONObject();
            obj.put("query", arr);

            WriteActivity.WriteCustomTask mainTest = new WriteActivity.WriteCustomTask();

            mainTest.execute(obj.toString());

            Log.e("RESULT", obj.toString());

        } catch (Exception e) {

        }


    }

    class WriteCustomTask extends AsyncTask<String, Void, String> {

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
            Log.i("WRITE_RESULT",s);
        }
    }

}
