package com.emotilog.app.emotilog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siopis Christos on 2017/11/16.
 */

//BarChartActivity retrieves data from the database.
//Specifically implements user's feelings, displaying them in a
//Bar Graph, with the feeling as x axis and the
//sum of each feeling as y axis

//All rights of this library belong to Philipp Jahoda
//Licensed under the Apache License, Version 2.0 (the "License")

public class BarChartActivity extends AppCompatActivity {

    private MyDatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        dbHelper = new MyDatabaseHelper(this, MyDatabaseHelper.DATABASE_NAME, null, 1);
        List<Entry> dataObjects = dbHelper.getAllEntry();
        BarChart chart = (BarChart) findViewById(R.id.bar_chart);

        int smile = 0, angry = 0, cry = 0, laugh = 0, sad = 0;
        int identity1 = 1, identity2 = 2, identity3 = 3, identity4 = 4, identity5 = 5;

        List<com.github.mikephil.charting.data.BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < dataObjects.size(); i++) {
            if (dataObjects.get(i).getFealing() == 1) laugh++;
            if (dataObjects.get(i).getFealing() == 2) smile++;
            if (dataObjects.get(i).getFealing() == 3) sad++;
            if (dataObjects.get(i).getFealing() == 4) cry++;
            if (dataObjects.get(i).getFealing() == 5) angry++;
        }

        entries.add(new com.github.mikephil.charting.data.BarEntry(identity1, laugh, 1));
        entries.add(new com.github.mikephil.charting.data.BarEntry(identity2, smile, 2));
        entries.add(new com.github.mikephil.charting.data.BarEntry(identity3, sad, 3));
        entries.add(new com.github.mikephil.charting.data.BarEntry(identity4, cry, 4));
        entries.add(new com.github.mikephil.charting.data.BarEntry(identity5, angry, 5));

        BarDataSet dataSet = new BarDataSet(entries, "Feelings");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(R.color.colorAccent);

        BarData barData = new BarData(dataSet);
        chart.setData(barData);
        chart.invalidate();
    }
}
