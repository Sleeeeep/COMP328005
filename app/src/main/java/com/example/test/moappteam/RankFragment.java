package com.example.test.moappteam;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankFragment extends Fragment {

    private TextView myRank;
    private TextView myId;
    private TextView myComment;
    private TextView myRate;


    public RankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_rank, container, false);

        myRank = view.findViewById(R.id.rank);
        myId = view.findViewById(R.id.rankUser);
        myComment = view.findViewById(R.id.rankCom);
        myRate = view.findViewById(R.id.rankRate);

        ListView rankListView = view.findViewById(R.id.rankListView);
        RankListViewAdapter adapter = new RankListViewAdapter();
        rankListView.setAdapter(adapter);

        adapter.addItem(1,"user", 1, 1);
        adapter.addItem(2,"user2",  0, 3);

        //내 순위 정보 업데이트 및 전체적인 디비 정보 받아오기

        return view;
    }

}
