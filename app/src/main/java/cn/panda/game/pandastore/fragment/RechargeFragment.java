package cn.panda.game.pandastore.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.panda.game.pandastore.R;
import cn.panda.game.pandastore.tool.MyUserInfoSaveTools;
import cn.panda.game.pandastore.tool.RouteTool;

public class RechargeFragment  extends Fragment implements View.OnClickListener
{
    private View mRootView;

    private View mLoginTipView;
    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mRootView           = inflater.inflate(R.layout.fragment_recharge, null);
        initView ();
        initData ();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        reflushView ();
    }

    private void initView ()
    {
        mLoginTipView   = mRootView.findViewById(R.id.login_tip_view);
        mRootView.findViewById(R.id.recharge_sign_in).setOnClickListener(this);
    }

    private void initData ()
    {
        reflushView ();
    }
    private void reflushView ()
    {
        String usetId   = MyUserInfoSaveTools.getUserId();
        if (!TextUtils.isEmpty(usetId))
        {
            mLoginTipView.setVisibility(View.GONE);
        }
        else
        {
            mLoginTipView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.recharge_sign_in:
            {
                RouteTool.jumpLogin(getActivity());
            }break;
        }
    }
}