package cn.panda.game.pandastore.untils;

import android.content.Context;

import cn.panda.game.pandastore.tool.Tools;

public class ApplicationContext
{
    public static Context mAppContext;
    public static String mAppNo;
    public static String mChannelNo;
    public static void init (Context context)
    {
        mAppContext     = context.getApplicationContext ();
        mAppNo          = Tools.getAppNo (mAppContext);
        mChannelNo      = Tools.getChannelNo (mAppContext);

    }
}
