package cn.panda.game.pandastore.tool;

import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

import cn.panda.game.pandastore.untils.ApplicationContext;

public class MyDownTools
{
    public final static String SAVE_APP_LOCATION = "/pandaDownload";

    public static MyDownTools mMyDownTools;

    private DownloadManager mDownloadManager;
    public static MyDownTools getInstance ()
    {
        if (mMyDownTools == null)
        {
            mMyDownTools     = new MyDownTools ();
        }
        return mMyDownTools;
    }

    public MyDownTools ()
    {
        mDownloadManager     = (DownloadManager) ApplicationContext.mAppContext.getSystemService(Context.DOWNLOAD_SERVICE);
    }
    /**
     * 下载Apk, 并设置Apk地址
     *
     * @param downLoadUrl 下载地址
     * @param apkName 下载文件名称
     */
    public boolean downloadApk(String downLoadUrl, String apkName, String gameName)
    {
        if (isHasDownThread (gameName))
        {
            return false;
        }
        DownloadManager.Request request     = null;
        try {
            request = new DownloadManager.Request(Uri.parse(downLoadUrl));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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

        if (mDownloadManager == null)
        {
            mDownloadManager = (DownloadManager)ApplicationContext.mAppContext.getSystemService(Context.DOWNLOAD_SERVICE);
        }
        //进入下载队列
        long mReference     = mDownloadManager.enqueue(request);
        SharedPreferUtil.write (ApplicationContext.mAppContext, String.valueOf (gameName), String.valueOf (mReference));
        return true;
    }


    private boolean isHasDownThread (String gameName)
    {
        String key  = SharedPreferUtil.read (ApplicationContext.mAppContext, String.valueOf (gameName));
        if (!TextUtils.isEmpty (key))
        {
            long mReference     = Long.parseLong (key);

            DownloadManager.Query query     = new DownloadManager.Query () ;
            query.setFilterById( mReference );
            if (mDownloadManager == null)
            {
                mDownloadManager = (DownloadManager)ApplicationContext.mAppContext.getSystemService(Context.DOWNLOAD_SERVICE);
            }
            Cursor cursor                   = mDownloadManager.query( query ) ;

            if ( !cursor.moveToFirst() )
            {//没有记录
                return false;
            }
            else
            {//有记录
                int status  = getStatus (cursor);
                //现在线程状态是失败，那么清空记录重新下载
                if (status == DownloadManager.STATUS_FAILED)
                {
                    return false;
                }

                return true;
            }
        }
        return false;
    }

    /**
     * 查询状态
     * @param c
     * @return
     */
    private String statusMessage(Cursor c){
        switch(c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS ))){
            case DownloadManager.STATUS_FAILED:
                return "Download failed";
            case DownloadManager.STATUS_PAUSED:
                return "Download paused";
            case DownloadManager.STATUS_PENDING:
                return "Download pending";
            case DownloadManager.STATUS_RUNNING:
                return "Download in progress!";
            case DownloadManager.STATUS_SUCCESSFUL:
                return "Download finished";
            default:
                return "Unknown Information";
        }
    }
    /**
     * 获取下载线程的状态
     * @param c
     * @return
     */
    private int getStatus (Cursor c)
    {
        return c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS ));
    }

}
