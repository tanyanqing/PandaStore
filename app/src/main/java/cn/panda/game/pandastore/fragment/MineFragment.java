package cn.panda.game.pandastore.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import cn.panda.game.pandastore.LoginActivity;
import cn.panda.game.pandastore.R;
import cn.panda.game.pandastore.bean.OrderBean;
import cn.panda.game.pandastore.bean.ParseTools;
import cn.panda.game.pandastore.net.HttpHandler;
import cn.panda.game.pandastore.net.Server;
import cn.panda.game.pandastore.tool.MyUserInfoSaveTools;
import cn.panda.game.pandastore.tool.RouteTool;
import cn.panda.game.pandastore.untils.ApplicationContext;
import cn.qqtheme.framework.picker.DatePicker;

public class MineFragment extends Fragment implements View.OnClickListener
{
    private View mRootView;
    private MyHandler mMyHandler;

    private View mLayoutNotLogin;

    private TextView mBalanceAccount;//账户余额
    private TextView mBalanceGame;//游戏余额
    private TextView mBalanceRedPacket;//红包余额
    private TextView mCoupon;//卡券

    private View mChangeAccount;

    private ProgressDialog mProgressDialog;//网络请求时的loading
    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mRootView           = inflater.inflate(R.layout.fragment_mine, null);
        mMyHandler          = new MyHandler (this);
        initView ();
        initData ();
        return mRootView;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        reflushData ();
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
            case R.id.change_account:
            {
                changeAccount ();
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
        mChangeAccount  = mRootView.findViewById(R.id.change_account);
        mChangeAccount.setOnClickListener(this);
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

            if (mChangeAccount !=  null)
            {
                mChangeAccount.setVisibility(View.VISIBLE);
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
            if (mChangeAccount !=  null)
            {
                mChangeAccount.setVisibility(View.INVISIBLE);
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
        if (MyUserInfoSaveTools.isLogin ())
        {
            onYearMonthPicker ();
        }
        else
        {
            Toast.makeText (getContext (), "请先登录", Toast.LENGTH_SHORT).show ();
        }

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

    /**
     * 切换账号
     */
    private void changeAccount ()
    {
        RouteTool.jumpLogin(getActivity());
    }


    /**
     * 显示年月选择器
     */
    private void onYearMonthPicker()
    {
        Calendar calendar   = Calendar.getInstance();
        DatePicker picker   = new DatePicker (getActivity (), DatePicker.YEAR_MONTH);
        picker.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        picker.setWidth((int) (picker.getScreenWidthPixels() * 0.6));
        picker.setRangeStart(2016, 10, 14);
        picker.setRangeEnd(2020, 11, 11);
        picker.setSelectedItem(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthPickListener()
        {
            @Override
            public void onDatePicked(String year, String month)
            {
                Message msg     = mMyHandler.obtainMessage (HANDLER_START_GET_ORDER);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append ("user_id=").append (TextUtils.isEmpty(MyUserInfoSaveTools.getUserId ())?("guest"):(MyUserInfoSaveTools.getUserId ()));

//                stringBuffer.append ("user_id=").append ("5bc73858fddcaa59cc1141b5");
                stringBuffer.append ("&year=").append (year);
                stringBuffer.append ("&month=").append (month);
                msg.obj     = stringBuffer;
                msg.sendToTarget ();
            }
        });
        picker.show();
    }

    private void requestOrder (Object obj)
    {
        StringBuffer params = (StringBuffer)obj;
        if (params != null)
        {
            showLoading (true);
            Server.getServer (getContext ()).getOrderStatements (params, new HttpHandler ()
            {
                @Override
                public void onSuccess (String result)
                {
                    Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_GET_ORDER);
                    msg.obj         = result;
                    msg.sendToTarget ();
                }

                @Override
                public void onFail (String result)
                {
                    Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_GET_ORDER);
                    msg.obj         = result;
                    msg.sendToTarget ();
                }
            });
        }
    }
    private void finishRequestOrder (Object obj)
    {
        showLoading (false);
        String result  = (String)obj;
        if (!TextUtils.isEmpty (result))
        {
            OrderBean mOrderBean    = ParseTools.parseOrderBean (result);
            if (mOrderBean != null)
            {
                String[] items  = new String[]{};
                for (int i = 0; i < mOrderBean.getData ().size (); i ++)
                {
                    OrderBean.Data data     = mOrderBean.getData ().get (i);
                    String str  = "商品:"+data.getGoods ()+"\n"+"总价:"+data.getAmount ()+"\n"+"方式:"+data.getPay_channel ()+"\n"+"时间:"+data.getTime ();
                    items = Arrays.copyOf(items, items.length+1);
                    items[i]    = str;
                }
                Message msg     = mMyHandler.obtainMessage (HANDLER_SHOW_ORDER);
                msg.obj         = items;
                msg.sendToTarget ();
            }
            else
            {
                Toast.makeText (getContext (), result, Toast.LENGTH_SHORT).show ();
            }
        }
    }

    private void showOrder (final String items[])
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity (), 3);
        builder.setTitle("交易记录");
        if (items.length == 0)
        {
            builder.setMessage("本月您还没有任何交易记录");
        }
        else
        {
            builder.setItems(items, null);

        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    private void showLoading (boolean isShow)
    {
        if (mProgressDialog == null)
        {
            mProgressDialog     = new ProgressDialog (getActivity ());
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            mProgressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
            mProgressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            mProgressDialog.setMessage("数据请求中，请稍等...");
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


    private final static int HANDLER_START_GET_ORDER    = 1;
    private final static int HANDLER_FINISH_GET_ORDER   = 2;
    private final static int HANDLER_SHOW_ORDER         = 3;
    private static class MyHandler extends Handler
    {

        private final WeakReference<MineFragment> mMineFragments;

        private MyHandler (MineFragment mMineFragment)
        {
            this.mMineFragments   = new WeakReference<MineFragment> (mMineFragment);
        }

        @Override
        public void handleMessage (Message msg)
        {
            super.handleMessage (msg);
            MineFragment mMineFragment    = mMineFragments.get ();
            if (mMineFragment != null)
            {
                switch (msg.what)
                {
                    case HANDLER_START_GET_ORDER:
                    {
                        mMineFragment.requestOrder (msg.obj);
                    }break;
                    case HANDLER_FINISH_GET_ORDER:
                    {
                        mMineFragment.finishRequestOrder (msg.obj);
                    }break;
                    case HANDLER_SHOW_ORDER:
                    {
                        mMineFragment.showOrder ((String[])msg.obj);
                    }break;
                }
            }
        }
    }
}
