package com.example.test.moappteam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;

public class SpeakViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak_view);

        ListView replyListView = findViewById(R.id.replyList);
        View header = getLayoutInflater().inflate(R.layout.activity_speak_view_header, null, false);
        View footer = getLayoutInflater().inflate(R.layout.activity_speak_view_footer, null, false);
        replyListView.addHeaderView(header);
        replyListView.addFooterView(footer);

        ReplyListViewAdapter adapter = new ReplyListViewAdapter();
        replyListView.setAdapter(adapter);

        adapter.addItem("sdf", "user", "12:00:00", 23);
    }
}