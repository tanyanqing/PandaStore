package cn.panda.game.pandastore.bean;

public class VerifyBean
{
    private String user_id;
    private boolean isSuccess;
    private String resultDesc;

    public String getResultDesc () {
        return resultDesc;
    }

    public void setResultDesc (String resultDesc) {
        this.resultDesc = resultDesc;
    }

    public String getUser_id () {
        return user_id;
    }

    public void setUser_id (String user_id) {
        this.user_id = user_id;
    }

    public boolean isSuccess () {
        return isSuccess;
    }

    public void setSuccess (boolean success) {
        isSuccess = success;
    }
}
