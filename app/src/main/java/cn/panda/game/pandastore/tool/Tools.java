package cn.panda.game.pandastore.tool;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.InputStream;
import java.util.List;

public class Tools
{
    /**
     * 获取app no
     * @param context
     * @return
     */
    public static String getAppNo (Context context)
    {
        String appno = null;
        try {
            InputStream is     = context.getAssets().open("appno.dat");  //获得AssetManger 对象, 调用其open 方法取得  对应的inputStream对象
            int size         = is.available();//取得数据流的数据大小
            byte[] buffer     = new byte[size];
            is.read(buffer);
            is.close();
            appno             = new String(buffer, "GB2312");
        } catch (Exception e) {
            // TODO: handle exception
            appno = "";//默认使用这个参数来调试
        }
        return appno.trim();
    }

    public static String getChannelNo (Context context)
    {
        String channel = null;
        try {
            InputStream is     = context.getAssets().open("channelno.dat");  //获得AssetManger 对象, 调用其open 方法取得  对应的inputStream对象
            int size         = is.available();//取得数据流的数据大小
            byte[] buffer     = new byte[size];
            is.read(buffer);
            is.close();
            channel             = new String(buffer, "GB2312");
        } catch (Exception e) {
            // TODO: handle exception
            channel = "";//默认使用这个参数来调试
        }
        return channel.trim();
    }

    public static boolean isWeixinAvilible(Context context)
    {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo             = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }
}
