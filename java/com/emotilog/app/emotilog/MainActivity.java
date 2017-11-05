package com.emotilog.app.emotilog;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void openGetHelp(View view) {
        Intent intent = new Intent(this, GetHelpActivity.class);
        startActivity(intent);
    }

    public void AddEntry(View view) {
        Intent intent = new Intent(this, AddEntryActivity.class);
        startActivity(intent);
    }

    public void openDiary(View view){
        Intent intent = new Intent(this, DiaryActivity.class);
        startActivity(intent);
    }
}
