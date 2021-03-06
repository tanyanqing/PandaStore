package cn.panda.game.pandastore.net;

import cn.panda.game.pandastore.untils.ApplicationContext;

public class Resource {
    private static final String SDK_PREFIX          = "http://opt.mycente.com";

    private static final String LOGIN               = "/storeUserRoute/login";
    private static final String REGIST              = "/storeUserRoute/regist";
    private static final String FORGET_PASSWORD     = "/storeUserRoute/forget_password";
    private static final String RESET_PASSWORD      = "/storeUserRoute/reset_password";
    private static final String RESET_PASSWORD2     = "/storeUserRoute/reset_password2";

    private static final String USER_CENTER_INFO    = "/storeUserRoute/user_center_info";//用户信息
    private static final String RECORD_ID_CARD      = "/storeUserRoute/record_id_card";//实名认证 记录生份证信息
    private static final String CHANGE_NICKNAME     = "/storeUserRoute/change_nickname";//修改用户名
    private static final String UNBIND_MOBILE       = "/storeUserRoute/unbind_mobile";//解绑手机
    private static final String REBIND_VER_CODE     = "/storeUserRoute/rebind_ver_code";//重绑手机验证
    private static final String REBIND_MOBILE       = "/storeUserRoute/rebind_mobile";//重新绑定手机

    private static final String BANNER_RECOMMEND    = "/gameRoute/banner_recommend/";//首页推荐接口 推荐位置共15个 前5位banner位置后10个为推荐位置索引从1开始
    private static final String GAME_LIST           = "/gameRoute/game_list/";//获取游戏列表
    private static final String DISCOVER            = "/gameRoute/discover/";
    private static final String TAG_GAMES           = "/gameRoute/tag_games/";
    private static final String DOWN_URL            = "/gameRoute/download_url/";
    private static final String RECOMMEND_PAGE      = "/gameRoute/recommend_page/";
    private static final String GAME_DETAIL         = "/gameRoute/game_detail/";
    private static final String SEARCH_GAME         = "/gameRoute/search_game/";

    private static final String ORDER_STATEMENTS    = "/storeUserRoute/order_statements";//交易记录
    private static final String SUGGEST             = "/storeUserRoute/suggest";//投诉建议

    private static final String RECHARGE_ORDER      = "/storeTradeRoute/create_recharge_order";//充值
    private static final String ORDER_STATUS        = "/storeTradeRoute/recharge_order_status";//订单状态查询

    private static final String LIST_COUPONS        = "/storeCouponRoute/list_coupons";
    private static final String APPLY_COUPONS       = "/storeCouponRoute/apply_coupon";
    private static final String LIST_OWN_COUPONS    = "/storeCouponRoute/list_own_coupon";



//    http://opt.mycente.com/gameRoute/recommend_page/10/10/0



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
     * 重置密码
     * @return
     */
    public static String getRestPassword2 ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (RESET_PASSWORD2).toString ();
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


    public static String getBannerRecommend ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (BANNER_RECOMMEND).append (ApplicationContext.mAppNo).toString ();
    }
    public static String getGameList (String page, String size)
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (GAME_LIST).append (ApplicationContext.mAppNo).append ("/").append (size).append ("/").append (page).toString ();
    }
    public static String getRecommendPage (String page, String size)
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (RECOMMEND_PAGE).append (ApplicationContext.mAppNo).append ("/").append (size).append ("/").append (page).toString ();
    }
    public static String getGameDetail (String gameId)
    {
        StringBuffer sb = new StringBuffer();
        return sb.append(getSdkPrefix()).append(GAME_DETAIL).append(ApplicationContext.mAppNo).append("/").append(gameId).toString();
    }
    public static String getDiscover (String page, String size)
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (DISCOVER).append (ApplicationContext.mAppNo).append ("/").append (size).append ("/").append (page).toString ();
    }
    public static String getTagGames ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (TAG_GAMES).append (ApplicationContext.mAppNo).toString ();
    }
    public static String getDownUrl ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (DOWN_URL).append (ApplicationContext.mAppNo).toString ();
    }
    public static String getSearchGame ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (SEARCH_GAME).append (ApplicationContext.mAppNo).append ("/").toString ();
    }

    public static String getOrderStatements ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (ORDER_STATEMENTS).toString ();
    }
    public static String getSuggest ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (SUGGEST).toString ();
    }
    public static String getRechargeOrder ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (RECHARGE_ORDER).toString ();
    }
    public static String getOrderStatus ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (ORDER_STATUS).toString ();
    }
    public static String getListCoupons ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (LIST_COUPONS).toString ();
    }
    public static String getApplyCoupons ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (APPLY_COUPONS).toString ();
    }
    public static String getListOwnCoupons ()
    {
        StringBuffer sb = new StringBuffer ();
        return sb.append (getSdkPrefix ()).append (LIST_OWN_COUPONS).toString ();
    }


}
