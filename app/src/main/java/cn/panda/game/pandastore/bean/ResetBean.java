package cn.panda.game.pandastore.bean;

public class ResetBean
{
    private boolean isSuccess;
    private String resultDesc;

    public boolean isSuccess () {
        return isSuccess;
    }

    public void setSuccess (boolean success) {
        isSuccess = success;
    }

    public String getResultDesc () {
        return resultDesc;
    }

    public void setResultDesc (String resultDesc) {
        this.resultDesc = resultDesc;
    }
}
