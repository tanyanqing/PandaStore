package cn.panda.game.pandastore;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.migu.video.components.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.panda.game.pandastore.bean.DownUrlBean;
import cn.panda.game.pandastore.bean.GameDetailBean;
import cn.panda.game.pandastore.bean.GameListBean;
import cn.panda.game.pandastore.bean.ParseTools;
import cn.panda.game.pandastore.net.HttpHandler;
import cn.panda.game.pandastore.net.Server;
import cn.panda.game.pandastore.tool.GlideTools;
import cn.panda.game.pandastore.tool.MyDownTools;
import cn.panda.game.pandastore.tool.MyUserInfoSaveTools;
import cn.panda.game.pandastore.untils.ApplicationContext;


public class GameDetailActivity extends Activity
{

    private MyHandler mMyHandler;

    private String mTitle;
    private TextView mTitleView;

    private ImageView mGameIcon;
    private TextView mGameName;
    private TextView mGameTag;
    private TextView mGameSize;

    private TextView mDetailDes;

    private TextView mDetailVersion;
    private TextView mDetailSize;
    private TextView mDetailTime;

    private GameDetailBean mGameDetailBean;

    private String mGameId;
    private ProgressDialog mProgressDialog;//网络请求时的loading

    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;
    private ImageView mImage4;
    private ImageView mImage5;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_res_detail);
        mMyHandler  = new MyHandler (this);

        initView ();
        initData ();
    }
    private void initView ()
    {
        mTitleView      = (TextView)findViewById (R.id.title);

        mGameIcon       = (ImageView)findViewById (R.id.game_icon);
        mGameName       = (TextView)findViewById (R.id.game_name);
        mGameTag        = (TextView)findViewById (R.id.game_tag);
        mGameSize       = (TextView)findViewById (R.id.game_size);

        mDetailDes      = (TextView)findViewById (R.id.detail_des);

        mDetailVersion  = (TextView)findViewById (R.id.detail_version);
        mDetailSize     = (TextView)findViewById (R.id.detail_size);
        mDetailTime     = (TextView)findViewById (R.id.detail_time);

        mImage1         = (ImageView)findViewById (R.id.image1);
        mImage2         = (ImageView)findViewById (R.id.image2);
        mImage3         = (ImageView)findViewById (R.id.image3);
        mImage4         = (ImageView)findViewById (R.id.image4);
        mImage5         = (ImageView)findViewById (R.id.image5);

    }
    private void initData ()
    {
        String data                     = getIntent ().getStringExtra ("data");
        GameListBean.Game mGameData     = ParseTools.parseGame (data);
        if (mGameData != null)
        {
            mGameId         = mGameData.getId();
            Message msg     = mMyHandler.obtainMessage(HANDLER_START_GET_GAME);
            msg.obj         = mGameId;
            msg.sendToTarget();
        }

    }

    public void backButton (View view)
    {
        this.finish ();
    }
    public void downButton (View view)
    {
        mMyHandler.sendEmptyMessage(HANDLER_REQUEST_DOWNURL);
    }

    private void startGetGameDetail (Object obj)
    {
        String id   = obj.toString();
        if (!TextUtils.isEmpty(id))
        {
            Server.getServer(ApplicationContext.mAppContext).getGameDetail(id, new HttpHandler() {
                @Override
                public void onSuccess(String result)
                {
                    Message msg     = mMyHandler.obtainMessage(HANDLER_FINISH_GET_GAME);
                    msg.obj         = result;
                    msg.sendToTarget();
                }

                @Override
                public void onFail(String result)
                {
                    Message msg     = mMyHandler.obtainMessage(HANDLER_FINISH_GET_GAME);
                    msg.obj         = result;
                    msg.sendToTarget();
                }
            });
        }

    }
    private void finishGetGameDetail (Object obj)
    {
        String result   = obj.toString();
        if (!TextUtils.isEmpty(result))
        {
            mGameDetailBean  = ParseTools.parseGameDetailBean(result);
            if (mGameDetailBean != null && mGameDetailBean.getData() != null)
            {
                mTitleView.setText (mGameDetailBean.getData().getName ());
                mGameName.setText (mGameDetailBean.getData().getName ());
                mGameTag.setText (mGameDetailBean.getData().getCategory ());
                mGameSize.setText (mGameDetailBean.getData().getSize ());
                GlideTools.setImageWithGlide (GameDetailActivity.this, mGameDetailBean.getData().getIcon (), mGameIcon, false);

                mDetailDes.setText(mGameDetailBean.getData().getDescription());

                mDetailVersion.setText(mGameDetailBean.getData().getVersion());
                mDetailSize.setText(mGameDetailBean.getData().getSize());
                mDetailTime.setText(mGameDetailBean.getData().getOpt_time());

                setImage (mImage1, mGameDetailBean.getData().getShow_pic1 ());
                setImage (mImage2, mGameDetailBean.getData().getShow_pic2 ());
                setImage (mImage3, mGameDetailBean.getData().getShow_pic3 ());
                setImage (mImage4, mGameDetailBean.getData().getShow_pic4 ());
                setImage (mImage5, mGameDetailBean.getData().getShow_pic5 ());
            }
            else
            {
                showErr();
            }
        }
        else
        {
            showErr ();
        }
    }
    private void setImage (ImageView view, String url)
    {
        if (!TextUtils.isEmpty (url))
        {
            view.setVisibility (View.VISIBLE);
            GlideTools.setImageWithGlide (getApplicationContext (), url, view, true);
        }
        else
        {
            view.setVisibility (View.GONE);
        }
    }
    private void showErr ()
    {
        Toast.makeText(GameDetailActivity.this, "游戏信息错误", Toast.LENGTH_SHORT).show();
        GameDetailActivity.this.finish();
    }

    private void getDownUrl ()
    {
        showLoading (true);
        Server.getServer(ApplicationContext.mAppContext).getDownUrl(MyUserInfoSaveTools.getUserId(), mGameId, new HttpHandler() {
            @Override
            public void onSuccess(String result)
            {
                Message msg     = mMyHandler.obtainMessage(HANDLER_FINISH_DOWNURL);
                msg.obj         = result;
                mMyHandler.sendMessageDelayed(msg, 3000);
            }

            @Override
            public void onFail(String result)
            {
                Message msg     = mMyHandler.obtainMessage(HANDLER_FINISH_DOWNURL);
                msg.obj         = result;
                mMyHandler.sendMessageDelayed(msg, 3000);
            }
        });
    }
    private void finishGetDownUrl (Object obj)
    {
        showLoading (false);
        String result  = obj.toString();
        if (!TextUtils.isEmpty(result))
        {
            Log.e("tommy","result="+result);
            DownUrlBean downUrlBean     = ParseTools.parseDownUrlBean(result, mGameId);
            if (downUrlBean != null)
            {
                if (downUrlBean.getResultCode() == 100)
                {
                    String url  = downUrlBean.getData().getDownload_url();
                    String id   = downUrlBean.getData().getId();
                    if (TextUtils.isEmpty(url))
                    {
                        Toast.makeText(GameDetailActivity.this, "请求下载失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {//开启下载
                        MyDownTools.downloadApk(ApplicationContext.mAppContext, url, id+".apk", mGameDetailBean.getData().getName());
                        Toast.makeText(GameDetailActivity.this, "下载任务已添加到任务栏", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(GameDetailActivity.this, downUrlBean.getResultDesc(), Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(GameDetailActivity.this, "请求下载失败，请重试", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(GameDetailActivity.this, "请求下载失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLoading (boolean isShow)
    {
        if (mProgressDialog == null)
        {
            mProgressDialog     = new ProgressDialog(GameDetailActivity.this);
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

    private final static int HANDLER_START_GET_GAME     = 1;
    private final static int HANDLER_FINISH_GET_GAME    = 2;
    private final static int HANDLER_REQUEST_DOWNURL    = 3;
    private final static int HANDLER_FINISH_DOWNURL     = 4;
    private static class MyHandler extends Handler
    {

        private final WeakReference<GameDetailActivity> mGameDetailActivitys;

        private MyHandler (GameDetailActivity mGameDetailActivity)
        {
            this.mGameDetailActivitys   = new WeakReference<GameDetailActivity> (mGameDetailActivity);
        }

        @Override
        public void handleMessage (Message msg)
        {
            super.handleMessage (msg);
            GameDetailActivity mGameDetailActivity    = mGameDetailActivitys.get ();
            if (mGameDetailActivity != null)
            {
                switch (msg.what)
                {
                    case HANDLER_START_GET_GAME:
                    {
                        mGameDetailActivity.startGetGameDetail(msg.obj);
                    }break;
                    case HANDLER_FINISH_GET_GAME:
                    {
                        mGameDetailActivity.finishGetGameDetail(msg.obj);
                    }break;
                    case HANDLER_REQUEST_DOWNURL:
                    {
                        mGameDetailActivity.getDownUrl();
                    }break;
                    case HANDLER_FINISH_DOWNURL:
                    {
                        mGameDetailActivity.finishGetDownUrl(msg.obj);
                    }break;
                }
            }
        }
    }
}
