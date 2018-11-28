package cn.panda.game.pandastore.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.panda.game.pandastore.LoginActivity;
import cn.panda.game.pandastore.R;
import cn.panda.game.pandastore.bean.OrderBean;
import cn.panda.game.pandastore.bean.ParseTools;
import cn.panda.game.pandastore.broadcast.BroadcastCallback;
import cn.panda.game.pandastore.broadcast.BroadcastConstant;
import cn.panda.game.pandastore.broadcast.MyBroadcastReceiver;
import cn.panda.game.pandastore.net.HttpHandler;
import cn.panda.game.pandastore.net.Server;
import cn.panda.game.pandastore.tool.MyDialog;
import cn.panda.game.pandastore.tool.MyUserInfoSaveTools;
import cn.panda.game.pandastore.tool.RouteTool;
import cn.panda.game.pandastore.untils.ApplicationContext;
import cn.qqtheme.framework.picker.DatePicker;

public class MineFragment extends Fragment implements View.OnClickListener, BroadcastCallback
{
    private View mRootView;
    private MyHandler mMyHandler;

    private View mLayoutNotLogin;
    private TextView mLoginName;

    private TextView mBalanceAccount;//账户余额
    private TextView mBalanceGame;//游戏余额
    private TextView mBalanceRedPacket;//红包余额
    private TextView mCoupon;//卡券

    private View mChangeAccount;

    private ProgressDialog mProgressDialog;//网络请求时的loading


    /**SUGGEST DIALOG*/
    private MyDialog mSuggestDailog;
    private Spinner mSpinner;
    private ArrayAdapter<String> mSpinnerAdapter;

    private EditText mSuggest;
    private EditText mContact;

    private MyBroadcastReceiver mMyBroadcastReceiver;
    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mRootView           = inflater.inflate(R.layout.fragment_mine, null);
        mMyHandler          = new MyHandler (this);
        mMyBroadcastReceiver    = new MyBroadcastReceiver (this);

