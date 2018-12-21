package com.example.test.moappteam;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.test.moappteam.DBpkg.DBClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    LinearLayout interest1;
    LinearLayout interest2;
    LinearLayout hot1;
    LinearLayout hot2;
    LinearLayout new1;
    LinearLayout new2;

    String result;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main,container,false);


        interest1 = (LinearLayout)view.findViewById(R.id.scrollInLay).findViewById(R.id.interestQ1);
        interest2 = (LinearLayout)view.findViewById(R.id.scrollInLay).findViewById(R.id.interestQ2);
        hot1 = (LinearLayout)view.findViewById(R.id.scrollInLay).findViewById(R.id.hotQ1);
        hot2 = (LinearLayout)view.findViewById(R.id.scrollInLay).findViewById(R.id.hotQ2);
        new1 = (LinearLayout)view.findViewById(R.id.scrollInLay).findViewById(R.id.newQ1);
        new2 = (LinearLayout)view.findViewById(R.id.scrollInLay).findViewById(R.id.newQ2);

        setListener();

        connectDb();

        setView();

        return view;

    }

    public void connectDb(){




    }

    public void setListener(){

        interest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SpeakViewActivity.class);
                startActivity(intent1);
            }
        });

        interest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SpeakViewActivity.class);
                startActivity(intent1);
            }
        });

        hot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SpeakViewActivity.class);
                startActivity(intent1);
            }
        });

        hot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SpeakViewActivity.class);
                startActivity(intent1);
            }
        });

        new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SpeakViewActivity.class);
                startActivity(intent1);
            }
        });

        new2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SpeakViewActivity.class);
                startActivity(intent1);
            }
        });
    }

    public void setView(){
        //화면을 구성하는 정보를 설정하는 함수
        try{

            JSONObject object = new JSONObject(result);

        }catch (Exception e){

        }
    }


    class MainFCustomTask extends AsyncTask<String, Void, String> {

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

            if(s.equals("1")){
                Toast.makeText(getActivity(),"회원가입에 성공했습니다",Toast.LENGTH_SHORT).show();
                result = s;
                setView();
            }
        }
    }


}
