package com.example.test.moappteam;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.moappteam.DBpkg.DBClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;


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

    TextView interId1;
    TextView interId2 ;

    TextView interTime1;
    TextView interTime2;

    TextView interTitle1;
    TextView interTitle2;

    TextView interCla1;
    TextView interCla2;

    TextView hotId1;
    TextView hotId2;

    TextView hotTime1;
    TextView hotTime2;

    TextView hotTitle1;
    TextView hotTitle2;

    TextView hotCla1;
    TextView hotCla2;

    TextView newId1;
    TextView newId2;

    TextView newTime1;
    TextView newTime2;

    TextView newTitle1;
    TextView newTitle2;

    TextView newCla1;
    TextView newCla2;

    Boolean Flag = true;

    String result;

    private Fragment frag = this;

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

        if(StaticVariables.sLoginid==null){

            Toast.makeText(getActivity(),"로그인이 필요합니다",Toast.LENGTH_LONG).show();

        }

        setView();

        connectDb();

        return view;

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

        interId1 =  (TextView) interest1.findViewById(R.id.interQuestionId);
        interId2 =  (TextView) interest1.findViewById(R.id.interQuestionId1);

        interTime1 =  (TextView) interest1.findViewById(R.id.interQuestionTime);
        interTime2 =  (TextView) interest1.findViewById(R.id.interQuestionTime1);

        interTitle1 =  (TextView) interest1.findViewById(R.id.interQuestionTitle);
        interTitle2 =  (TextView) interest1.findViewById(R.id.interQuestionTitle1);

        interCla1 =  (TextView) interest1.findViewById(R.id.interQuestionInter);
        interCla2 =  (TextView) interest1.findViewById(R.id.interQuestionInter1);

        hotId1 =  (TextView) interest1.findViewById(R.id.hotQuestionId);
        hotId2 =  (TextView) interest1.findViewById(R.id.hotQuestionId1);

        hotTime1 =  (TextView) interest1.findViewById(R.id.hotQuestionTime);
        hotTime2 =  (TextView) interest1.findViewById(R.id.hotQuestionTime1);

        hotTitle1 =  (TextView) interest1.findViewById(R.id.hotQuestionTitle);
        hotTitle2 =  (TextView) interest1.findViewById(R.id.hotQuestionTitle1);

        hotCla1 =  (TextView) interest1.findViewById(R.id.hotQuestionInterest);
        hotCla2 =  (TextView) interest1.findViewById(R.id.hotQuestionInterest1);

        newId1 =  (TextView) interest1.findViewById(R.id.newQuestionId);
        newId2 =  (TextView) interest1.findViewById(R.id.newQuestionId1);

        newTime1 =  (TextView) interest1.findViewById(R.id.newQuestionTime);
        newTime2 =  (TextView) interest1.findViewById(R.id.newQuestionTime1);

        newTitle1 =  (TextView) interest1.findViewById(R.id.newQuestionTitle);
        newTitle2 =  (TextView) interest1.findViewById(R.id.newQuestionTitle1);

        newCla1 =  (TextView) interest1.findViewById(R.id.newQuestionInterest);
        newCla2 =  (TextView) interest1.findViewById(R.id.newQuestionInterest1);

        //interId1.setText("");

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
                            Log.i("mainResult",jArr.toString());
                            data += jArr.toString();
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

            Flag = false;

            StaticVariables.mainResult = s;

            Log.i("mainResult",StaticVariables.mainResult);

            try {

                JSONArray jArr = new JSONArray(StaticVariables.mainResult);

                interId1.setText(""+ jArr.getJSONObject(1).getString("Qtime"));
                jArr.getJSONObject(1).getString("Title");
                jArr.getJSONObject(1).getString("Good");

            } catch (Exception e) {

            }

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(frag).attach(frag).commit();

        }
    }

    public void connectDb(){

        if(StaticVariables.sLoginid==null){

        }else{

            if(Flag) {

                try {

                    JSONObject obj = new JSONObject();
                    JSONArray arr = new JSONArray();

                    obj.put("Type", "CUSTOM");

                    obj.put("Query", "SELECT Uid, Qnumber, Title, Good, Cname, Qtime " +
                            "FROM Favor_Qlist WHERE Id='" + StaticVariables.sLoginid + "'");

                    arr = new JSONArray();
                    arr.put(obj);

                    obj = new JSONObject();
                    obj.put("query", arr);

                    MainFCustomTask mainTest = new MainFCustomTask();

                    mainTest.execute(obj.toString());

                    Log.e("RESULT", obj.toString());


                } catch (Exception e) {

                }

                Flag = true;
            }
        }
    }


}
