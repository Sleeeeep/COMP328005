package com.example.test.moappteam;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;


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

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_main,container,false);

        Button button = (Button) view.findViewById(R.id.scrollInLay).findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),WriteActivity.class);
                startActivity(intent);

            }
        });

        interest1 = (LinearLayout)view.findViewById(R.id.scrollInLay).findViewById(R.id.interestQ1);
        interest2 = (LinearLayout)view.findViewById(R.id.scrollInLay).findViewById(R.id.interestQ2);
        hot1 = (LinearLayout)view.findViewById(R.id.scrollInLay).findViewById(R.id.hotQ1);
        hot2 = (LinearLayout)view.findViewById(R.id.scrollInLay).findViewById(R.id.hotQ2);
        new1 = (LinearLayout)view.findViewById(R.id.scrollInLay).findViewById(R.id.newQ1);
        new2 = (LinearLayout)view.findViewById(R.id.scrollInLay).findViewById(R.id.newQ2);

        setListener();


        return view;

    }

    public void setListener(){

        interest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SignUPActivity.class);
                startActivity(intent1);
            }
        });

        interest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SignUPActivity.class);
                startActivity(intent1);
            }
        });

        hot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SignUPActivity.class);
                startActivity(intent1);
            }
        });

        hot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SignUPActivity.class);
                startActivity(intent1);
            }
        });

        new1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SignUPActivity.class);
                startActivity(intent1);
            }
        });

        new2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),SignUPActivity.class);
                startActivity(intent1);
            }
        });
    }

    public void setView(){
        //화면을 구성하는 정보를 설정하는 창

    }

}
