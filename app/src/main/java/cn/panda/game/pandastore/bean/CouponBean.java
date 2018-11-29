package cn.panda.game.pandastore.bean;

import java.util.ArrayList;
import java.util.List;

public class CouponBean
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
        if (this.datas == null)
        {
            this.datas  = new ArrayList<> ();
        }
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
//        "store_game_no": "ga23112747146171555",
//                "end_time": "2018-12-01 23:59:59",
//                "surplus_count": 0,
//                "name": "礼包6",
//                "app_name": "楚留香",
//                "total_count": 1,
//                "start_time": "2018-06-07 00:00:00"
        private String store_game_no;
        private String end_time;
        private int surplus_count;
        private String name;
        private int total_count;
        private String start_time;
        private String app_name;

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

        public String getEnd_time () {
            return end_time;
        }

        public void setEnd_time (String end_time) {
            this.end_time = end_time;
        }

        public int getSurplus_count () {
            return surplus_count;
        }

        public void setSurplus_count (int surplus_count) {
            this.surplus_count = surplus_count;
        }

        public String getName () {
            return name;
        }

        public void setName (String name) {
            this.name = name;
        }

        public int getTotal_count () {
            return total_count;
        }

        public void setTotal_count (int total_count) {
            this.total_count = total_count;
        }

        public String getStart_time () {
            return start_time;
        }

        public void setStart_time (String start_time) {
            this.start_time = start_time;
        }




    }
}
