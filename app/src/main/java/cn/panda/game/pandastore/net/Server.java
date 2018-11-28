package cn.panda.game.pandastore.net;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.migu.video.components.shareDish.net.MGSVHeader;
import com.migu.video.components.shareDish.net.MGSVHttpConnectionUtil;
import com.migu.video.components.shareDish.net.MGSVHttpRequest;
import com.migu.video.components.shareDish.net.MGSVHttpResponseCallback;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import cn.panda.game.pandastore.bean.ParseTools;
import cn.panda.game.pandastore.sql.DatabaseHelper;
import cn.panda.game.pandastore.untils.ApplicationContext;

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
        final String url            = Resource.getRecommendPage (String.valueOf (page),String.valueOf (size));

        MGSVHttpRequest req         = new MGSVHttpRequest (url, "GET", header, params, null);
        MGSVHttpConnectionUtil.getHttp ().startRequest (req, new MGSVHttpResponseCallback () {
            @Override
            public void onSuccess (String s)
            {
                if (handler != null)
                {
                    if (ParseTools.isSuccess (s))
                    {
                        DatabaseHelper.getInstance (ApplicationContext.mAppContext).write (url, s);
                    }
                    handler.onSuccess (s);
                }
            }

            @Override
            public void onFail (String s)
            {
                if (handler != null)
                {
                    String data     = DatabaseHelper.getInstance (ApplicationContext.mAppContext).read (url);
                    if (!TextUtils.isEmpty (data))
                    {
                        handler.onSuccess (data);
                    }
                    else
                    {
                        handler.onFail (s);
                    }
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
        final String url            = Resource.getDiscover (String.valueOf (page),String.valueOf (size));

        MGSVHttpRequest req         = new MGSVHttpRequest (url, "GET", header, params, null);
        MGSVHttpConnectionUtil.getHttp ().startRequest (req, new MGSVHttpResponseCallback () {
            @Override
            public void onSuccess (String s)
            {
                if (handler != null)
                {
                    if (ParseTools.isSuccess (s))
                    {
                        DatabaseHelper.getInstance (ApplicationContext.mAppContext).write (url, s);
                    }
                    handler.onSuccess (s);
                }
            }

            @Override
            public void onFail (String s)
            {
                if (handler != null)
                {
                    String data     = DatabaseHelper.getInstance (ApplicationContext.mAppContext).read (url);
                    if (!TextUtils.isEmpty (data))
                    {
                        handler.onSuccess (data);
                    }
                    else
                    {
                        handler.onFail (s);
                    }
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

    /**
     * 获取下载地址
     * @param userId
     * @param gameId
     * @param handler
     */
    public void getDownUrl (String userId, String gameId, final HttpHandler handler)
    {
        MGSVHeader header           = new MGSVHeader ();
        Map<String, String> params  = new HashMap<> ();
        params.put("user_id", TextUtils.isEmpty(userId)?("guest"):(userId));
        params.put("game_id", gameId);


        MGSVHttpRequest req         = new MGSVHttpRequest (Resource.getDownUrl(), "GET", header, params, null);

        Log.e("tommy","url="+req.getUrl());
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
     * 定购记录
     * @param params
     * @param handler
     */
    public void getOrderStatements (StringBuffer params, final HttpHandler handler)
    {
        MGSVHeader header           = new MGSVHeader ();

        client.postForm (Request.Method.POST, Resource.getOrderStatements (), params, new MyHttpResponseHandler () {
            @Override
            public void onSuccess (String s)
            {
                if (handler != null)
                {
                    handler.onSuccess (s);
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
     * 投诉建议
     */
    public void getSuggest (StringBuffer params, final HttpHandler handler)
    {
//        MGSVHeader header       = new MGSVHeader ();
//        StringBuffer params     = new StringBuffer ();
//        try
//        {
//            params.append ("user_id=").append (userId);
//            params.append ("&user_name=").append (URLEncoder.encode (userName,"UTF-8"));
//            params.append ("&suggest=").append (URLEncoder.encode (suggest,"UTF-8"));
//            params.append ("&contact=").append (URLEncoder.encode (contact,"UTF-8"));
//            params.append ("&contact_type=").append (URLEncoder.encode (contact_type,"UTF-8"));
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace ();
//        }
        client.postForm (Request.Method.POST, Resource.getSuggest (), params, new MyHttpResponseHandler () {
            @Override
            public void onSuccess (String s)
            {
                if (handler != null)
                {
                    handler.onSuccess (s);
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
     * 充值
     * @param user_id
     * @param store_no 等于channel_no
     * @param app_no 注册后平台提供的app_no
     * @param app_trade_no 必填 应用产生的订单号
     * @param count 必填 数量
     * @param pay_channel 必填 支付渠道
     * @param coin_type 0平台币 不绑定游戏 1定向币 绑定游戏
     * @param handler
     */
    public void rechargeOrder (String user_id, String store_no, String app_no, String app_trade_no, String count, String pay_channel, String coin_type,final HttpHandler handler)
    {
        StringBuffer params     = new StringBuffer ();
        params.append ("user_id").append ("=").append (user_id);
        params.append ("&store_no").append ("=").append (store_no);
        params.append ("&app_no").append ("=").append (app_no);
        params.append ("&app_trade_no").append ("=").append (app_trade_no);
        params.append ("&count").append ("=").append (count);
        params.append ("&pay_channel").append ("=").append (pay_channel);
        params.append ("&coin_type").append ("=").append (coin_type);

        client.postForm (Request.Method.POST, Resource.getRechargeOrder (), params, new MyHttpResponseHandler ()
        {
            @Override
            public void onSuccess (String s)
            {
                if (handler != null)
                {
                    handler.onSuccess (s);
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
     * 查询支付订单状态
     * @param out_trade_no
     * @param handler
     */
    public void rechargeOrderStatus (String out_trade_no,final HttpHandler handler)
    {
        StringBuffer params     = new StringBuffer ();
        params.append ("out_trade_no").append ("=").append (out_trade_no);

        client.postForm (Request.Method.POST, Resource.getOrderStatus (), params, new MyHttpResponseHandler ()
        {
            @Override
            public void onSuccess (String s)
            {
                if (handler != null)
                {
                    handler.onSuccess (s);
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
     * 更新用户信息
     * @param user_id
     * @param store_no
     * @param handler
     */
    public void getUserCenterInfo (String user_id, String store_no, final HttpHandler handler)
    {
        StringBuffer params     = new StringBuffer ();
        params.append ("user_id").append ("=").append (user_id);
        params.append ("&store_no").append ("=").append (store_no);

        client.postForm (Request.Method.POST, Resource.getUserCenterInfo (), params, new MyHttpResponseHandler ()
        {
            @Override
            public void onSuccess (String s)
            {
                if (handler != null)
                {
                    handler.onSuccess (s);
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
     * 修改用户名
     * @param user_id
     * @param nick_name
     * @param handler
     */
    public void getChangeNickname (String user_id, String nick_name, final HttpHandler handler)
    {
        StringBuffer params     = new StringBuffer ();
        params.append ("user_id").append ("=").append (user_id);
        try
        {
            params.append ("&nick_name").append ("=").append (URLEncoder.encode (nick_name, "UTF-8"));
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }


        client.postForm (Request.Method.POST, Resource.getChangeNickname (), params, new MyHttpResponseHandler ()
        {
            @Override
            public void onSuccess (String s)
            {
                if (handler != null)
                {
                    handler.onSuccess (s);
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
     * 实名认证
     * @param user_id
     * @param real_name
     * @param id_card_no
     * @param handler
     */
    public void getRecordIdCard (String user_id, String real_name, String id_card_no, final HttpHandler handler)
    {
        StringBuffer params     = new StringBuffer ();
        params.append ("user_id").append ("=").append (user_id);
        try
        {
            params.append ("&real_name").append ("=").append (URLEncoder.encode(real_name, "UTF-8"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        params.append ("&id_card_no").append ("=").append (id_card_no);

        client.postForm (Request.Method.POST, Resource.getRecordIdCard (), params, new MyHttpResponseHandler ()
        {
            @Override
            public void onSuccess (String s)
            {
                if (handler != null)
                {
                    handler.onSuccess (s);
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
     * 修改密码
     * @param userId
     * @param oldPsd
     * @param newPsd
     * @param handler
     */
    public void changePsd (String userId, String oldPsd, String newPsd, final HttpHandler handler)
    {

    }

    /**
     * 获取绑定验证码
     * @param userId
     * @param tel
     * @param handler
     */
    public void getVerifyForBind (String userId, String tel, final HttpHandler handler)
    {

    }

    /**
     * 绑定手机
     * @param userId
     * @param tel
     * @param verify
     * @param handler
     */
    public void getBindTel (String userId, String tel, String verify,final HttpHandler handler)
    {

    }
}
