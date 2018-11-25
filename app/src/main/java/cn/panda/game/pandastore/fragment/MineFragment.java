package cn.panda.game.pandastore.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.panda.game.pandastore.R;
import cn.panda.game.pandastore.tool.MyUserInfoSaveTools;
import cn.panda.game.pandastore.tool.RouteTool;

public class MineFragment extends Fragment implements View.OnClickListener
{
    private View mRootView;

    private View mLayoutNotLogin;

    private TextView mBalanceAccount;//账户余额
    private TextView mBalanceGame;//游戏余额
    private TextView mBalanceRedPacket;//红包余额
    private TextView mCoupon;//卡券
    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mRootView           = inflater.inflate(R.layout.fragment_mine, null);
        initView ();
        initData ();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
//        reflushData ();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.avatar_view:
            {
                clickAvatar ();
            }break;
            case R.id.personal_order_manager:
            {
                personalOrder ();
            }break;
            case R.id.layout_coupon:
            {
                showCoupon ();
            }break;
            case R.id.custom_service:
            {
                customService ();
            }break;
            case R.id.feedback:
            {
                feedback ();
            }
            break;
            case R.id.about_us:
            {
                aboutUs ();
            }break;
        }
    }

    private void initView ()
    {
        mRootView.findViewById(R.id.avatar_view).setOnClickListener(this);
        mLayoutNotLogin     = mRootView.findViewById(R.id.layout_not_login);

        mRootView.findViewById(R.id.personal_order_manager).setOnClickListener(this);

        mBalanceAccount     = (TextView)mRootView.findViewById(R.id.balance_account);
        mBalanceGame        = (TextView)mRootView.findViewById(R.id.balance_game);
        mBalanceRedPacket   = (TextView)mRootView.findViewById(R.id.balance_red_packet);
        mRootView.findViewById(R.id.layout_coupon).setOnClickListener(this);
        mCoupon             = (TextView)mRootView.findViewById(R.id.coupon);

        mRootView.findViewById(R.id.custom_service).setOnClickListener(this);
        mRootView.findViewById(R.id.feedback).setOnClickListener(this);
        mRootView.findViewById(R.id.about_us).setOnClickListener(this);
    }

    private void initData ()
    {
        reflushData ();
    }
    private void reflushData ()
    {
        if (mLayoutNotLogin != null)
        {
            mLayoutNotLogin.setVisibility(MyUserInfoSaveTools.isLogin() ?(View.INVISIBLE):(View.VISIBLE));
        }


        if (MyUserInfoSaveTools.isLogin())
        {
            if (mBalanceAccount != null)
            {
                mBalanceAccount.setVisibility(View.VISIBLE);
                mBalanceAccount.setText(MyUserInfoSaveTools.getCoinCount());
            }

            if (mBalanceGame != null)
            {
                mBalanceGame.setVisibility(View.VISIBLE);
                mBalanceGame.setText(MyUserInfoSaveTools.getAppCoin());
            }

        }
        else
        {
            if (mBalanceAccount != null)
            {
                mBalanceAccount.setVisibility(View.INVISIBLE);
            }
            if (mBalanceGame != null)
            {
                mBalanceGame.setVisibility(View.INVISIBLE);
            }

        }
    }

    private void clickAvatar ()
    {
        if (MyUserInfoSaveTools.isLogin())
        {//签到

        }
        else
        {
            RouteTool.jumpLogin(getActivity());
        }
    }

    /**
     * 充值记录
     */
    private void personalOrder ()
    {

    }

    /**
     * 显示卡券
     */
    private void showCoupon ()
    {

    }

    /**
     * 联系客服
     */
    private void customService ()
    {

    }

    /**
     * 投诉建议
     */
    private void feedback ()
    {

    }

    /**
     * 关于我们
     */
    private void aboutUs ()
    {

    }
}
