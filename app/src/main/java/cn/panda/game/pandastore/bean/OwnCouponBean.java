package cn.panda.game.pandastore.bean;

import java.util.ArrayList;
import java.util.List;

public class OwnCouponBean
{
    private String resultDesc;
    private String status;
    private int resultCode;
    private List<Data> datas;

    public String getResultDesc () {
        return resultDesc;
    }

    public void setResultDesc (String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    public int getResultCode () {
        return resultCode;
    }

    public void setResultCode (int resultCode) {
        this.resultCode = resultCode;
    }

    public List<Data> getDatas () {
        if (this.datas == null)
        {
            this.datas  = new ArrayList<> ();
        }
        return datas;
    }

    public void setDatas (List<Data> datas) {
        this.datas = datas;
    }
    public void addData (Data data)
    {
        if (this.datas == null)
        {
            this.datas  = new ArrayList<> ();
        }
        this.datas.add (data);
    }

    public static class Data
    {
        private String end_time;
        private String apply_time;
        private String user_id;
        private String group_no;
        private String app_name;
        private String store_game_no;
        private String user_name;
        private String download_url;
        private String icon_url;
        private String name;
        private String start_time;
        private String coupon_code;

        public String getEnd_time () {
            return end_time;
        }

        public void setEnd_time (String end_time) {
            this.end_time = end_time;
        }

        public String getApply_time () {
            return apply_time;
        }

        public void setApply_time (String apply_time) {
            this.apply_time = apply_time;
        }

        public String getUser_id () {
            return user_id;
        }

        public void setUser_id (String user_id) {
            this.user_id = user_id;
        }

        public String getGroup_no () {
            return group_no;
        }

        public void setGroup_no (String group_no) {
            this.group_no = group_no;
        }

        public String getApp_name () {
            return app_name;
        }

        public void setApp_name (String app_name) {
            this.app_name = app_name;
        }

        public String getStore_game_no () {
            return store_game_no;
        }

        public void setStore_game_no (String store_game_no) {
            this.store_game_no = store_game_no;
        }

        public String getUser_name () {
            return user_name;
        }

        public void setUser_name (String user_name) {
            this.user_name = user_name;
        }

        public String getDownload_url () {
            return download_url;
        }

        public void setDownload_url (String download_url) {
            this.download_url = download_url;
        }

        public String getIcon_url () {
            return icon_url;
        }

        public void setIcon_url (String icon_url) {
            this.icon_url = icon_url;
        }

        public String getName () {
            return name;
        }

        public void setName (String name) {
            this.name = name;
        }

        public String getStart_time () {
            return start_time;
        }

        public void setStart_time (String start_time) {
            this.start_time = start_time;
        }

        public String getCoupon_code () {
            return coupon_code;
        }

        public void setCoupon_code (String coupon_code) {
            this.coupon_code = coupon_code;
        }
    }
}
