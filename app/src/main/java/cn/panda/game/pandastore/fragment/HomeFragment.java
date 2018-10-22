package cn.panda.game.pandastore.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.panda.game.pandastore.R;

public class HomeFragment extends Fragment
{
    private View mRootView;
    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
//        mRootView           = inflater.inflate(R.layout.fragment_home, null);
//        return mRootView;

        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
        return mRootView;
    }
}
