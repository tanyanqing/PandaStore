package cn.panda.game.pandastore.bean;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

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
}
