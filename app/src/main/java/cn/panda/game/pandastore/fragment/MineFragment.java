package cn.panda.game.pandastore.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

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
import cn.panda.game.pandastore.adapter.OwnerCouponListAdapter;
import cn.panda.game.pandastore.bean.BindTelBean;
import cn.panda.game.pandastore.bean.ChangeNickNameBean;
import cn.panda.game.pandastore.bean.GetVerifyBean;
import cn.panda.game.pandastore.bean.IdCardBean;
import cn.panda.game.pandastore.bean.OrderBean;
import cn.panda.game.pandastore.bean.OwnCouponBean;
import cn.panda.game.pandastore.bean.ParseTools;
import cn.panda.game.pandastore.broadcast.BroadcastCallback;
import cn.panda.game.pandastore.broadcast.BroadcastConstant;
import cn.panda.game.pandastore.broadcast.MyBroadcastReceiver;
import cn.panda.game.pandastore.net.HttpHandler;
import cn.panda.game.pandastore.net.Server;
import cn.panda.game.pandastore.tool.InitRecyclerViewLayout;
import cn.panda.game.pandastore.tool.MyDialog;
import cn.panda.game.pandastore.tool.MyUserInfoSaveTools;
import cn.panda.game.pandastore.tool.RouteTool;
import cn.panda.game.pandastore.tool.SharedPreferUtil;
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


    private MyDialog mAboutusDialog;
    private MyDialog mClientServiceDetailDialog;

    private MyDialog mChangeNicknameDialog;
    private EditText mNickNameEditText;

    private MyDialog mAddIdCardDialog;
    private EditText mIdCardName;
    private EditText mIdCardNumber;

    private MyDialog mChangePsdDialog;
    private EditText mOldPsd;
    private EditText mNewPsd;
    private EditText mConfirmPsd;

    private MyDialog mBindDialog;
    private EditText mTelNumber;
    private EditText mVerify;
    private Button mGetVerify;

    private MyDialog mCouponDialog;

    private View mPersonalView;
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
            case R.id.personal_nick_name:
            {
                changeNickName ();
            }break;
            case R.id.personal_id_card:
            {
                addIdCard ();
            }break;
            case R.id.personal_change_psd:
            {
                showChangePsdDialog ();
            }break;
            case R.id.dialog_bind:
            {
                showBindDialog ();
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

        mPersonalView = mRootView.findViewById (R.id.personal_view);
        mRootView.findViewById(R.id.personal_nick_name).setOnClickListener(this);
        mRootView.findViewById(R.id.personal_id_card).setOnClickListener(this);
        mRootView.findViewById(R.id.personal_change_psd).setOnClickListener(this);
        mRootView.findViewById(R.id.dialog_bind).setOnClickListener(this);
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
            if (mPersonalView != null)
            {
                mPersonalView.setVisibility (View.VISIBLE);
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
            if (mPersonalView != null)
            {
                mPersonalView.setVisibility (View.GONE);
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


        if (!MyUserInfoSaveTools.isLogin ())
        {
            Toast.makeText (getContext (), "请先登录", Toast.LENGTH_SHORT).show ();
            return;
        }
        showLoading (true);
        Server.getServer (getContext ()).getListOwnCoupons (MyUserInfoSaveTools.getUserId (), new HttpHandler () {
            @Override
            public void onSuccess (String result)
            {
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_GET_OWN_COUPON);
                msg.obj         = result;
                mMyHandler.sendMessageDelayed (msg, 1000);
            }

            @Override
            public void onFail (String result)
            {
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_GET_OWN_COUPON);
                msg.obj         = result;
                mMyHandler.sendMessageDelayed (msg, 1000);
            }
        });
    }

    private void finishGetOwnCoupon (String result)
    {

        if (!TextUtils.isEmpty (result))
        {
            OwnCouponBean ownCouponBean     = ParseTools.parseOwnCouponBean (result);
            if (ownCouponBean != null && ownCouponBean.getDatas ().size () > 0)
            {
                showCouponDialog (ownCouponBean);
            }
            else if (ownCouponBean != null)
            {
                Toast.makeText (getContext (), ownCouponBean.getResultCode () == 100 ?(ownCouponBean.getDatas ().size () == 0 ?("您还没获取卡券"):(ownCouponBean.getResultDesc ())):(ownCouponBean.getResultDesc ()), Toast.LENGTH_SHORT).show ();
            }
            else
            {
                Toast.makeText (getContext (), "获取失败，请重试", Toast.LENGTH_SHORT).show ();
            }
        }
        else
        {
            Toast.makeText (getContext (), "获取失败，请重试", Toast.LENGTH_SHORT).show ();
        }
        showLoading (false);
    }
    private void showCouponDialog (OwnCouponBean ownCouponBean)
    {
        if (mCouponDialog != null && mCouponDialog.isShowing ())
        {
            return;
        }
        View view               = getActivity ().getLayoutInflater().inflate(R.layout.dialog_coupon_list, null);
        RecyclerView container  = (RecyclerView)view.findViewById (R.id.continer);
        InitRecyclerViewLayout.initLinearLayoutVERTICAL (getActivity (), container);
        OwnerCouponListAdapter  ownerCouponListAdapter  = new OwnerCouponListAdapter (getContext (), ownCouponBean.getDatas ());
        container.setAdapter (ownerCouponListAdapter);

        view.findViewById (R.id.cancel).setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View view)
            {
                mCouponDialog.dismiss ();
            }
        });

        mCouponDialog  = new MyDialog(getContext (), view);
        mCouponDialog.show();
    }

    /**
     * 联系客服
     */
    private void customService ()
    {
        if (mClientServiceDetailDialog != null && mClientServiceDetailDialog.isShowing ())
        {
            return;
        }
        View view   = getActivity ().getLayoutInflater().inflate(R.layout.dialog_client_service, null);
        TextView clientService      = (TextView)view.findViewById (R.id.client_service);
        String clientServiceDetail  = getResources().getString(R.string.dialog_client_service_detail);
        clientService.setText(Html.fromHtml(clientServiceDetail));
        view.findViewById (R.id.cancel).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view)
            {
                mClientServiceDetailDialog.dismiss ();
            }
        });

        mClientServiceDetailDialog  = new MyDialog(getContext (), view);
        mClientServiceDetailDialog.show();
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
        if (mAboutusDialog != null && mAboutusDialog.isShowing ())
        {
            return;
        }
        View view   = getActivity ().getLayoutInflater().inflate(R.layout.dialog_aboutus, null);
        TextView aboutus        = (TextView)view.findViewById (R.id.about_us);
        String aboutusDetail    = getResources().getString(R.string.dialog_aboutus_detail);
        aboutus.setText(Html.fromHtml(aboutusDetail));
        view.findViewById (R.id.cancel).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view)
            {
                mAboutusDialog.dismiss ();
            }
        });

        mAboutusDialog  = new MyDialog(getContext (), view);
        mAboutusDialog.show();
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
        if (mSuggestDailog != null && mSuggestDailog.isShowing ())
        {
            return;
        }
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


    /**
     * 点击修改用户名
     */
    private void changeNickName ()
    {
        if (mChangeNicknameDialog != null && mChangeNicknameDialog.isShowing ())
        {
            return;
        }
        View view           = getActivity ().getLayoutInflater().inflate(R.layout.dialog_change_nickname, null);
        mNickNameEditText   = (EditText)view.findViewById (R.id.nick_name);
        view.findViewById (R.id.cancel).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view)
            {
                mChangeNicknameDialog.dismiss ();
            }
        });
        view.findViewById (R.id.send).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view)
            {
                String nickName     = mNickNameEditText.getText ().toString ();
                Message msg         = mMyHandler.obtainMessage (HANDLER_START_CHANGE_NICKNAME);
                msg.obj             = nickName;
                msg.sendToTarget ();
            }
        });

        mChangeNicknameDialog  = new MyDialog(getContext (), view);
        mChangeNicknameDialog.show();
    }
    private void startChangeNickname (String nickName)
    {
        if (TextUtils.isEmpty (nickName))
        {
            Toast.makeText (getContext (), "请正确输入用户名", Toast.LENGTH_SHORT).show ();
            return;
        }
        showLoading (true);
        Server.getServer (getContext ()).getChangeNickname (MyUserInfoSaveTools.getUserId (), nickName, new HttpHandler () {
            @Override
            public void onSuccess (String result)
            {
                Message msg         = mMyHandler.obtainMessage (HANDLER_FINSH_CHANGE_NICKNAME);
                msg.obj             = result;
                msg.sendToTarget ();
            }

            @Override
            public void onFail (String result)
            {
                Message msg         = mMyHandler.obtainMessage (HANDLER_FINSH_CHANGE_NICKNAME);
                msg.obj             = result;
                msg.sendToTarget ();
            }
        });

    }
    private void finishChangeNickname (String result)
    {
        showLoading (false);
        if (!TextUtils.isEmpty (result))
        {
            ChangeNickNameBean changeNickNameBean   = ParseTools.parseChangeNickNameBean (result);
            if (changeNickNameBean != null && changeNickNameBean.getData () != null && !TextUtils.isEmpty (changeNickNameBean.getData ().getNick_name ()))
            {
                if (mChangeNicknameDialog != null && mChangeNicknameDialog.isShowing ())
                {
                    mChangeNicknameDialog.dismiss ();
                }
                MyUserInfoSaveTools.saveNickName (changeNickNameBean.getData ().getNick_name ());
                if (mLoginName != null)
                {
                    mLoginName.setText (MyUserInfoSaveTools.getNickName ());
                }
                Toast.makeText (getContext (), "用户名修改完成", Toast.LENGTH_SHORT).show ();
            }
            else if (changeNickNameBean != null)
            {
                Toast.makeText (getContext (), changeNickNameBean.getResultDesc (), Toast.LENGTH_SHORT).show ();
            }
        }
        else
        {
            Toast.makeText (getContext (), "用户名修改失败，请重试", Toast.LENGTH_SHORT).show ();
        }
    }


    /**
     * 点击实名认证
     */
    private void addIdCard ()
    {
        if (mAddIdCardDialog != null && mAddIdCardDialog.isShowing ())
        {
            return;
        }
        View view       = getActivity ().getLayoutInflater().inflate(R.layout.dialog_add_idcard, null);
        mIdCardName     = (EditText)view.findViewById (R.id.name);
        mIdCardNumber   = (EditText)view.findViewById (R.id.id_card);
        view.findViewById (R.id.cancel).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view)
            {
                mAddIdCardDialog.dismiss ();
            }
        });
        view.findViewById (R.id.send).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view)
            {
                Message msg         = mMyHandler.obtainMessage (HANDLER_START_ADD_ID_CARD);
                msg.sendToTarget ();
            }
        });

        mAddIdCardDialog  = new MyDialog(getContext (), view);
        mAddIdCardDialog.show();
    }

    private void startAddIdCard ()
    {
        if (mIdCardName != null && mIdCardNumber != null)
        {
            String name     = mIdCardName.getText ().toString ();
            String number   = mIdCardNumber.getText ().toString ();
            if (TextUtils.isEmpty (number) || number.length () < 15 || number.length () > 18)
            {
                Toast.makeText (getContext (), "请正确输入身份证号码", Toast.LENGTH_SHORT).show ();
                return;
            }
            if (TextUtils.isEmpty (name) || name.length () < 2)
            {
                Toast.makeText (getContext (), "请正确输入姓名", Toast.LENGTH_SHORT).show ();
                return;
            }
            showLoading (true);
            Server.getServer (getContext ()).getRecordIdCard (MyUserInfoSaveTools.getUserId (), name, number, new HttpHandler () {
                @Override
                public void onSuccess (String result)
                {
                    Message msg         = mMyHandler.obtainMessage (HANDLER_FINISH_ADD_ID_CARD);
                    msg.obj             = result;
                    msg.sendToTarget ();
                }

                @Override
                public void onFail (String result)
                {
                    Message msg         = mMyHandler.obtainMessage (HANDLER_FINISH_ADD_ID_CARD);
                    msg.obj             = result;
                    msg.sendToTarget ();
                }
            });

        }
    }
    private void finishAddIdCard (String result)
    {
        showLoading (false);
        if (!TextUtils.isEmpty (result))
        {
            IdCardBean idCardBean   = ParseTools.parseIdCardBean (result);
            if (idCardBean != null && idCardBean.getData () != null)
            {
                if (mAddIdCardDialog != null && mAddIdCardDialog.isShowing ())
                {
                    mAddIdCardDialog.dismiss ();
                }
                MyUserInfoSaveTools.saveRealName (idCardBean.getData ().getReal_name ());
                Toast.makeText (getContext (), "实名认证完成", Toast.LENGTH_SHORT).show ();
            }
            else if (idCardBean != null)
            {
                Toast.makeText (getContext (), idCardBean.getResultDesc (), Toast.LENGTH_SHORT).show ();
            }
        }
        else
        {
            Toast.makeText (getContext (), "实名认证失败，请重试", Toast.LENGTH_SHORT).show ();
        }
    }

    /**
     * 显示修改密码
     */
    private void showChangePsdDialog ()
    {
        if (mChangePsdDialog != null && mChangePsdDialog.isShowing ())
        {
            return;
        }
        View view           = getActivity ().getLayoutInflater().inflate(R.layout.dialog_change_psd, null);
        mOldPsd   = (EditText)view.findViewById (R.id.old_psd);
        mNewPsd     = (EditText)view.findViewById(R.id.new_psd);
        mConfirmPsd = (EditText)view.findViewById(R.id.confirm_psd);

        view.findViewById (R.id.cancel).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view)
            {
                mChangePsdDialog.dismiss ();
            }
        });
        view.findViewById (R.id.send).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view)
            {
                Message msg         = mMyHandler.obtainMessage (HANDLER_START_CHANGE_PSD);
                msg.sendToTarget ();
            }
        });

        mChangePsdDialog  = new MyDialog(getContext (), view);
        mChangePsdDialog.show();
    }

    private void startChangePsd ()
    {
        if (mOldPsd != null && mNewPsd != null && mConfirmPsd != null)
        {
            String oldPsd   = mOldPsd.getText().toString();
            String newPsd   = mNewPsd.getText().toString();
            String confirmPsd   = mConfirmPsd.getText().toString();
            if (TextUtils.isEmpty(oldPsd) || TextUtils.isEmpty(newPsd) || TextUtils.isEmpty(confirmPsd))
            {
                Toast.makeText (getContext (), "请正确输入密码", Toast.LENGTH_SHORT).show ();
                return;
            }
            if (!confirmPsd.equals(newPsd))
            {
                Toast.makeText (getContext (), "新密码和确认密码不一致", Toast.LENGTH_SHORT).show ();
                return;
            }
            showLoading (true);
            Server.getServer(getContext()).resetPassword2(MyUserInfoSaveTools.getUserId(), oldPsd, newPsd, new HttpHandler() {
                @Override
                public void onSuccess(String result)
                {
                    Message msg         = mMyHandler.obtainMessage (HANDLER_FINISH_CHANGE_PSD);
                    msg.obj             = result;
                    msg.sendToTarget ();
                }

                @Override
                public void onFail(String result)
                {
                    Message msg         = mMyHandler.obtainMessage (HANDLER_FINISH_CHANGE_PSD);
                    msg.obj             = result;
                    msg.sendToTarget ();
                }
            });
        }

    }
    private void finishChangePsd (String result)
    {
        showLoading (false);
        if (!TextUtils.isEmpty (result) && ParseTools.isSuccess (result))
        {
            Toast.makeText (getContext (), "修改密码成功", Toast.LENGTH_SHORT).show ();
            //修改成功
            if (mNewPsd != null)
            {
                SharedPreferUtil.write (getContext(), SharedPreferUtil.LOGIN_PASSWORD, mNewPsd.getText().toString());
            }
            if (mChangePsdDialog != null && mChangePsdDialog.isShowing ())
            {
                mChangePsdDialog.dismiss ();
            }
        }
        else if (!TextUtils.isEmpty (result))
        {
            try {
                JSONObject jo   = new JSONObject (result);
                if (jo != null)
                {
                    Toast.makeText (getContext (), jo.optString ("resultDesc"), Toast.LENGTH_SHORT).show ();
                }
            }
            catch (Exception e)
            {
                Toast.makeText (getContext (), "修改失败，请重试", Toast.LENGTH_SHORT).show ();
            }

        }
    }

    /**
     * 绑定手机
     */
    private void showBindDialog ()
    {
        if (mBindDialog != null && mBindDialog.isShowing ())
        {
            return;
        }

        timerForBind.cancel();

        View view   = getActivity ().getLayoutInflater().inflate(R.layout.dialog_bind, null);
        mTelNumber  = (EditText)view.findViewById (R.id.tel_number);
        mVerify     = (EditText)view.findViewById(R.id.verify);
        mGetVerify  = (Button) view.findViewById(R.id.getverify);
        mGetVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyHandler.sendEmptyMessage(HANDLER_GET_VERIFY);
            }
        });

        view.findViewById (R.id.cancel).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view)
            {
                mBindDialog.dismiss ();
                finishBindCount ();
            }
        });
        view.findViewById (R.id.send).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view)
            {
                Message msg     = mMyHandler.obtainMessage (HANDLER_START_BIND);
                msg.sendToTarget ();
            }
        });

        mBindDialog  = new MyDialog(getContext (), view);
        mBindDialog.show();
    }

    private void startGetVerify ()
    {
        if (mTelNumber != null)
        {
            String tel  = mTelNumber.getText().toString();
            if (TextUtils.isEmpty(tel) || tel.length() != 11)
            {
                Toast.makeText (getContext (), "请正确输入手机号码", Toast.LENGTH_SHORT).show ();
                return;
            }
            startBindCount ();
            Server.getServer(getContext()).getVerifyForBind(MyUserInfoSaveTools.getUserId(), tel, new HttpHandler() {
                @Override
                public void onSuccess(String result)
                {
                    Message msg     = mMyHandler.obtainMessage(HANDLER_FINISH_GET_VERIFY);
                    msg.obj         = result;
                    msg.sendToTarget();
                }

                @Override
                public void onFail(String result)
                {
                    Message msg     = mMyHandler.obtainMessage(HANDLER_FINISH_GET_VERIFY);
                    msg.obj         = result;
                    msg.sendToTarget();
                }
            });
        }


    }
    private void finishGetVerify (String result)
    {
        if (!TextUtils.isEmpty (result))
        {
            GetVerifyBean getVerifyBean   = ParseTools.parseGetVerifyBean (result);
            if (getVerifyBean != null)
            {
                if (getVerifyBean.getResultCode () == 100)
                {
                    Toast.makeText (getContext (), "获取验证码成功，请关注手机短信", Toast.LENGTH_SHORT).show ();
                }
                else
                {
                    finishBindCount();
                    Toast.makeText (getContext (), getVerifyBean.getResultDesc (), Toast.LENGTH_SHORT).show ();
                }
            }
            else
            {
                finishBindCount();
                Toast.makeText (getContext (), "获取验证码失败，请重新获取", Toast.LENGTH_SHORT).show ();
            }
        }
    }

    private void startBind ()
    {
        if (mTelNumber != null && mVerify != null)
        {
            String tel  = mTelNumber.getText().toString();
            String verify   = mVerify.getText().toString();
            if (TextUtils.isEmpty(tel) || tel.length() != 11)
            {
                Toast.makeText (getContext (), "请正确输入手机号码", Toast.LENGTH_SHORT).show ();
                return;
            }
            if (TextUtils.isEmpty(verify))
            {
                Toast.makeText (getContext (), "请正确输入验证码", Toast.LENGTH_SHORT).show ();
                return;
            }
            showLoading(true);
            Server.getServer(getContext()).getBindTel(MyUserInfoSaveTools.getUserId(), tel, verify, new HttpHandler() {
                @Override
                public void onSuccess(String result)
                {
                    Message msg     = mMyHandler.obtainMessage(HANDLER_FINISH_BIND);
                    msg.obj         = result;
                    msg.sendToTarget();
                }

                @Override
                public void onFail(String result)
                {
                    Message msg     = mMyHandler.obtainMessage(HANDLER_FINISH_BIND);
                    msg.obj         = result;
                    msg.sendToTarget();
                }
            });
        }

    }
    private void finishBind (String result)
    {
        showLoading(false);
        if (!TextUtils.isEmpty (result))
        {
            BindTelBean bindTelBean   = ParseTools.parseBindTelBean (result);
            if (bindTelBean != null)
            {
                if (bindTelBean.getResultCode () == 100)
                {
                    //绑定成功
                    if (mBindDialog != null && mBindDialog.isShowing ())
                    {
                        mBindDialog.dismiss ();
                    }
                    Toast.makeText (getContext (), "完成绑定手机号码", Toast.LENGTH_SHORT).show ();
                }
                else
                {
                    Toast.makeText (getContext (), bindTelBean.getResultDesc (), Toast.LENGTH_SHORT).show ();
                }
            }
            else
            {
                Toast.makeText (getContext (), "绑定手机号码失败，请重新绑定", Toast.LENGTH_SHORT).show ();
            }
        }
    }


    CountDownTimer timerForBind   = new CountDownTimer (60*1000, 1000)
    {
        @Override
        public void onTick(long millisUntilFinished)
        {
            Message msg = mMyHandler.obtainMessage();
            msg.what    = HANDLER_FORGET_UPDATECOUNT;
            msg.arg1    = 2;
            msg.arg2    = (int) millisUntilFinished / 1000;
            msg.sendToTarget ();
        }

        @Override
        public void onFinish()
        {
            Message msg = mMyHandler.obtainMessage();
            msg.what    = HANDLER_FORGET_UPDATECOUNT;
            msg.arg1    = 3;
            msg.sendToTarget ();
        }

    };

    /**
     * 开始倒计时
     */
    private void startBindCount ()
    {
        Message msg = mMyHandler.obtainMessage(HANDLER_FORGET_UPDATECOUNT);
        msg.arg1    = 1;
        msg.sendToTarget ();
    }

    /**
     * 结束倒计时
     */
    private void finishBindCount ()
    {
        timerForBind.cancel();

        Message msg = mMyHandler.obtainMessage(HANDLER_FORGET_UPDATECOUNT);
        msg.arg1    = 4;
        mMyHandler.sendMessageDelayed(msg, 1000);
    }

    private void updateGetVerifyButton (int index, int second)
    {
        if (index == 1)
        {/**开始倒计时，屏蔽按钮*/
            if (mGetVerify != null)
            {
                mGetVerify.setEnabled(false);
                mGetVerify.setBackgroundResource (R.drawable.verify_enable_button);
                mGetVerify.setText (60+"秒");
            }
            timerForBind.start();
        }
        else if (index ==2)
        {/**倒计中，更新按钮文字*/
            if (mGetVerify != null)
            {
                mGetVerify.setText(second+"秒");
            }
        }
        else if (index == 3)
        {/**倒计结束*/
            if (mGetVerify != null)
            {
                mGetVerify.setEnabled(true);
                mGetVerify.setBackgroundResource (R.drawable.login_button);
                mGetVerify.setText(R.string.dialog_bind_tip_get_verify);
            }

        }
        else if (index == 4)
        {
            if (mGetVerify != null)
            {
                mGetVerify.setEnabled(true);
                mGetVerify.setBackgroundResource (R.drawable.login_button);
                mGetVerify.setText(R.string.dialog_bind_tip_get_verify);
            }

        }
    }


    private final static int HANDLER_START_GET_ORDER            = 1;
    private final static int HANDLER_FINISH_GET_ORDER           = 2;
    private final static int HANDLER_SHOW_ORDER                 = 3;
    private final static int HANDLER_SEND_SUGGEST               = 4;
    private final static int HANDLER_FINSISH_SUGGEST            = 5;
    private final static int HANDLER_RECEIVE_BROADCAST          = 6;
    private final static int HANDLER_START_CHANGE_NICKNAME      = 7;
    private final static int HANDLER_FINSH_CHANGE_NICKNAME      = 8;
    private final static int HANDLER_START_ADD_ID_CARD          = 9;
    private final static int HANDLER_FINISH_ADD_ID_CARD         = 10;
    private final static int HANDLER_START_CHANGE_PSD           = 11;
    private final static int HANDLER_FINISH_CHANGE_PSD          = 12;
    private final static int HANDLER_FORGET_UPDATECOUNT         = 13;
    private final static int HANDLER_GET_VERIFY                 = 14;
    private final static int HANDLER_FINISH_GET_VERIFY          = 15;
    private final static int HANDLER_START_BIND                 = 16;
    private final static int HANDLER_FINISH_BIND                = 17;
    private final static int HANDLER_FINISH_GET_OWN_COUPON      = 18;
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
                    case HANDLER_START_CHANGE_NICKNAME:
                    {
                        mMineFragment.startChangeNickname ((String)msg.obj);
                    }break;
                    case HANDLER_FINSH_CHANGE_NICKNAME:
                    {
                        mMineFragment.finishChangeNickname ((String)msg.obj);
                    }break;
                    case HANDLER_START_ADD_ID_CARD:
                    {
                        mMineFragment.startAddIdCard ();
                    }break;
                    case HANDLER_FINISH_ADD_ID_CARD:
                    {
                        mMineFragment.finishAddIdCard ((String)msg.obj);
                    }break;
                    case HANDLER_START_CHANGE_PSD:
                    {
                        mMineFragment.startChangePsd ();
                    }break;
                    case HANDLER_FINISH_CHANGE_PSD:
                    {
                        mMineFragment.finishChangePsd((String)msg.obj);
                    }break;
                    case HANDLER_FORGET_UPDATECOUNT:
                    {
                        mMineFragment.updateGetVerifyButton (msg.arg1, msg.arg2);
                    }break;
                    case HANDLER_GET_VERIFY:
                    {
                        mMineFragment.startGetVerify ();
                    }break;
                    case HANDLER_FINISH_GET_VERIFY:
                    {
                        mMineFragment.finishGetVerify ((String)msg.obj);
                    }break;
                    case HANDLER_START_BIND:
                    {
                        mMineFragment.startBind ();
                    }break;
                    case HANDLER_FINISH_BIND:
                    {
                        mMineFragment.finishBind ((String)msg.obj);
                    }break;
                    case HANDLER_FINISH_GET_OWN_COUPON:
                    {
                        mMineFragment.finishGetOwnCoupon ((String)msg.obj);
                    }break;
                }
            }
        }
    }
}
