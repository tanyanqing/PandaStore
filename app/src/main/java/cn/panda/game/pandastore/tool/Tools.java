package cn.panda.game.pandastore.tool;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import cn.panda.game.pandastore.untils.ApplicationContext;

public class Tools {
    /**
     * 获取app no
     * @param context
     * @return
     */
    public static String getAppNo (Context context) {
        String appno = null;
        try {
            InputStream is = context.getAssets ().open ("appno.dat");  //获得AssetManger 对象, 调用其open 方法取得  对应的inputStream对象
            int size = is.available ();//取得数据流的数据大小
            byte[] buffer = new byte[size];
            is.read (buffer);
            is.close ();
            appno = new String (buffer, "GB2312");
        } catch (Exception e) {
            // TODO: handle exception
            appno = "";//默认使用这个参数来调试
        }
        return appno.trim ();
    }

    public static String getChannelNo (Context context) {
        String channel = null;
        try {
            InputStream is = context.getAssets ().open ("channelno.dat");  //获得AssetManger 对象, 调用其open 方法取得  对应的inputStream对象
            int size = is.available ();//取得数据流的数据大小
            byte[] buffer = new byte[size];
            is.read (buffer);
            is.close ();
            channel = new String (buffer, "GB2312");
        } catch (Exception e) {
            // TODO: handle exception
            channel = "";//默认使用这个参数来调试
        }
        return channel.trim ();
    }

    public static boolean isWeixinAvilible (Context context) {
        final PackageManager packageManager = context.getPackageManager ();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages (0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size (); i++) {
                String pn = pinfo.get (i).packageName;
                if (pn.equals ("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getVersion (Context context) {
        PackageInfo pkg;
        try {
            pkg = context.getPackageManager ().getPackageInfo (context.getApplicationContext ().getPackageName (), 0);
            String versionName = pkg.versionName;
            return versionName;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 获取手机IMEI
     *
     * @param context
     * @return
     */
    public static final String getIMEI (Context context) {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService (Context.TELEPHONY_SERVICE);
            //获取IMEI号
            if (ActivityCompat.checkSelfPermission (context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                return "";
            }
            String imei = telephonyManager.getDeviceId ();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            Log.e ("tommy","imei="+imei);
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    private static String saveUUID ()
    {
        String uuid  = SharedPreferUtil.read (ApplicationContext.mAppContext, "panda_store_uuid");
        if (TextUtils.isEmpty (uuid))
        {
            uuid    = UUID.randomUUID ().toString ();
        }
        SharedPreferUtil.write (ApplicationContext.mAppContext, "panda_store_uuid", uuid);

        try
        {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                String fileName     = "pandan_store_uuid.txt";

                //得到指定文件的输出流
                String sdPath       = Environment.getExternalStorageDirectory().getAbsolutePath();
                File file           = new File (sdPath+"/data");
                if (!file.exists()) {
                    file.mkdirs();
                }
                String filePath     = sdPath+"/data/"+fileName;
                FileOutputStream fos=new FileOutputStream (filePath);

                fos.write(uuid.getBytes("utf-8"));
                fos.close();


            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return uuid;
    }

    public static String readUUID ()
    {
        try
        {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

                String fileName             = "pandan_store_uuid.txt";
                String sdPath               = Environment.getExternalStorageDirectory().getAbsolutePath();
                String filePath             = sdPath+"/data/"+fileName;
                FileInputStream fis         = new FileInputStream (filePath);
                ByteArrayOutputStream baos  = new ByteArrayOutputStream();
                byte[] buffer               = new byte[1024];
                int len                     = -1;
                while((len=fis.read(buffer))!=-1)
                {
                    baos.write(buffer, 0, len);
                }
                String uuid = baos.toString();
                fis.close();

                if (!TextUtils.isEmpty (uuid))
                {
                    return uuid;
                }
            }
        }
        catch (Exception e)
        {

        }
        return saveUUID ();
    }
}
