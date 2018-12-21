package com.example.test.moappteam;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.test.moappteam.DBpkg.DBClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Iterator;

public class LogInActivity extends AppCompatActivity {

    Button loginBtn;
    Button signBtn;
    EditText inputId;
    EditText inputPw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        loginBtn = (Button)findViewById(R.id.LogInButton);
        signBtn = (Button)findViewById(R.id.signUpButton);
        inputId = (EditText)findViewById(R.id.inputID);
        inputPw = (EditText)findViewById(R.id.inputPW);

    }

    public void doSignUp(View view){

        Intent intent = new Intent(LogInActivity.this,SignUPActivity.class);
        startActivity(intent);

    }

    public void doLogin(View view){

        //로그인 시도

        if(!inputId.getText().toString().equals("") && !inputPw.getText().toString().equals("")){

            try{


                JSONObject obj = new JSONObject();
                JSONArray arr = new JSONArray();

                obj.put("Type", "LOGIN");
                arr.put("Id='"+inputId.getText().toString()+"'");
                arr.put("Pw='"+inputPw.getText().toString()+"'");
                obj.put("Cond", arr);

                arr = new JSONArray();
                arr.put(obj);

                obj = new JSONObject();
                obj.put("query", arr);

                LoginCustomTask loginTest = new LoginCustomTask();
                loginTest.execute(obj.toString());
                Log.e("RESULT",obj.toString());


            }catch (JSONException e){

                Log.i("로그인 json",e.getStackTrace().toString());
            }

        }else {

            Toast.makeText(LogInActivity.this,"아이디와 비밀번호를 확인해 주세요",Toast.LENGTH_SHORT).show();
        }


    }


    class LoginCustomTask extends AsyncTask<String, Void, String> {

        JSONObject json = null;
        String data = "";

        DBClass mDB;

        @Override
        protected String doInBackground(String... strings) {
            try {
                mDB = new DBClass("http://155.230.84.89:8080/mDB/JsonTest.jsp?");
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

            if(s.equals(inputId.getText().toString())){

                Toast.makeText(LogInActivity.this,"로그인에 성공했습니다",Toast.LENGTH_SHORT).show();

                finish();

            }else {

                Toast.makeText(LogInActivity.this,"로그인에 실패했습니다",Toast.LENGTH_SHORT).show();

            }

        }
    }




}
