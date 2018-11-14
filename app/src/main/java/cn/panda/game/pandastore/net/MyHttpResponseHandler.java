package cn.panda.game.pandastore.net;

import com.android.volley.VolleyError;

public abstract class MyHttpResponseHandler
{
    public abstract void onSuccess(String response);
    public abstract void onFailure(VolleyError error, String errMsg);
}
