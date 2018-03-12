package com.sendbird.android.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.sendbird.android.sample.gcm.User;
import com.sendbird.android.shadow.com.google.gson.Gson;
import com.sendbird.android.shadow.com.google.gson.GsonBuilder;

public class MatchingActivity extends Activity {
    final String appId = "A7A2672C-AD11-11E4-8DAA-0A18B21C2D82"; /* Sample SendBird Application */

    //String userId = SendBirdChatActivity.Helper.generateDeviceUUID(MatchingActivity.this); /* Generate Device UUID */
    //String userName = "User-" + userId.substring(0, 5); /* Generate User Nickname */
    //String userName = "TestUserMan";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);
        Gson gson = new GsonBuilder().serializeNulls().create();

        SharedPreferences preferences = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        User user = gson.fromJson(preferences.getString("profile", null), User.class);
        final String appKey = "A7A2672C-AD11-11E4-8DAA-0A18B21C2D82";
        final String userId = user.getUserid();
        final String userName = user.getUsername();
        findViewById(R.id.chat_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(R.anim.sendbird_slide_in_from_left, R.anim.sendbird_slide_out_to_right);
                Intent intent = new Intent(MatchingActivity.this, SendBirdMessagingChannelListActivity.class);
                Bundle args = SendBirdMessagingChannelListActivity.makeSendBirdArgs(appId, userId, userName);
                intent.putExtras(args);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.profile_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(R.anim.sendbird_slide_in_from_left, R.anim.sendbird_slide_out_to_right);
                Intent intent = new Intent(MatchingActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findViewById(R.id.auto_matching).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchingActivity.this, MatchingListActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.select_matching).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MatchingActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }

    public void finish(int a, int b) {
        super.finish();
        overridePendingTransition(a, b);
    }

}
