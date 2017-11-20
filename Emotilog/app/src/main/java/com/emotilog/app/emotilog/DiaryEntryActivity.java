package com.emotilog.app.emotilog;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.data;
import static android.R.attr.id;
import static android.R.attr.name;
import static android.os.Build.ID;
import static com.emotilog.app.emotilog.R.id.date;
import static com.emotilog.app.emotilog.R.id.faccina;
import static com.emotilog.app.emotilog.R.id.picoftheday;
import android.support.v7.app.ActionBar;

public class DiaryEntryActivity extends AppCompatActivity {
    private ImageView emjoy;
    private ImageView photo;
    private TextView diaryText;
    public TextView display_date;
    private Button showinmaps;
    private TextView locText;
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_entry);
        dbHelper = new MyDatabaseHelper(this, MyDatabaseHelper.DATABASE_NAME, null, 1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3498db")));
        this.getWindow().setStatusBarColor(Color.parseColor("#2979af"));

        //prendo quello che mi serve
        //EntryRepo repo=new EntryRepo(this);
        int id = getIntent().getIntExtra("id",0);
        final Entry e = dbHelper.getEntry(id);
        //Log.e("ok",""+e.DATE_TIME);

        //tutti gli associamenti con xml
        diaryText = (TextView) findViewById(R.id.textView);
        display_date = (TextView) findViewById(R.id.date);
        showinmaps = (Button) findViewById(R.id.showLocation);
        emjoy=(ImageView)findViewById(faccina);
        photo=(ImageView)findViewById(picoftheday);
        //String diary_id = getIntent().getStringExtra("id");
        //String date_display = getIntent().getStringExtra("display_date");

        //diaryText.setText("row with id= " + diary_id);
        //display_date.setText("entry date= " + date_display);


        showinmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(e.getLocation()!=null) {
                    Uri gmmIntentUri = Uri.parse(e.LOCATION.toString());
                    Intent ShowInMapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    ShowInMapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(ShowInMapIntent);
                }}
        });
        //Log.e("ok","prima swith");
        if (e.FEALING == 1)
            emjoy.setImageResource(R.drawable.laugh_color);
        else if (e.FEALING == 2)
            emjoy.setImageResource(R.drawable.smile_color);
        else if(e.FEALING==3)
            emjoy.setImageResource(R.drawable.sad_color);
        else if(e.FEALING==4)
            emjoy.setImageResource(R.drawable.cry_color);
        else if(e.FEALING==5)
            emjoy.setImageResource(R.drawable.angry_color);
        //Log.e("ok","dopo swith");
        display_date.setText(e.DATE_TIME);
        diaryText.setText(e.TEXT);
        if(e.PHOTO!=null) photo.setImageBitmap(BitmapFactory.decodeByteArray(e.PHOTO, 0, e.PHOTO.length));
        else photo.setVisibility(View.INVISIBLE);

        //locText.setText("Location: "+"\n"+e.LOCATION.substring(e.LOCATION.lastIndexOf("=")+1));
    }
}
