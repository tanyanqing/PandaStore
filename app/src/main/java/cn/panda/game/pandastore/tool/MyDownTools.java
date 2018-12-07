package cn.panda.game.pandastore.tool;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class MyDownTools
{
    public final static String SAVE_APP_LOCATION = "/pandaDownload";
    /**
     * 下载Apk, 并设置Apk地址
     *
     * @param appContext    上下文
     * @param downLoadUrl 下载地址
     * @param apkName 下载文件名称
     */
    public static void downloadApk(Context appContext, String downLoadUrl, String apkName, String gameName)
    {
        DownloadManager.Request request     = null;
        try {
            request = new DownloadManager.Request(Uri.parse(downLoadUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        request.setTitle("《"+gameName + "》下载");
        request.setDescription("下载完后请点击打开");

        //在通知栏显示下载进度
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }

        //设置保存下载apk保存路径
        request.setDestinationInExternalPublicDir(SAVE_APP_LOCATION, apkName);

        DownloadManager manager = (DownloadManager)appContext.getSystemService(Context.DOWNLOAD_SERVICE);
        //进入下载队列
        manager.enqueue(request);
    }

}
