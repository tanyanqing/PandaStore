package cn.panda.game.pandastore.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.panda.game.pandastore.GameDetailActivity;
import cn.panda.game.pandastore.R;
import cn.panda.game.pandastore.base.MGSVBaseRecyclerViewAdapter;
import cn.panda.game.pandastore.base.MGSVBaseRecyclerViewHolder;
import cn.panda.game.pandastore.bean.OwnCouponBean;
import cn.panda.game.pandastore.tool.MyDownTools;
import cn.panda.game.pandastore.untils.ApplicationContext;

public class OwnerCouponListAdapter extends MGSVBaseRecyclerViewAdapter<OwnCouponBean.Data>
{
    public OwnerCouponListAdapter (Context context, List<OwnCouponBean.Data> dataList)
    {
        super (context, dataList);
    }

    @Override
    public void bindData (MGSVBaseRecyclerViewHolder holder, OwnCouponBean.Data data)
    {
        if(data != null && holder != null)
        {
            MyHolder mMyHolder    = (MyHolder)holder;
            mMyHolder.setData (data);
        }
    }

    @Override
    public MGSVBaseRecyclerViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        View itemView   = layoutInflater.inflate(R.layout.adapter_coupon, parent, false);
        return new MyHolder (itemView);
    }

    public class MyHolder extends MGSVBaseRecyclerViewHolder
    {

        private TextView mCouponName;
        private TextView mCouponGame;
        private TextView mCouponTime;
        private TextView mCouponCode;
        private Button mDown;
        private Button mCopy;
        public MyHolder (View itemView)
        {
            super (itemView);
            init ();
        }
        private void init ()
        {
            mCouponName     = (TextView)itemView.findViewById (R.id.coupon_name);
            mCouponGame     = (TextView)itemView.findViewById (R.id.coupon_game);
            mCouponTime     = (TextView)itemView.findViewById (R.id.coupon_time);
            mCouponCode     = (TextView)itemView.findViewById (R.id.coupon_code);
            mDown           = (Button)itemView.findViewById (R.id.down);
            mDown.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View view) {
                    OwnCouponBean.Data data = (OwnCouponBean.Data)view.getTag ();
                    if (data != null && !TextUtils.isEmpty (data.getDownload_url ()))
                    {
                        MyDownTools.downloadApk(ApplicationContext.mAppContext, data.getDownload_url (), data.getApp_name ()+".apk", data.getApp_name());
                        Toast.makeText(ApplicationContext.mAppContext, "下载任务已添加到任务栏", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            mCopy           = (Button)itemView.findViewById (R.id.copy);
            mCopy.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View view)
                {
                    OwnCouponBean.Data data = (OwnCouponBean.Data)view.getTag ();
                    {
                        ClipboardManager cm = (ClipboardManager) ApplicationContext.mAppContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData myClip     = ClipData.newPlainText("text", data.getCoupon_code ());
                        cm.setPrimaryClip(myClip);
                        Toast.makeText(ApplicationContext.mAppContext, "兑换码已经复制到剪切板", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        public void setData (OwnCouponBean.Data data)
        {
            mCouponName.setText (data.getName ());
            mCouponGame.setText (data.getApp_name ());
            mCouponCode.setText ("卡券码: "+data.getCoupon_code ());
            mCouponTime.setText ("有效期至："+data.getEnd_time ());
            mDown.setTag (data);
            mCopy.setTag (data);
            if (data != null || TextUtils.isEmpty (data.getDownload_url ()))
            {
                mDown.setVisibility (View.GONE);
            }
        }
    }
}
