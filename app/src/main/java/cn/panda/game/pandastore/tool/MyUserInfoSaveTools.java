package cn.panda.game.pandastore.tool;

import android.content.Context;
import android.text.TextUtils;

import cn.panda.game.pandastore.bean.LoginUserInfo;
import cn.panda.game.pandastore.untils.ApplicationContext;

public class MyUserInfoSaveTools
{

    private final static String COIN_COUNT  = "coin_count";
    private final static String USER_ID     = "user_id";
    private final static String APP_COIN    = "app_coin";
    private final static String NICK_NAME   = "nick_name";
    private final static String REAL_NAME   = "real_name";
    private final static String MOBILE      = "mobile";

    public static void clearAll ()
    {
        SharedPreferUtil.write (ApplicationContext.mAppContext, COIN_COUNT, String.valueOf (0));
        SharedPreferUtil.write (ApplicationContext.mAppContext, USER_ID, "");
        SharedPreferUtil.write (ApplicationContext.mAppContext, APP_COIN, String.valueOf (0));
        SharedPreferUtil.write (ApplicationContext.mAppContext, NICK_NAME, "");
        SharedPreferUtil.write (ApplicationContext.mAppContext, REAL_NAME, "");
        SharedPreferUtil.write (ApplicationContext.mAppContext, MOBILE, "");
    }
    public static void saveAll (LoginUserInfo.Data data)
    {
        if (data == null)
        {
            return;
        }
        if (data.getCoin_count () >= 0)
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, COIN_COUNT, String.valueOf (data.getCoin_count ()));
        }
        else
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, COIN_COUNT, String.valueOf (0));
        }
        if (!TextUtils.isEmpty (data.getUser_id ()))
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, USER_ID, data.getUser_id ());
        }
        else
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, USER_ID, "");
        }
        if (data.getApp_coin () >= 0)
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, APP_COIN, String.valueOf (data.getApp_coin ()));
        }
        else
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, APP_COIN, String.valueOf (0));
        }
        if (!TextUtils.isEmpty (data.getNick_name ()))
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, NICK_NAME, data.getNick_name ());
        }
        else
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, NICK_NAME, "");
        }

        if (!TextUtils.isEmpty (data.getReal_name ()))
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, REAL_NAME, data.getReal_name ());
        }
        else
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, REAL_NAME, "");
        }

        if (!TextUtils.isEmpty (data.getMobile ()))
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, MOBILE, data.getMobile ());
        }
        else
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, MOBILE, "");
        }

    }

    public static void saveCoinCount (int count)
    {
        if (count >= 0)
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, COIN_COUNT, String.valueOf (count));
        }
        else
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, COIN_COUNT, String.valueOf (0));
        }
    }

    public static void saveUserId (String userId)
    {
        if (!TextUtils.isEmpty (userId))
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, USER_ID, userId);
        }
        else
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, USER_ID, "");
        }
    }

    public static void saveAppCoin (int count)
    {
        if (count >= 0)
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, APP_COIN, String.valueOf (count));
        }
        else
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, APP_COIN, String.valueOf (0));
        }
    }

    public static void saveNickName (String nickName)
    {
        if (!TextUtils.isEmpty (nickName))
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, NICK_NAME, nickName);
        }
        else
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, NICK_NAME, "");
        }
    }
    public static void saveRealName (String realName)
    {
        if (!TextUtils.isEmpty (realName))
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, REAL_NAME, realName);
        }
        else
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, REAL_NAME, "");
        }
    }
    public static void saveMobile (String mobile)
    {
        if (!TextUtils.isEmpty (mobile))
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, MOBILE, mobile);
        }
        else
        {
            SharedPreferUtil.write (ApplicationContext.mAppContext, MOBILE, "");
        }
    }

    public static int getCoinCount ()
    {
        String coinCount   = SharedPreferUtil.read (ApplicationContext.mAppContext, COIN_COUNT);
        if (TextUtils.isEmpty (coinCount))
        {
            coinCount  = "0";
        }
        return Integer.parseInt (coinCount);
    }
    public static String getUserId ()
    {
        String userId   = SharedPreferUtil.read (ApplicationContext.mAppContext, USER_ID);
        if (TextUtils.isEmpty (userId))
        {
            userId  = "";
        }
        return userId;
    }
    public static int getAppCoin ()
    {
        String appCoin   = SharedPreferUtil.read (ApplicationContext.mAppContext, APP_COIN);
        if (TextUtils.isEmpty (appCoin))
        {
            appCoin  = "0";
        }
        return Integer.parseInt (appCoin);
    }
    public static String getNickName ()
    {
        String nickname   = SharedPreferUtil.read (ApplicationContext.mAppContext, NICK_NAME);
        if (TextUtils.isEmpty (nickname))
        {
            nickname  = "";
        }
        return nickname;
    }
    public static String getRealName ()
    {
        String realname   = SharedPreferUtil.read (ApplicationContext.mAppContext, REAL_NAME);
        if (TextUtils.isEmpty (realname))
        {
            realname  = "";
        }
        return realname;
    }
    public static String getMobile ()
    {
        String mobile   = SharedPreferUtil.read (ApplicationContext.mAppContext, MOBILE);
        if (TextUtils.isEmpty (mobile))
        {
            mobile  = "";
        }
        return mobile;
    }
}
