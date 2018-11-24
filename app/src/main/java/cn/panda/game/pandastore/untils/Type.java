package cn.panda.game.pandastore.untils;

import android.text.TextUtils;

public class Type
{
    public static final int NONE            = 0;//没这个样式
    public static final int BANNER          = 1;
    public static final int RECOMMAND_1     = 2;//标题在上面
    public static final int RECOMMAND_2     = 3;//标题在左侧
    public static final int TITLE           = 4;
    public static final int COMMON_1        = 5;//普通的样式
    public static final int COMMON_2        = 6;//带banner图片的样式

    public static int getType (String type)
    {
        if (TextUtils.isEmpty(type))
        {
            return NONE;
        }
        else if (type.equalsIgnoreCase("title"))
        {
            return Type.TITLE;
        }
        else if (type.equalsIgnoreCase("recommand1"))
        {
            return Type.RECOMMAND_1;
        }
        else if (type.equalsIgnoreCase("recommand2"))
        {
            return Type.RECOMMAND_2;
        }
        else if (type.equalsIgnoreCase("banner"))
        {
            return Type.BANNER;
        }
        else if (type.equalsIgnoreCase("common1"))
        {
            return Type.COMMON_1;
        }
        else if (type.equalsIgnoreCase("common2"))
        {
            return Type.COMMON_2;
        }
        return NONE;
    }
}
