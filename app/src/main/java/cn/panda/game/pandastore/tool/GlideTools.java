package cn.panda.game.pandastore.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;

import cn.panda.game.pandastore.R;
import jp.wasabeef.glide.transformations.BlurTransformation;


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
    public static void setImageWithGlide (Context context, String url, final ImageView view, boolean isBanner)
    {
        if (context == null || TextUtils.isEmpty (url) || view == null)
        {
            return;
        }
        try
        {

//            GlideApp.with (context).load (url).placeholder (isVerticalImage?R.drawable.logo_v:R.drawable.logo_h).error (isVerticalImage?R.drawable.logo_v:R.drawable.logo_h).into (view);
            Glide.with (context).load (url).placeholder (isBanner? R.drawable.big_default_img:R.drawable.small_default_img).error (isBanner?R.drawable.big_default_img:R.drawable.small_default_img).into (view);

        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
    }

    public static void setImageWithGlide (Context context, String url, final ImageView view)
    {
        Glide.with (context).load (url).placeholder (R.drawable.big_default_img).error (R.drawable.big_default_img).bitmapTransform(new BlurTransformation(context, 8, 3)).into (view);
    }


}
