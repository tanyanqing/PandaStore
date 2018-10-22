package cn.panda.game.pandastore;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hjm.bottomtabbar.BottomTabBar;

import cn.panda.game.pandastore.fragment.DiscoveryFragment;
import cn.panda.game.pandastore.fragment.HomeFragment;
import cn.panda.game.pandastore.fragment.MineFragment;
import cn.panda.game.pandastore.fragment.RechargeFragment;

public class MainActivity extends AppCompatActivity
{
    private BottomTabBar mBottomTabBar;
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

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
                    public void onTabChange(int position, String name) {

                    }
                });
    }
}
