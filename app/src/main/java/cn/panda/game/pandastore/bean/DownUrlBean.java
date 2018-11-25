package cn.panda.game.pandastore.bean;

public class DownUrlBean
{
    private String resultDesc;
    private String status;
    private int resultCode;
    private Data data;

    public String getResultDesc() {
        return resultDesc;
    }

    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data
    {
        private String name;
        private String group_no;
        private String download_url;
        private String app_no;
        private String id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGroup_no() {
            return group_no;
        }

        public void setGroup_no(String group_no) {
            this.group_no = group_no;
        }

        public String getDownload_url() {
            return download_url;
        }

        public void setDownload_url(String download_url) {
            this.download_url = download_url;
        }

        public String getApp_no() {
            return app_no;
        }

        public void setApp_no(String app_no) {
            this.app_no = app_no;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
