package cn.panda.game.pandastore.net;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;

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
        stringBuffer.append("mobile").append("=").append(mobile);
        stringBuffer.append("ver_code").append("=").append(ver_code);
        stringBuffer.append("new_password").append("=").append(new_password);

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
}
