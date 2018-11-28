package cn.panda.game.pandastore.bean;

public class ChangeNickNameBean
{
    private String resultDesc;
    private int resultCode;
    private Data data;

    public Data getData () {
        return data;
    }

    public void setData (Data data) {
        this.data = data;
    }

    public String getResultDesc () {
        return resultDesc;
    }

    public void setResultDesc (String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public int getResultCode () {
        return resultCode;
    }

    public void setResultCode (int resultCode) {
        this.resultCode = resultCode;
    }

    public static class Data
    {
        private String nick_name;

        public String getNick_name () {
            return nick_name;
        }

        public void setNick_name (String nick_name) {
            this.nick_name = nick_name;
        }
    }
}
