package com.example.myapplication;

import android.content.Context;
import android.net.Uri;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import inter.JsonCallBack;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by David on 2016/7/30.
 * public static void getA8PrimaryDataHot(String url, String type, String page, String pageSize, AsyncHttpResponseHandler responseHandler, Context context) {
 * RequestParams params = new RequestParams();
 * //        params.put("userId", PrefsUtils.getPrefs(context).getString(PrefsUtils.PREFS_A8_USER_ID, ""));
 * params.add("gameType", type);
 * params.add("page", page);
 * params.add("pageSize", pageSize);
 * params.add("gameVersion", "2");
 * cdnGet(url, params, responseHandler);
 * //
 * <p>
 * public static void cdnGet(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
 * client.get(getCdnAbsoluteUrl(url), params, responseHandler);
 * <p>
 * //
 * <p>
 * GetBuilder builder = OkHttpUtils.get()
 * .addParams("leagueId", leagueId)
 * .addParams("pageSize", pageSize + "")
 * .addParams("page", page + "");
 * <p>
 * if (!URLConstants.GAME_NEWS_LIST.equals(relativeUrl)) {
 * builder = builder
 * .url(URLConstants.DOMAIN_A8_BASE_URL + relativeUrl)
 * .addParams("leagueList", parseLeagueList(PrefsUtils.getPrefs(context).getString(PrefsUtils.PREFS_A8_LEAGUE_LIST, ""), context).toString());
 * <p>
 * builder = addGeneralParams(builder, context);
 * builder = encodeUri(builder);
 * <p>
 * } else {
 * builder = builder
 * .url(URLConstants.CDN_A8_BASE_URL + relativeUrl);
 * <p>
 * builder = addPlatformAndVersion(builder, context);
 * }
 * <p>
 * builder.build().execute(callback);
 * }
 */

public class ZdOkHttpUtils {

    private static OkHttpClient okhttpclient;
    private static ZdOkHttpUtils zdokhttputils;

    public static ZdOkHttpUtils getInstance() {
        if (zdokhttputils == null) {
            zdokhttputils = new ZdOkHttpUtils();
        }
        return zdokhttputils;
    }

    private OkHttpClient getClient() {
        if (okhttpclient == null) {
            okhttpclient = new OkHttpClient.Builder()
                    .readTimeout(2, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(2, TimeUnit.SECONDS)//设置写的超时时间
                    .connectTimeout(2, TimeUnit.SECONDS)//设置连接超时时间
                    .build();
        }
        return okhttpclient;
    }

    public void normalGet(String url, HashMap map, Context context, JsonCallBack callBack) {

        // 添加请求参数
        String s = appendParams(url, map);

        // 创建request
        Request.Builder builder = new Request.Builder();
        builder.url(s);
        Request request = builder.build();

        //请求加入调度
        Call call = getClient().newCall(request);
        call.enqueue(new AsyncResponsehandler(callBack, context));

    }
	
	
	public void normalPost(String url, Map<String,String> map, Context context, JsonCallBack callBack) {

        FormBody.Builder formBody = new FormBody.Builder();
        for (Map.Entry<String,String> entry: map.entrySet()) {
            formBody.add(entry.getKey(), entry.getValue());
        }
        FormBody body = formBody.build();
        //
        Request.Builder builder = new Request.Builder();
        builder.url(url);//这里的URL路径为全路径，传递时注意
        builder.post(body);
        Request request = builder.build();
        //请求加入调度
        Call call = getClient().newCall(request);
        call.enqueue(new AsyncResponsehandler(callBack,context));
    }


    private String appendParams(String url, Map<String, String> params) {
        if (url == null || params == null || params.isEmpty()) {
            return url;
        }
        Uri.Builder builder = Uri.parse(url).buildUpon();
        Set<String> keys = params.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            builder.appendQueryParameter(key, params.get(key));
        }
        return builder.build().toString();
    }
}
