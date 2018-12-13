package cn.panda.game.pandastore;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.panda.game.pandastore.adapter.OwnerCouponListAdapter;
import cn.panda.game.pandastore.bean.ApplyCouponBean;
import cn.panda.game.pandastore.bean.CouponBean;
import cn.panda.game.pandastore.bean.DownUrlBean;
import cn.panda.game.pandastore.bean.GameDetailBean;
import cn.panda.game.pandastore.bean.GameListBean;
import cn.panda.game.pandastore.bean.ParseTools;
import cn.panda.game.pandastore.net.HttpHandler;
import cn.panda.game.pandastore.net.Server;
import cn.panda.game.pandastore.tool.GlideTools;
import cn.panda.game.pandastore.tool.InitRecyclerViewLayout;
import cn.panda.game.pandastore.tool.MyDialog;
import cn.panda.game.pandastore.tool.MyDownTools;
import cn.panda.game.pandastore.tool.MyUserInfoSaveTools;
import cn.panda.game.pandastore.untils.ApplicationContext;
import cn.panda.game.pandastore.widget.SpaceImageDetailActivity;


public class GameDetailActivity extends Activity
{

    private MyHandler mMyHandler;

    private ImageView mBannerImage;

    private String mTitle;
    private TextView mTitleView;

    private ImageView mGameIcon;
    private TextView mGameName;
    private TextView mGameTag;
    private TextView mGameSize;

    private TextView mDetailDes;

    private TextView mDetailVersion;
    private TextView mDetailSize;
    private TextView mDetailTime;

    private GameDetailBean mGameDetailBean;

    private String mGameId;
    private ProgressDialog mProgressDialog;//网络请求时的loading

    private ImageView mImage1;
    private ImageView mImage2;
    private ImageView mImage3;
    private ImageView mImage4;
    private ImageView mImage5;

    private View mCouponView;
    private LinearLayout mCouponList;

    private MyDialog mCouponDialog;

