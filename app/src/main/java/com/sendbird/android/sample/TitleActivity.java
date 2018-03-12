package com.sendbird.android.sample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;

import com.sendbird.android.sample.gcm.RegistrationIntentService;
import com.sendbird.android.sample.gcm.User;
import com.sendbird.android.shadow.com.google.gson.Gson;

public class TitleActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Gson gson = new Gson();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);

        SharedPreferences preferences = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        User user = gson.fromJson(preferences.getString("profile", null), User.class);
        if (user == null) {
            Intent in = new Intent(this, RegistrationIntentService.class);
            startService(in);
            Intent intent = new Intent(TitleActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(TitleActivity.this, MatchingActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
