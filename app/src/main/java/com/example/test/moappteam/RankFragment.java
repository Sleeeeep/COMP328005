package com.example.test.moappteam;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.moappteam.DBpkg.DBClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment {

    private TextView myRank;
    private TextView myId;
    private TextView myComment;
    private TextView myRate;

    JSONObject obj = new JSONObject();
    JSONArray arr = new JSONArray();


    public RankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_rank, container, false);

        myRank = view.findViewById(R.id.rank);
        myId = view.findViewById(R.id.rankUser);
        myComment = view.findViewById(R.id.rankCom);
        myRate = view.findViewById(R.id.rankRate);


        ListView rankListView = view.findViewById(R.id.rankListView);
        RankListViewAdapter adapter = new RankListViewAdapter();
        rankListView.setAdapter(adapter);

        connectDb();

        //for문 사용해서 붙이기

        adapter.addItem(1,"user", 1, 1);



        //내 순위 정보 업데이트 및 전체적인 디비 정보 받아오기

        return view;

    }

    public void connectDb(){
        //이미 정보가 있는 경우 서버와 연동하지 않음

        if(StaticVariables.sRankInfo==null){

            try{

                obj.put("Type", "LOGIN");
                //쿼리문에 맞게 전달 해 줘야함
                //arr.put("Id='"+inputId.getText().toString()+"'");
                //arr.put("Pw='"+inputPw.getText().toString()+"'");
                obj.put("Cond", arr);

                arr = new JSONArray();
                arr.put(obj);

                obj = new JSONObject();
                obj.put("query", arr);

                RankCustomTask RankTest = new RankCustomTask();
                RankTest.execute(obj.toString());
                Log.e("RESULT",obj.toString());

            }catch (JSONException e){

                Log.i("로그인 json",e.getStackTrace().toString());
            }

        }

    }


    class RankCustomTask extends AsyncTask<String, Void, String> {

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

            

        }
    }

}
