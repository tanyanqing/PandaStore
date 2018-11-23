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

                        JSONArray gamesArray    = dataObj.optJSONArray ("games");
                        if (gamesArray != null)
                        {
                            for (int i = 0; i < gamesArray.length (); i ++)
                            {
                                JSONObject gameObj      = gamesArray.optJSONObject (i);

                                if (gameObj != null)
                                {
                                    GameListBean.Game game  = new GameListBean.Game ();
                                    game.setSub_title (gameObj.optString ("sub_title"));
                                    game.setFirst_discount (gameObj.optString ("first_discount"));
                                    game.setDownload_count (gameObj.optInt ("download_count"));
                                    game.setName (gameObj.optString ("name"));
                                    game.setDownload_url (gameObj.optString ("download_url"));
                                    game.setIcon (gameObj.optString ("icon"));
                                    game.setTag (gameObj.optString ("tag"));
                                    game.setCategory (gameObj.optString ("category"));
                                    game.setDiscount_end (gameObj.optString ("discount_end"));
                                    game.setSecond_discount (gameObj.optString ("second_discount"));
                                    game.setSize (gameObj.optString ("size"));
                                    game.setDiscount_start (gameObj.optString ("discount_start"));
                                    game.setRelated_game (gameObj.optString ("related_game"));
                                    game.setVersion (gameObj.optString ("version"));
                                    game.setDescription (gameObj.optString ("description"));
                                    game.setBanner (gameObj.optString ("banner"));
                                    game.setShow_pic1 (gameObj.optString ("show_pic1"));
                                    game.setShow_pic2 (gameObj.optString ("show_pic2"));
                                    game.setShow_pic3 (gameObj.optString ("show_pic3"));
                                    game.setShow_pic4 (gameObj.optString ("show_pic4"));
                                    game.setShow_pic5 (gameObj.optString ("show_pic5"));
                                    data.addGame (game);
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
}
