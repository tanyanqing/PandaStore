package cn.panda.game.pandastore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.migu.video.components.glide.Glide;

import java.lang.ref.WeakReference;

import cn.panda.game.pandastore.bean.GameListBean;
import cn.panda.game.pandastore.bean.ParseTools;
import cn.panda.game.pandastore.tool.GlideTools;


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

    private GameListBean.Game mGameData;
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
        String data     = getIntent ().getStringExtra ("data");
        mGameData       = ParseTools.parseGame (data);
        if (mGameData != null)
        {
            mTitleView.setText (mGameData.getName ());
            mGameName.setText (mGameData.getName ());
            mGameTag.setText (mGameData.getCategory ());
            mGameSize.setText (mGameData.getSize ());
            GlideTools.setImageWithGlide (GameDetailActivity.this, mGameData.getIcon (), mGameIcon);

            mDetailDes.setText (mGameData.getDescription ());

            mDetailVersion.setText (mGameData.getVersion ());
            mDetailSize.setText (mGameData.getSize ());
//            mDetailTime.setText (mGameData.get);
        }

    }

    public void backButton (View view)
    {
        this.finish ();
    }
    public void downButton (View view)
    {

    }


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
                }
            }
        }
    }
}
