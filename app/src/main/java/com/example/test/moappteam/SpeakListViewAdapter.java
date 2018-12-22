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

public class SpeakListViewAdapter extends BaseAdapter {
    private ArrayList<SpeakListItem> speakListItemList = new ArrayList<SpeakListItem>();

    public SpeakListViewAdapter() {}

    @Override
    public int getCount() {
        return speakListItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        TextView textTextView = (TextView) convertView.findViewById(R.id.speakText);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        SpeakListItem speakListItem = speakListItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(speakListItem.getTitle());
        userTextView.setText(speakListItem.getUser());
        timeTextView.setText(speakListItem.getTime());
        classifyTextView.setText(speakListItem.getClassify());
        replyNumTextView.setText(Integer.toString(speakListItem.getReplyNum()));
        likeNumTextView.setText(Integer.toString(speakListItem.getLikeNum()));
        textTextView.setText(speakListItem.getText());

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
    public void addItem(JSONObject obj) {
        SpeakListItem item = new SpeakListItem();
        Log.i("Json",obj.toString());

        try {
            //item.setSpeakNum(obj.getInt("Qnumber"));
            item.setSpeakNum(1);
            item.setTitle(obj.getString("Title"));
            item.setUser(obj.getString("Uid"));
            item.setTime(obj.getString("Qtime"));
            item.setClassify(obj.getString("Cname"));
            if(!obj.isNull("Ccnt"))
                item.setReplyNum(obj.getInt("Ccnt"));
            else
                item.setReplyNum(0);
            item.setLikeNum(obj.getInt("Good"));
            String str = obj.getString("Content");
            if(str.length() > 30)
                item.setText(str.substring(0, 30) + "...");
            else
                item.setText(str);
            item.setObj(obj);

            speakListItemList.add(item);
        } catch (Exception e){
            Log.i("Error", "Err");
        }

    }
    public void resetItem() {
        speakListItemList = new ArrayList<>();
    }
    public JSONObject getJSONobj(int pos) {
        return speakListItemList.get(pos).getObj();
    }
}

//http://recipes4dev.tistory.com/43
