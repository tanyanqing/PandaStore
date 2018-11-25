package cn.panda.game.pandastore.fragment;

import android.graphics.Color;
import android.os.Bundle;
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

import cn.panda.game.pandastore.R;
import cn.panda.game.pandastore.tool.MyUserInfoSaveTools;
import cn.panda.game.pandastore.tool.RouteTool;

public class RechargeFragment  extends Fragment implements View.OnClickListener
{
    private View mRootView;

    private View mLoginTipView;
    private View mRechargeView;

    private TextView mChargeAccount;

    private TextView mChargeAmount_100;
    private TextView mChargeAmount_500;
    private TextView mChargeAmount_1000;
    private TextView mChargeAmount_2000;
    private EditText mChargeAmount_edit;

    private TextView mChargeExplainText;
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

        mChargeExplainText  = (TextView)mRootView.findViewById(R.id.game_charge_explain_text);
        String explain      = getResources().getString(R.string.game_charge_explain_detail);
        mChargeExplainText.setText(Html.fromHtml(explain));
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


    }


}