package cn.panda.game.pandastore.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

/**
 * 数据库具体操作类
 */

public class DatabaseManger extends SQLiteOpenHelper {

    private static DatabaseManger instance = null;
    private SQLiteDatabase sqLiteDatabase;
    private static String database_name = "";
    private static String field_name = "";
    private static String field_content = "";
    //数据库版本号
    public static int database_version = 1;

    /**
     * 记数器 应该设置静态的类变量
     *
     * @param context
     */
    private static int mCount;

    public DatabaseManger(Context context, String dbName, String fieldName, String fieldContent) {
        super(context, dbName, null, database_version);
        database_name = dbName;
        field_name = fieldName;
        field_content = fieldContent;
    }


    /**
     * 获取本类对象的实例
     *
     * @param context
     * @return
     */
    public static synchronized DatabaseManger getInstance(Context context, String table, String fieldName, String fieldContent) {
        if (instance == null) {
            if (context == null) {
                throw new RuntimeException("Context is null.");

            }
            instance = new DatabaseManger(context, table, fieldName, fieldContent);
        }

        return instance;
    }



    public synchronized void saveData (String keyValue, String dataValue)
    {
        try
        {
            String saveData = getData(keyValue);
            if(TextUtils.isEmpty (saveData))
            {
                String sql = "INSERT INTO " + database_name + "(key, value) VALUES(\'" + keyValue +
                        "\',\'" + dataValue + "\');";
                getWritableDatabase().execSQL(sql);
            }
            else
            {
                String sql = "UPDATE " + database_name + " SET value = \'" + dataValue + "\' WHERE key =\'" + keyValue + "\';";
                getWritableDatabase().execSQL(sql);
            }
        }
        catch (Exception e)
        {
            Log.e ("tommy","DatabaseManger saveData e="+e.toString ());
        }
    }

    public synchronized String getData(String keyValue)
    {
        try
        {
            String sql = "SELECT * from " + database_name + " WHERE key = \'" + keyValue + "\';";

            Cursor cursor = getReadableDatabase().rawQuery(sql, null);
            if (cursor.moveToNext())
            {
                String dataValue    = cursor.getString(2);
                return dataValue;
            }
        }
        catch (Exception e)
        {
            Log.e ("tommy","DatabaseManger getData e="+e.toString ());
        }
        return "";
    }

    public synchronized void delete (String id)
    {
        try
        {
            getWritableDatabase ().delete (database_name, "key=?",new String[]{id});
            getWritableDatabase ().close ();
        }
        catch (Exception e)
        {
            Log.e ("tommy","DatabaseManger delete e="+e.toString ());
        }

    }
    public synchronized void clear()
    {
        try
        {
            String sql  = String.valueOf ("DROP TABLE IF EXISTS "+database_name);
            getWritableDatabase().execSQL(sql);

            String str  = "CREATE TABLE IF NOT EXISTS " + database_name + " (_id Integer primary key, " + field_name + " text, " + field_content + " text);";
            getWritableDatabase().execSQL(String.valueOf (str));

            getWritableDatabase ().close ();
        }
        catch (Exception e)
        {
            Log.e ("tommy","DatabaseManger clear e="+e.toString ());
        }
    }

    //创建数据库表格
    @Override
    public void onCreate(SQLiteDatabase db) {
        String str  = "CREATE TABLE " + database_name + " (_id Integer primary key, " + field_name + " text, " + field_content + " text);";
        db.execSQL(String.valueOf (str));
    }

    //关闭数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            String str  = "DROP TABLE IF EXISTS " + database_name;
            db.execSQL(String.valueOf (str));
        }
    }
}
