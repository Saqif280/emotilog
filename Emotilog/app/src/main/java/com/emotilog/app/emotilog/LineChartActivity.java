package com.emotilog.app.emotilog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Siopis Christos on 2017/11/16.
 */

//LineChartActivity retrieves data from the database and
//specifically the user's feelings, displaying them in a
//Line Graph, with the id of the entry as x axis and the
//feeling of the specific id as y axis

//All rights of this library belong to Philipp Jahoda
//Licensed under the Apache License, Version 2.0 (the "License")

public class LineChartActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        dbHelper = new MyDatabaseHelper(this, MyDatabaseHelper.DATABASE_NAME, null, 1);
        List<Entry> dataObjects = dbHelper.getAllEntry();
        LineChart chart = (LineChart) findViewById(R.id.line_chart);

        List<com.github.mikephil.charting.data.Entry> entries = new ArrayList<>();

        for (Entry data : dataObjects) {
            entries.add(new com.github.mikephil.charting.data.Entry(data.getId(), data.getFealing()));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Feelings");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextColor(R.color.colorAccent);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate();
    }
}
