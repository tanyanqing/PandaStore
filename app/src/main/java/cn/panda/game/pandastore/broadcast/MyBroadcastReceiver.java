package cn.panda.game.pandastore.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver
{
    private int mark    = 0;
    private BroadcastCallback mBroadcastCallback;
    public MyBroadcastReceiver (BroadcastCallback callback)
    {
        mBroadcastCallback    = callback;
    }
    @Override
    public void onReceive (Context context, Intent intent)
    {

        if (intent != null && intent.getAction().equals (BroadcastConstant.ACTION_FILTER))
        {
            if (mBroadcastCallback != null)
            {
                mBroadcastCallback.callback ();
            }
//            if (mark == 1)
//            {
//                mark    = 0;
//                if (mBroadcastCallback != null)
//                {
//                    mBroadcastCallback.callback ();
//                }
//            }
//            else
//            {
//                mark ++;
//            }

        }
    }
}
