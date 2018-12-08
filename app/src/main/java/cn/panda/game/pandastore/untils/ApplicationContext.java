package cn.panda.game.pandastore.untils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import cn.panda.game.pandastore.tool.Tools;

public class ApplicationContext
{
    public static Context mAppContext;
    public static String mAppNo;
    public static String mChannelNo;

    public static int mScreenWidth;
    public static int mScreenHeight;

    public static void init (Context context)
    {
        mAppContext     = context.getApplicationContext ();
        mAppNo          = Tools.getAppNo (mAppContext);
        mChannelNo      = Tools.getChannelNo (mAppContext);

        WindowManager wm    = (WindowManager) mAppContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm   = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth        = dm.widthPixels;         // 屏幕宽度（像素）
        mScreenHeight       = dm.heightPixels;       // 屏幕高度（像素）

    }
}
