package cn.panda.game.pandastore.tool;

import android.content.Context;
import android.text.TextUtils;

import cn.panda.game.pandastore.bean.LoginUserInfo;

public class MyUserInfoSaveTools
{

    private final static String COIN_COUNT  = "coin_count";
    private final static String USER_ID     = "user_id";
    private final static String APP_COIN    = "app_coin";
    private final static String NICK_NAME   = "nick_name";
    private final static String REAL_NAME   = "real_name";
    private final static String MOBILE      = "mobile";

    public static void clearAll (Context context)
    {
        SharedPreferUtil.write (context, COIN_COUNT, String.valueOf (0));
        SharedPreferUtil.write (context, USER_ID, "");
        SharedPreferUtil.write (context, APP_COIN, String.valueOf (0));
        SharedPreferUtil.write (context, NICK_NAME, "");
        SharedPreferUtil.write (context, REAL_NAME, "");
        SharedPreferUtil.write (context, MOBILE, "");
    }
    public static void saveAll (Context context, LoginUserInfo.Data data)
    {
        if (data == null)
        {
            return;
        }
        if (data.getCoin_count () >= 0)
        {
            SharedPreferUtil.write (context, COIN_COUNT, String.valueOf (data.getCoin_count ()));
        }
        else
        {
            SharedPreferUtil.write (context, COIN_COUNT, String.valueOf (0));
        }
        if (!TextUtils.isEmpty (data.getUser_id ()))
        {
            SharedPreferUtil.write (context, USER_ID, data.getUser_id ());
        }
        else
        {
            SharedPreferUtil.write (context, USER_ID, "");
        }
        if (data.getApp_coin () >= 0)
        {
            SharedPreferUtil.write (context, APP_COIN, String.valueOf (data.getApp_coin ()));
        }
        else
        {
            SharedPreferUtil.write (context, APP_COIN, String.valueOf (0));
        }
        if (!TextUtils.isEmpty (data.getNick_name ()))
        {
            SharedPreferUtil.write (context, NICK_NAME, data.getNick_name ());
        }
        else
        {
            SharedPreferUtil.write (context, NICK_NAME, "");
        }

        if (!TextUtils.isEmpty (data.getReal_name ()))
        {
            SharedPreferUtil.write (context, REAL_NAME, data.getReal_name ());
        }
        else
        {
            SharedPreferUtil.write (context, REAL_NAME, "");
        }

        if (!TextUtils.isEmpty (data.getMobile ()))
        {
            SharedPreferUtil.write (context, MOBILE, data.getMobile ());
        }
        else
        {
            SharedPreferUtil.write (context, MOBILE, "");
        }

    }

    public static void saveCoinCount (Context context, int count)
    {
        if (count >= 0)
        {
            SharedPreferUtil.write (context, COIN_COUNT, String.valueOf (count));
        }
        else
        {
            SharedPreferUtil.write (context, COIN_COUNT, String.valueOf (0));
        }
    }

    public static void saveUserId (Context context, String userId)
    {
        if (!TextUtils.isEmpty (userId))
        {
            SharedPreferUtil.write (context, USER_ID, userId);
        }
        else
        {
            SharedPreferUtil.write (context, USER_ID, "");
        }
    }

    public static void saveAppCoin (Context context, int count)
    {
        if (count >= 0)
        {
            SharedPreferUtil.write (context, APP_COIN, String.valueOf (count));
        }
        else
        {
            SharedPreferUtil.write (context, APP_COIN, String.valueOf (0));
        }
    }

    public static void saveNickName (Context context, String nickName)
    {
        if (!TextUtils.isEmpty (nickName))
        {
            SharedPreferUtil.write (context, NICK_NAME, nickName);
        }
        else
        {
            SharedPreferUtil.write (context, NICK_NAME, "");
        }
    }
    public static void saveRealName (Context context, String realName)
    {
        if (!TextUtils.isEmpty (realName))
        {
            SharedPreferUtil.write (context, REAL_NAME, realName);
        }
        else
        {
            SharedPreferUtil.write (context, REAL_NAME, "");
        }
    }
    public static void saveMobile (Context context, String mobile)
    {
        if (!TextUtils.isEmpty (mobile))
        {
            SharedPreferUtil.write (context, MOBILE, mobile);
        }
        else
        {
            SharedPreferUtil.write (context, MOBILE, "");
        }
    }

    public static int getCoinCount (Context context)
    {
        String coinCount   = SharedPreferUtil.read (context, COIN_COUNT);
        if (TextUtils.isEmpty (coinCount))
        {
            coinCount  = "0";
        }
        return Integer.parseInt (coinCount);
    }
    public static String getUserId (Context context)
    {
        String userId   = SharedPreferUtil.read (context, USER_ID);
        if (TextUtils.isEmpty (userId))
        {
            userId  = "";
        }
        return userId;
    }
    public static int getAppCoin (Context context)
    {
        String appCoin   = SharedPreferUtil.read (context, APP_COIN);
        if (TextUtils.isEmpty (appCoin))
        {
            appCoin  = "0";
        }
        return Integer.parseInt (appCoin);
    }
    public static String getNickName (Context context)
    {
        String nickname   = SharedPreferUtil.read (context, NICK_NAME);
        if (TextUtils.isEmpty (nickname))
        {
            nickname  = "";
        }
        return nickname;
    }
    public static String getRealName (Context context)
    {
        String realname   = SharedPreferUtil.read (context, REAL_NAME);
        if (TextUtils.isEmpty (realname))
        {
            realname  = "";
        }
        return realname;
    }
    public static String getMobile (Context context)
    {
        String mobile   = SharedPreferUtil.read (context, MOBILE);
        if (TextUtils.isEmpty (mobile))
        {
            mobile  = "";
        }
        return mobile;
    }
}
