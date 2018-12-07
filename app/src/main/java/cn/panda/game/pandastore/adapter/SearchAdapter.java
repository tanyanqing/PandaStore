package cn.panda.game.pandastore.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.panda.game.pandastore.GameDetailActivity;
import cn.panda.game.pandastore.R;
import cn.panda.game.pandastore.base.MGSVBaseRecyclerViewAdapter;
import cn.panda.game.pandastore.base.MGSVBaseRecyclerViewHolder;
import cn.panda.game.pandastore.bean.GameListBean;
import cn.panda.game.pandastore.bean.SearchBean;
import cn.panda.game.pandastore.tool.GlideTools;
import cn.panda.game.pandastore.untils.ApplicationContext;

public class SearchAdapter extends MGSVBaseRecyclerViewAdapter<SearchBean.Data>
{
    private View.OnClickListener mOnClickListener;
    public SearchAdapter (Context context, List<SearchBean.Data> dataList, View.OnClickListener clickListener)
    {
        super (context, dataList);
        this.mOnClickListener   = clickListener;
    }

    @Override
    public void bindData (MGSVBaseRecyclerViewHolder holder, SearchBean.Data data)
    {
        if (data != null && holder != null)
        {
            SearchListAdapterHolder myHolder    = (SearchListAdapterHolder)holder;
            myHolder.setData (data, this.mOnClickListener);
        }
    }

    @Override
    public MGSVBaseRecyclerViewHolder onCreateViewHolder (ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = layoutInflater.inflate(R.layout.adapter_game_list_common_first, parent, false);
        return new SearchListAdapterHolder (itemView);
    }

    public class SearchListAdapterHolder extends MGSVBaseRecyclerViewHolder
    {
        private ImageView mGameIcon;
        private TextView mNameView;
        private TextView mDescView;
        private TextView mSizeView;
        private View mDown;
        public SearchListAdapterHolder (View itemView)
        {
            super (itemView);
            initView ();
        }
        private void initView ()
        {
            mGameIcon       = (ImageView)itemView.findViewById (R.id.game_icon);
            mNameView       = (TextView)itemView.findViewById (R.id.name);
            mDescView       = (TextView)itemView.findViewById (R.id.desc);
            mSizeView       = (TextView)itemView.findViewById (R.id.size);
            mDown           = itemView.findViewById (R.id.down);
        }
        private void setData (SearchBean.Data data, View.OnClickListener clickListener)
        {
            mNameView.setText (data.getName ());
            mDescView.setText (data.getCategory ());
            mSizeView.setText (data.getSize ());
            GlideTools.setImageWithGlide (ApplicationContext.mAppContext, data.getIcon (), mGameIcon, false);


            itemView.setTag (data);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent   = new Intent (mContext, GameDetailActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    SearchBean.Data data  = (SearchBean.Data)view.getTag ();
                    if (data != null)
                    {
                        intent.putExtra ("data",data.getJsonStr ());
                    }
                    mContext.startActivity (intent);
                }
            });

            mDown.setTag (data);
            mDown.setOnClickListener (clickListener);
        }
    }
}
