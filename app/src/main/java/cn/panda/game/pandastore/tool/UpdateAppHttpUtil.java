package cn.panda.game.pandastore.tool;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.vector.update_app.HttpManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

public class UpdateAppHttpUtil implements HttpManager
{
//    https://github.com/WVector/AppUpdate
    @Override
    public void asyncGet (@NonNull String url, @NonNull Map<String, String> params, @NonNull final Callback callBack) {
        OkHttpUtils.get()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback () {
                    @Override
                    public void onError(Call call, Response response, Exception e, int id) {
                        callBack.onError(validateError(e, response));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try
                        {
                            JSONObject jo   =    new JSONObject (response);
                            if (jo != null && jo.optInt ("resultCode") == 100)
                            {
                                String data     = jo.optString ("data");
                                if (!TextUtils.isEmpty (data))
                                {
                                    callBack.onResponse (data);
                                }
                                else
                                {
                                    callBack.onError (jo.optString ("resultCode"));
                                }
                            }

                        }
                        catch (Exception e)
                        {
                            callBack.onError (e.toString ());
                        }
                    }
                });
    }

    @Override
    public void asyncPost (@NonNull String url, @NonNull Map<String, String> params, @NonNull final Callback callBack)
    {
        OkHttpUtils.post()
                .url(url)
                .params(params)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Response response, Exception e, int id) {
                        callBack.onError(validateError(e, response));
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        callBack.onResponse(response);
                    }
                });
    }

    @Override
    public void download (@NonNull String url, @NonNull String path, @NonNull String fileName, @NonNull final FileCallback callback) {

        OkHttpUtils.get()
                .url(url)
                .build()
                .execute(new FileCallBack (path, fileName) {
                    @Override
                    public void inProgress(float progress, long total, int id) {
                        callback.onProgress(progress, total);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e, int id) {
                        callback.onError(validateError(e, response));
                    }

                    @Override
                    public void onResponse(File response, int id) {
                        callback.onResponse(response);

                    }

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                        callback.onBefore();
                    }
                });
    }
}
