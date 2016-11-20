package com.example.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.application.R;

import java.util.Date;
import java.util.HashMap;

import inter.JsonCallBack;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    TextView tv_send;
    TextView tv_send2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_send = (TextView) findViewById(R.id.tv_send);
        tv_send2 = (TextView) findViewById(R.id.tv_send2);
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMsg();
            }
        });
        tv_send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postMsg();
            }
        });
    }

    private void sendMsg() {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("pageSize", 20 + "");
        map.put("page", 0 + "");
        map.put("gameType", 0 + "");

        ZdOkHttpUtils.getInstance().normalGet("http://a8.tvesou.com/v3/olympic/game_list_one", map, this, new JsonCallBack() {
            @Override
            public void onJSONResponse(boolean success, Response response, Throwable throwable) {
                if (success) {
                    String str = null;
                    try {
                        int code = response.code();
                        Log.e("success", "请求成功,code=" + code);
                        str = response.body().string();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("success", str);
                } else {
                    Log.e("success", "请求失败," + throwable.getMessage());
                }
            }
        });
    }

    private void postMsg() {
        HashMap<String, String> map = new HashMap<String, String>();

        map.put("content", 20 + "aaaaaa");
        map.put("version", "2.2.0");
        map.put("os_ver", android.os.Build.VERSION.RELEASE);//获取系统版本号
        map.put("platform", "android");
        map.put("channel", "360");

        Date date = new Date();
        int time = (int) (date.getTime() / 1000);
        String md5Url;
        try {
            md5Url = MD5Utils.getStringMD5String("fungoa8sport" + time);
            map.put("time", time + "");
            map.put("sign", md5Url);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ZdOkHttpUtils.getInstance().normalPost("http://user.a8tiyu.com/v2/statistic", map, this, new JsonCallBack() {
            @Override
            public void onJSONResponse(boolean success, Response response, Throwable throwable) {
                if (success) {
                    String str = null;
                    try {
                        int code = response.code();
                        Log.e("success", "请求成功,code=" + code);
                        str = response.body().string();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("success", str);
                } else {
                    Log.e("success", "请求失败," + throwable.getMessage());
                }
            }
        });
    }

}
