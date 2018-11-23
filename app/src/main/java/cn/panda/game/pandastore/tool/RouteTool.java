package cn.panda.game.pandastore.tool;

import android.app.Activity;
import android.content.Intent;

import cn.panda.game.pandastore.LoginActivity;
import cn.panda.game.pandastore.MainActivity;

public class RouteTool
{
    public static void jumpLogin (Activity activity)
    {
        Intent intent   = new Intent (activity, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity (intent);
    }
}
