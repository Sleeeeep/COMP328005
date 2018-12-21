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

        setView();

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

        //메인프레그먼트 세팅

        Log.i("MainFResult",StaticVariables.mainResult);

        try{

            JSONArray jArr = new JSONArray(StaticVariables.mainResult);

            interId1.setText(""+jArr.getJSONObject(0).toString());
            interId2.setText(""+jArr.getJSONObject(1).getString("Uid"));
            interTime1.setText(""+jArr.getJSONObject(0).getString("Qtime"));
            interTime2.setText(""+jArr.getJSONObject(1).getString("Qtime"));
            interTitle1.setText(""+jArr.getJSONObject(0).getString("Title"));
            interTitle2.setText(""+jArr.getJSONObject(1).getString("Title"));
            interCla1.setText(""+jArr.getJSONObject(0).getString("Cname"));
            interCla2.setText(""+jArr.getJSONObject(1).getString("Cname"));

            hotId1.setText(""+jArr.getJSONObject(2).getString("Uid"));
            hotId2.setText(""+jArr.getJSONObject(3).getString("Uid"));
            hotTime1.setText(""+jArr.getJSONObject(2).getString("Qtime"));
            hotTime2.setText(""+jArr.getJSONObject(3).getString("Qtime"));
            hotTitle1.setText(""+jArr.getJSONObject(2).getString("Title"));
            hotTitle2.setText(""+jArr.getJSONObject(3).getString("Title"));
            hotCla1.setText(""+jArr.getJSONObject(2).getString("Cname"));
            hotCla2.setText(""+jArr.getJSONObject(3).getString("Cname"));

            newId1.setText(""+jArr.getJSONObject(4).getString("Uid"));
            newId2.setText(""+jArr.getJSONObject(5).getString("Uid"));
            newTime1.setText(""+jArr.getJSONObject(4).getString("Qtime"));
            newTime2.setText(""+jArr.getJSONObject(5).getString("Qtime"));
            newTitle1.setText(""+jArr.getJSONObject(4).getString("Title"));
            newTitle2.setText(""+jArr.getJSONObject(5).getString("Title"));
            newCla1.setText(""+jArr.getJSONObject(4).getString("Cname"));
            newCla2.setText(""+jArr.getJSONObject(5).getString("Cname"));

        }catch (Exception e){
            Log.e("setView Error","setView Error");
        }

    }

}
