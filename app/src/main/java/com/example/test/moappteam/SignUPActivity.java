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
import android.widget.Spinner;
import android.widget.Toast;

import com.example.test.moappteam.DBpkg.DBClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Iterator;

public class SignUPActivity extends AppCompatActivity {

    Toolbar mToolbar = null;
    private String[] interest;
    private String[] major;
    Spinner spinner;
    Spinner spinner1;

    EditText id;
    EditText password;
    EditText passwordC;
    EditText nickName;
    EditText sid;
    EditText sName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mToolbar = (Toolbar) findViewById(R.id.signUpToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        spinner = (Spinner) findViewById(R.id.favorSpinner);
        spinner1 = (Spinner) findViewById(R.id.sDepSpinner);

        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        passwordC = (EditText) findViewById(R.id.confirmPassword);
        nickName = (EditText) findViewById(R.id.nickName);
        sid = (EditText) findViewById(R.id.sId);
        sName = (EditText) findViewById(R.id.name);


        interest = getResources().getStringArray(R.array.favor);
        major = getResources().getStringArray(R.array.major);


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
                        Log.i("MainActivity.spinner", "선택 없음");
                    }
                }
        );

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.major,
                //R.layout.spinner_interest);
                android.R.layout.simple_spinner_dropdown_item);
        //android.R.layout.simple_spinner_item );

        spinner1.setAdapter(adapter1);

        spinner1.setOnItemSelectedListener(
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
    }


    public void signUpClicked(View view) {


        if (!checkPw()) {
            Toast.makeText(SignUPActivity.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
        } else {
            //서버로 보내는 작업
            //통신부분

            try {
                JSONObject signUpInfo = new JSONObject();
                signUpInfo.put("id", id.getText().toString());
                signUpInfo.put("password", password.getText().toString());
                signUpInfo.put("nickName", nickName.getText().toString());
                signUpInfo.put("sid", sid.getText().toString());
                signUpInfo.put("sName", sName.getText().toString());

                JSONObject obj = new JSONObject();
                JSONArray arr = new JSONArray();

                obj.put("Type", "INSERT");
                obj.put("Table", "mUSER");
                arr.put("Id");
                arr.put("Pw");
                arr.put("Sid");
                arr.put("Name");
                arr.put("Nick");
                obj.put("Col", arr);
                arr = new JSONArray();
                arr.put("'"+id.getText().toString()+"'");
                arr.put("'"+password.getText().toString()+"'");
                arr.put("'"+sid.getText().toString()+"'");
                arr.put("'"+sName.getText().toString()+"'");
                arr.put("'"+nickName.getText().toString()+"'");
                obj.put("Value", arr);

                arr = new JSONArray();
                arr.put(obj);

                obj = new JSONObject();
                obj.put("query", arr);


                CustomTask test = new CustomTask();
                test.execute(obj.toString());


            } catch (JSONException e) {
                Log.e("Error JSON", e.getStackTrace().toString());
            }

        }

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



    public boolean checkPw() {

        if (!passwordC.getText().toString().equals(password.getText().toString())) {

            return false;

        } else {

            return true;

        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }





}
