package cn.panda.game.pandastore.net;

public class Resource {
    private static final String SDK_PREFIX          = "http://opt.mycente.com";

    private static final String LOGIN               = "/storeUserRoute/login";
    private static final String REGIST              = "/storeUserRoute/regist";
    private static final String FORGET_PASSWORD     = "/storeUserRoute/forget_password";
    private static final String RESET_PASSWORD      = "/storeUserRoute/reset_password";

    private static final String USER_CENTER_INFO    = "/storeUserRoute/user_center_info";//用户信息
    private static final String RECORD_ID_CARD      = "/storeUserRoute/record_id_card";//实名认证 记录生份证信息
    private static final String CHANGE_NICKNAME     = "/storeUserRoute/change_nickname";//修改用户名
    private static final String UNBIND_MOBILE       = "/storeUserRoute/unbind_mobile";//解绑手机
    private static final String REBIND_VER_CODE     = "/storeUserRoute/rebind_ver_code";//重绑手机验证
    private static final String REBIND_MOBILE       = "/storeUserRoute/rebind_mobile";//重新绑定手机



    private static String getSdkPrefix ()
    {
        return SDK_PREFIX;
    }

    /**
     * 登录
     * @return
     */
    public static String getLogin ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (LOGIN).toString ();
    }

    /**
     * 注册
     * @return
     */
    public static String getRegist ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (REGIST).toString ();
    }

    /**
     * 忘记密码
     * @return
     */
    public static String getForgetPassword ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (FORGET_PASSWORD).toString ();
    }

    /**
     * 重置密码
     * @return
     */
    public static String getRestPassword ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (RESET_PASSWORD).toString ();
    }

    /**
     * 用户信息
     * @return
     */
    public static String getUserCenterInfo()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (USER_CENTER_INFO).toString ();
    }

    /**
     * 实名认证 记录生份证信息
     * @return
     */
    public static String getRecordIdCard()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (RECORD_ID_CARD).toString ();
    }

    /**
     * 修改用户名
     * @return
     */
    public static String getChangeNickname()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (CHANGE_NICKNAME).toString ();
    }

    /**
     * 解绑手机
     * @return
     */
    public static String getUnbindMobile()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (UNBIND_MOBILE).toString ();
    }
    /**
     * 重绑手机验证
     * @return
     */
    public static String getRebindVerCode()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (REBIND_VER_CODE).toString ();
    }
    /**
     * 重新绑定手机
     * @return
     */
    public static String getRebindMobile()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (REBIND_MOBILE).toString ();
    }
}
