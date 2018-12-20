package com.example.test.moappteam;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class SpeakListViewAdapter extends BaseAdapter {
    private ArrayList<SpeakListItem> speakListItemList = new ArrayList<SpeakListItem>();

    public SpeakListViewAdapter() {}

    @Override
    public int getCount() {
        return speakListItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_speak_list, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.speakTitle);
        TextView userTextView = (TextView) convertView.findViewById(R.id.speakWho);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.speakTime);
        TextView classifyTextView = (TextView) convertView.findViewById(R.id.speakClassify);
        TextView replyNumTextView = (TextView) convertView.findViewById(R.id.speakReplyNum);
        TextView likeNumTextView = (TextView) convertView.findViewById(R.id.speakLikeNum);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        SpeakListItem speakListItem = speakListItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(speakListItem.getTitle());
        userTextView.setText(speakListItem.getUser());
        timeTextView.setText(speakListItem.getTime());
        classifyTextView.setText(speakListItem.getClassify());
        replyNumTextView.setText(""+speakListItem.getReplyNum());
        likeNumTextView.setText(""+speakListItem.getLikeNum());

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
        return speakListItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String title, String user, String time, String classify, int replyNum, int likeNum) {
        SpeakListItem item = new SpeakListItem();

        item.setTitle(title);
        item.setUser(user);
        item.setTime(time);
        item.setClassify(classify);
        item.setReplyNum(replyNum);
        item.setLikeNum(likeNum);

        speakListItemList.add(item);
    }
}

//http://recipes4dev.tistory.com/43
