package com.example.test.moappteam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

public class SpeakViewActivity extends AppCompatActivity {
    private EditText replyText;
    private ImageButton replyConfirm;
    private JSONObject mainText;

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

        Toolbar toolbar = (Toolbar)findViewById(R.id.speakToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_white);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try {
            mainText = new JSONObject(getIntent().getStringExtra("MAIN_TEXT"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter.addItem("sdf", "user", "12:00:00", 23);

        replyText = findViewById(R.id.newReply);
        replyConfirm = findViewById(R.id.replyBtn);
    }
}