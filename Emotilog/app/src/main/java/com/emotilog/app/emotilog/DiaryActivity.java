package com.emotilog.app.emotilog;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

/**
 * Created by Siopis Christos on 2017/10/29.
 */

public class DiaryActivity extends AppCompatActivity {

    public ListView listView;
    private ArrayList<String> stringArrayList;
    private ArrayAdapter<String> stringArrayAdapter;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        //opens the AddEntryActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiaryActivity.this, AddEntryActivity.class);
                startActivity(intent);
            }

        });

        dbHelper = new MyDatabaseHelper(this, MyDatabaseHelper.DATABASE_NAME, null, 1);
        listView = (ListView) findViewById(R.id.list_view_id); //connects the xml file with the java file
        final List<Entry> entrys = dbHelper.getAllEntry(); //retrieves data from the database and insert the to the list

        stringArrayList = new ArrayList<>(); //creating a new listArray calling the ArrayLIst method
        stringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                stringArrayList){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view =super.getView(position, convertView, parent);

                TextView textView=(TextView) view.findViewById(android.R.id.text1);

            /*YOUR CHOICE OF COLOR*/
                textView.setTextColor(Color.BLACK);

                return view;
            }
        };;
        ListView listView = (ListView) findViewById(R.id.list_view_id);
        listView.getSolidColor();
        listView.setAdapter(stringArrayAdapter);

        int size = entrys.size(); //return the entries of the column ID in the database
        for (int i = 0; i < size; i++) {
            stringArrayList.add(entrys.get(i).DATE_TIME.toString()); //displays the date and time in format "yyyy-MM-dd HH:mm"  in the listView
        }

        stringArrayAdapter.notifyDataSetChanged();

        //when one item is clicked on the list, opens the DiaryEntryActivity
        //and sends the rows' id to DiaryEntryActivity
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long i) {
                Intent intent = new Intent(DiaryActivity.this, DiaryEntryActivity.class);
                intent.putExtra("id", entrys.get(position).ID);
                startActivity(intent);
            }

        });


    }
}
