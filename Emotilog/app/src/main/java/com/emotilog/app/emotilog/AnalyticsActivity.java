package com.emotilog.app.emotilog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Siopis Christos on 2017/11/13.
 */

//AnalyticsActivity is just a mediator, which provides
//three options to the user to choose between Line, Pie and
//Bar graphs to display the entered feelings

public class AnalyticsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);
    }

    //opens the LineChartActivity via intent
    public void openLineChartActivity(View view) {
        Intent intent = new Intent(this, LineChartActivity.class);
        startActivity(intent);
    }

    //opens the PieChartActivity via intent
    public void openPieChartActivity(View view) {
        Intent intent = new Intent(this, PieChartActivity.class);
        startActivity(intent);
    }

    //opens the BarChartActivity via intent
    public void openBarChartActivity(View view) {
        Intent intent = new Intent(this, BarChartActivity.class);
        startActivity(intent);
    }


}