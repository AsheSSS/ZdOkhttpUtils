package inter;



import okhttp3.Response;

/**
 * Created by lzd on 2015/8/26.
 */
public interface JsonCallBack {
    public void onJSONResponse(boolean success, Response response, Throwable throwable);
}
