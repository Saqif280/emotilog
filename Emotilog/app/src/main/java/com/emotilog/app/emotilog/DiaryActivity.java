package com.emotilog.app.emotilog;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import static android.Manifest.permission_group.LOCATION;
import static android.R.attr.id;
import static android.R.attr.value;
import static android.R.id.list;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.JoiningGroup.FE;
import static android.nfc.NfcAdapter.EXTRA_ID;
import static android.os.Build.ID;
import static android.os.Build.VERSION_CODES.M;
import static android.provider.Contacts.SettingsColumns.KEY;
import static com.emotilog.app.emotilog.MyDatabaseHelper.TABLE_ENTRYS;
import static com.emotilog.app.emotilog.R.id.location;
import static com.emotilog.app.emotilog.R.id.new_entry_photo;

public class DiaryActivity extends AppCompatActivity {
    public ListView listView;
    private ArrayList<String> stringArrayList;
    private ArrayAdapter<String> stringArrayAdapter;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        dbHelper = new MyDatabaseHelper(this,MyDatabaseHelper.DATABASE_NAME ,null,1);
        listView = (ListView) findViewById(R.id.list_view_id);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiaryActivity.this, AddEntryActivity.class);
                startActivity(intent);
            }

        });
        final List<Entry> entrys= dbHelper.getAllEntry();//new ArrayList<Entry>();
        //entrys=dbHelper.getAllEntry();
        Log.e("number of entrys",""+entrys.size());
        stringArrayList= new ArrayList<String>();
        stringArrayAdapter =new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,stringArrayList);
        listView.setAdapter(stringArrayAdapter);

        int size=entrys.size();
        for(int i=0;i<size;i++){
            Log.e("number of entrys",""+i);

            stringArrayList.add( entrys.get(i).DATE_TIME.toString());
            Log.e("text ",entrys.get(i).TEXT.toString());
            //Log.e("location ",entrys.get(i).LOCATION.toString());
            Log.e("date_time ",""+entrys.get(i).DATE_TIME);
            Log.e("fealing ",""+entrys.get(i).FEALING);
        }

        //stringArrayList.add(entry.TEXT.toString());
        //stringArrayList.add("test2");


        stringArrayAdapter.notifyDataSetChanged();
        Log.e("after the for"," ok");
        //button.setOnClickListener(new View.OnClickListener()

        Log.e("before click", "ok");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
                Log.e("when i click", "ok");

                Intent intent = new Intent(DiaryActivity.this, DiaryEntryActivity.class);
                Log.e("id= ",""+entrys.get(position).ID);
                intent.putExtra("id", entrys.get(position).ID); //returns rows' id
                //intent.putExtra("display_date", listView.getItemAtPosition(position).toString());//returns date and time

                startActivity(intent);

                Log.e("after startActivity","ok");

            }

        });



    }
}
