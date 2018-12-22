package com.example.test.moappteam;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

public class RankListViewAdapter extends BaseAdapter {
    private ArrayList<RankListItem> rankListItemList = new ArrayList<RankListItem>();

    public RankListViewAdapter() {}

    @Override
    public int getCount() {
        return rankListItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.rank_component, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView rankTextView = (TextView) convertView.findViewById(R.id.rank);
        TextView userTextView = (TextView) convertView.findViewById(R.id.rankUser);
        TextView replyNumTextView = (TextView) convertView.findViewById(R.id.rankCom);
        TextView likeNumTextView = (TextView) convertView.findViewById(R.id.rankRate);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        RankListItem rankListItem = rankListItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        rankTextView.setText(rankListItem.getRank()+" 등");
        userTextView.setText(rankListItem.getUser());
        replyNumTextView.setText(""+rankListItem.getReplyNum());
        likeNumTextView.setText(""+rankListItem.getLikeNum());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return rankListItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(int rank,String user, int replyNum, int likeNum) {
        RankListItem item = new RankListItem();

        item.setUser(user);
        item.setReplyNum(replyNum);
        item.setLikeNum(likeNum);
        item.setRank(rank);

        rankListItemList.add(item);
    }
    public void addItem(JSONObject obj, int rank) {
        RankListItem item = new RankListItem();

        try {
            item.setUser(obj.getString("Id"));
            item.setLikeNum(obj.getInt("Good"));
            item.setRank(rank);
            item.setReplyNum(obj.getInt("Cnt"));

            rankListItemList.add(item);
        } catch (Exception e) {
            Log.e("Error", "JSON 에러");
        }
    }
    public void resetItem() {
        rankListItemList = new ArrayList<>();
    }
}
