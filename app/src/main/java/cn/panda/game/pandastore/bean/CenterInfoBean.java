package cn.panda.game.pandastore.bean;

public class CenterInfoBean
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
//        "ver_status": 1,
//                "user_id": "5bea657e587eb174ef752a24",
//                "real_name": null,
//                "id_card_no": null,
//                "mobile": null,
//                "nick_name": "13671515236",
//                "list_app_coin": [],
//                "login_type": 0,
//                "coin_count": 2
        private int ver_status;
        private String user_id;
        private String real_name;
        private String id_card_no;
        private String mobile;
        private String nick_name;
        private int login_type;
        private int coin_count;

        public int getVer_status () {
            return ver_status;
        }

        public void setVer_status (int ver_status) {
            this.ver_status = ver_status;
        }

        public String getUser_id () {
            return user_id;
        }

        public void setUser_id (String user_id) {
            this.user_id = user_id;
        }

        public String getReal_name () {
            return real_name;
        }

        public void setReal_name (String real_name) {
            this.real_name = real_name;
        }

        public String getId_card_no () {
            return id_card_no;
        }

        public void setId_card_no (String id_card_no) {
            this.id_card_no = id_card_no;
        }

        public String getMobile () {
            return mobile;
        }

        public void setMobile (String mobile) {
            this.mobile = mobile;
        }

        public String getNick_name () {
            return nick_name;
        }

        public void setNick_name (String nick_name) {
            this.nick_name = nick_name;
        }

        public int getLogin_type () {
            return login_type;
        }

        public void setLogin_type (int login_type) {
            this.login_type = login_type;
        }

        public int getCoin_count () {
            return coin_count;
        }

        public void setCoin_count (int coin_count) {
            this.coin_count = coin_count;
        }
    }
}
