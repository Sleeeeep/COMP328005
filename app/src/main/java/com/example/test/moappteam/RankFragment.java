package com.example.test.moappteam;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import java.util.ArrayList;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment {

    private TextView myRank;
    private TextView myId;
    private TextView myComment;
    private TextView myRate;

    private String myRankText;
    private String myIdText;
    private String myCommentText;
    private String myRateText;

    JSONObject obj = new JSONObject();
    JSONArray arr = new JSONArray();

    private ListView rankListView;
    private RankListViewAdapter adapter = new RankListViewAdapter();
    private boolean flag = true;
    private Fragment frag = this;

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

        rankListView = view.findViewById(R.id.rankListView);
        rankListView.setAdapter(adapter);

        if(flag)
            connectDb();
        else {
            myRank.setText(myRankText + " 등");
            myId.setText(myIdText);
            myComment.setText(myCommentText);
            myRate.setText(myRateText);
            flag = true;
        }

        //for문 사용해서 붙이기

        //adapter.addItem(1,"user", 1, 1);



        //내 순위 정보 업데이트 및 전체적인 디비 정보 받아오기

        return view;
    }

    public void connectDb(){
        //이미 정보가 있는 경우 서버와 연동하지 않음

        if(StaticVariables.sRankInfo==null){

            try{
                adapter.resetItem();
                obj.put("Type", "CUSTOM");
                obj.put("Query", "SELECT * FROM Ranking");

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


    class RankCustomTask extends AsyncTask<String, Void, JSONArray> {

        JSONObject json = null;
        String data = "";

        DBClass mDB;

        @Override
        protected JSONArray doInBackground(String... strings) {
            JSONArray jArr = new JSONArray();
            try {
                mDB = new DBClass(StaticVariables.ipAddress);
                mDB.setURL();

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
            boolean breakFlag = false;
            super.onPostExecute(arr);
            Log.i("RESULT",arr.toString());

            for(int i = 0; i < arr.length(); i++) {
                if(i < 10) {
                    try {
                        adapter.addItem(arr.getJSONObject(i), i + 1);
                    } catch (Exception e) {
                        Log.e("Error", "어댑터에러");
                    }
                }
                try {
                    if (arr.getJSONObject(i).getString("Id").equals(StaticVariables.sLoginid)) {
                        myRankText = Integer.toString(i + 1);
                        myIdText = StaticVariables.sLoginid;
                        myCommentText = arr.getJSONObject(i).getString("Cnt");
                        if(myCommentText.equals("null"))
                            myCommentText = "0";
                        myRateText = arr.getJSONObject(i).getString("Good");
                        if(myRateText.equals("null"))
                            myRateText = "0";
                    }
                }catch (Exception e) {

                }
                if(i >= 10 && breakFlag)
                    break;
            }
            flag = false;
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(frag).attach(frag).commit();
        }
    }

}