        initView ();
        initData ();
        return mRootView;
    }

    /**
     * 广播回调
     */
    @Override
    public void callback ()
    {
        if (mMyHandler != null)
        {
            mMyHandler.sendEmptyMessage (HANDLER_RECEIVE_BROADCAST);
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        reflushData ();
        try
        {
            if (mMyBroadcastReceiver != null)
            {
                IntentFilter intentFilter   = new IntentFilter ();
                intentFilter.addAction(BroadcastConstant.ACTION_FILTER);
                getActivity().registerReceiver(mMyBroadcastReceiver,intentFilter);
            }
        }
        catch (Exception e)
        {

        }


    }

    @Override
    public void onPause ()
    {
        try
        {
            if (mMyBroadcastReceiver != null)
            {
                getActivity().unregisterReceiver (mMyBroadcastReceiver);
            }
        }
        catch (Exception e)
        {

        }
        super.onPause ();

    }

    @Override
    public void onDestroy ()
    {
        super.onDestroy ();
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

        mLoginName          = (TextView)mRootView.findViewById (R.id.tv_personal_username);

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
        List<String> mSpinnerList = new ArrayList<String> ();
        mSpinnerList.add("QQ");
        mSpinnerList.add("微信");
        mSpinnerList.add("邮件");
        mSpinnerAdapter     = new ArrayAdapter<String> (getContext (), android.R.layout.simple_spinner_item, mSpinnerList);
        mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        reflushData ();
    }
    private void reflushData ()
    {
        if (mLayoutNotLogin != null)
        {
            mLayoutNotLogin.setVisibility(MyUserInfoSaveTools.isLogin() ?(View.GONE):(View.VISIBLE));
        }


        if (MyUserInfoSaveTools.isLogin())
        {
            if (mLoginName != null)
            {
                mLoginName.setVisibility (View.VISIBLE);
                mLoginName.setText (MyUserInfoSaveTools.getNickName ());
            }
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
            if (mLoginName != null)
            {
                mLoginName.setVisibility (View.GONE);
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
        if (MyUserInfoSaveTools.isLogin ())
        {
            showFeedbackDialog ();
        }
        else
        {
            Toast.makeText (getContext (), "请先登录", Toast.LENGTH_SHORT).show ();
        }
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
     * 投诉建议输入对话框
     */
    private void showFeedbackDialog ()
    {
        View view   = getActivity ().getLayoutInflater().inflate(R.layout.dialog_suggest, null);

        mSuggest    = (EditText)view.findViewById (R.id.suggest);
        mContact    = (EditText)view.findViewById (R.id.contact);

        mSpinner    = (Spinner)view.findViewById (R.id.spinner);
        mSpinner.setAdapter(mSpinnerAdapter);

        mSuggestDailog = new MyDialog(getContext (), view);
        mSuggestDailog.setCancelable(true);
        mSuggestDailog.setCanceledOnTouchOutside (false);
        mSuggestDailog.show();

        mSpinner.setOnItemSelectedListener (new AdapterView.OnItemSelectedListener ()
        {
            @Override
            public void onItemSelected (AdapterView<?> adapterView, View view, int i, long l)
            {
                mContact.setText ("");
            }

            @Override
            public void onNothingSelected (AdapterView<?> adapterView)
            {
            }
        });

        view.findViewById (R.id.cancel).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view)
            {
                mSuggestDailog.dismiss ();
            }
        });
        view.findViewById (R.id.send).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view)
            {
                String suggest  = mSuggest.getText ().toString ();
                if (TextUtils.isEmpty (suggest))
                {
                    Toast.makeText (getContext (), "请输入投诉建议", Toast.LENGTH_SHORT).show ();
                    return;
                }
                String contact  = mContact.getText ().toString ();
                if (TextUtils.isEmpty (contact))
                {
                    Toast.makeText (getContext (), "请输入联系方式", Toast.LENGTH_SHORT).show ();
                    return;
                }
                String contactType  = mSpinner.getSelectedItem ().toString ();

                StringBuffer params     = new StringBuffer ();
                try
                {
                    params.append ("user_id=").append (MyUserInfoSaveTools.getUserId ());
                    params.append ("&user_name=").append (URLEncoder.encode (MyUserInfoSaveTools.getNickName (),"UTF-8"));
                    params.append ("&suggest=").append (URLEncoder.encode (suggest,"UTF-8"));
                    params.append ("&contact=").append (URLEncoder.encode (contact,"UTF-8"));
                    params.append ("&contact_type=").append (contactType);
                }
                catch (Exception e)
                {
                    e.printStackTrace ();
                }
                Message msg     = mMyHandler.obtainMessage (HANDLER_SEND_SUGGEST);
                msg.obj         = params;
                msg.sendToTarget ();
//                mSuggestDailog.dismiss ();
            }
        });
    }
    private void sendSuggest (Object obj)
    {
        showLoading (true);
        StringBuffer params = (StringBuffer)obj;
        if (params != null)
        {
            Server.getServer (getContext ()).getSuggest (params, new HttpHandler ()
            {
                @Override
                public void onSuccess (String result)
                {
                    Message msg     = mMyHandler.obtainMessage (HANDLER_FINSISH_SUGGEST);
                    if (ParseTools.isSuccess (result))
                    {
                        msg.obj     = "提交完成";
                        msg.arg1    = 1;
                    }
                    else
                    {
                        msg.obj     = "提交失败，请重试";
                        msg.arg1    = 0;
                    }
                    msg.sendToTarget ();
                }

                @Override
                public void onFail (String result)
                {
                    Message msg     = mMyHandler.obtainMessage (HANDLER_FINSISH_SUGGEST);
                    msg.obj     = "提交失败，请重试";
                    msg.arg1    = 0;
                    msg.sendToTarget ();
                }
            });
        }
    }
    private void finishSuggest (String str, boolean isSuccess)
    {
        showLoading (false);
        Toast.makeText (ApplicationContext.mAppContext, str, Toast.LENGTH_SHORT).show ();
        if (isSuccess)
        {
            mSuggestDailog.dismiss ();
        }

    }
    /**
     * 显示年月选择器
     */
    private void onYearMonthPicker()
    {
        Calendar calendar   = Calendar.getInstance();
        DatePicker picker   = new DatePicker (getActivity (), DatePicker.YEAR_MONTH);
        picker.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        picker.setWidth((int) (picker.getScreenWidthPixels() * 1.0));
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

    /**
     * 接收广播
     */
    private void receiveBroadcast ()
    {
        reflushData ();
    }



    private final static int HANDLER_START_GET_ORDER    = 1;
    private final static int HANDLER_FINISH_GET_ORDER   = 2;
    private final static int HANDLER_SHOW_ORDER         = 3;
    private final static int HANDLER_SEND_SUGGEST       = 4;
    private final static int HANDLER_FINSISH_SUGGEST    = 5;
    private final static int HANDLER_RECEIVE_BROADCAST  = 6;
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
                    case HANDLER_SEND_SUGGEST:
                    {
                        mMineFragment.sendSuggest (msg.obj);
                    }break;
                    case HANDLER_FINSISH_SUGGEST:
                    {
                        mMineFragment.finishSuggest ((String)msg.obj, msg.arg1 == 1);
                    }break;
                    case HANDLER_RECEIVE_BROADCAST:
                    {
                        mMineFragment.receiveBroadcast ();
                    }break;
                }
            }
        }
    }
}
