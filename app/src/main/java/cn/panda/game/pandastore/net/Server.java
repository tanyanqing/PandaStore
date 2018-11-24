package cn.panda.game.pandastore.net;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.migu.video.components.shareDish.net.MGSVHeader;
import com.migu.video.components.shareDish.net.MGSVHttpConnectionUtil;
import com.migu.video.components.shareDish.net.MGSVHttpRequest;
import com.migu.video.components.shareDish.net.MGSVHttpResponseCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Server
{
    private static Server mServer;
    public static Server getServer (Context context)
    {
        if (mServer == null)
        {
            synchronized (Server.class)
            {
                if (mServer == null)
                {
                    mServer = new Server(context);
                }
            }
        }
        return mServer;
    }

    private MyHttpClient client     = null;
    public Server (Context context)
    {
        client  = new MyHttpClient (context);
    }

    /**
     * 登录
     * @param nick_name
     * @param password
     * @param login_type 登陆类型 0为原始登陆  登陆类型为0时 name 和 mobile 二选一登陆
     * @param app_no
     * @param handler
     */
    public void login (String nick_name, String password, String login_type, String app_no, final HttpHandler handler)
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("nick_name").append("=").append(nick_name);
        stringBuffer.append("&password").append("=").append(password);
        stringBuffer.append("&login_type").append("=").append(login_type);
        stringBuffer.append("&app_no").append("=").append(app_no);

        client.postForm (Request.Method.POST, Resource.getLogin (), stringBuffer, new MyHttpResponseHandler ()
        {
            @Override
            public void onSuccess (String response)
            {
                if (handler != null)
                {
                    handler.onSuccess (response);
                }
            }

            @Override
            public void onFailure (VolleyError error, String errMsg)
            {
                if (handler != null)
                {
                    handler.onFail (errMsg);
                }
            }
        });
    }

    /**
     * 注册
     * @param nick_name
     * @param password
     * @param app_no
     * @param handler
     */
    public void regist (String nick_name, String password, String app_no, final HttpHandler handler)
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("nick_name").append("=").append(nick_name);
        stringBuffer.append("&password").append("=").append(password);
        stringBuffer.append("&app_no").append("=").append(app_no);

        client.postForm (Request.Method.POST, Resource.getRegist (), stringBuffer, new MyHttpResponseHandler ()
        {
            @Override
            public void onSuccess (String response)
            {
                if (handler != null)
                {
                    handler.onSuccess (response);
                }
            }

            @Override
            public void onFailure (VolleyError error, String errMsg)
            {
                if (handler != null)
                {
                    handler.onFail (errMsg);
                }
            }
        });
    }

    /**
     * 忘记密码获取验证码
     * @param name
     * @param mobile
     * @param handler
     */
    public void forgetPassword (String name, String mobile, final HttpHandler handler)
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("mobile").append("=").append(mobile);
        stringBuffer.append("&nick_name").append("=").append(name);

        client.postForm (Request.Method.POST, Resource.getForgetPassword (), stringBuffer, new MyHttpResponseHandler ()
        {
            @Override
            public void onSuccess (String response)
            {
                if (handler != null)
                {
                    handler.onSuccess (response);
                }
            }

            @Override
            public void onFailure (VolleyError error, String errMsg)
            {
                if (handler != null)
                {
                    handler.onFail (errMsg);
                }

            }
        });
    }

    /**
     * 重置密码
     * @param user_id
     * @param mobile
     * @param ver_code
     * @param new_password
     * @param handler
     */
    public void resetPassword (String user_id, String mobile, String ver_code, String new_password, final HttpHandler handler)
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("user_id").append("=").append(user_id);
        stringBuffer.append("&mobile").append("=").append(mobile);
        stringBuffer.append("&ver_code").append("=").append(ver_code);
        stringBuffer.append("&new_password").append("=").append(new_password);

        client.postForm (Request.Method.POST, Resource.getRestPassword (), stringBuffer, new MyHttpResponseHandler ()
        {
            @Override
            public void onSuccess (String response)
            {
                if (handler != null)
                {
                    handler.onSuccess (response);
                }
            }

            @Override
            public void onFailure (VolleyError error, String errMsg)
            {
                if (handler != null)
                {
                    handler.onFail (errMsg);
                }
            }
        });
    }


    public void getGameList (int page, int size, final HttpHandler handler)
    {
        MGSVHeader header           = new MGSVHeader ();
        Map<String, String> params  = new HashMap<> ();

        MGSVHttpRequest req         = new MGSVHttpRequest (Resource.getGameList (String.valueOf (page),String.valueOf (size)), "GET", header, params, null);
        MGSVHttpConnectionUtil.getHttp ().startRequest (req, new MGSVHttpResponseCallback () {
            @Override
            public void onSuccess (String s)
            {
                if (handler != null)
                {
                    handler.onSuccess (s);
                }
            }

            @Override
            public void onFail (String s)
            {
                if (handler != null)
                {
                    handler.onFail (s);
                }
            }
        });
    }

    /**
     * 首页
     * @param page
     * @param size
     * @param handler
     */
    public void getRecommendPage (int page, int size, final HttpHandler handler)
    {
        MGSVHeader header           = new MGSVHeader ();
        Map<String, String> params  = new HashMap<> ();

        MGSVHttpRequest req         = new MGSVHttpRequest (Resource.getRecommendPage (String.valueOf (page),String.valueOf (size)), "GET", header, params, null);
        MGSVHttpConnectionUtil.getHttp ().startRequest (req, new MGSVHttpResponseCallback () {
            @Override
            public void onSuccess (String s)
            {
                if (handler != null)
                {
                    handler.onSuccess (s);
                }
            }

            @Override
            public void onFail (String s)
            {
                if (handler != null)
                {
                    handler.onFail (s);
                }
            }
        });
    }

    /**
     * 发现
     * @param page
     * @param size
     * @param handler
     */
    public void getDiscover (int page, int size, final HttpHandler handler)
    {
        MGSVHeader header           = new MGSVHeader ();
        Map<String, String> params  = new HashMap<> ();

        MGSVHttpRequest req         = new MGSVHttpRequest (Resource.getDiscover (String.valueOf (page),String.valueOf (size)), "GET", header, params, null);
        MGSVHttpConnectionUtil.getHttp ().startRequest (req, new MGSVHttpResponseCallback () {
            @Override
            public void onSuccess (String s)
            {
                if (handler != null)
                {
                    handler.onSuccess (s);
                }
            }

            @Override
            public void onFail (String s)
            {
                if (handler != null)
                {
                    handler.onFail (s);
                }
            }
        });
    }

    /**
     * 详情页面
     * @param gameId
     * @param handler
     */
    public void getGameDetail (String gameId, final HttpHandler handler)
    {
        MGSVHeader header           = new MGSVHeader ();
        Map<String, String> params  = new HashMap<> ();

        MGSVHttpRequest req         = new MGSVHttpRequest (Resource.getGameDetail(gameId), "GET", header, params, null);
        Log.e("tommy", "getGameDetail url="+req.getUrl());
        MGSVHttpConnectionUtil.getHttp ().startRequest (req, new MGSVHttpResponseCallback () {
            @Override
            public void onSuccess (String s)
            {
                if (handler != null)
                {
                    handler.onSuccess (s);
                }
            }

            @Override
            public void onFail (String s)
            {
                if (handler != null)
                {
                    handler.onFail (s);
                }
            }
        });
    }
}
