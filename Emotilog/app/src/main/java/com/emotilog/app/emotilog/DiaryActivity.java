package com.emotilog.app.emotilog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DiaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
    }

    public void AddEntry (View view) {
        Intent intent = new Intent(this, AddEntryActivity.class);
        startActivity(intent);
    }

}
