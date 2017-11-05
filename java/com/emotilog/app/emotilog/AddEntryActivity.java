package com.emotilog.app.emotilog;



import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
    private static final int PHOTO_GRAPH = 1;// 拍照
    private static final int PHOTO_ZOOM = 2; // 缩放
    private static final int PHOTO_RESOULT = 3;// 结果
    private static final String IMAGE_UNSPECIFIED = "image/*";
    private String sdPath;
    private String picPath;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
    }

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
                break;
            case R.id.emoji2_down:
                emojiup=(ImageView) findViewById(R.id.emoji2_up);
                findViewById(R.id.emoji2_up).setVisibility(View.VISIBLE);
                break;
            case R.id.emoji3_down:
                emojiup=(ImageView) findViewById(R.id.emoji3_up);
                findViewById(R.id.emoji3_up).setVisibility(View.VISIBLE);
                break;
            case R.id.emoji4_down:
                emojiup=(ImageView) findViewById(R.id.emoji4_up);
                findViewById(R.id.emoji4_up).setVisibility(View.VISIBLE);
                break;
            case R.id.emoji5_down:
                emojiup=(ImageView) findViewById(R.id.emoji5_up);
                findViewById(R.id.emoji5_up).setVisibility(View.VISIBLE);
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
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            //set file name
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
            Date date = new Date(System.currentTimeMillis());
            String filename = format.format(date);
            sdPath = Environment.getExternalStorageDirectory().getPath();
            picPath = sdPath + "/" + filename + ".png";


            //create file to save image in DCIM
            imageUri = Uri.fromFile(new File(picPath));

            //convert file into uri and set intent to take photo

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //照相
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); //指定图片输出地址
            startActivityForResult(intent,PHOTO_GRAPH);
        }
        else{
            Toast.makeText(getApplicationContext(), "please ensure SD card",
                    Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK||data==null) {
            Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_SHORT).show();
            return;
        }

        switch (requestCode){
            case PHOTO_GRAPH:
                File picture = new File(picPath);
                startPhotoZoom(Uri.fromFile(picture));
                break;
            case PHOTO_ZOOM:
                startPhotoZoom(data.getData());
                break;
            case PHOTO_RESOULT:
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// (0-100)压缩文件
                    //此处可以把Bitmap保存到sd卡中，具体请看：http://www.cnblogs.com/linjiqin/archive/2011/12/28/2304940.html
                    ((ImageView)findViewById(R.id.new_entry_photo)).setImageBitmap(photo); //把图片显示在ImageView控件上
                }

        }
    }
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 3);
        intent.putExtra("aspectY", 5);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 500);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESOULT);
    }



}
