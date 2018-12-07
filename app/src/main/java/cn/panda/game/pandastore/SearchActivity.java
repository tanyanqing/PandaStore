package cn.panda.game.pandastore;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cn.panda.game.pandastore.net.HttpHandler;
import cn.panda.game.pandastore.net.Server;
import cn.panda.game.pandastore.untils.ApplicationContext;


public class SearchActivity extends Activity
{

    private MyHandler mMyHandler;

    private EditText mSearchEditText;
    private View mSearchButton;

    private View mResultView;
    private RecyclerView mContiner;
    private View mErrView;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_search);
        mMyHandler  = new MyHandler (this);

        initView ();
    }
    private void initView ()
    {
        mSearchEditText     = (EditText)findViewById (R.id.search_edittext);
        mSearchButton       = findViewById (R.id.search_button);

        mResultView         = findViewById (R.id.result_view);
        mContiner           = (RecyclerView)findViewById (R.id.continer);
        mErrView            = findViewById (R.id.err_view);

        mResultView.setVisibility (View.GONE);
    }

    private void showErr ()
    {
        mResultView.setVisibility (View.VISIBLE);
        mErrView.setVisibility (View.VISIBLE);
        mContiner.setVisibility (View.GONE);
    }
    private void showContiner ()
    {
        mResultView.setVisibility (View.VISIBLE);
        mContiner.setVisibility (View.VISIBLE);
        mErrView.setVisibility (View.GONE);
    }

    private void showLoading (boolean isShow)
    {
        if (mProgressDialog == null)
        {
            mProgressDialog     = new ProgressDialog (SearchActivity.this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            mProgressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
            mProgressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            mProgressDialog.setMessage("搜索中，请稍等...");
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
    public void searchButton (View view)
    {
        mMyHandler.sendEmptyMessage (HANDLER_START_SEARCH);
    }

    private void search ()
    {
        String searchValue  = mSearchEditText.getText ().toString ();
        if (TextUtils.isEmpty (searchValue))
        {
            Toast.makeText (SearchActivity.this, "请输入搜索条件", Toast.LENGTH_SHORT).show ();
            mSearchButton.setClickable (true);
            return;
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchEditText.getWindowToken(), 0);

        showLoading (true);

        Server.getServer (ApplicationContext.mAppContext).getSearch (searchValue, new HttpHandler () {
            @Override
            public void onSuccess (String result) {
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_SEARCH);
                msg.obj         = result;
                mMyHandler.sendMessageDelayed (msg, 1000);
            }

            @Override
            public void onFail (String result)
            {
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_SEARCH);
                msg.obj         = result;
                mMyHandler.sendMessageDelayed (msg, 1000);
            }
        });
    }
    private void finishSearch (String str)
    {
        showLoading (false);

    }

    private final static int HANDLER_START_SEARCH       = 1;
    private final static int HANDLER_FINISH_SEARCH      = 2;
    private static class MyHandler extends Handler
    {

        private final WeakReference<SearchActivity> mSearchActivitys;

        private MyHandler (SearchActivity mSearchActivity)
        {
            this.mSearchActivitys   = new WeakReference<SearchActivity> (mSearchActivity);
        }

        @Override
        public void handleMessage (Message msg)
        {
            super.handleMessage (msg);
            SearchActivity mSearchActivity    = mSearchActivitys.get ();
            if (mSearchActivity != null)
            {
                switch (msg.what)
                {
                    case HANDLER_START_SEARCH:
                    {
                        mSearchActivity.search ();
                    }break;
                    case HANDLER_FINISH_SEARCH:
                    {
                        mSearchActivity.finishSearch ((String)msg.obj);
                    }break;
                }
            }
        }
    }


}
