/**
 * Created by Saqif Badruddin: 17200777
 * */
package com.emotilog.app.emotilog;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.ActionBar;

public class GetHelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_help);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#e74c3c")));
        this.getWindow().setStatusBarColor(Color.parseColor("#b83c30"));
    }

    /** press to open contacts app */
    public void contactFriend(View view) {
        // https://stackoverflow.com/questions/5787088/android-launching-contacts-application
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.android.contacts", "com.android.contacts.DialtactsContactsEntryActivity"));
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.addCategory("android.intent.category.DEFAULT");
        startActivity(intent);
    }

    /** press to open google maps with a search query of therapists nearby */
    public void contactProfessional(View view) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=therapists+near+me");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}
