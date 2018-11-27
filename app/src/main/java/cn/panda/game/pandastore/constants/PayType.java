package cn.panda.game.pandastore.constants;

public class PayType
{
    public static final int PAY_ALI     = 0;//支付宝
    public static final int PAY_WX      = 3;//微信 APP内支付 1，目前使用3调用h5支付
    public static final int PAY_UNION   = 2;//银联支付
    public static final int PAY_WX_H5   = 3;//微信 H5支付
    public static final int PAY_PANDA   = 4;//品台币支付
    public static final int PAY_COUP    = 5;//定向券

    /**
     * 判断当前支付方式是否支持
     * @param type
     * @return
     */
    public static boolean isSupportPayType (int type)
    {
        if (type == PAY_ALI || type == PAY_WX || type == PAY_WX_H5 || type == PAY_PANDA)
        {
            return true;
        }
        return false;
    }
    public static boolean isWXPayType (int type)
    {
        if (type == PAY_WX)
        {
            return true;
        }
        return false;
    }
}
