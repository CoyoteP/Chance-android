package com.sendbird.android.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sendbird.android.SendBird;
import com.sendbird.android.sample.gcm.User;
import com.sendbird.android.shadow.com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ProfileActivity extends Activity {
    final String appId = "A7A2672C-AD11-11E4-8DAA-0A18B21C2D82"; /* Sample SendBird Application */
    String userId = SendBirdChatActivity.Helper.generateDeviceUUID(ProfileActivity.this); /* Generate Device UUID */
    String userName = "User-" + userId.substring(0, 5); /* Generate User Nickname */

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        //overridePendingTransition(R.anim.sendbird_slide_in_from_left, R.anim.sendbird_slide_out_to_right);
        SharedPreferences preferences = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        User user = gson.fromJson(preferences.getString("profile", null), User.class);
        TextView name = (TextView) findViewById(R.id.profilename);
        name.setText(user.getUsername());


        findViewById(R.id.matching_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(R.anim.sendbird_slide_in_from_right, R.anim.sendbird_slide_out_to_left);
                Intent intent = new Intent(ProfileActivity.this, MatchingActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.chat_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(R.anim.sendbird_slide_in_from_right, R.anim.sendbird_slide_out_to_left);
                Intent intent = new Intent(ProfileActivity.this, SendBirdMessagingChannelListActivity.class);
                Bundle args = SendBirdMessagingChannelListActivity.makeSendBirdArgs(appId, userId, userName);
                intent.putExtras(args);
                startActivity(intent);
                finish();
            }
        });

        LinearLayout layoutMain = (LinearLayout) findViewById(R.id.tagList);
        for (String tag : user.getTag()) {
            TextView textView = new TextView(this);
            textView.setText("・" + tag);
            textView.setTextSize(25);
            //textView.setBackgroundColor(0x66ff0000);
            layoutMain.addView(textView, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        }


    }

    public void finish(int a, int b) {
        super.finish();
        overridePendingTransition(a, b);
    }


}
