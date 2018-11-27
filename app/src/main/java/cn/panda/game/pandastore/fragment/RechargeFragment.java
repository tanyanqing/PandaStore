package cn.panda.game.pandastore.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.UUID;

import cn.panda.game.pandastore.R;
import cn.panda.game.pandastore.bean.PayResult;
import cn.panda.game.pandastore.constants.Code;
import cn.panda.game.pandastore.constants.PayType;
import cn.panda.game.pandastore.net.HttpHandler;
import cn.panda.game.pandastore.net.Server;
import cn.panda.game.pandastore.tool.MyUserInfoSaveTools;
import cn.panda.game.pandastore.tool.PandaPayBrowser;
import cn.panda.game.pandastore.tool.RouteTool;
import cn.panda.game.pandastore.tool.Tools;

public class RechargeFragment  extends Fragment implements View.OnClickListener
{
    private View mRootView;
    private MyHandler mMyHandler;

    private View mLoginTipView;
    private View mRechargeView;

    private TextView mChargeAccount;

    private TextView mChargeAmount_100;
    private TextView mChargeAmount_500;
    private TextView mChargeAmount_1000;
    private TextView mChargeAmount_2000;
    private EditText mChargeAmount_edit;

    private TextView mChargeTypeAli;
    private TextView mChargeTypeWx;

    private TextView mChargeExplainText;

