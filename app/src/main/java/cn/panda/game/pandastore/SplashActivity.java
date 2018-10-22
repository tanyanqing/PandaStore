package cn.panda.game.pandastore;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.ref.WeakReference;


public class SplashActivity extends Activity
{

    private MyHandler mMyHandler;
    private final int JUMP_TIME     = 3000;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_splash);
        mMyHandler  = new MyHandler (this);
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
}
