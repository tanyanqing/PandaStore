package cn.panda.game.pandastore;

import android.app.Application;

import cn.panda.game.pandastore.untils.ApplicationContext;

public class MyApplication extends Application
{
    @Override
    public void onCreate () {
        super.onCreate ();
        ApplicationContext.init (MyApplication.this);
    }
}