    public static DisplayImageOptions mNormalImageOptions;
    public static final String SDCARD_PATH      = Environment.getExternalStorageDirectory().toString();
    public static final String IMAGES_FOLDER    = SDCARD_PATH + File.separator + "demo" + File.separator + "images" + File.separator;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_res_detail);
        mMyHandler  = new MyHandler (this);

        initView ();
        initData ();
        initImageLoader (this);

    }

    private void initImageLoader(Context context)
    {
        int memoryCacheSize     = (int) (Runtime.getRuntime().maxMemory() / 5);
        MemoryCacheAware<String, Bitmap> memoryCache;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
        {
            memoryCache     = new LruMemoryCache (memoryCacheSize);
        } else {
            memoryCache     = new LRULimitedMemoryCache (memoryCacheSize);
        }

        mNormalImageOptions     = new DisplayImageOptions.Builder().bitmapConfig(Bitmap.Config.RGB_565).cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true).build();

        ImageLoaderConfiguration config     = new ImageLoaderConfiguration.Builder(context).defaultDisplayImageOptions(mNormalImageOptions)
                .denyCacheImageMultipleSizesInMemory().discCache(new UnlimitedDiscCache (new File (IMAGES_FOLDER)))
                .memoryCache(memoryCache)
                .tasksProcessingOrder(QueueProcessingType.LIFO).threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(3).build();
        ImageLoader.getInstance().init(config);
    }
    private void initView ()
    {
        mTitleView      = (TextView)findViewById (R.id.title);

        mBannerImage    = (ImageView)findViewById(R.id.banner_image);

        mGameIcon       = (ImageView)findViewById (R.id.game_icon);
        mGameName       = (TextView)findViewById (R.id.game_name);
        mGameTag        = (TextView)findViewById (R.id.game_tag);
        mGameSize       = (TextView)findViewById (R.id.game_size);

        mDetailDes      = (TextView)findViewById (R.id.detail_des);

        mDetailVersion  = (TextView)findViewById (R.id.detail_version);
        mDetailSize     = (TextView)findViewById (R.id.detail_size);
        mDetailTime     = (TextView)findViewById (R.id.detail_time);

        mImage1         = (ImageView)findViewById (R.id.image1);
        mImage2         = (ImageView)findViewById (R.id.image2);
        mImage3         = (ImageView)findViewById (R.id.image3);
        mImage4         = (ImageView)findViewById (R.id.image4);
        mImage5         = (ImageView)findViewById (R.id.image5);


        mCouponView     = findViewById (R.id.coupon_view);
        mCouponList     = (LinearLayout) findViewById (R.id.coupon_list);

    }
    private void initData ()
    {
        mCouponView.setVisibility (View.GONE);

        String data                     = getIntent ().getStringExtra ("data");
        GameListBean.Game mGameData     = ParseTools.parseGame (data);
        if (mGameData != null)
        {
            mGameId         = mGameData.getId();
            Message msg     = mMyHandler.obtainMessage(HANDLER_START_GET_GAME);
            msg.obj         = mGameId;
            msg.sendToTarget();
        }


    }

    public void backButton (View view)
    {
        this.finish ();
    }
    public void downButton (View view)
    {
        mMyHandler.sendEmptyMessage(HANDLER_REQUEST_DOWNURL);
    }

    public void clickImage (View view)
    {
        List<String> datas  = new ArrayList<> ();
        int position        = 0;
        if (view.getId () == R.id.image1)
        {
            datas.add (mGameDetailBean.getData().getShow_pic1 ());
        }
        else if (view.getId () == R.id.image2)
        {
            datas.add (mGameDetailBean.getData().getShow_pic2 ());
        }
        if (view.getId () == R.id.image3)
        {
            datas.add (mGameDetailBean.getData().getShow_pic3 ());
        }
        if (view.getId () == R.id.image4)
        {
            datas.add (mGameDetailBean.getData().getShow_pic4 ());
        }
        if (view.getId () == R.id.image5)
        {
            datas.add (mGameDetailBean.getData().getShow_pic5 ());
        }

        Intent intent = new Intent(GameDetailActivity.this, SpaceImageDetailActivity.class);
        intent.putExtra("images", (ArrayList<String>) datas);
        intent.putExtra("position", 0);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        intent.putExtra("locationX", location[0]);
        intent.putExtra("locationY", location[1]);

        intent.putExtra("width", view.getWidth());
//        intent.putExtra("height", view.getHeight());
        intent.putExtra("height", view.getHeight() - getResources ().getDimension (R.dimen.dp_5));
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void startGetGameDetail (Object obj)
    {
        String id   = obj.toString();
        if (!TextUtils.isEmpty(id))
        {
            Server.getServer(ApplicationContext.mAppContext).getGameDetail(id, new HttpHandler() {
                @Override
                public void onSuccess(String result)
                {
                    Message msg     = mMyHandler.obtainMessage(HANDLER_FINISH_GET_GAME);
                    msg.obj         = result;
                    msg.sendToTarget();
                }

                @Override
                public void onFail(String result)
                {
                    Message msg     = mMyHandler.obtainMessage(HANDLER_FINISH_GET_GAME);
                    msg.obj         = result;
                    msg.sendToTarget();
                }
            });
        }

    }
    private void finishGetGameDetail (Object obj)
    {
        String result   = obj.toString();
        if (!TextUtils.isEmpty(result))
        {
            mGameDetailBean  = ParseTools.parseGameDetailBean(result);
            if (mGameDetailBean != null && mGameDetailBean.getData() != null)
            {
                mTitleView.setText (mGameDetailBean.getData().getName ());
                mGameName.setText (mGameDetailBean.getData().getName ());
                mGameTag.setText (mGameDetailBean.getData().getCategory ());
                mGameSize.setText (mGameDetailBean.getData().getSize ());
                GlideTools.setImageWithGlide (GameDetailActivity.this, mGameDetailBean.getData().getIcon (), mGameIcon, false);

                mDetailDes.setText(mGameDetailBean.getData().getDescription());

                mDetailVersion.setText(mGameDetailBean.getData().getVersion());
                mDetailSize.setText(mGameDetailBean.getData().getSize());
                mDetailTime.setText(mGameDetailBean.getData().getOpt_time());

                setImage (mImage1, mGameDetailBean.getData().getShow_pic1 ());
                setImage (mImage2, mGameDetailBean.getData().getShow_pic2 ());
                setImage (mImage3, mGameDetailBean.getData().getShow_pic3 ());
                setImage (mImage4, mGameDetailBean.getData().getShow_pic4 ());
                setImage (mImage5, mGameDetailBean.getData().getShow_pic5 ());

                getCouponList (mGameDetailBean.getData ().getGame_no ());

                if (!TextUtils.isEmpty(mGameDetailBean.getData().getBanner()))
                {
                    mBannerImage.setVisibility(View.VISIBLE);
                    LinearLayout.LayoutParams lp    =  (LinearLayout.LayoutParams)mBannerImage.getLayoutParams();
                    lp.width                        = ApplicationContext.mScreenWidth;
                    lp.height                       = ApplicationContext.mScreenWidth * 1080 / 1920;
                    GlideTools.setImageWithGlide (getApplicationContext (), mGameDetailBean.getData().getBanner(), mBannerImage);
                }
                else
                {
                    mBannerImage.setVisibility(View.INVISIBLE);
                }
            }
            else
            {
                showErr();
            }
        }
        else
        {
            showErr ();
        }
    }
    private void setImage (ImageView view, String url)
    {
        if (!TextUtils.isEmpty (url))
        {
            view.setVisibility (View.VISIBLE);
            GlideTools.setImageWithGlide (getApplicationContext (), url, view, true);
        }
        else
        {
            view.setVisibility (View.GONE);
        }
    }
    private void showErr ()
    {
        Toast.makeText(GameDetailActivity.this, "游戏信息错误", Toast.LENGTH_SHORT).show();
        GameDetailActivity.this.finish();
    }

    private void getCouponList (String game_no)
    {
        Server.getServer (getApplicationContext ()).getListCoupons (MyUserInfoSaveTools.getUserId (), game_no, new HttpHandler () {
            @Override
            public void onSuccess (String result)
            {
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_COUPON_LIST);
                msg.obj         = result;
                msg.sendToTarget ();
            }

            @Override
            public void onFail (String result)
            {
                Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_COUPON_LIST);
                msg.obj         = result;
                msg.sendToTarget ();
            }
        });
    }
    private void finishGetCouponList (String result)
    {
        if (!TextUtils.isEmpty (result))
        {
            CouponBean couponBean   = ParseTools.parseCouponBean (result);
            if (couponBean != null && couponBean.getDatas ().size () > 0)
            {
                mCouponView.setVisibility (View.VISIBLE);
                for (int i = 0; i < couponBean.getDatas ().size (); i ++)
                {
                    addCoupon (couponBean.getDatas ().get (i), i == 0);
                }
            }
        }
    }
    private void addCoupon (CouponBean.Data data, boolean isFirst)
    {
        View view       = GameDetailActivity.this.getLayoutInflater().inflate(R.layout.coupon_list_item, null);
        view.findViewById (R.id.line).setVisibility (isFirst?(View.INVISIBLE):(View.VISIBLE));
        ((TextView)view.findViewById (R.id.coupon_name)).setText (data.getName ());
        int left   = data.getSurplus_count ()*100/data.getTotal_count ();
        ((TextView)view.findViewById (R.id.coupon_left)).setText ("还剩余："+left+"%");
        ((ProgressBar)view.findViewById (R.id.progress)).setProgress (left);
        ((ProgressBar)view.findViewById (R.id.progress)).setMax (100);
        ((TextView)view.findViewById (R.id.coupon_time)).setText ("有效期："+data.getStart_time ()+"~"+data.getEnd_time ());
        (view.findViewById (R.id.button)).setTag (data);
        view.findViewById (R.id.button).setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view)
            {
                CouponBean.Data tagData     = (CouponBean.Data)view.getTag ();
                Message msg     = mMyHandler.obtainMessage (HANDLER_START_APPLY_COUPON);
                msg.obj         = tagData;
                msg.sendToTarget ();
            }
        });
        mCouponList.addView (view, new LinearLayout.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
    }

    private void applyCoupon (CouponBean.Data data)
    {
        if (!MyUserInfoSaveTools.isLogin ())
        {
            Toast.makeText(GameDetailActivity.this, "您还未登陆，请先登录", Toast.LENGTH_SHORT).show();
            return;
        }
        if (data != null)
        {
            showLoading (true);
            Server.getServer (getApplicationContext ()).getApplyCoupons (MyUserInfoSaveTools.getUserId (), data.getStore_game_no (), data.getName (), new HttpHandler () {
                @Override
                public void onSuccess (String result) {
                    Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_APPLY_COUPON);
                    msg.obj         = result;
                    mMyHandler.sendMessageDelayed (msg, 1000);
                }

                @Override
                public void onFail (String result) {
                    Message msg     = mMyHandler.obtainMessage (HANDLER_FINISH_APPLY_COUPON);
                    msg.obj         = result;
                    mMyHandler.sendMessageDelayed (msg, 1000);
                }
            });
        }
    }
    private void finishApplyCoupon (String result)
    {
        showLoading (false);
        if (!TextUtils.isEmpty(result))
        {
            ApplyCouponBean applyCouponBean     = ParseTools.parseApplyCouponBean (result);
            if (applyCouponBean != null && applyCouponBean.getResultCode () == 100 && applyCouponBean.getData () != null)
            {
                showApplyCouponDialog (applyCouponBean.getData ());
            }
            else
            {
                Toast.makeText(GameDetailActivity.this, "请求失败，请重试", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showApplyCouponDialog (ApplyCouponBean.Data data)
    {
        if (mCouponDialog != null && mCouponDialog.isShowing ())
        {
            return;
        }
        View view       = getLayoutInflater().inflate(R.layout.dialog_apply_coupon, null);
        TextView title  = (TextView)view.findViewById (R.id.title);
        title.setText (data.getName ());
        TextView couponCode  = (TextView)view.findViewById (R.id.coupon_code);
        couponCode.setText (data.getCoupon_code ());

        view.findViewById (R.id.cancel).setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View view)
            {
                mCouponDialog.dismiss ();
            }
        });
        view.findViewById (R.id.copy).setTag (data.getCoupon_code ());
        view.findViewById (R.id.copy).setOnClickListener (new View.OnClickListener ()
        {
            @Override
            public void onClick (View view)
            {
                String code     = (String)view.getTag ();
                if (!TextUtils.isEmpty (code))
                {
                    ClipboardManager cm = (ClipboardManager) ApplicationContext.mAppContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData myClip     = ClipData.newPlainText("text", code);
                    cm.setPrimaryClip(myClip);

                    Toast.makeText(ApplicationContext.mAppContext, "兑换码已经复制到剪切板", Toast.LENGTH_SHORT).show();

                    mCouponDialog.dismiss ();
                }

            }
        });

        mCouponDialog  = new MyDialog (GameDetailActivity.this, view);
        mCouponDialog.show();
    }

    private void getDownUrl ()
    {
        showLoading (true);
        Server.getServer(ApplicationContext.mAppContext).getDownUrl(MyUserInfoSaveTools.getUserId(), mGameId, new HttpHandler() {
            @Override
            public void onSuccess(String result)
            {
                Message msg     = mMyHandler.obtainMessage(HANDLER_FINISH_DOWNURL);
                msg.obj         = result;
                mMyHandler.sendMessageDelayed(msg, 3000);
            }

            @Override
            public void onFail(String result)
            {
                Message msg     = mMyHandler.obtainMessage(HANDLER_FINISH_DOWNURL);
                msg.obj         = result;
                mMyHandler.sendMessageDelayed(msg, 3000);
            }
        });
    }
    private void finishGetDownUrl (Object obj)
    {
        showLoading (false);
        String result  = obj.toString();
        if (!TextUtils.isEmpty(result))
        {
            DownUrlBean downUrlBean     = ParseTools.parseDownUrlBean(result, mGameId);
            if (downUrlBean != null)
            {
                if (downUrlBean.getResultCode() == 100)
                {
                    String url  = downUrlBean.getData().getDownload_url();
                    String id   = downUrlBean.getData().getId();
                    if (TextUtils.isEmpty(url))
                    {
                        Toast.makeText(GameDetailActivity.this, "暂无下载", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {//开启下载
                        MyDownTools.getInstance ().downloadApk(url, id+".apk", mGameDetailBean.getData().getName());
                        Toast.makeText(GameDetailActivity.this, "下载任务已添加到任务栏", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(GameDetailActivity.this, downUrlBean.getResultDesc(), Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(GameDetailActivity.this, "暂无下载", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(GameDetailActivity.this, "请求下载失败，请重试", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLoading (boolean isShow)
    {
        if (mProgressDialog == null)
        {
            mProgressDialog     = new ProgressDialog(GameDetailActivity.this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条
            mProgressDialog.setCancelable(false);// 设置是否可以通过点击Back键取消
            mProgressDialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条
            mProgressDialog.setMessage("加载中，请稍等...");
        }
        if (isShow)
        {
            mProgressDialog.show();
        }
        else
        {
            mProgressDialog.dismiss();
        }
    }

    private final static int HANDLER_START_GET_GAME         = 1;
    private final static int HANDLER_FINISH_GET_GAME        = 2;
    private final static int HANDLER_REQUEST_DOWNURL        = 3;
    private final static int HANDLER_FINISH_DOWNURL         = 4;
    private final static int HANDLER_FINISH_COUPON_LIST     = 5;
    private final static int HANDLER_START_APPLY_COUPON     = 6;
    private final static int HANDLER_FINISH_APPLY_COUPON    = 7;
    private static class MyHandler extends Handler
    {

        private final WeakReference<GameDetailActivity> mGameDetailActivitys;

        private MyHandler (GameDetailActivity mGameDetailActivity)
        {
            this.mGameDetailActivitys   = new WeakReference<GameDetailActivity> (mGameDetailActivity);
        }

        @Override
        public void handleMessage (Message msg)
        {
            super.handleMessage (msg);
            GameDetailActivity mGameDetailActivity    = mGameDetailActivitys.get ();
            if (mGameDetailActivity != null)
            {
                switch (msg.what)
                {
                    case HANDLER_START_GET_GAME:
                    {
                        mGameDetailActivity.startGetGameDetail(msg.obj);
                    }break;
                    case HANDLER_FINISH_GET_GAME:
                    {
                        mGameDetailActivity.finishGetGameDetail(msg.obj);
                    }break;
                    case HANDLER_REQUEST_DOWNURL:
                    {
                        mGameDetailActivity.getDownUrl();
                    }break;
                    case HANDLER_FINISH_DOWNURL:
                    {
                        mGameDetailActivity.finishGetDownUrl(msg.obj);
                    }break;
                    case HANDLER_FINISH_COUPON_LIST:
                    {
                        mGameDetailActivity.finishGetCouponList ((String)msg.obj);
                    }break;
                    case HANDLER_START_APPLY_COUPON:
                    {
                        mGameDetailActivity.applyCoupon ((CouponBean.Data)msg.obj);
                    }break;
                    case HANDLER_FINISH_APPLY_COUPON:
                    {
                        mGameDetailActivity.finishApplyCoupon ((String)msg.obj);
                    }break;
                }
            }
        }
    }
}
