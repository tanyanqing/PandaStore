package cn.panda.game.pandastore;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{
    private MyHandler mMyHandler;

    private View mLoginview;
    private View mRegistview;
    private View mForgetview;

    private Button mForgetGetverify;
    final int verCodeTime           = 60;//倒计时时长
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_login);
        mMyHandler  = new MyHandler (this);

        initView ();
    }

    @Override
    public void onClick (View view)
    {
        switch (view.getId ())
        {
            case R.id.login_forget:
            {
                showForget ();
            }break;
            case R.id.login_back:
            {
                exitLogin ();
            }break;
            case R.id.login_regist:
            {
                showRegist ();
            }break;
            case R.id.login_dologin:
            {
                doLogin ();
            }break;
            case R.id.regist_back:
            case R.id.regist_docancel:
            {
                showLogin ();
            }break;
            case R.id.regist_doregist:
            {
                doRegist ();
            }break;
            case R.id.forget_back:
            case R.id.forget_docancel:
            {
                showLogin ();
                finishForgetCount ();

                timerForForget.cancel();
                mForgetGetverify.setEnabled(true);
                mForgetGetverify.setBackgroundResource (R.drawable.login_button);
                mForgetGetverify.setText(R.string.forget_verify_get);
            }break;
            case R.id.forget_getverify:
            {
                getVerify ();
            }break;
            case R.id.forget_do:
            {
                doForget ();
            }break;
        }
    }

    private void initView ()
    {
        mLoginview      = findViewById (R.id.login_view);
        mRegistview     = findViewById (R.id.regist_view);
        mForgetview     = findViewById (R.id.forget_view);

        findViewById (R.id.login_forget).setOnClickListener (this);
        findViewById (R.id.login_back).setOnClickListener (this);
        findViewById (R.id.login_regist).setOnClickListener (this);
        findViewById (R.id.login_dologin).setOnClickListener (this);

        findViewById (R.id.regist_back).setOnClickListener (this);
        findViewById (R.id.regist_docancel).setOnClickListener (this);
        findViewById (R.id.regist_doregist).setOnClickListener (this);


        mForgetGetverify    = (Button)findViewById (R.id.forget_getverify);
        findViewById (R.id.forget_back).setOnClickListener (this);
        mForgetGetverify.setOnClickListener (this);
        findViewById (R.id.forget_do).setOnClickListener (this);
        findViewById (R.id.forget_docancel).setOnClickListener (this);
        showLogin ();
    }

    private void showLogin ()
    {
        mLoginview.setVisibility (View.VISIBLE);
        mRegistview.setVisibility (View.GONE);
        mForgetview.setVisibility (View.GONE);
    }
    private void showRegist ()
    {
        mLoginview.setVisibility (View.GONE);
        mRegistview.setVisibility (View.VISIBLE);
        mForgetview.setVisibility (View.GONE);
    }
    private void showForget ()
    {
        mLoginview.setVisibility (View.GONE);
        mRegistview.setVisibility (View.GONE);
        mForgetview.setVisibility (View.VISIBLE);


    }

    /**
     * 退出登录
     */
    private void exitLogin ()
    {
        LoginActivity.this.finish ();
    }
    /**
     * 发起登录请求
     */
    private void doLogin ()
    {

    }

    /**
     * 发起注册请求
     */
    private void doRegist ()
    {

    }

    /**
     * 发起重置密码
     */
    private void doForget ()
    {

    }

    /**
     * 获取验证码
     */
    private void getVerify ()
    {

        mForgetGetverify.setEnabled(false);
        mForgetGetverify.setBackgroundResource (R.drawable.verify_enable_button);
        startForgetCount ();
    }
    /**
     * 忘记密码界面获取验证码倒计时
     */
    CountDownTimer timerForForget   = new CountDownTimer (verCodeTime*1000, 1000)
    {
        @Override
        public void onTick(long millisUntilFinished)
        {
            Message msg = mMyHandler.obtainMessage();
            msg.what    = HANDLER_FORGET_UPDATECOUNT;
            msg.arg1    = 2;
            msg.arg2    = (int) millisUntilFinished / 1000;
            msg.sendToTarget ();
        }

        @Override
        public void onFinish()
        {
            Message msg = mMyHandler.obtainMessage();
            msg.what    = HANDLER_FORGET_UPDATECOUNT;
            msg.arg1    = 3;
            msg.sendToTarget ();
        }

    };

    /**
     * 开始倒计时
     */
    private void startForgetCount ()
    {
        Message msg = mMyHandler.obtainMessage();
        msg.what    = HANDLER_FORGET_UPDATECOUNT;
        msg.arg1    = 1;
        msg.sendToTarget ();
    }

    /**
     * 结束倒计时
     */
    private void finishForgetCount ()
    {
        timerForForget.cancel();

        Message msg = mMyHandler.obtainMessage();
        msg.what    = HANDLER_FORGET_UPDATECOUNT;
        msg.arg1    = 4;
        mMyHandler.sendMessageDelayed(msg, 1000);
    }

    private void updateGetVerifyButton (int index, int second)
    {
        if (index == 1)
        {/**开始倒计时，屏蔽按钮*/
            mForgetGetverify.setEnabled(false);
            mForgetGetverify.setBackgroundResource (R.drawable.verify_enable_button);
            mForgetGetverify.setText (verCodeTime+"秒");
            timerForForget.start();
        }
        else if (index ==2)
        {/**倒计中，更新按钮文字*/
            mForgetGetverify.setText(second+"秒");
        }
        else if (index == 3)
        {/**倒计结束*/
            mForgetGetverify.setEnabled(true);
            mForgetGetverify.setBackgroundResource (R.drawable.login_button);
            mForgetGetverify.setText(R.string.forget_verify_get);
        }
        else if (index == 4)
        {
            mForgetGetverify.setEnabled(true);
            mForgetGetverify.setBackgroundResource (R.drawable.login_button);
            mForgetGetverify.setText(R.string.forget_verify_get);
        }
    }

    private static final int HANDLER_FORGET_UPDATECOUNT    = 1;//找回密码界面获取验证码按钮更新
    private static class MyHandler extends Handler
    {

        private final WeakReference<LoginActivity> mLoginActivitys;

        private MyHandler (LoginActivity mLoginActivity)
        {
            this.mLoginActivitys   = new WeakReference<LoginActivity> (mLoginActivity);
        }

        @Override
        public void handleMessage (Message msg)
        {
            super.handleMessage (msg);
            LoginActivity mLoginActivity    = mLoginActivitys.get ();
            if (mLoginActivity != null)
            {
                switch (msg.what)
                {
                    case HANDLER_FORGET_UPDATECOUNT:
                    {
                        mLoginActivity.updateGetVerifyButton (msg.arg1, msg.arg2);
                    }break;
                }
            }
        }
    }
}
