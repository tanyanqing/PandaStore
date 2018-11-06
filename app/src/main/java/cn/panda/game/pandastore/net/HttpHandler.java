package cn.panda.game.pandastore.net;

public abstract class HttpHandler
{
    public abstract void onSuccess (String result);
    public abstract void onFail (String result);
}
