package com.sendbird.android.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sendbird.android.SendBird;
import com.sendbird.android.sample.gcm.User;
import com.sendbird.android.shadow.com.google.gson.Gson;
import com.sendbird.android.shadow.com.google.gson.GsonBuilder;
import com.sendbird.android.shadow.com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MatchingListActivity extends Activity {
    Toast wiretoast;
    Gson gson;
    //ArrayList<User> list;
    ArrayList<MatchingItem> list;
    MatchingItemAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_list);
        //Gson gson = new Gson();
        gson = new GsonBuilder().serializeNulls().create();

        SharedPreferences preferences = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        User user = gson.fromJson(preferences.getString("profile", null), User.class);
        final String appKey = "A7A2672C-AD11-11E4-8DAA-0A18B21C2D82";
        final String uuid = user.getUserid();
        final String nickname = user.getUsername();
        final String gcmRegToken = PreferenceManager.getDefaultSharedPreferences(MatchingListActivity.this).getString("SendBirdGCMToken", "");


        SendBird.init(this, appKey);
        SendBird.login(SendBird.LoginOption.build(uuid).setUserName(nickname).setGCMRegToken(gcmRegToken));
        listView = (ListView) findViewById(R.id.matching_listview);
        wiretoast = Toast.makeText(this, "通信エラー", Toast.LENGTH_LONG);
        list = new ArrayList<>();
        adapter = new MatchingItemAdapter(MatchingListActivity.this);
        adapter.setTweetList(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                switch (view.getId()) {

                    case R.id.chatButton:
                        //Toast.makeText(MatchingListActivity.this, "の編集ボタンが押されました", Toast.LENGTH_SHORT).show();

                        String[] ids = {adapter.getTweetList().get(position).getUserid()};
                        Intent data = new Intent(MatchingListActivity.this, SendBirdMessagingActivity.class);
                        Bundle args = SendBirdMessagingActivity.makeMessagingStartArgs(appKey, uuid, nickname, ids);
                        data.putExtras(args);
                        startActivity(data);
                        //finish();
                        break;

                }

            }
        });


        String profile = preferences.getString("profile", null);


        RequestBody formBody = new FormBody.Builder()
                .add("param", profile)
                .build();

        Request request = new Request.Builder()
                .url("http://chancerAWS.ddo.jp:8080/AWS/rest/matching/auto")       // HTTPアクセス POST送信 テスト確認用ページ
                .post(formBody)
                .build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                wiretoast.show();
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                Log.d("Testnau", res);

                final ArrayList<User> users = gson.fromJson(res, new TypeToken<ArrayList<User>>() {
                }.getType());
                //User user = gson.fromJson(res,User.class);
                //final ArrayList<User> users = new ArrayList<>();
                //users.add(user);
                runOnUiThread(new Runnable() {
                    public void run() {
                        for (User user : users) {
                            MatchingItem tweet = new MatchingItem();
                            tweet.setUserid(user.getUserid());
                            tweet.setUsername(user.getUsername());
                            tweet.setTags(user.getTag());

                            list.add(tweet);
                        }
                        adapter.notifyDataSetChanged();
                        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(MatchingListActivity.this, MatchingActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }

                });
            }
        });


    }


}
