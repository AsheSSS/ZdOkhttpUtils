package com.example.myapplication;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import inter.JsonCallBack;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by David on 2016/7/30.
 */

public class AsyncResponsehandler implements Callback {

    private Context context;
    private JsonCallBack callback;
//    private boolean needDecode;

    public AsyncResponsehandler(JsonCallBack callback, Context context) {
        this.callback = callback;
        this.context = context;
//        this.needDecode = true;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.e("success","內部onFailure");
        try {
            callback.onJSONResponse(false,null,e);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        callback.onJSONResponse(true,response,null);
    }
}
