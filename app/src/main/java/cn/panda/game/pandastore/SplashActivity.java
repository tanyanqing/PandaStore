package cn.panda.game.pandastore;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;


public class SplashActivity extends Activity
{

    private MyHandler mMyHandler;
    private final int JUMP_TIME     = 3000;

    private final String mPermissions[] =
            {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE
            };
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_splash);
        mMyHandler  = new MyHandler (this);

        checkPermissions ();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mMyHandler.sendEmptyMessageDelayed (HANDLER_JUMP, JUMP_TIME);
    }

    private void jump ()
    {
        Intent intent   = new Intent (SplashActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        SplashActivity.this.startActivity (intent);
        SplashActivity.this.finish ();
    }
    private static final int HANDLER_JUMP   = 1;
    private static class MyHandler extends Handler
    {

        private final WeakReference<SplashActivity> mSplashActivitys;

        private MyHandler (SplashActivity mSplashActivity)
        {
            this.mSplashActivitys   = new WeakReference<SplashActivity> (mSplashActivity);
        }

        @Override
        public void handleMessage (Message msg)
        {
            super.handleMessage (msg);
            SplashActivity mSplashActivity    = mSplashActivitys.get ();
            if (mSplashActivity != null)
            {
                switch (msg.what)
                {
                    case HANDLER_JUMP:
                    {
                        mSplashActivity.jump ();
                    }break;
                }
            }
        }
    }

    /**
     * 返回缺失的权限
     * @param context
     * @param permissions
     * @return 返回缺少的权限，null 意味着没有缺少权限
     */
    public static String[] getDeniedPermissions(Context context, String[] permissions){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            ArrayList<String> deniedPermissionList  = new ArrayList<>();
            for(String permission : permissions)
            {
                if(context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED)
                {
                    deniedPermissionList.add(permission);
                }
            }
            int size    = deniedPermissionList.size ();
            if(size > 0)
            {
                return deniedPermissionList.toArray(new String[deniedPermissionList.size()]);
            }
        }
        return null;
    }

    /**
     * 检查权限
     */
    private void checkPermissions ()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            String [] permissions   = getDeniedPermissions (SplashActivity.this, mPermissions);
            if (permissions != null)
            {
                requestPermissions(permissions, 10000);
            }
            else
            {
                mMyHandler.sendEmptyMessageDelayed (HANDLER_JUMP, JUMP_TIME);
            }
        }
        else
        {
            mMyHandler.sendEmptyMessageDelayed (HANDLER_JUMP, JUMP_TIME);
        }

    }

}
