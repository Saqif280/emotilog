package com.emotilog.app.emotilog;



import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Chen on 2017/10/29.
 */

public class AddEntryActivity extends AppCompatActivity {
    ImageView emojiup;
    private static final int NONE = 0;
    private static final int PHOTO_GRAPH = 1;// take picture
    private static final int PHOTO_ZOOM = 2; // zoom phtoto
    private static final int PHOTO_RESOULT = 3;// the result
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private String sdPath;
    private String picPath;
    private Uri imageUri;

    private MyDatabaseHelper dbHelper;
    private int emoji_id;
    private ByteArrayOutputStream os=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        dbHelper = new MyDatabaseHelper(this,MyDatabaseHelper.DATABASE_NAME,null,1);

    }

    /*public void update(View v){
        dbHelper.onUpgrade(dbHelper.getWritableDatabase(),1,2);
    }*/

    //when user click on emoji
    public void EmojionClick(View v) {
        if(v==emojiup)
            return;

        if(emojiup!=null)
        {
            emojiup.setVisibility(View.GONE);
            switch(emojiup.getId()){
                case R.id.emoji1_up:
                    findViewById(R.id.emoji1_down).setVisibility(View.VISIBLE);
                    break;
                case R.id.emoji2_up:
                    findViewById(R.id.emoji2_down).setVisibility(View.VISIBLE);
                    break;
                case R.id.emoji3_up:
                    findViewById(R.id.emoji3_down).setVisibility(View.VISIBLE);
                    break;
                case R.id.emoji4_up:
                    findViewById(R.id.emoji4_down).setVisibility(View.VISIBLE);
                    break;
                case R.id.emoji5_up:
                    findViewById(R.id.emoji5_down).setVisibility(View.VISIBLE);
                    break;
            }

        }
        v.setVisibility(View.GONE);
        switch(v.getId()){
            case R.id.emoji1_down:
                emojiup=(ImageView) findViewById(R.id.emoji1_up);
                findViewById(R.id.emoji1_up).setVisibility(View.VISIBLE);
                emoji_id=1;
                break;
            case R.id.emoji2_down:
                emojiup=(ImageView) findViewById(R.id.emoji2_up);
                findViewById(R.id.emoji2_up).setVisibility(View.VISIBLE);
                emoji_id=2;
                break;
            case R.id.emoji3_down:
                emojiup=(ImageView) findViewById(R.id.emoji3_up);
                findViewById(R.id.emoji3_up).setVisibility(View.VISIBLE);
                emoji_id=3;
                break;
            case R.id.emoji4_down:
                emojiup=(ImageView) findViewById(R.id.emoji4_up);
                findViewById(R.id.emoji4_up).setVisibility(View.VISIBLE);
                emoji_id=4;
                break;
            case R.id.emoji5_down:
                emojiup=(ImageView) findViewById(R.id.emoji5_up);
                findViewById(R.id.emoji5_up).setVisibility(View.VISIBLE);
                emoji_id=5;
                break;
        }
    }

    //when user click choose from album
    public void choosefromalbum(View v)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
        startActivityForResult(intent, PHOTO_ZOOM);
    }
    //when user click take a photo
    public void takeaphoto(View v)
    {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},222);
                return;
            }else{
                openCamera();
            }
        } else {
            openCamera();
        }

    }

    public void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// call camera
        startActivityForResult(intent, PHOTO_GRAPH);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 222:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    openCamera();
                } else {
                    // Permission Denied
                    Toast.makeText(this, "Sorry, you disabled the camera.", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK||data==null) {
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode){
            case PHOTO_GRAPH:
                Bundle bundle = data.getExtras();
                Bitmap photo_cam = bundle.getParcelable("data");
                ByteArrayOutputStream stream_cam = new ByteArrayOutputStream();
                photo_cam.compress(Bitmap.CompressFormat.JPEG, 100, stream_cam);
                os=stream_cam;
                ((ImageView)findViewById(R.id.new_entry_photo)).setVisibility(View.VISIBLE);
                ((ImageView)findViewById(R.id.new_entry_photo)).setImageBitmap(photo_cam); //show the picture

                break;
            case PHOTO_ZOOM:
                startPhotoZoom(data.getData());
                break;
            case PHOTO_RESOULT:
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo_al = extras.getParcelable("data");
                    ByteArrayOutputStream stream_al = new ByteArrayOutputStream();
                    photo_al.compress(Bitmap.CompressFormat.JPEG, 100, stream_al);
                    os=stream_al;
                    ((ImageView)findViewById(R.id.new_entry_photo)).setVisibility(View.VISIBLE);
                    ((ImageView)findViewById(R.id.new_entry_photo)).setImageBitmap(photo_al); //show the picture
                }

        }
    }
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 4);
        intent.putExtra("aspectY", 3);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESOULT);
    }


    public void savetodatabase(View v){
        Entry entry=new Entry();
        entry.TEXT=((EditText)findViewById(R.id.new_entry_text)).getText().toString();
        entry.FEALING=emoji_id;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String time_tag = format.format(date);
        Log.e("time: ",""+time_tag);
        entry.DATE_TIME=time_tag;
        if(os!=null)entry.PHOTO=os.toByteArray();
        dbHelper.addEntry(entry);

        /*SQLiteDatabase db = dbHelper.getWritableDatabase();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String time_tag = format.format(date);

        ContentValues values = new ContentValues();
        //开始组装第一条数据
        values.put("state",((EditText)findViewById(R.id.new_entry_text)).getText().toString());
        values.put("emoji_id", emoji_id);
        values.put("time", time_tag);
        if(os!=null)
            values.put("image", os.toByteArray());
        //插入第一条数据
        db.insert("Entry", null, values);*/
        Toast.makeText(this, "save successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }
/*
    public void showentry(View v) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //查询Book表中的所有数据
        Cursor cursor = db.query("Entry", null, null, null, null, null, null, null);
        //遍历Curosr对象，取出数据并打印
        while (cursor.moveToNext()) {
            String state = cursor.getString(cursor.getColumnIndex("state"));
            String time = cursor.getString(cursor.getColumnIndex("time"));
            int emoji = cursor.getInt(cursor.getColumnIndex("emoji_id"));
            ImageView imageView = (ImageView) findViewById(R.id.img);
            byte[] data = cursor.getBlob(cursor.getColumnIndex("image"));
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(data, 0, data.length));

            Log.d("Entry", "state:" + state + " time:"
                    + time + " emoji_id:" + emoji);
        }
        //关闭Cursor
        cursor.close();
    }*/
}
