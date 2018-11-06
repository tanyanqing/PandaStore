package cn.panda.game.pandastore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.hjm.bottomtabbar.BottomTabBar;

import cn.panda.game.pandastore.fragment.DiscoveryFragment;
import cn.panda.game.pandastore.fragment.HomeFragment;
import cn.panda.game.pandastore.fragment.MineFragment;
import cn.panda.game.pandastore.fragment.RechargeFragment;

public class MainActivity extends AppCompatActivity
{
    private BottomTabBar mBottomTabBar;
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        initView ();
    }

    @SuppressLint ("ClickableViewAccessibility")
    private void initView ()
    {
        mBottomTabBar  = (BottomTabBar)findViewById(R.id.bottom_tab_bar);
        mBottomTabBar.init(getSupportFragmentManager())
                .setImgSize(60,60)
                .setFontSize(12)
                .setTabPadding(4,6,10)
                .setChangeColor(getResources ().getColor (R.color.my_green), Color.DKGRAY)
                .addTabItem("首页",R.drawable.main_tab_home, HomeFragment.class)
                .addTabItem("发现",R.drawable.main_tab_home, DiscoveryFragment.class)
                .addTabItem("充值",R.drawable.main_tab_recharge, RechargeFragment.class)
                .addTabItem("个人",R.drawable.main_tab_mine, MineFragment.class)
                .isShowDivider(false)
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override
                    public void onTabChange(int position, String name)
                    {
                        if (name.equals ("充值"))
                        {
                            Intent intent   = new Intent (MainActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            MainActivity.this.startActivity (intent);
                        }
                    }
                });

    }
}
