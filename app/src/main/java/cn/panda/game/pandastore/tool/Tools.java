package cn.panda.game.pandastore.tool;

import android.content.Context;

import java.io.InputStream;

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
}
