package com.example.test.moappteam;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class SpeakViewActivity extends Activity {
    private ListView replyListView;
    private String newReply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speak_view);
    }
}
