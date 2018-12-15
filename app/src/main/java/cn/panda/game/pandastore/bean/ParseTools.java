package cn.panda.game.pandastore.bean;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import cn.panda.game.pandastore.net.Server;

public class ParseTools
{
    private final static String TAG     = "ParseTools";
    private final static int SUCCESS    = 100;

    public static LoginUserInfo parseLoginUserInfo (String str)
    {
        try
        {
            JSONObject obj  = new JSONObject (str);
            if (obj != null)
            {
                LoginUserInfo loginUserInfo     = new LoginUserInfo ();
                loginUserInfo.setResultCode (obj.optInt ("resultCode"));
                loginUserInfo.setResultDesc (obj.optString ("resultDesc"));

                JSONObject dataObj  = obj.optJSONObject ("data");
                if (dataObj != null && loginUserInfo.getResultCode () == SUCCESS)
                {
                    LoginUserInfo.Data data     = new LoginUserInfo.Data ();
                    data.setCoin_count (dataObj.optInt ("coin_count", 0));
                    data.setUser_id (dataObj.optString ("user_id", ""));
                    data.setApp_coin (dataObj.optInt ("app_coin", 0));
                    data.setId_card_no (dataObj.optString ("id_card_no", ""));
                    data.setNick_name (dataObj.optString ("nick_name", ""));
                    data.setApp_name (dataObj.optString ("app_name", ""));
                    data.setGroup_no (dataObj.optString ("group_no", ""));
                    data.setChannel_no (dataObj.optString ("channel_no", ""));
                    data.setReal_name (dataObj.optString ("real_name", ""));
                    data.setLogin_type (dataObj.optInt ("login_type", 0));
                    data.setVer_status (dataObj.optInt ("ver_status", 1));
                    data.setApp_no (dataObj.optString ("app_no", ""));
                    data.setMobile (dataObj.optString ("mobile", ""));

                    loginUserInfo.setData (data);
                }
                return loginUserInfo;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
            Log.e (TAG, "ParseTools parseLoginUserInfo e="+e.toString ());
        }
        return null;
    }

    /**
     * 解析注册之后的用户信息
     * @param str
     * @return
     */
    public static RegistUserInfo parseRegistUserInfo (String str)
    {
        try
        {
            JSONObject obj  = new JSONObject (str);
            if (obj != null)
            {
                RegistUserInfo registUserInfo   = new RegistUserInfo ();
                registUserInfo.setResultCode (obj.optInt ("resultCode"));
                registUserInfo.setResultDesc (obj.optString ("resultDesc"));

                JSONObject dataObj  = obj.optJSONObject ("data");
                if (dataObj != null && registUserInfo.getResultCode () == SUCCESS)
                {
                    RegistUserInfo.Data data  = new RegistUserInfo.Data ();
                    data.setCoin_count (dataObj.optInt ("coin_count", 0));
                    data.setReal_name (dataObj.optString ("real_name", ""));
                    data.setUser_id (dataObj.optString ("user_id", ""));
                    data.setVer_status (dataObj.optInt ("ver_status", 1));
                    data.setLogin_type (dataObj.optInt ("login_type", 0));
                    data.setNick_name (dataObj.optString ("nick_name", ""));
                    data.setId_card_no (dataObj.optString ("id_card_no", ""));
                    data.setMobile (dataObj.optString ("mobile", ""));

                    registUserInfo.setData (data);
                }
                return registUserInfo;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
            Log.e (TAG, "ParseTools parseUserBaseInfo e="+e.toString ());
        }
        return null;
    }

    /**
     * 解析获取验证码
     * @param str
     * @return
     */
    public static VerifyBean parseVerify (String str)
    {
        try
        {
            if (!TextUtils.isEmpty (str))
            {
                JSONObject obj  = new JSONObject (str);
                if (obj != null)
                {
                    VerifyBean verifyBean   = new VerifyBean ();

                    verifyBean.setSuccess (obj.optInt ("resultCode") == SUCCESS);
                    verifyBean.setResultDesc (obj.optString ("resultDesc"));
                    JSONObject dataObj  = obj.optJSONObject ("data");
                    if (verifyBean.isSuccess () && dataObj != null)
                    {
                        verifyBean.setUser_id (dataObj.optString ("user_id"));
                    }
                    return verifyBean;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
            Log.e (TAG, "ParseTools parseVerify e="+e.toString ());
        }
        return null;
    }

    /**
     * 解析重试密码
     * @param str
     * @return
     */
    public static ResetBean parseResetBean (String str)
    {
        try
        {
            if (!TextUtils.isEmpty (str))
            {
                JSONObject obj  = new JSONObject (str);
                if (obj != null)
                {
                    ResetBean resetBean   = new ResetBean ();

                    resetBean.setSuccess (obj.optInt ("resultCode") == SUCCESS);
                    resetBean.setResultDesc (obj.optString ("resultDesc"));

                    return resetBean;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
            Log.e (TAG, "ParseTools parseResetBean e="+e.toString ());
        }
        return null;
    }

    /**
     * 解析游戏列表
     * @param str
     * @return
     */
    public static GameListBean parseGameListBean (String str)
    {
        try
        {
            if (!TextUtils.isEmpty (str))
            {
                JSONObject obj  = new JSONObject (str);
                if (obj != null)
                {
                    GameListBean gameListBean   = new GameListBean ();

                    gameListBean.setResultCode (obj.optInt ("resultCode"));
                    gameListBean.setResultDesc (obj.optString ("resultDesc"));
                    gameListBean.setStatus (obj.optString ("status"));

                    JSONObject dataObj  = obj.optJSONObject ("data");
                    if (dataObj != null)
                    {
                        GameListBean.Data data  = new GameListBean.Data ();
                        data.setTotal_pages (dataObj.optInt ("total_pages"));
                        data.setGame_count (dataObj.optInt ("game_count"));
                        data.setPage_size (dataObj.optInt ("page_size"));
                        data.setCurrent_page (dataObj.optInt ("current_page"));

                        JSONArray pageDataArray    = dataObj.optJSONArray ("page_data");
                        if (pageDataArray != null)
                        {
                            for (int i = 0; i < pageDataArray.length(); i ++)
                            {
                                JSONObject pageObject   = pageDataArray.getJSONObject(i);
                                if (pageObject != null)
                                {
                                    GameListBean.Page page  = new GameListBean.Page();
                                    page.setShowType(pageObject.optString("showType"));
                                    page.setTitle(pageObject.optString("title"));
                                    page.setMore(pageObject.optString("more"));
                                    page.setBackground(pageObject.optString("background"));

                                    JSONArray gamesArray    = pageObject.optJSONArray("gameslist");
                                    if (gamesArray != null)
                                    {
                                        for (int j = 0; j < gamesArray.length(); j ++)
                                        {
                                            JSONObject gameObj      = gamesArray.optJSONObject (j);
                                            if (gameObj != null)
                                            {
                                                GameListBean.Game game  = new GameListBean.Game ();
                                                game.setSub_title (gameObj.optString ("sub_title"));
                                                game.setDiscount_start (gameObj.optString ("discount_start"));
                                                game.setDownload_count (gameObj.optInt ("download_count"));
                                                game.setName (gameObj.optString ("name"));
                                                game.setBanner (gameObj.optString ("banner"));
                                                game.setIcon (gameObj.optString ("icon"));
                                                game.setTag (gameObj.optString ("tag"));
                                                game.setCategory (gameObj.optString ("category"));
                                                game.setDiscount_end (gameObj.optString ("discount_end"));
                                                game.setSecond_discount (gameObj.optString ("second_discount"));
                                                game.setSize (gameObj.optString ("size"));
                                                game.setFirst_discount(gameObj.optString("first_discount"));
                                                game.setId(gameObj.optString("id"));
                                                game.setEnable_download(gameObj.optBoolean("enable_download"));
                                                game.setJsonStr(gamesArray.optString(j));
                                                page.addGame(game);
                                            }
                                        }
                                    }

                                    data.addPage(page);
                                }
                            }
                        }
                        gameListBean.setData (data);
                    }

                    return gameListBean;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
            Log.e (TAG, "ParseTools parseResetBean e="+e.toString ());
        }
        return null;
    }

    public static GameListBean.Game parseGame (String str)
    {
        try
        {
            JSONObject gameObj  = new JSONObject (str);
            GameListBean.Game game  = new GameListBean.Game ();
            game.setSub_title (gameObj.optString ("sub_title"));
            game.setDiscount_start (gameObj.optString ("discount_start"));
            game.setDownload_count (gameObj.optInt ("download_count"));
            game.setName (gameObj.optString ("name"));
            game.setBanner (gameObj.optString ("banner"));
            game.setIcon (gameObj.optString ("icon"));
            game.setTag (gameObj.optString ("tag"));
            game.setCategory (gameObj.optString ("category"));
            game.setDiscount_end (gameObj.optString ("discount_end"));
            game.setSecond_discount (gameObj.optString ("second_discount"));
            game.setSize (gameObj.optString ("size"));
            game.setFirst_discount(gameObj.optString("first_discount"));
            game.setId(gameObj.optString("id"));
            game.setJsonStr(str);

            return game;
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 详情页面数据
     * @param str
     * @return
     */
    public static GameDetailBean parseGameDetailBean (String str)
    {
        try
        {
            if (!TextUtils.isEmpty(str))
            {
                JSONObject obj  = new JSONObject (str);
                if (obj != null)
                {
                    GameDetailBean gameDetailBean   = new GameDetailBean();
                    gameDetailBean.setResultCode (obj.optInt ("resultCode"));
                    gameDetailBean.setResultDesc (obj.optString ("resultDesc"));
                    gameDetailBean.setStatus (obj.optString ("status"));
                    if (gameDetailBean.getResultCode() == SUCCESS)
                    {
                        if (obj.optJSONObject ("data") != null)
                        {
                            JSONObject dataObj  = obj.optJSONObject ("data").optJSONObject("game_data");
                            if (dataObj != null)
                            {

                                GameDetailBean.Data data    = new GameDetailBean.Data();
                                data.setName(dataObj.optString("name"));
                                Log.e("tommy","111 name="+data.getName());
                                data.setDiscount_start(dataObj.optString("discount_start"));
                                data.setDiscount_end(dataObj.optString("discount_end"));
                                data.setDownload_count(dataObj.optString("download_count"));
                                data.setShow_pic5(dataObj.optString("show_pic5"));
                                data.setShow_pic3(dataObj.optString("show_pic3"));
                                data.setShow_pic4(dataObj.optString("show_pic4"));
                                data.setTag(dataObj.optString("tag"));
                                data.setSecond_discount(dataObj.optString("second_discount"));
                                data.setFirst_discount(dataObj.optString("first_discount"));
                                data.setSub_title(dataObj.optString("sub_title"));
                                data.setBanner(dataObj.optString("banner"));
                                data.setVersion(dataObj.optString("version"));
                                data.setIcon(dataObj.optString("icon"));
                                data.setShow_pic2(dataObj.optString("show_pic2"));
                                data.setShow_pic1(dataObj.optString("show_pic1"));
                                data.setCategory(dataObj.optString("category"));
                                data.setRelated_game(dataObj.optString("related_game"));
                                data.setSize(dataObj.optString("size"));
                                data.setDescription(dataObj.optString("description"));
                                data.setId(dataObj.optString("id"));
                                data.setOpt_time(dataObj.optString("opt_time"));
                                data.setGame_no (dataObj.optString ("game_no"));
                                data.setEnable_download(dataObj.optBoolean("enable_download"));

                                gameDetailBean.setData(data);
                            }
                        }
                    }
                    return gameDetailBean;
                }
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析下载
     * @param str
     * @return
     */
    public static DownUrlBean parseDownUrlBean (String str, String gameId)
    {
        try
        {
            if (!TextUtils.isEmpty(str))
            {
                JSONObject obj  = new JSONObject (str);
                if (obj != null)
                {
                    DownUrlBean downUrlBean     = new DownUrlBean ();
                    downUrlBean.setResultCode(obj.optInt("resultCode"));
                    downUrlBean.setResultDesc (obj.optString ("resultDesc"));
                    downUrlBean.setStatus (obj.optString ("status"));
                    if (downUrlBean.getResultCode() == SUCCESS)
                    {
                        JSONObject dataObj   = obj.optJSONObject("data");
                        if (dataObj != null)
                        {
                            DownUrlBean.Data data   = new DownUrlBean.Data();
                            data.setApp_no(dataObj.optString("app_no"));
                            data.setName(dataObj.optString("name"));
                            data.setGroup_no(dataObj.optString("group_no"));
                            data.setDownload_url(dataObj.optString("download_url"));
                            data.setId(gameId);

                            downUrlBean.setData(data);
                        }
                    }
                    return downUrlBean;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
            Log.e (TAG, "ParseTools parseDownUrlBean e="+e.toString ());
        }
        return null;
    }


    /**
     * 解析充值记录
     * @param data
     * @return
     */
    public static OrderBean parseOrderBean (String data)
    {
        try
        {
            if (!TextUtils.isEmpty (data))
            {
                JSONObject obj  = new JSONObject (data);
                if (obj != null)
                {
                    OrderBean orderBean     = new OrderBean ();
                    orderBean.setResultCode(obj.optInt("resultCode"));
                    orderBean.setResultDesc (obj.optString ("resultDesc"));
                    orderBean.setStatus (obj.optString ("status"));
                    if (orderBean.getResultCode() == SUCCESS)
                    {
                        JSONArray dataArray     = obj.optJSONArray ("data");
                        if (dataArray != null)
                        {
                            for (int i = 0; i < dataArray.length (); i ++)
                            {
                                JSONObject jo   = dataArray.optJSONObject (i);
                                if (jo != null)
                                {
                                    OrderBean.Data dataBean     = new OrderBean.Data ();
                                    dataBean.setObject (jo.optString ("object"));
                                    dataBean.setGoods (jo.optString ("goods"));
                                    dataBean.setType (jo.optString ("type"));
                                    dataBean.setPay_channel (jo.optString ("pay_channel"));
                                    dataBean.setTime (jo.optString ("time"));
                                    dataBean.setAmount (jo.optString ("amount"));
                                    orderBean.addData (dataBean);
                                }
                            }
                        }
                    }
                    return orderBean;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 判断返回的数据是成功还是失败
     * @param str
     * @return
     */
    public static boolean isSuccess (String str)
    {
        try
        {
            JSONObject jo   = new JSONObject (str);
            if (jo != null)
            {
                int resultCode   = jo.optInt ("resultCode");
                if (resultCode == SUCCESS)
                {
                    return true;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return false;
    }

    /**
     * 用户信息更新
     * @param str
     * @return
     */
    public static CenterInfoBean parseCenterInfoBean (String str)
    {
        try
        {
            JSONObject obj   = new JSONObject (str);
            if (obj != null)
            {
                CenterInfoBean centerInfoBean   = new CenterInfoBean ();
                centerInfoBean.setResultCode (obj.optInt ("resultCode"));
                centerInfoBean.setResultDesc (obj.optString ("resultDesc"));
                if (centerInfoBean.getResultCode () == SUCCESS)
                {
                    JSONObject dataObj  = obj.optJSONObject ("data");
                    if (dataObj != null)
                    {
                        CenterInfoBean.Data dataBean    = new CenterInfoBean.Data ();
                        dataBean.setVer_status (dataObj.optInt ("ver_status"));
                        dataBean.setUser_id (dataObj.optString ("user_id"));
                        dataBean.setReal_name (dataObj.optString ("real_name"));
                        dataBean.setId_card_no (dataObj.optString ("id_card_no"));
                        dataBean.setMobile (dataObj.optString ("mobile"));
                        dataBean.setNick_name (dataObj.optString ("nick_name"));
                        dataBean.setLogin_type (dataObj.optInt ("login_type"));
                        dataBean.setCoin_count (dataObj.optInt ("coin_count"));
                        centerInfoBean.setData (dataBean);
                    }
                }
                return centerInfoBean;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 修改用户名
     * @return
     */
    public static ChangeNickNameBean parseChangeNickNameBean (String str)
    {
        try
        {
            JSONObject obj   = new JSONObject (str);
            if (obj != null)
            {
                ChangeNickNameBean changeNickNameBean   = new ChangeNickNameBean ();
                changeNickNameBean.setResultCode (obj.optInt ("resultCode"));
                changeNickNameBean.setResultDesc (obj.optString ("resultDesc"));
                if (changeNickNameBean.getResultCode () == SUCCESS)
                {
                    JSONObject dataObj  = obj.optJSONObject ("data");
                    if (dataObj != null)
                    {
                        ChangeNickNameBean.Data dataBean    = new ChangeNickNameBean.Data ();
                        dataBean.setNick_name (dataObj.optString ("nick_name"));
                        changeNickNameBean.setData (dataBean);
                    }
                }
                return changeNickNameBean;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 解析实名认证
     * @param str
     * @return
     */
    public static IdCardBean parseIdCardBean (String str)
    {
        try
        {
            JSONObject obj   = new JSONObject (str);
            if (obj != null)
            {
                IdCardBean idCardBean   = new IdCardBean ();
                idCardBean.setResultCode (obj.optInt ("resultCode"));
                idCardBean.setResultDesc (obj.optString ("resultDesc"));
                if (idCardBean.getResultCode () == SUCCESS)
                {
                    JSONObject dataObj  = obj.optJSONObject ("data");
                    if (dataObj != null)
                    {
                        IdCardBean.Data dataBean    = new IdCardBean.Data ();
                        dataBean.setVer_status (dataObj.optInt ("ver_status"));
                        dataBean.setUser_id (dataObj.optString ("user_id"));
                        dataBean.setReal_name (dataObj.optString ("real_name"));
                        dataBean.setId_card_no (dataObj.optString ("id_card_no"));
                        dataBean.setMobile (dataObj.optString ("mobile"));
                        dataBean.setNick_name (dataObj.optString ("nick_name"));
                        dataBean.setLogin_type (dataObj.optInt ("login_type"));
                        dataBean.setCoin_count (dataObj.optInt ("coin_count"));
                        idCardBean.setData (dataBean);
                    }
                }
                return idCardBean;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 解析绑定验证码
     * @param str
     * @return
     */
    public static GetVerifyBean parseGetVerifyBean (String str)
    {
        try
        {
            JSONObject jo   = new JSONObject (str);
            if (jo != null)
            {
                GetVerifyBean getVerifyBean     = new GetVerifyBean ();
                getVerifyBean.setResultCode (jo.optInt ("resultCode"));
                getVerifyBean.setResultDesc (jo.optString ("resultDesc"));
                getVerifyBean.setStatus (jo.optString ("status"));
                return getVerifyBean;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return null;
    }

    public static BindTelBean parseBindTelBean (String str)
    {
        try
        {
            JSONObject jo   = new JSONObject (str);
            if (jo != null)
            {
                BindTelBean bindTelBean     = new BindTelBean ();
                bindTelBean.setResultCode (jo.optInt ("resultCode"));
                bindTelBean.setResultDesc (jo.optString ("resultDesc"));
                bindTelBean.setStatus (jo.optString ("status"));
                return bindTelBean;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 解析礼券
     * @param str
     * @return
     */
    public static CouponBean parseCouponBean (String str)
    {
        try
        {
            JSONObject jo   = new JSONObject (str);
            if (jo != null)
            {
                CouponBean couponBean   = new CouponBean ();
                couponBean.setResultCode (jo.optInt ("resultCode"));
                couponBean.setResultDesc (jo.optString ("resultDesc"));
                couponBean.setStatus (jo.optString ("status"));
                if (couponBean.getResultCode () == SUCCESS)
                {
                    JSONArray dataArr  = jo.optJSONArray ("data");
                    if (dataArr != null)
                    {
                        for (int i = 0 ; i < dataArr.length (); i ++)
                        {
                            JSONObject ja   = dataArr.optJSONObject (i);
                            CouponBean.Data data    = new CouponBean.Data ();
                            data.setStore_game_no (ja.optString ("store_game_no"));
                            data.setEnd_time (ja.optString ("end_time"));
                            data.setSurplus_count (ja.optInt ("surplus_count"));
                            data.setName (ja.optString ("name"));
                            data.setTotal_count (ja.optInt ("total_count"));
                            data.setStart_time (ja.optString ("start_time"));
                            data.setApp_name (ja.optString ("app_name"));
                            couponBean.addData (data);
                        }

                    }
                }
                return couponBean;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 解析拥有的优惠券
     * @param str
     * @return
     */
    public static OwnCouponBean parseOwnCouponBean (String str)
    {
        try
        {
            JSONObject jo   = new JSONObject (str);
            if (jo != null)
            {
                OwnCouponBean ownCouponBean   = new OwnCouponBean ();
                ownCouponBean.setResultCode (jo.optInt ("resultCode"));
                ownCouponBean.setResultDesc (jo.optString ("resultDesc"));
                ownCouponBean.setStatus (jo.optString ("status"));
                if (ownCouponBean.getResultCode () == SUCCESS)
                {
                    JSONArray dataArr  = jo.optJSONArray ("data");
                    if (dataArr != null)
                    {
                        for (int i = 0 ; i < dataArr.length (); i ++)
                        {
                            JSONObject ja   = dataArr.optJSONObject (i);
                            OwnCouponBean.Data data    = new OwnCouponBean.Data ();
                            data.setEnd_time (ja.optString ("end_time"));
                            data.setApply_time (ja.optString ("apply_time"));
                            data.setUser_id (ja.optString ("user_id"));
                            data.setGroup_no (ja.optString ("group_no"));
                            data.setApp_name (ja.optString ("app_name"));
                            data.setStore_game_no (ja.optString ("store_game_no"));
                            data.setUser_id (ja.optString ("user_name"));
                            data.setDownload_url (ja.optString ("download_url"));
                            data.setIcon_url (ja.optString ("icon_url"));
                            data.setName (ja.optString ("name"));
                            data.setStart_time (ja.optString ("start_time"));
                            data.setCoupon_code (ja.optString ("coupon_code"));
                            ownCouponBean.addData (data);
                        }
                    }
                }
                return ownCouponBean;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 解析获取优惠券
     * @param str
     * @return
     */
    public static ApplyCouponBean parseApplyCouponBean (String str)
    {
        try
        {
            JSONObject jo   = new JSONObject (str);
            if (jo != null)
            {
                ApplyCouponBean applyCouponBean   = new ApplyCouponBean ();
                applyCouponBean.setResultCode (jo.optInt ("resultCode"));
                applyCouponBean.setResultDesc (jo.optString ("resultDesc"));
                applyCouponBean.setStatus (jo.optString ("status"));
                if (applyCouponBean.getResultCode () == SUCCESS)
                {
                    JSONObject dataObj  = jo.optJSONObject ("data");
                    if (dataObj != null)
                    {
                        ApplyCouponBean.Data data    = new ApplyCouponBean.Data ();
                        data.setEnd_time (dataObj.optString ("end_time"));
                        data.setApply_time (dataObj.optString ("apply_time"));
                        data.setUser_id (dataObj.optString ("user_id"));
                        data.setGroup_no (dataObj.optString ("group_no"));
                        data.setApp_name (dataObj.optString ("app_name"));
                        data.setStore_game_no (dataObj.optString ("store_game_no"));
                        data.setUser_id (dataObj.optString ("user_name"));
                        data.setDownload_url (dataObj.optString ("download_url"));
                        data.setIcon_url (dataObj.optString ("icon_url"));
                        data.setName (dataObj.optString ("name"));
                        data.setStart_time (dataObj.optString ("start_time"));
                        data.setCoupon_code (dataObj.optString ("coupon_code"));
                        applyCouponBean.setData (data);
                    }
                }
                return applyCouponBean;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return null;
    }

    /**
     * 解析收缩结果
     * @param str
     * @return
     */
    public static SearchBean parseSearchBean (String str)
    {
        try
        {
            JSONObject jo   = new JSONObject (str);
            if (jo != null)
            {
                SearchBean searchBean   = new SearchBean ();
                searchBean.setResultCode (jo.optInt ("resultCode"));
                searchBean.setResultDesc (jo.optString ("resultDesc"));
                searchBean.setStatus (jo.optString ("status"));
                if (searchBean.getResultCode () == SUCCESS)
                {
                    JSONObject dataObj  = jo.optJSONObject ("data");
                    if (dataObj != null)
                    {
                        JSONArray pageData  = dataObj.optJSONArray ("page_data");
                        if (pageData != null)
                        {
                            for (int i = 0; i < pageData.length (); i ++)
                            {
                                JSONObject pageDataObj  = pageData.optJSONObject (i);
                                String showType     = pageDataObj.optString ("showType");
                                if (!TextUtils.isEmpty (showType) && showType.contains ("common"))
                                {
                                    JSONArray gameslistArray     = pageDataObj.optJSONArray ("gameslist");
                                    if (gameslistArray != null && gameslistArray.length () > 0)
                                    {
                                        JSONObject dataItemObj  = gameslistArray.optJSONObject (0);
                                        if (dataItemObj != null)
                                        {
                                            SearchBean.Data data    = new SearchBean.Data ();
                                            data.setSize (dataItemObj.optString ("size"));
                                            data.setIcon (dataItemObj.optString ("icon"));
                                            data.setTag (dataItemObj.optString ("tag"));
                                            data.setSecond_discount (dataItemObj.optString ("second_discount"));
                                            data.setDiscount_start (dataItemObj.optString ("discount_start"));
                                            data.setFirst_discount (dataItemObj.optString ("first_discount"));
                                            data.setId (dataItemObj.optString ("id"));
                                            data.setBanner (dataItemObj.optString ("banner"));
                                            data.setDiscount_end (dataItemObj.optString ("discount_end"));
                                            data.setName (dataItemObj.optString ("name"));
                                            data.setSub_title (dataItemObj.optString ("sub_title"));
                                            data.setCategory (dataItemObj.optString ("category"));
                                            data.setDownload_count (dataItemObj.optString ("download_count"));
                                            data.setJsonStr (dataItemObj.toString ());
                                            searchBean.addData (data);
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
                return searchBean;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return null;
    }
}
