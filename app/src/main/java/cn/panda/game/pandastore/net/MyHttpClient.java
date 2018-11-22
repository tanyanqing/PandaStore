package cn.panda.game.pandastore.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.migu.video.components.shareDish.net.MGSVHttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyHttpClient
{
    RequestQueue mRequestQueue;
    public MyHttpClient (Context context)
    {
        mRequestQueue   = Volley.newRequestQueue(context);
    }


    public void startHttpRequest (int method, String url, String json, final MyHttpResponseHandler handler)
    {
        try {
            JSONObject jo = new JSONObject(json);
            JsonObjectRequest request     = new JsonObjectRequest(method, url, jo, new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            // TODO Auto-generated method stub
                            handler.onSuccess(response.toString());
                        }

                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            // TODO Auto-generated method stub
                            handler.onFailure(error, error.getMessage());
                        }
                    });
            mRequestQueue.add(request);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            handler.onFailure(null, e.toString());
        }

    }

    public void postForm (int method, final String url, final StringBuffer params, final MyHttpResponseHandler handler)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run() {

                try {
                    byte[] data = params.toString().getBytes();
                    HttpURLConnection httpURLConnection = (HttpURLConnection)new URL (url).openConnection();
                    httpURLConnection.setConnectTimeout(3000);//设置连接超时时间
                    httpURLConnection.setDoInput(true);                  //打开输入流，以便从服务器获取数据
                    httpURLConnection.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
                    httpURLConnection.setRequestMethod("POST");           //设置以Post方式提交数据
                    httpURLConnection.setUseCaches(false);               //使用Post方式不能使用缓存
                    //设置请求体的类型是文本类型
                    httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    //设置请求体的长度
                    httpURLConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
                    //获得输出流，向服务器写入数据
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    outputStream.write(data);

                    int response = httpURLConnection.getResponseCode();            //获得服务器的响应码
                    if(response == HttpURLConnection.HTTP_OK) {
                        InputStream inptStream = httpURLConnection.getInputStream();
                        String result = dealResponseResult(inptStream);                     //处理服务器的响应结果
                        handler.onSuccess(result);
                    }
                    else
                    {
                        handler.onFailure(null, response+"");
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                    handler.onFailure(null, e.toString());
                }
            }
        }).start();
    }

    public static String dealResponseResult(InputStream inputStream)
    {
        String resultData = null;      //存储处理结果
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int len = 0;
        try {
            while((len = inputStream.read(data)) != -1) {
                byteArrayOutputStream.write(data, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultData = new String(byteArrayOutputStream.toByteArray());
        return resultData;
    }
}
