package cn.panda.game.pandastore.tool;

import java.util.HashMap;
import java.util.Map;

import cn.panda.game.pandastore.bean.CenterInfoBean;
import cn.panda.game.pandastore.bean.ParseTools;
import cn.panda.game.pandastore.broadcast.BroadcastConstant;
import cn.panda.game.pandastore.constants.Code;
import cn.panda.game.pandastore.net.HttpHandler;
import cn.panda.game.pandastore.net.Server;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import org.json.JSONObject;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint("SetJavaScriptEnabled") 
public class PandaPayBrowser extends Activity 
{
    private WebView browserView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        browserView = new WebView(this);
        LayoutParams viewLayoutParams = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        viewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        viewLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        browserView.setLayoutParams(viewLayoutParams);

        RelativeLayout layout = new RelativeLayout(this);
        LayoutParams layoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(layoutParams);

        layout.addView(browserView);

        this.setContentView(layout);
        

        Bundle bundle = this.getIntent().getExtras();
        
        WebSettings settings = browserView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        String uri                  = bundle.getString(Code.PAYURL.name());
        String myOutTradeNo         = bundle.getString(Code.OutTradeNo.name());

        Map<String, String> extraHeaders = new HashMap<String, String>();
        extraHeaders.put("Referer", "http://www.mycente.com");//例如 http://www.baidu.com 
        uri     = uri +"&redirect_url=http%3a%2f%2fcms.mycente.com%2fhtmlRoute%2fwechatPay%2fPayFinish.html%3fmyOutTradeNo%3d"+myOutTradeNo;

        browserView.loadUrl(uri, extraHeaders);
        
        browserView.setWebViewClient(new BrowserWebViewClient());
        browserView.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                return true;
            }
        });
    }
    Handler exitHandler = new Handler ()
    {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            PandaPayBrowser.this.finish();
        }
        
    };
    
    private class BrowserWebViewClient extends WebViewClient
    {
        @Override 
        public void onReceivedSslError(WebView view, SslErrorHandler handler,
                SslError error) {
            // TODO Auto-generated method stub
            super.onReceivedSslError(view, handler, error);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) 
        {
            // TODO Auto-generated method stub
            if (url.startsWith("weixin://wap/pay?"))
            {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                return true;
            }
            else if (url.startsWith("http://www.mycente.com/wechatPay/PayResult.html") || url.startsWith("http://cms.mycente.com/htmlRoute/wechatPay/PayResult.html"))
            {
                Uri uri         = Uri.parse(url);
                String data     = uri.getQueryParameter ("data");
                finishWXH5Callback (data);
                PandaPayBrowser.this.finish ();
            }
            
            return false;
        }

        @Override
        public void onPageFinished(WebView view, String url) 
        {
            // TODO Auto-generated method stub
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                String description, String failingUrl) 
        {
            // TODO Auto-generated method stub
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
        
        
        
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) 
        {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) 
        {
            return false;
        }
        return super.onKeyUp(keyCode, event);
    }

    
    private void finishWXH5Callback (String data)
    {
        if (!TextUtils.isEmpty (data))
        {
            try {
                JSONObject jo      = new JSONObject (data);
                int resultCode     = jo.optInt ("resultCode");
                if (resultCode == 100)
                {
                    JSONObject dataJo       = jo.optJSONObject ("data");
                    String out_trade_no     = dataJo.optString ("out_trade_no");
                    String order_status     = dataJo.optString ("order_status");
                    if (order_status.contains("成功"))
                    {
                        Toast.makeText (this, "支付完成", Toast.LENGTH_SHORT).show ();

                        Server.getServer (PandaPayBrowser.this).getUserCenterInfo (MyUserInfoSaveTools.getUserId (), Tools.getChannelNo (PandaPayBrowser.this), new HttpHandler () {
                            @Override
                            public void onSuccess (String result)
                            {
                                CenterInfoBean centerInfoBean   = ParseTools.parseCenterInfoBean (result);
                                if (centerInfoBean != null && centerInfoBean.getData () != null)
                                {
                                    if (centerInfoBean.getData ().getUser_id ().equals (MyUserInfoSaveTools.getUserId ()))
                                    {
                                        MyUserInfoSaveTools.saveCoinCount (centerInfoBean.getData ().getCoin_count ());
                                    }
                                }

                                Intent intent = new Intent ();
                                intent.setAction (BroadcastConstant.ACTION_FILTER);
                                PandaPayBrowser.this.sendBroadcast(intent);
                            }

                            @Override
                            public void onFail (String result) {

                            }
                        });
                    }
                    else if (order_status.contains("未支付"))
                    {
                        Toast.makeText (this, "订单未支付", Toast.LENGTH_SHORT).show ();
                    }
                    else
                    {
                        Toast.makeText (this, "支付失败", Toast.LENGTH_SHORT).show ();
                    }
                }
                else
                {
                    Toast.makeText (this, "支付失败", Toast.LENGTH_SHORT).show ();
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Toast.makeText (this, "支付失败", Toast.LENGTH_SHORT).show ();
            }
        }
    }
    
    
    
}
