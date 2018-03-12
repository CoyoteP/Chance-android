package com.sendbird.android.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sendbird.android.sample.gcm.User;
import com.sendbird.android.shadow.com.google.gson.Gson;
import com.sendbird.android.shadow.com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterTagActivity extends Activity {
    String name;
    Toast toast;
    Toast wiretoast;
    String res;
    Gson gson = new GsonBuilder().serializeNulls().create();
    SharedPreferences pref;
    User user;
    String[] tags = {"スポーツ", "映画", "ゲーム", "プログラミング", "筋トレ", "アニメ", "楽器", "ドライブ", "手芸", "イラスト", "料理", "観葉植物", "読書", "サーフィン", "釣り"};
    ArrayList<String> array = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_tag);
        user = gson.fromJson(getIntent().getStringExtra("user"), User.class);
        LinearLayout layoutMain = (LinearLayout) findViewById(R.id.linearLayout3);
        layoutMain.setOrientation(LinearLayout.VERTICAL);
        LinearLayout layoutItem = new LinearLayout(this);
        layoutItem.setOrientation(LinearLayout.HORIZONTAL);
        int su = 0;
        for (String tag : tags) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setText(tag);
            checkBox.setTextSize(15);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                    if (isChecked) {
                        array.add(compoundButton.getText().toString());
                    } else {
                        array.remove(array.indexOf(compoundButton.getText().toString()));
                    }
                    //Toast.makeText(getApplication(), array.toString(), Toast.LENGTH_LONG).show();
                }
            });
            layoutItem.addView(checkBox, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            su++;
            if (su == 2) {

                layoutMain.addView(layoutItem, new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        100));
                layoutItem = new LinearLayout(this);
                layoutItem.setOrientation(LinearLayout.HORIZONTAL);
                su = 0;
            }
        }


        res = "";
        toast = Toast.makeText(this, "名前を入力してください。", Toast.LENGTH_LONG);
        wiretoast = Toast.makeText(this, "通信エラー", Toast.LENGTH_LONG);
        pref = getSharedPreferences("DATA", Context.MODE_PRIVATE);
        final String userId = SendBirdChatActivity.Helper.generateDeviceUUID(RegisterTagActivity.this);
        findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (array.size() > 1) {
                    user.setTag(array);
                    Register(user);
                } else {
                    Toast.makeText(getApplication(), "タグを2つ以上選択してください", Toast.LENGTH_LONG).show();
                }

            }
        });

    }


    public String Register(User user) {
        String value = gson.toJson(user);
        //name.setText(value);
        RequestBody formBody = new FormBody.Builder()
                .add("param", value)
                .build();

        Request request = new Request.Builder()
                .url("http://chancerAWS.ddo.jp:8080/AWS/rest/matching/register")       // HTTPアクセス POST送信 テスト確認用ページ
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
                res = response.body().string();

                if (res.equals("true")) {
                    intent();
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        //Toast.makeText(getApplication(), res, Toast.LENGTH_LONG).show();
                    }

                });
            }
        });

        return res;
    }

    public void intent() {
        pref.edit().putString("profile", gson.toJson(user)).apply();
        Intent intent = new Intent(RegisterTagActivity.this, MatchingActivity.class);
        startActivity(intent);
        finish();
    }

}
