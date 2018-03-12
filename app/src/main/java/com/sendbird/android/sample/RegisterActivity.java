package com.sendbird.android.sample;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.sendbird.android.sample.gcm.User;
import com.sendbird.android.shadow.com.google.gson.Gson;
import com.sendbird.android.shadow.com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class RegisterActivity extends Activity {
    EditText name;
    Toast toast;
    User user;
    Gson gson = new GsonBuilder().serializeNulls().create();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = (EditText) findViewById(R.id.nameedit);
        toast = Toast.makeText(this, "名前を入力してください。", Toast.LENGTH_LONG);
        final String userId = SendBirdChatActivity.Helper.generateDeviceUUID(RegisterActivity.this);
        findViewById(R.id.register_next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name == null) {
                    toast.show();

                } else {
                    user = new User();
                    user.setUserid(userId);
                    user.setUsername(name.getText().toString());
                    Intent intent = new Intent(RegisterActivity.this, RegisterTagActivity.class);
                    intent.putExtra("user", gson.toJson(user));
                    startActivity(intent);
                    finish();
                }
            }
        });

    }


}
