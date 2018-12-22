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

public class ReplyListViewAdapter extends BaseAdapter {
    private ArrayList<ReplyListItem> replyListItemList = new ArrayList<ReplyListItem>();

    public ReplyListViewAdapter() {}

    @Override
    public int getCount() {
        return replyListItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_reply, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView userTextView = (TextView) convertView.findViewById(R.id.replyWho);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.replyTime);
        TextView textTextView = (TextView) convertView.findViewById(R.id.replyText);
        TextView likeNumTextView = (TextView) convertView.findViewById(R.id.replyLikeNum);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ReplyListItem replyListItem = replyListItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        textTextView.setText(replyListItem.getText());
        userTextView.setText(replyListItem.getUser());
        timeTextView.setText(replyListItem.getTime());
        likeNumTextView.setText(""+replyListItem.getLikeNum());

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
        return replyListItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String text, String user, String time, int likeNum) {
        ReplyListItem item = new ReplyListItem();

        item.setText(text);
        item.setUser(user);
        item.setTime(time);
        item.setLikeNum(likeNum);

        replyListItemList.add(item);
    }
    public void addItem(JSONObject obj) {
        ReplyListItem item = new ReplyListItem();

        Log.i("replyQuery",obj.toString());

        try {
            item.setText(obj.getString("Content"));
            item.setUser(obj.getString("Uid"));
            item.setTime(obj.getString("Ctime"));
            item.setLikeNum(obj.getInt("Good"));
        }catch(Exception e) {

        }

        //replyListItemList.add(item);
    }
    public void resetItem() {
        replyListItemList = new ArrayList<>();
    }
}

//http://recipes4dev.tistory.com/43