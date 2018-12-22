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
import org.json.JSONException;
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
    TextView interId2;
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
    TextView interNum1;
    TextView interNum2;
    TextView hotNum1;
    TextView hotNum2;
    TextView newNum1;
    TextView newNum2;
    TextView interRate1;
    TextView interRate2;
    TextView hotRate1;
    TextView hotRate2;
    TextView newRate1;
    TextView newRate2;


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

                String result = StaticVariables.mainResult;
                JSONObject jsonObject = null;
                try{

                    JSONArray jsonArray = new JSONArray(result);
                    jsonObject = jsonArray.getJSONObject(0);

                }catch (JSONException e){

                }

                Intent intent = new Intent(getActivity(), SpeakViewActivity.class);
                intent.putExtra("MAIN_TEXT",jsonObject.toString());
                startActivity(intent);
            }
        });

        interest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = StaticVariables.mainResult;
                JSONObject jsonObject = null;
                try{

                    JSONArray jsonArray = new JSONArray(result);
                    jsonObject = jsonArray.getJSONObject(1);

                }catch (JSONException e){

                }

                Intent intent = new Intent(getActivity(), SpeakViewActivity.class);
                intent.putExtra("MAIN_TEXT", jsonObject.toString());
                startActivity(intent);
            }
        });

        hot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = StaticVariables.mainResult;
                JSONObject jsonObject = null;
                try{

                    JSONArray jsonArray = new JSONArray(result);
                    jsonObject = jsonArray.getJSONObject(2);

                }catch (JSONException e){

                }

                Intent intent = new Intent(getActivity(), SpeakViewActivity.class);
                intent.putExtra("MAIN_TEXT",jsonObject.toString());
                startActivity(intent);
            }
        });

        hot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = StaticVariables.mainResult;
                JSONObject jsonObject = null;
                try{

                    JSONArray jsonArray = new JSONArray(result);
                    jsonObject = jsonArray.getJSONObject(3);

                }catch (JSONException e){

                }

                Intent intent = new Intent(getActivity(), SpeakViewActivity.class);
                intent.putExtra("MAIN_TEXT", jsonObject.toString());
                startActivity(intent);
            }
        });

        new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = StaticVariables.mainResult;
                JSONObject jsonObject = null;

                try{

                    JSONArray jsonArray = new JSONArray(result);
                    String subString = result.substring(jsonArray.toString().length());
                    jsonArray = new JSONArray(subString);
                    jsonObject = jsonArray.getJSONObject(0);


                }catch (JSONException e){

                }

                Intent intent = new Intent(getActivity(), SpeakViewActivity.class);
                intent.putExtra("MAIN_TEXT", jsonObject.toString());
                startActivity(intent);
            }
        });

        new2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String result = StaticVariables.mainResult;
                JSONObject jsonObject = null;

                try{

                    JSONArray jsonArray = new JSONArray(result);
                    String subString = result.substring(jsonArray.toString().length());
                    jsonArray = new JSONArray(subString);
                    jsonObject = jsonArray.getJSONObject(1);

                }catch (JSONException e){

                }

                Intent intent = new Intent(getActivity(), SpeakViewActivity.class);
                intent.putExtra("MAIN_TEXT", jsonObject.toString());
                startActivity(intent);
            }
        });
    }

    public void setView(){
        //화면을 구성하는 정보를 설정하는 함수

        interId1 =  (TextView) interest1.findViewById(R.id.interQuestionId);
        interId2 =  (TextView) interest2.findViewById(R.id.interQuestionId1);

        interTime1 =  (TextView) interest1.findViewById(R.id.interQuestionTime);
        interTime2 =  (TextView) interest2.findViewById(R.id.interQuestionTime1);

        interTitle1 =  (TextView) interest1.findViewById(R.id.interQuestionTitle);
        interTitle2 =  (TextView) interest2.findViewById(R.id.interQuestionTitle1);

        interCla1 =  (TextView) interest1.findViewById(R.id.interQuestionInter);
        interCla2 =  (TextView) interest2.findViewById(R.id.interQuestionInter1);

        hotId1 =  (TextView) hot1.findViewById(R.id.hotQuestionId);
        hotId2 =  (TextView) hot2.findViewById(R.id.hotQuestionId1);

        hotTime1 =  (TextView) hot1.findViewById(R.id.hotQuestionTime);
        hotTime2 =  (TextView) hot2.findViewById(R.id.hotQuestionTime1);

        hotTitle1 =  (TextView) hot1.findViewById(R.id.hotQuestionTitle);
        hotTitle2 =  (TextView) hot2.findViewById(R.id.hotQuestionTitle1);

        hotCla1 =  (TextView) hot1.findViewById(R.id.hotQuestionInterest);
        hotCla2 =  (TextView) hot2.findViewById(R.id.hotQuestionInterest1);

        newId1 =  (TextView) new1.findViewById(R.id.newQuestionId);
        newId2 =  (TextView) new2.findViewById(R.id.newQuestionId1);

        newTime1 =  (TextView) new1.findViewById(R.id.newQuestionTime);
        newTime2 =  (TextView) new2.findViewById(R.id.newQuestionTime1);

        newTitle1 =  (TextView) new1.findViewById(R.id.newQuestionTitle);
        newTitle2 =  (TextView) new2.findViewById(R.id.newQuestionTitle1);

        newCla1 =  (TextView) new1.findViewById(R.id.newQuestionInterest);
        newCla2 =  (TextView) new2.findViewById(R.id.newQuestionInterest1);

        interNum1 =  (TextView) interest1.findViewById(R.id.interNum);
        interNum2 =  (TextView) interest2.findViewById(R.id.interNum1);

        hotNum1 =  (TextView) hot1.findViewById(R.id.hotNum);
        hotNum2 =  (TextView) hot2.findViewById(R.id.hotNum1);

        newNum1 =  (TextView) new1.findViewById(R.id.newNum);
        newNum2 =  (TextView) new2.findViewById(R.id.newNum1);

        interRate1 =  (TextView) interest1.findViewById(R.id.interRate);
        interRate2 =  (TextView) interest2.findViewById(R.id.interRate1);

        hotRate1 =  (TextView) hot1.findViewById(R.id.hotRate);
        hotRate2 =  (TextView) hot2.findViewById(R.id.hotRate1);

        newRate1 =  (TextView) new1.findViewById(R.id.newRate);
        newRate2 =  (TextView) new2.findViewById(R.id.newRate1);

        //메인프레그먼트 세팅

        Log.i("MainFResult",StaticVariables.mainResult);

        try{

            JSONArray jArr = new JSONArray(StaticVariables.mainResult);
            JSONObject obj = jArr.getJSONObject(0);
            Log.i("jArr",StaticVariables.mainResult);

            interId1.setText(""+obj.getString("Uid"));
            interTime1.setText(""+obj.getString("Qtime"));
            interTitle1.setText(""+obj.getString("Title"));
            interCla1.setText(""+obj.getString("Cname"));
            interNum1.setText(""+obj.getString("Ccnt"));
            interRate1.setText(""+obj.getString("Good"));

            obj = jArr.getJSONObject(1);

            interId2.setText(""+obj.getString("Uid"));
            interTime2.setText(""+obj.getString("Qtime"));
            interTitle2.setText(""+obj.getString("Title"));
            interCla2.setText(""+obj.getString("Cname"));
            interNum2.setText(""+obj.getString("Ccnt"));
            interRate2.setText(""+obj.getString("Good"));

            obj = jArr.getJSONObject(2);

            hotId1.setText(""+obj.getString("Uid"));
            hotTime1.setText(""+obj.getString("Qtime"));
            hotTitle1.setText(""+obj.getString("Title"));
            hotCla1.setText(""+obj.getString("Cname"));
            hotNum1.setText(""+obj.getString("Ccnt"));
            hotRate1.setText(""+obj.getString("Good"));

            obj = jArr.getJSONObject(3);

            hotId2.setText(""+obj.getString("Uid"));
            hotTime2.setText(""+obj.getString("Qtime"));
            hotTitle2.setText(""+obj.getString("Title"));
            hotCla2.setText(""+obj.getString("Cname"));
            hotNum2.setText(""+obj.getString("Ccnt"));
            hotRate2.setText(""+obj.getString("Good"));

            String subResult = StaticVariables.mainResult.substring(jArr.toString().length());
            Log.i("subResult",subResult);

            jArr = new JSONArray(subResult);
            obj = jArr.getJSONObject(0);

            newId1.setText(""+obj.getString("Uid"));
            newTime1.setText(""+obj.getString("Qtime"));
            newTitle1.setText(""+obj.getString("Title"));
            newCla1.setText(""+obj.getString("Cname"));
            newNum1.setText(""+obj.getString("Ccnt"));
            newRate1.setText(""+obj.getString("Good"));

            obj = jArr.getJSONObject(1);

            newId2.setText(""+obj.getString("Uid"));
            newTime2.setText(""+obj.getString("Qtime"));
            newTitle2.setText(""+obj.getString("Title"));
            newCla2.setText(""+obj.getString("Cname"));
            newNum2.setText(""+obj.getString("Ccnt"));
            newRate2.setText(""+obj.getString("Good"));

            Log.e("setView","setView");

        }catch (JSONException e){
            Log.e("setView Error",e.toString());
        }

    }


}
