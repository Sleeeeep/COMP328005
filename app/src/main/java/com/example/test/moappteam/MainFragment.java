package com.example.test.moappteam;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


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

        Toast.makeText(getActivity(),"로그인이 필요합니다",Toast.LENGTH_LONG).show();

        setView();

        return view;

    }

    @Override
    public void onPause() {
        super.onPause();

        setView();

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

        TextView interId1 =  (TextView) interest1.findViewById(R.id.interQuestionId);
        TextView interId2 =  (TextView) interest1.findViewById(R.id.interQuestionId1);

        TextView interTime1 =  (TextView) interest1.findViewById(R.id.interQuestionTime);
        TextView interTime2 =  (TextView) interest1.findViewById(R.id.interQuestionTime1);

        TextView interTitle1 =  (TextView) interest1.findViewById(R.id.interQuestionTitle);
        TextView interTitle2 =  (TextView) interest1.findViewById(R.id.interQuestionTitle1);

        TextView interTxt1 =  (TextView) interest1.findViewById(R.id.interQuestionComponent);
        TextView interTxt2 =  (TextView) interest1.findViewById(R.id.interQuestionComponent1);

        TextView interCla1 =  (TextView) interest1.findViewById(R.id.interQuestionInter);
        TextView interCla2 =  (TextView) interest1.findViewById(R.id.interQuestionInter1);

        TextView hotId1 =  (TextView) interest1.findViewById(R.id.hotQuestionId);
        TextView hotId2 =  (TextView) interest1.findViewById(R.id.hotQuestionId1);

        TextView hotTime1 =  (TextView) interest1.findViewById(R.id.hotQuestionTime);
        TextView hotTime2 =  (TextView) interest1.findViewById(R.id.hotQuestionTime1);

        TextView hotTitle1 =  (TextView) interest1.findViewById(R.id.hotQuestionTitle);
        TextView hotTitle2 =  (TextView) interest1.findViewById(R.id.hotQuestionTitle1);

        TextView hotTxt1 =  (TextView) interest1.findViewById(R.id.hotQuestionComponent);
        TextView hotTxt2 =  (TextView) interest1.findViewById(R.id.hotQuestionComponent1);

        TextView hotCla1 =  (TextView) interest1.findViewById(R.id.hotQuestionInterest);
        TextView hotCla2 =  (TextView) interest1.findViewById(R.id.hotQuestionInterest1);

        TextView newId1 =  (TextView) interest1.findViewById(R.id.newQuestionId);
        TextView newId2 =  (TextView) interest1.findViewById(R.id.newQuestionId1);

        TextView newTime1 =  (TextView) interest1.findViewById(R.id.newQuestionTime);
        TextView newTime2 =  (TextView) interest1.findViewById(R.id.newQuestionTime1);

        TextView newTitle1 =  (TextView) interest1.findViewById(R.id.newQuestionTitle);
        TextView newTitle2 =  (TextView) interest1.findViewById(R.id.newQuestionTitle1);

        TextView newTxt1 =  (TextView) interest1.findViewById(R.id.newQuestionComponent);
        TextView newTxt2 =  (TextView) interest1.findViewById(R.id.newQuestionComponent1);

        TextView newCla1 =  (TextView) interest1.findViewById(R.id.newQuestionInterest);
        TextView newCla2 =  (TextView) interest1.findViewById(R.id.newQuestionInterest1);

        Log.i("SetView","SetView");
        



    }


}
