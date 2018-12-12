package cn.panda.game.pandastore.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.panda.game.pandastore.R;
import cn.panda.game.pandastore.adapter.GameListAdapter;
import cn.panda.game.pandastore.bean.DownUrlBean;
import cn.panda.game.pandastore.bean.GameListBean;
import cn.panda.game.pandastore.bean.ParseTools;
import cn.panda.game.pandastore.bean.SearchBean;
import cn.panda.game.pandastore.net.HttpHandler;
import cn.panda.game.pandastore.net.Server;
import cn.panda.game.pandastore.tool.InitRecyclerViewLayout;
import cn.panda.game.pandastore.tool.MyDownTools;
import cn.panda.game.pandastore.tool.MyUserInfoSaveTools;
import cn.panda.game.pandastore.untils.ApplicationContext;

public class DiscoveryFragment extends Fragment
{
    private Context mContext;
    private MyHandler mMyHandler;
    private View mRootView;

    private Animation mFadeIn;
    private Animation mFadeOut;

    private View mLoadingView;

    private RecyclerView mContiner;
    private GameListAdapter mAdapter;
    private List<GameListBean.Page> dataList;

    private int pageIndex;
    private int pageSize;
    private boolean isRequesting    = false;
    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mContext        = getContext();
        mMyHandler      = new MyHandler (this);
        mRootView       = inflater.inflate(R.layout.fragment_discovery, container, false);
        init ();
        return mRootView;
    }
    private void init ()
    {
        mFadeIn         = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        mFadeOut        = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        mLoadingView    = mRootView.findViewById(R.id.loading_view);

        mContiner   = (RecyclerView)mRootView.findViewById (R.id.continer);
        InitRecyclerViewLayout.initLinearLayoutVERTICAL (mContext, mContiner);
        dataList    = new ArrayList<>();
        mAdapter    = new GameListAdapter (mContext, dataList, downClickListener);
        mContiner.setAdapter (mAdapter);

        pageIndex   = 0;
        mContiner.addOnScrollListener (new RecyclerView.OnScrollListener ()
        {
            @Override
            public void onScrollStateChanged (RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged (recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !isRequesting)
                {
                    pageIndex ++;
                    if (pageIndex < pageSize)
                    {
                        mMyHandler.sendEmptyMessage (HANDLER_START_GET_LIST);
                    }
                }
            }
        });

        pageIndex   = 0;
        showLoading();
        mMyHandler.sendEmptyMessage (HANDLER_START_GET_LIST);
    }

    private String mGameId;
    private String mGameName;
    private ProgressDialog mProgressDialog;
    private View.OnClickListener downClickListener  = new View.OnClickListener () {
        @Override
        public void onClick (View v)
        {
            showLoading (true);

            GameListBean.Game data    = (GameListBean.Game)v.getTag ();
            if (data != null)
            {
                mGameId     = data.getId ();
                mGameName   = data.getName ();
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

        }
    };
    private void finishGetDownUrl (Object obj)
    {
        showLoading (false);
        String result  = obj.toString();
        if (!TextUtils.isEmpty(result))
        {
            DownUrlBean downUrlBean     = ParseTools.parseDownUrlBean(result, mGameId);
            if (downUrlBean != null)
            {
                if (downUrlBean.getResultCode() == 100)
                {
                    String url  = downUrlBean.getData().getDownload_url();
                    String id   = downUrlBean.getData().getId();
                    if (TextUtils.isEmpty(url))
                    {
                        Toast.makeText(getActivity (), "请求下载失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {//开启下载
                        MyDownTools.getInstance ().downloadApk(url, id+".apk", mGameName);
                        Toast.makeText(getActivity (), "下载任务已添加到任务栏", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity (), downUrlBean.getResultDesc(), Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(getActivity (), "请求下载失败，请重试", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getActivity (), "请求下载失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }
    private void showLoading (boolean isShow)
    {
        if (mProgressDialog == null)
        {
            mProgressDialog     = new ProgressDialog (getActivity ());
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

    private void requestGame ()
    {
        isRequesting    = true;
        Server.getServer (ApplicationContext.mAppContext).getDiscover (pageIndex, 10, new HttpHandler() {
            @Override
            public void onSuccess (String result)
            {
                GameListBean gameListBean   = ParseTools.parseGameListBean (result);
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_GET_LIST);
                msg.obj         = gameListBean;
                msg.sendToTarget ();
            }

            @Override
            public void onFail (String result)
            {
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_GET_LIST);
                msg.sendToTarget ();
            }
        });
    }
    private void finishRequestGame (Object obj)
    {
        finishLoading ();
        isRequesting    = false;
        if (obj != null)
        {
            GameListBean gameListBean   = (GameListBean)obj;
            if (gameListBean != null && gameListBean.getData () != null && gameListBean.getData ().getPage_data() != null)
            {
                for (int i = 0; i < gameListBean.getData ().getPage_data().size(); i ++)
                {
                    dataList.add (gameListBean.getData ().getPage_data ().get (i));
                }
                pageSize    = gameListBean.getData ().getPage_size ();
                pageIndex   = gameListBean.getData ().getCurrent_page ();
                mAdapter.notifyDataSetChanged ();
            }
        }
    }

    private void showLoading ()
    {
        mLoadingView.setVisibility(View.VISIBLE);
        mContiner.setVisibility(View.GONE);
    }
    private void finishLoading ()
    {
        if (mLoadingView.getVisibility() != View.GONE)
        {
            mLoadingView.setVisibility(View.GONE);
            mLoadingView.startAnimation(mFadeOut);
        }

        if (mContiner.getVisibility() != View.VISIBLE)
        {
            mContiner.setVisibility(View.VISIBLE);
            mContiner.startAnimation(mFadeIn);
        }

    }

    private final static int HANDLER_START_GET_LIST     = 0;
    private final static int HANDLER_FINISH_GET_LIST    = 1;
    private final static int HANDLER_FINISH_DOWNURL     = 2;
    private static class MyHandler extends Handler
    {

        private final WeakReference<DiscoveryFragment> mDiscoveryFragments;

        private MyHandler (DiscoveryFragment mDiscoveryFragment)
        {
            this.mDiscoveryFragments   = new WeakReference<DiscoveryFragment> (mDiscoveryFragment);
        }

        @Override
        public void handleMessage (Message msg)
        {
            super.handleMessage (msg);
            DiscoveryFragment mDiscoveryFragment    = mDiscoveryFragments.get ();
            if (mDiscoveryFragment != null)
            {
                switch (msg.what)
                {
                    case HANDLER_START_GET_LIST:
                    {
                        mDiscoveryFragment.requestGame ();
                    }break;
                    case HANDLER_FINISH_GET_LIST:
                    {
                        mDiscoveryFragment.finishRequestGame (msg.obj);
                    }break;
                    case HANDLER_FINISH_DOWNURL:
                    {
                        mDiscoveryFragment.finishGetDownUrl (msg.obj);
                    }break;
                }
            }
        }
    }
}
