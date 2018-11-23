package cn.panda.game.pandastore.tool;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.migu.video.components.glide.Glide;


/**
 * Date : 2018-08-27 09:48
 * Author : YalingFeng
 * Comment :
 */

public class GlideTools
{
    /**
     * 加载图片
     * @param context 上下文
     * @param url 图片加载地址
     * @param view 显示图片ImageView
     */
    public static void setImageWithGlide (Context context, String url, final ImageView view)
    {
        if (context == null || TextUtils.isEmpty (url) || view == null)
        {
            return;
        }
        try
        {

//            GlideApp.with (context).load (url).placeholder (isVerticalImage?R.drawable.logo_v:R.drawable.logo_h).error (isVerticalImage?R.drawable.logo_v:R.drawable.logo_h).into (view);
            Glide.with (context).load (url).into (view);

        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }


}
