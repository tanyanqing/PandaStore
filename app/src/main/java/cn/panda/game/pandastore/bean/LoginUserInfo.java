package cn.panda.game.pandastore.bean;

public class LoginUserInfo
{
    private int resultCode;
    private String resultDesc;
    private Data data;

    public int getResultCode () {
        return resultCode;
    }

    public void setResultCode (int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultDesc () {
        return resultDesc;
    }

    public void setResultDesc (String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public Data getData () {
        return data;
    }

    public void setData (Data data) {
        this.data = data;
    }

    public static class Data
    {
        private int coin_count;
        private String user_id;
        private int app_coin;
        private String id_card_no;
        private String nick_name;
        private String app_name;
        private String group_no;
        private String channel_no;
        private String real_name;
        private int login_type;
        private int ver_status;
        private String app_no;
        private String mobile;

        public int getCoin_count () {
            return coin_count;
        }

        public void setCoin_count (int coin_count) {
            this.coin_count = coin_count;
        }

        public String getUser_id () {
            return user_id;
        }

        public void setUser_id (String user_id) {
            this.user_id = user_id;
        }

        public int getApp_coin () {
            return app_coin;
        }

        public void setApp_coin (int app_coin) {
            this.app_coin = app_coin;
        }

        public String getId_card_no () {
            return id_card_no;
        }

        public void setId_card_no (String id_card_no) {
            this.id_card_no = id_card_no;
        }

        public String getNick_name () {
            return nick_name;
        }

        public void setNick_name (String nick_name) {
            this.nick_name = nick_name;
        }

        public String getApp_name () {
            return app_name;
        }

        public void setApp_name (String app_name) {
            this.app_name = app_name;
        }

        public String getGroup_no () {
            return group_no;
        }

        public void setGroup_no (String group_no) {
            this.group_no = group_no;
        }

        public String getChannel_no () {
            return channel_no;
        }

        public void setChannel_no (String channel_no) {
            this.channel_no = channel_no;
        }

        public String getReal_name () {
            return real_name;
        }

        public void setReal_name (String real_name) {
            this.real_name = real_name;
        }

        public int getLogin_type () {
            return login_type;
        }

        public void setLogin_type (int login_type) {
            this.login_type = login_type;
        }

        public int getVer_status () {
            return ver_status;
        }

        public void setVer_status (int ver_status) {
            this.ver_status = ver_status;
        }

        public String getApp_no () {
            return app_no;
        }

        public void setApp_no (String app_no) {
            this.app_no = app_no;
        }

        public String getMobile () {
            return mobile;
        }

        public void setMobile (String mobile) {
            this.mobile = mobile;
        }
    }
}
