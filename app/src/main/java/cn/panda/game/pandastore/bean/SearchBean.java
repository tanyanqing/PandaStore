package cn.panda.game.pandastore.bean;

import java.util.ArrayList;
import java.util.List;

public class SearchBean
{
    private int resultCode;
    private String resultDesc;
    private String status;
    private List<Data> datas;

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

    public String getStatus () {
        return status;
    }

    public void setStatus (String status) {
        this.status = status;
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
        private String size;
        private String icon;
        private String tag;
        private String second_discount;
        private String discount_start;
        private String first_discount;
        private String id;
        private String banner;
        private String discount_end;
        private String name;
        private String sub_title;
        private String category;
        private String download_count;
        private String jsonStr;

        public String getJsonStr () {
            return jsonStr;
        }

        public void setJsonStr (String jsonStr) {
            this.jsonStr = jsonStr;
        }

        public String getSize () {
            return size;
        }

        public void setSize (String size) {
            this.size = size;
        }

        public String getIcon () {
            return icon;
        }

        public void setIcon (String icon) {
            this.icon = icon;
        }

        public String getTag () {
            return tag;
        }

        public void setTag (String tag) {
            this.tag = tag;
        }

        public String getSecond_discount () {
            return second_discount;
        }

        public void setSecond_discount (String second_discount) {
            this.second_discount = second_discount;
        }

        public String getDiscount_start () {
            return discount_start;
        }

        public void setDiscount_start (String discount_start) {
            this.discount_start = discount_start;
        }

        public String getFirst_discount () {
            return first_discount;
        }

        public void setFirst_discount (String first_discount) {
            this.first_discount = first_discount;
        }

        public String getId () {
            return id;
        }

        public void setId (String id) {
            this.id = id;
        }

        public String getBanner () {
            return banner;
        }

        public void setBanner (String banner) {
            this.banner = banner;
        }

        public String getDiscount_end () {
            return discount_end;
        }

        public void setDiscount_end (String discount_end) {
            this.discount_end = discount_end;
        }

        public String getName () {
            return name;
        }

        public void setName (String name) {
            this.name = name;
        }

        public String getSub_title () {
            return sub_title;
        }

        public void setSub_title (String sub_title) {
            this.sub_title = sub_title;
        }

        public String getCategory () {
            return category;
        }

        public void setCategory (String category) {
            this.category = category;
        }

        public String getDownload_count () {
            return download_count;
        }

        public void setDownload_count (String download_count) {
            this.download_count = download_count;
        }
    }
}
