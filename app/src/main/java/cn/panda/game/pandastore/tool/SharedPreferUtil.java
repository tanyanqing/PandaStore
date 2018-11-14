package cn.panda.game.pandastore.tool;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

public class SharedPreferUtil 
{
    private static final String PREFERENCES_NAME    = "cn_panda_store_android";

    public static final String APP_IS_REMEMBER      = "APP_IS_REMEMBER";//记住密码
    public static final String LOGIN_NAME           = "LOGIN_NAME";
    public static final String LOGIN_PASSWORD       = "LOGIN_PASSWORD";
    
    public static void write(Context context, String key, String value)
    {
        if (null == context || TextUtils.isEmpty(key))
        {
            return;
        }
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        Editor editor = pref.edit();
        editor.putString(key, value);
          
        editor.commit();
    }
    
    public static String read(Context context, String key)
    {
        if (null == context || TextUtils.isEmpty(key)) {
            return null;
        }
        
        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        String value = pref.getString(key, "");
        
        return value;
    }
    
    public static Boolean clean(Context context, String key)
    {
         if (null == context || TextUtils.isEmpty(key)) {
             return false;
         }
         write (context, key, "");
         return true;
    }
}
