package cn.panda.game.pandastore.sql;

import android.content.Context;

public class DatabaseHelper
{
    private static DatabaseHelper instance  = null;

    private static DatabaseBean dataBean;
    private static DatabaseManger mDatabaseManger;

    private final static String TABLE_NAME  = "panda_store_sql";
    private final static String KEY         = "key";
    private final static String VALUE       = "value";

    private DatabaseHelper (Context mContenxt)
    {
        if (dataBean == null)
        {
            dataBean    = new DatabaseBean (KEY, VALUE);
        }

        if (mDatabaseManger == null)
        {
            mDatabaseManger = DatabaseManger.getInstance(mContenxt,TABLE_NAME, KEY, VALUE);
        }
    }

    public static DatabaseHelper getInstance (Context mContenxt)
    {
        if (instance == null)
        {
            instance    = new DatabaseHelper (mContenxt);
        }
        return instance;
    }

    public void write (String key, String value)
    {
        mDatabaseManger.saveData (key, value);

    }

    public String read (String key)
    {
        return mDatabaseManger.getData (key);

    }



    public void clearAll ()
    {
        mDatabaseManger.clear ();
    }

    public void clear (String id)
    {
        mDatabaseManger.delete (id);
    }
}
