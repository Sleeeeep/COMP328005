package com.example.test.moappteam;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.moappteam.DBpkg.DBClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.Iterator;


/**
 * A simple {@link Fragment} subclass.
 */
public class MypageFragment extends Fragment {

    private TextView userName;
    private TextView userDept;
    private TextView userRank;
    private TextView userAsk;     // 답글
    private TextView userComment;   // 댓글

    public MypageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        userName = view.findViewById(R.id.myPage_userName);
        userDept = view.findViewById(R.id.myPage_userDept);
        userRank = view.findViewById(R.id.myPage_userRank);
        userAsk = view.findViewById(R.id.myPage_userAsk);
        userComment = view.findViewById(R.id.myPage_userComment);

        try{
            MypageCustomTask task = new MypageCustomTask();
            JSONArray arr = new JSONArray();
            JSONObject obj = new JSONObject();

            obj.put("Type", "SELECT");
            obj.put("Table", "Mypage");
            arr.put("Name");
        //    arr.put("Dname");
          //  arr.put("Rank");        // 등수
            arr.put("Qcnt");      // 질문글 갯수
            arr.put("Ccnt");      // 댓글 갯수
            obj.put("Col", arr);
            arr = new JSONArray();
            arr.put("Id='test1'");
            obj.put("Cond", arr);

            arr = new JSONArray();
            arr.put(obj);

            obj = new JSONObject();
            obj.put("query", arr);

           task.execute(obj.toString());

        }catch(JSONException e){
            Log.i("myPage json", e.getStackTrace().toString());
        }

        return view;
    }

    class MypageCustomTask extends AsyncTask<String, Void, String>
    {
        JSONObject json = null;
        String strName = "";
        String strDept = "";
        String strRank = "";
        String strQcnt = "";
        String strCcnt = "";

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
                            json = jArr.getJSONObject(i);

                            strName  = json.getString("Name") + "님";
                         //   strDept = json.getString("Dname");
                           // strRank = json.getString("Rank") + " 위";
                            strQcnt = json.getString("Qcnt") + " 개";
                            strCcnt = json.getString("Ccnt") + " 개";
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return strName;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            userName.setText(strName);
         //   userDept.setText(strDept);
          //  userRank.setText(strRank);
            userAsk.setText(strQcnt);
            userComment.setText(strCcnt);
        }
    }
}
