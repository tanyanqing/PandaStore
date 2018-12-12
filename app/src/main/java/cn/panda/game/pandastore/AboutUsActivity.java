package cn.panda.game.pandastore;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;

import cn.panda.game.pandastore.tool.SharedPreferUtil;
import cn.panda.game.pandastore.tool.Tools;
import cn.panda.game.pandastore.tool.UpdateAppHttpUtil;
import cn.panda.game.pandastore.untils.ApplicationContext;

public class AboutUsActivity extends FragmentActivity
{
    private TextView mName;
    private TextView mVersion;

    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_about_us);

        mName       = (TextView)findViewById (R.id.name);
        mVersion    = (TextView)findViewById (R.id.version);

        mName.setText (R.string.app_name);
        mVersion.setText (Tools.getVersion (AboutUsActivity.this));
    }

    private void showLoading (boolean isShow)
    {
        if (mProgressDialog == null)
        {
            mProgressDialog     = new ProgressDialog (AboutUsActivity.this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            mProgressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
            mProgressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            mProgressDialog.setMessage("版本检查中，请稍等...");
        }
        if (isShow)
        {
            mProgressDialog.show();
        }
        else
        {
            mProgressDialog.dismiss();
        }
    }
    public void backButton (View view)
    {
        this.finish ();
    }

    public void checkVersion (View viwe)
    {
        showLoading (true);
        String version      = Tools.getVersion (AboutUsActivity.this);
        String mUpdateUrl   = "http://opt.mycente.com/gameRoute/check_version/0/"+version;
        new UpdateAppManager.Builder().setActivity(AboutUsActivity.this).setUpdateUrl(mUpdateUrl)
                .handleException(new ExceptionHandler () {
                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                .setPost (false)
                .setUpdateDialogFragmentListener (new IUpdateDialogFragmentListener ()
                {
                    @Override
                    public void onUpdateNotifyDialogCancel (UpdateAppBean updateApp)
                    {
                        SharedPreferUtil.write (ApplicationContext.mAppContext, "version", updateApp.getNewVersion ());
                    }
                })
                //实现httpManager接口的对象
                .setHttpManager(new UpdateAppHttpUtil ())
                .build()
                .checkNewApp (new UpdateCallback ()
                {
                    @Override
                    protected void hasNewApp (UpdateAppBean updateApp, UpdateAppManager updateAppManager)
                    {
                        showLoading (false);
                        updateAppManager.showDialogFragment ();
                    }
                    @Override
                    protected void noNewApp (String error)
                    {
                        showLoading (false);
                        Toast.makeText (AboutUsActivity.this, "已是最新版本", Toast.LENGTH_SHORT).show ();
//                        super.noNewApp (error);
                    }
                });
    }
}
