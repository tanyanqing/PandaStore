package cn.panda.game.pandastore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.migu.video.components.glide.Glide;

import java.lang.ref.WeakReference;

import cn.panda.game.pandastore.bean.GameDetailBean;
import cn.panda.game.pandastore.bean.GameListBean;
import cn.panda.game.pandastore.bean.ParseTools;
import cn.panda.game.pandastore.net.HttpHandler;
import cn.panda.game.pandastore.net.Server;
import cn.panda.game.pandastore.tool.GlideTools;
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

    }
    private void initData ()
    {
        String data                     = getIntent ().getStringExtra ("data");
        GameListBean.Game mGameData     = ParseTools.parseGame (data);
        if (mGameData != null)
        {
            Message msg     = mMyHandler.obtainMessage(HANDLER_START_GET_GAME);
            msg.obj         = mGameData.getId();
            msg.sendToTarget();
        }

    }

    public void backButton (View view)
    {
        this.finish ();
    }
    public void downButton (View view)
    {

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
                GlideTools.setImageWithGlide (GameDetailActivity.this, mGameDetailBean.getData().getIcon (), mGameIcon);

                mDetailDes.setText(mGameDetailBean.getData().getDescription());

                mDetailVersion.setText(mGameDetailBean.getData().getVersion());
                mDetailSize.setText(mGameDetailBean.getData().getSize());
                mDetailTime.setText(mGameDetailBean.getData().getVersion());
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
    private void showErr ()
    {

    }


    private final static int HANDLER_START_GET_GAME     = 1;
    private final static int HANDLER_FINISH_GET_GAME    = 2;
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
                }
            }
        }
    }
}
