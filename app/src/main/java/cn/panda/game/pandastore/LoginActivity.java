package cn.panda.game.pandastore;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import cn.panda.game.pandastore.bean.LoginUserInfo;
import cn.panda.game.pandastore.bean.ParseTools;
import cn.panda.game.pandastore.bean.RegistUserInfo;
import cn.panda.game.pandastore.net.HttpHandler;
import cn.panda.game.pandastore.net.Server;
import cn.panda.game.pandastore.tool.SharedPreferUtil;
import cn.panda.game.pandastore.tool.Tools;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener
{
    private MyHandler mMyHandler;

    private View mLoginview;
    private View mRegistview;
    private View mForgetview;

    private Button mForgetGetverify;
    final int verCodeTime           = 60;//倒计时时长

    private ProgressDialog mProgressDialog;//网络请求时的loading


    private CheckBox mLoginCheckbox;
    private EditText mLoginPassword;
    private EditText mLoginName;

    private EditText mRegistName;
    private EditText mRegistPassword;
    private EditText mRegistPassword2;

    private EditText mForgetName;
    private EditText mForgetPassword1;
    private EditText mForgetPassword2;
    private EditText mForgetVerify;

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

    @Override
    public void onCheckedChanged (CompoundButton compoundButton, boolean isChecked)
    {
        SharedPreferUtil.write(getApplicationContext (), SharedPreferUtil.APP_IS_REMEMBER, isChecked?("true"):("false"));
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

        /**登录界面*/
        mLoginName      = (EditText)findViewById (R.id.login_name);
        mLoginPassword  = (EditText)findViewById (R.id.login_password);
        mLoginCheckbox  = (CheckBox)findViewById (R.id.login_checkbox);
        mLoginCheckbox.setOnCheckedChangeListener (this);

        /**注册界面*/
        mRegistName         = (EditText)findViewById (R.id.regist_name);
        mRegistPassword     = (EditText)findViewById (R.id.regist_psd);
        mRegistPassword2    = (EditText)findViewById (R.id.regist_psd2);

        /**忘记密码界面*/
        mForgetName         = (EditText)findViewById (R.id.forget_name);
        mForgetPassword1    = (EditText)findViewById (R.id.forget_psd_1);
        mForgetPassword2    = (EditText)findViewById (R.id.forget_psd_2);
        mForgetVerify       = (EditText)findViewById (R.id.forget_verify);

        showLogin ();
    }

    private void showLogin ()
    {
        mLoginview.setVisibility (View.VISIBLE);
        mRegistview.setVisibility (View.GONE);
        mForgetview.setVisibility (View.GONE);

        boolean isChecked   = SharedPreferUtil.read(getApplicationContext (), SharedPreferUtil.APP_IS_REMEMBER).equals("true")?(true):(false);
        mLoginCheckbox.setChecked(isChecked);

        String name     = SharedPreferUtil.read (getApplicationContext (), SharedPreferUtil.LOGIN_NAME);
        if (!TextUtils.isEmpty (name))
        {
            mLoginName.setText (name);
        }
        if (isChecked)
        {
            String psd     = SharedPreferUtil.read (getApplicationContext (), SharedPreferUtil.LOGIN_PASSWORD);
            if (!TextUtils.isEmpty (psd))
            {
                mLoginPassword.setText (psd);
            }
        }
    }
    private void showRegist ()
    {
        mLoginview.setVisibility (View.GONE);
        mRegistview.setVisibility (View.VISIBLE);
        mForgetview.setVisibility (View.GONE);

        mRegistName.setText ("");
        mRegistPassword.setText ("");
        mRegistPassword2.setText ("");
    }
    private void showForget ()
    {
        mLoginview.setVisibility (View.GONE);
        mRegistview.setVisibility (View.GONE);
        mForgetview.setVisibility (View.VISIBLE);

        mForgetName.setText ("");
        mForgetPassword1.setText ("");
        mForgetPassword2.setText ("");
        mForgetVerify.setText ("");

    }

    private void showLoading (boolean isShow)
    {
        if (mProgressDialog == null)
        {
            mProgressDialog     = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            mProgressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
            mProgressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            mProgressDialog.setMessage("加载中，请稍等...");
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
        String nickName     = mLoginName.getText ().toString ();
        String password     = mLoginPassword.getText ().toString ();
        if (TextUtils.isEmpty (nickName))
        {
            Toast.makeText (getApplicationContext (), "请正确输入账号", Toast.LENGTH_SHORT).show ();
            return;
        }
        if (TextUtils.isEmpty (password))
        {
            Toast.makeText (getApplicationContext (), "请正确输入密码", Toast.LENGTH_SHORT).show ();
            return;
        }
        String loginType    = "0";
        String appNo        = Tools.getAppNo (getApplicationContext ());
        showLoading (true);
        Server.getServer (getApplicationContext ()).login (nickName, password, loginType, appNo, new HttpHandler ()
        {
            @Override
            public void onSuccess (String result)
            {
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_LOGIN);
                msg.arg1        = SUCCESS;
                msg.obj         = result;
                msg.sendToTarget ();
            }

            @Override
            public void onFail (String result)
            {
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_LOGIN);
                msg.arg1        = FAIL;
                msg.obj         = result;
                msg.sendToTarget ();
            }
        });
    }
    private void finishLogin (boolean isSuccess, Object object)
    {
        showLoading (false);
        String str  = object.toString ();
        if (isSuccess)
        {
            LoginUserInfo loginUserInfo     = ParseTools.parseLoginUserInfo (str);
            if (loginUserInfo != null && loginUserInfo.getResultCode () == 100)
            {//登录成功
                String nickName     = mLoginName.getText ().toString ();
                String password     = mLoginPassword.getText ().toString ();
                SharedPreferUtil.write (getApplicationContext (), SharedPreferUtil.LOGIN_NAME, nickName);
                boolean isChecked   = SharedPreferUtil.read(getApplicationContext (), SharedPreferUtil.APP_IS_REMEMBER).equals("true")?(true):(false);
                if (isChecked)
                {
                    SharedPreferUtil.write (getApplicationContext (), SharedPreferUtil.LOGIN_PASSWORD, password);
                }
                else
                {
                    SharedPreferUtil.write (getApplicationContext (), SharedPreferUtil.LOGIN_PASSWORD, "");
                }
                Toast.makeText (getApplicationContext (), "登录成功", Toast.LENGTH_SHORT).show ();
                LoginActivity.this.finish ();

            }
            else
            {
                if (loginUserInfo != null)
                {
                    Toast.makeText (getApplicationContext (), "登录失败:"+ loginUserInfo.getResultDesc (), Toast.LENGTH_SHORT).show ();
                }
                else
                {
                    Toast.makeText (getApplicationContext (), "登录失败", Toast.LENGTH_SHORT).show ();
                }
            }
        }
        else
        {
            Toast.makeText (getApplicationContext (), "登录失败:"+str, Toast.LENGTH_SHORT).show ();
        }
    }

    /**
     * 发起注册请求
     */
    private void doRegist ()
    {
        String nickName     = mRegistName.getText ().toString ();
        String password1    = mRegistPassword.getText ().toString ();
        String password2    = mRegistPassword2.getText ().toString ();
        if (TextUtils.isEmpty (nickName))
        {
            Toast.makeText (getApplicationContext (), "请正确输入账号", Toast.LENGTH_SHORT).show ();
            return;
        }
        if (TextUtils.isEmpty (password1) || TextUtils.isEmpty (password2))
        {
            Toast.makeText (getApplicationContext (), "请正确输入密码", Toast.LENGTH_SHORT).show ();
            return;
        }
        if (!password1.equals (password2))
        {
            Toast.makeText (getApplicationContext (), "请检查密码和确认密码是否一致", Toast.LENGTH_SHORT).show ();
            return;
        }
        String appNo        = Tools.getAppNo (getApplicationContext ());
        showLoading (true);
        Server.getServer (getApplicationContext ()).regist (nickName, password1, appNo, new HttpHandler () {
            @Override
            public void onSuccess (String result)
            {
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_REGIST);
                msg.arg1        = SUCCESS;
                msg.obj         = result;
                msg.sendToTarget ();
            }

            @Override
            public void onFail (String result)
            {
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_REGIST);
                msg.arg1        = FAIL;
                msg.obj         = result;
                msg.sendToTarget ();
            }
        });
    }
    private void finishRegist (boolean isSuccess, Object object)
    {
        showLoading (false);
        String str  = object.toString ();
        if (isSuccess)
        {
            RegistUserInfo registUserInfo = ParseTools.parseRegistUserInfo (str);
            if (registUserInfo != null && registUserInfo.getResultCode () == 100)
            {//注册成功
                String nickName     = mRegistName.getText ().toString ();
                SharedPreferUtil.write (getApplicationContext (), SharedPreferUtil.LOGIN_NAME, nickName);

                Toast.makeText (getApplicationContext (), "注册成功", Toast.LENGTH_SHORT).show ();
                showLogin ();
            }
            else
            {
                if (registUserInfo != null)
                {
                    Toast.makeText (getApplicationContext (), "注册失败:"+ registUserInfo.getResultDesc (), Toast.LENGTH_SHORT).show ();
                }
                else
                {
                    Toast.makeText (getApplicationContext (), "注册失败", Toast.LENGTH_SHORT).show ();
                }
            }
        }
        else
        {
            Toast.makeText (getApplicationContext (), "注册失败:"+str, Toast.LENGTH_SHORT).show ();
        }
    }

    /**
     * 发起重置密码
     */
    private void doForget ()
    {
        String user_id          = "";
        String mobile           = "";
        String ver_code         = "";
        String new_password     = "";

        showLoading (true);
        Server.getServer (getApplicationContext ()).resetPassword (user_id, mobile, ver_code, new_password, new HttpHandler ()
        {
            @Override
            public void onSuccess (String result)
            {
                Log.e ("tommy", "doForget onSuccess="+result);
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_RESET);
                msg.arg1        = SUCCESS;
                msg.obj         = result;
                msg.sendToTarget ();
            }

            @Override
            public void onFail (String result)
            {
                Log.e ("tommy", "getVerify onFail="+result);
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_RESET);
                msg.arg1        = FAIL;
                msg.obj         = result;
                msg.sendToTarget ();
            }
        });
    }
    private void finishReset (boolean isSuccess, Object object)
    {
        showLoading (false);
        String str  = object.toString ();
        if (isSuccess)
        {

        }
        else
        {

        }
    }

    /**
     * 获取验证码
     */
    private void getVerify ()
    {
        String mobile   = mForgetName.getText ().toString ();
        if (TextUtils.isEmpty (mobile) || mobile.length () < 11)
        {
            Toast.makeText (getApplicationContext (), "请正确输入手机号码", Toast.LENGTH_SHORT).show ();
            return;
        }

        mForgetGetverify.setEnabled(false);
        mForgetGetverify.setBackgroundResource (R.drawable.verify_enable_button);
        startForgetCount ();

        showLoading (true);
        Server.getServer (getApplicationContext ()).forgetPassword (mobile, new HttpHandler ()
        {
            @Override
            public void onSuccess (String result)
            {
                Log.e ("tommy", "getVerify onSuccess="+result);
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_VERIFY);
                msg.arg1        = SUCCESS;
                msg.obj         = result;
                msg.sendToTarget ();
            }

            @Override
            public void onFail (String result)
            {
                Log.e ("tommy", "getVerify onFail="+result);
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_VERIFY);
                msg.arg1        = FAIL;
                msg.obj         = result;
                msg.sendToTarget ();
            }
        });
    }
    private void finishVerify (boolean isSuccess, Object object)
    {
        showLoading (false);
        String str  = object.toString ();
        if (isSuccess)
        {

        }
        else
        {
            finishForgetCount ();
        }
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

    private static final int SUCCESS    = 1;
    private static final int FAIL       = 2;

    private static final int HANDLER_FORGET_UPDATECOUNT     = 1;//找回密码界面获取验证码按钮更新
    private static final int HANDLER_FINISH_LOGIN           = 2;
    private static final int HANDLER_FINISH_REGIST          = 3;
    private static final int HANDLER_FINISH_VERIFY          = 4;
    private static final int HANDLER_FINISH_RESET           = 5;
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
                    case HANDLER_FINISH_LOGIN:
                    {
                        mLoginActivity.finishLogin (msg.arg1 == SUCCESS?(true):(false), msg.obj);
                    }break;
                    case HANDLER_FINISH_REGIST:
                    {
                        mLoginActivity.finishRegist (msg.arg1 == SUCCESS?(true):(false), msg.obj);
                    }break;
                    case HANDLER_FINISH_VERIFY:
                    {
                        mLoginActivity.finishVerify (msg.arg1 == SUCCESS?(true):(false), msg.obj);
                    }break;
                    case HANDLER_FINISH_RESET:
                    {
                        mLoginActivity.finishReset (msg.arg1 == SUCCESS?(true):(false), msg.obj);
                    }break;
                }
            }
        }
    }
}
