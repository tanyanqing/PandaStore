package cn.panda.game.pandastore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.panda.game.pandastore.R;
import cn.panda.game.pandastore.net.HttpHandler;
import cn.panda.game.pandastore.net.Server;
import cn.panda.game.pandastore.untils.ApplicationContext;

public class HomeFragment extends Fragment
{
    private View mRootView;
    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        init ();
        return mRootView;
    }

    private void init ()
    {
        Server.getServer (ApplicationContext.mAppContext).getGameList (0, 10, new HttpHandler () {
            @Override
            public void onSuccess (String result)
            {
                Log.e ("tommy","getGameList onSuccess="+result);
            }

            @Override
            public void onFail (String result) {
                Log.e ("tommy","getGameList onFail="+result);
            }
        });
    }
}
