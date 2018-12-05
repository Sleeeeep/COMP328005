package com.example.test.moappteam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SignUPActivity extends AppCompatActivity {

    Toolbar mToolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mToolbar = (Toolbar)findViewById(R.id.signUpToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Spinner spinner = (Spinner)findViewById(R.id.favorSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.favor,
                android.R.layout.simple_spinner_dropdown_item );
                //android.R.layout.simple_spinner_item );

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("MainActivity.spinner", "pos = " + position + ", id = " + id);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.i("MainActivity.spinner", "선택 없음");
                    }
                }
        );
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
