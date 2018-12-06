package cn.panda.game.pandastore;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hjm.bottomtabbar.BottomTabBar;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.listener.ExceptionHandler;

import cn.panda.game.pandastore.bean.CenterInfoBean;
import cn.panda.game.pandastore.bean.ParseTools;
import cn.panda.game.pandastore.broadcast.BroadcastConstant;
import cn.panda.game.pandastore.fragment.DiscoveryFragment;
import cn.panda.game.pandastore.fragment.HomeFragment;
import cn.panda.game.pandastore.fragment.MineFragment;
import cn.panda.game.pandastore.fragment.RechargeFragment;
import cn.panda.game.pandastore.net.HttpHandler;
import cn.panda.game.pandastore.net.Server;
import cn.panda.game.pandastore.tool.MyUserInfoSaveTools;
import cn.panda.game.pandastore.tool.Tools;
import cn.panda.game.pandastore.tool.UpdateAppHttpUtil;

public class MainActivity extends AppCompatActivity
{
    private BottomTabBar mBottomTabBar;
    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState)
    {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        initView ();

        reflushCenterInfo ();

        checkUpdate ();
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
                .isShowDivider(false);
//                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
//                    @Override
//                    public void onTabChange(int position, String name)
//                    {
//                        if (name.equals ("个人"))
//                        {
//                            Intent intent = new Intent ();
//                            intent.setAction (BroadcastConstant.ACTION_FILTER);
//                            MainActivity.this.sendBroadcast(intent);
//                        }
//                    }
//                });

    }

    /**
     * 刷新用户信息
     */
    private void reflushCenterInfo ()
    {
        if (MyUserInfoSaveTools.isLogin ())
        {
            Server.getServer (getApplicationContext ()).getUserCenterInfo (MyUserInfoSaveTools.getUserId (), Tools.getChannelNo (getApplicationContext ()), new HttpHandler () {
                @Override
                public void onSuccess (String result)
                {
                    CenterInfoBean centerInfoBean   = ParseTools.parseCenterInfoBean (result);
                    if (centerInfoBean != null && centerInfoBean.getData () != null)
                    {
                        if (centerInfoBean.getData ().getUser_id ().equals (MyUserInfoSaveTools.getUserId ()))
                        {
                            MyUserInfoSaveTools.saveCoinCount (centerInfoBean.getData ().getCoin_count ());
                            if (!TextUtils.isEmpty (centerInfoBean.getData ().getNick_name ()))
                            {
                                MyUserInfoSaveTools.saveNickName (centerInfoBean.getData ().getNick_name ());
                            }

                        }
                    }

                    Intent intent = new Intent ();
                    intent.setAction (BroadcastConstant.ACTION_FILTER);
                    MainActivity.this.sendBroadcast(intent);
                }

                @Override
                public void onFail (String result) {

                }
            });
        }
    }

    private void checkUpdate ()
    {
        String version      = Tools.getVersion (MainActivity.this);
        String mUpdateUrl   = "http://opt.mycente.com/gameRoute/check_version/0/"+version;
        new UpdateAppManager.Builder().setActivity(MainActivity.this).setUpdateUrl(mUpdateUrl)
                .handleException(new ExceptionHandler () {
                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                .setPost (false)
                //实现httpManager接口的对象
                .setHttpManager(new UpdateAppHttpUtil ())
                .build()
                .update();
    }
}
