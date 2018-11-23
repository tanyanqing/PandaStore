package cn.panda.game.pandastore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.panda.game.pandastore.R;
import cn.panda.game.pandastore.base.MGSVBaseRecyclerViewAdapter;
import cn.panda.game.pandastore.base.MGSVBaseRecyclerViewHolder;
import cn.panda.game.pandastore.bean.GameListBean;
import cn.panda.game.pandastore.tool.GlideTools;
import cn.panda.game.pandastore.untils.ApplicationContext;

public class GameListAdapter extends MGSVBaseRecyclerViewAdapter<GameListBean.Game>
{

    public GameListAdapter (Context context, List<GameListBean.Game> dataList) {
        super (context, dataList);
    }

    @Override
    public void bindData (MGSVBaseRecyclerViewHolder holder, GameListBean.Game data)
    {
        if(data != null && holder != null)
        {
            GameListAdapterHolder mGameListAdapterHolder    = (GameListAdapterHolder)holder;
            mGameListAdapterHolder.mNameView.setText (data.getName ());
            mGameListAdapterHolder.mDescView.setText (data.getDescription ());
            mGameListAdapterHolder.mSizeView.setText (data.getSize ());

            GlideTools.setImageWithGlide (ApplicationContext.mAppContext, data.getIcon (), mGameListAdapterHolder.mGameIcon);
        }
    }

    @Override
    public MGSVBaseRecyclerViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        View itemView = layoutInflater.inflate(R.layout.adapter_game_list_one, parent, false);
        return new GameListAdapterHolder(itemView, viewType);
    }

    @Override
    public int getItemViewType (int position)
    {
        if (dataList != null)
        {
//            if (dataList.get (position).)
        }
        return super.getItemViewType (position);
    }

    public class GameListAdapterHolder extends MGSVBaseRecyclerViewHolder
    {
        private ImageView mGameIcon;
        private TextView mNameView;
        private TextView mDescView;
        private TextView mSizeView;
        public GameListAdapterHolder (View itemView, int viewType)
        {
            super (itemView);
            initView (viewType);
        }

        private void initView (int viewType)
        {
            mGameIcon   = (ImageView)itemView.findViewById (R.id.game_icon);
            mNameView   = (TextView)itemView.findViewById (R.id.name);
            mDescView   = (TextView)itemView.findViewById (R.id.desc);
            mSizeView   = (TextView)itemView.findViewById (R.id.size);
        }
    }
}
