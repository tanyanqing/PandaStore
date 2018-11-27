package cn.panda.game.pandastore.tool;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import cn.panda.game.pandastore.R;

public class MyDialog extends Dialog
{
    public MyDialog (@NonNull Context context, View layout)
    {
        super (context, R.style.DialogTheme);
        setContentView(layout);

        Window window   = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity  = Gravity.CENTER;
        window.setAttributes(params);
    }
}
