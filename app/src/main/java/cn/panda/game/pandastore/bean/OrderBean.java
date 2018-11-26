package cn.panda.game.pandastore.bean;

import java.util.ArrayList;
import java.util.List;

public class OrderBean
{
    private String resultDesc;
    private String status;
    private int resultCode;
    private List<Data> data;

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

    public List<Data> getData ()
    {
        if (this.data == null)
        {
            this.data   = new ArrayList<> ();
        }
        return data;
    }

    public void setData (List<Data> data) {
        this.data = data;
    }
    public void addData (Data data)
    {
        if (this.data == null)
        {
            this.data   = new ArrayList<> ();
        }
        this.data.add (data);
    }

    public static class Data
    {
        private String object;
        private String goods;
        private String type;
        private String pay_channel;
        private String time;
        private String amount;

        public String getObject () {
            return object;
        }

        public void setObject (String object) {
            this.object = object;
        }

        public String getGoods () {
            return goods;
        }

        public void setGoods (String goods) {
            this.goods = goods;
        }

        public String getType () {
            return type;
        }

        public void setType (String type) {
            this.type = type;
        }

        public String getPay_channel () {
            return pay_channel;
        }

        public void setPay_channel (String pay_channel) {
            this.pay_channel = pay_channel;
        }

        public String getTime () {
            return time;
        }

        public void setTime (String time) {
            this.time = time;
        }

        public String getAmount () {
            return amount;
        }

        public void setAmount (String amount) {
            this.amount = amount;
        }
    }
}