    private int mPayAmount;
    private int mPayType;
    @Nullable
    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        mRootView   = inflater.inflate(R.layout.fragment_recharge, null);
        mMyHandler  = new MyHandler (this);
        initView ();
        initData ();
        return mRootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        reflushView ();
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
            case R.id.game_charge_amount_100:
            case R.id.game_charge_amount_500:
            case R.id.game_charge_amount_1000:
            case R.id.game_charge_amount_2000:
            {
                mChargeAmount_edit.setText("");
                clickAmount (view.getId());
            }break;
            case R.id.game_charge_type_ali:
            case R.id.game_charge_type_wx:
            {
                clickType (view.getId());
            }break;
            case R.id.game_charge_confirm_btn:
            {
                clickPay ();
            }break;
        }
    }
    private void initView ()
    {
        mLoginTipView   = mRootView.findViewById(R.id.login_tip_view);
        mRootView.findViewById(R.id.recharge_sign_in).setOnClickListener(this);

        mChargeAccount  = (TextView)mRootView.findViewById(R.id.charge_user_id);

        mRechargeView   = mRootView.findViewById(R.id.recharge_view);

        mChargeAmount_100   = (TextView)mRootView.findViewById(R.id.game_charge_amount_100);
        mChargeAmount_500   = (TextView)mRootView.findViewById(R.id.game_charge_amount_500);
        mChargeAmount_1000  = (TextView)mRootView.findViewById(R.id.game_charge_amount_1000);
        mChargeAmount_2000  = (TextView)mRootView.findViewById(R.id.game_charge_amount_2000);
        mChargeAmount_100.setOnClickListener(this);
        mChargeAmount_500.setOnClickListener(this);
        mChargeAmount_1000.setOnClickListener(this);
        mChargeAmount_2000.setOnClickListener(this);

        mChargeAmount_edit  = (EditText)mRootView.findViewById(R.id.game_charge_amount_edit);
        mChargeAmount_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (mChargeAmount_edit.getText() != null && mChargeAmount_edit.getText().length() > 0)
                {
                    clickAmount(mChargeAmount_edit.getId());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mChargeTypeAli   = (TextView)mRootView.findViewById(R.id.game_charge_type_ali);
        mChargeTypeWx    = (TextView)mRootView.findViewById(R.id.game_charge_type_wx);
        mChargeTypeAli.setOnClickListener (this);
        mChargeTypeWx.setOnClickListener (this);

        if (Tools.isWeixinAvilible (getContext ()))
        {
            mChargeTypeWx.setVisibility (View.VISIBLE);
        }
        else
        {
            mChargeTypeWx.setVisibility (View.GONE);
        }

        mChargeExplainText  = (TextView)mRootView.findViewById(R.id.game_charge_explain_text);
        String explain      = getResources().getString(R.string.game_charge_explain_detail);
        mChargeExplainText.setText(Html.fromHtml(explain));

        mRootView.findViewById (R.id.game_charge_confirm_btn).setOnClickListener (this);
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
            mRechargeView.setVisibility(View.VISIBLE);

            String name     = MyUserInfoSaveTools.getNickName();
            if (TextUtils.isEmpty(name))
            {
                name    = MyUserInfoSaveTools.getRealName();
            }
            if (TextUtils.isEmpty(name))
            {
                name    = MyUserInfoSaveTools.getMobile();
            }
            if (TextUtils.isEmpty(name))
            {
                name    = MyUserInfoSaveTools.getUserId();
            }
            mChargeAccount.setText(name);
        }
        else
        {
            mLoginTipView.setVisibility(View.VISIBLE);
            mRechargeView.setVisibility(View.GONE);
        }
        mChargeAmount_edit.setText("");
        clickAmount (R.id.game_charge_amount_100);
        clickType (R.id.game_charge_type_ali);
    }

    private void clickAmount (int id)
    {
        mChargeAmount_100.setTextColor(Color.parseColor(id == R.id.game_charge_amount_100?("#ffffff"):("#00a4ff")));
        mChargeAmount_100.setBackgroundResource(id == R.id.game_charge_amount_100?(R.drawable.shape_charge_amount_choose):(R.drawable.shape_charge_amount_item));

        mChargeAmount_500.setTextColor(Color.parseColor(id == R.id.game_charge_amount_500?("#ffffff"):("#00a4ff")));
        mChargeAmount_500.setBackgroundResource(id == R.id.game_charge_amount_500?(R.drawable.shape_charge_amount_choose):(R.drawable.shape_charge_amount_item));

        mChargeAmount_1000.setTextColor(Color.parseColor(id == R.id.game_charge_amount_1000?("#ffffff"):("#00a4ff")));
        mChargeAmount_1000.setBackgroundResource(id == R.id.game_charge_amount_1000?(R.drawable.shape_charge_amount_choose):(R.drawable.shape_charge_amount_item));

        mChargeAmount_2000.setTextColor(Color.parseColor(id == R.id.game_charge_amount_2000?("#ffffff"):("#00a4ff")));
        mChargeAmount_2000.setBackgroundResource(id == R.id.game_charge_amount_2000?(R.drawable.shape_charge_amount_choose):(R.drawable.shape_charge_amount_item));

        if (id == R.id.game_charge_amount_100)
        {
            mPayAmount  = 100;
        }
        else if (id == R.id.game_charge_amount_500)
        {
            mPayAmount  = 500;
        }
        else if (id == R.id.game_charge_amount_1000)
        {
            mPayAmount  = 1000;
        }
        else if (id == R.id.game_charge_amount_2000)
        {
            mPayAmount  = 2000;
        }
        else if (id == R.id.game_charge_amount_edit)
        {
            mPayAmount   = -1;
        }

    }

    private void clickType (int id)
    {
        mChargeTypeAli.setTextColor (Color.parseColor(id == R.id.game_charge_type_ali?("#ffffff"):("#00a4ff")));
        mChargeTypeAli.setBackgroundResource(id == R.id.game_charge_type_ali?(R.drawable.shape_charge_amount_choose):(R.drawable.shape_charge_amount_item));

        mChargeTypeWx.setTextColor (Color.parseColor(id == R.id.game_charge_type_wx?("#ffffff"):("#00a4ff")));
        mChargeTypeWx.setBackgroundResource(id == R.id.game_charge_type_wx?(R.drawable.shape_charge_amount_choose):(R.drawable.shape_charge_amount_item));

        if (id == R.id.game_charge_type_ali)
        {
            mPayType  = PayType.PAY_ALI;
        }
        else if (id == R.id.game_charge_type_wx)
        {
            mPayType  = PayType.PAY_WX;
        }
    }

    private void clickPay ()
    {
        if (mPayAmount == -1)
        {
            String amount   = mChargeAmount_edit.getText ().toString ();
            if (!TextUtils.isEmpty (amount))
            {
                int count   = Integer.parseInt (amount);
                if (count > 0)
                {
                    mPayAmount = count;
                }
            }
        }
        if (mPayAmount == -1)
        {
            Toast.makeText (getContext (), "请输入充值金额", Toast.LENGTH_SHORT).show ();
            return;
        }
        if (mPayType != PayType.PAY_ALI && mPayType != PayType.PAY_WX)
        {
            Toast.makeText (getContext (), "请选择支付方式", Toast.LENGTH_SHORT).show ();
            return;
        }

        String store_no         = Tools.getChannelNo (getContext ());
        String app_no           = Tools.getAppNo (getContext ());
        String app_trade_no     = UUID.randomUUID ().toString ();
        String coin_type        = "0";

        Server.getServer (getContext ()).rechargeOrder (MyUserInfoSaveTools.getUserId (), store_no, app_no, app_trade_no, String.valueOf (mPayAmount), String.valueOf (mPayType), coin_type, new HttpHandler()
        {
            @Override
            public void onSuccess (String result)
            {
                if (!TextUtils.isEmpty (result))
                {
                    try
                    {
                        JSONObject jo = new JSONObject(result);
                        if (jo.getInt("resultCode") == 100)
                        {
                            JSONObject data     = jo.getJSONObject("data");
                            if (data != null)
                            {
                                Message msg     = mMyHandler.obtainMessage (HANDLER_START_THIRD_PAY);
                                msg.obj         = data;
                                msg.sendToTarget ();
                            }
                            else
                            {
                                showTip ("生成支付订单失败，请重试");
                            }
                        }
                        else
                        {
                            showTip ("生成支付订单失败，请重试");
                        }
                    }
                    catch (Exception e)
                    {
                        // TODO: handle exception
                        showTip ("生成支付订单失败，请重试");

                    }
                }
                else
                {
                    showTip ("生成支付订单失败，请重试");
                }

            }

            @Override
            public void onFail (String result)
            {
                showTip ("生成支付订单失败，请重试");
            }
        });
    }

    private void startThirdPay (JSONObject data)
    {
        try
        {
            int myPayType = data.getInt("pay_channel");
            if (myPayType == PayType.PAY_ALI)
            {
                final String orderInfo      = data.getString("sign");
                final String out_trade_no   = data.getString("out_trade_no");
                Runnable payRunnable        = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        PayTask alipay = new PayTask (getActivity ());
                        Map<String, String> result = alipay.payV2(orderInfo, true);

                        PayResult payResult = new PayResult(result);
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            finishPay (out_trade_no);
                        }
                        else
                        {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            showTip ("支付失败");
                        }
                    }
                };

                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }
            else if (myPayType == PayType.PAY_WX_H5)
            {//微信H5支付
                String myOutTradeNo    = data.getString("out_trade_no");

                JSONObject json = data.getJSONObject("sign");
                if (null != json)
                {
                    String url     = json.getString("mweb_url");
                    startWXH5Pay (url, myOutTradeNo);
                }
                else
                {
                    showTip ("微信签名数据错误，请稍后再试");
                }
            }
        }
        catch (Exception e)
        {
            showTip ("解析支付信息失败，请重试");
        }
    }
    private void startWXH5Pay (String url, String myOutTradeNo)
    {
        Intent intent = new Intent (getActivity (), PandaPayBrowser.class);
        intent.putExtra(Code.PAYURL.name(), url);
        intent.putExtra(Code.OutTradeNo.name(), myOutTradeNo);
        getActivity().startActivityForResult(intent, 20000);
    }

    /**
     * 支付成功完成之后的回调
     */
    private void finishPay (String out_trade_no)
    {
        Message msg     = mMyHandler.obtainMessage(HANDLER_FINISH_PAY);
        msg.obj         = out_trade_no;
        mMyHandler.sendMessage(msg);
    }

    /**
     * 请求订单状态
     * @param out_trade_no
     */
    private void questOrder (String out_trade_no)
    {

    }


    private void showTip (String tip)
    {
        Message msg     = mMyHandler.obtainMessage (HANDLER_SHOW_TIP);
        msg.obj         = tip;
        msg.sendToTarget ();
    }
    private final static int HANDLER_START_THIRD_PAY    = 1;
    private final static int HANDLER_FINISH_PAY         = 2;
    private final static int HANDLER_SHOW_TIP           = 3;
    private static class MyHandler extends Handler
    {

        private final WeakReference<RechargeFragment> mRechargeFragments;

        private MyHandler (RechargeFragment mRechargeFragment)
        {
            this.mRechargeFragments   = new WeakReference<RechargeFragment> (mRechargeFragment);
        }

        @Override
        public void handleMessage (Message msg)
        {
            super.handleMessage (msg);
            RechargeFragment mRechargeFragment    = mRechargeFragments.get ();
            if (mRechargeFragment != null)
            {
                switch (msg.what)
                {
                    case HANDLER_FINISH_PAY:
                    {
                        mRechargeFragment.questOrder ((String)msg.obj);
                    }break;
                    case HANDLER_SHOW_TIP:
                    {
                        Toast.makeText (mRechargeFragment.getContext (), msg.obj.toString (), Toast.LENGTH_SHORT).show ();
                    }break;
                    case HANDLER_START_THIRD_PAY:
                    {
                        mRechargeFragment.startThirdPay ((JSONObject)msg.obj);
                    }break;
                }
            }
        }
    }

}