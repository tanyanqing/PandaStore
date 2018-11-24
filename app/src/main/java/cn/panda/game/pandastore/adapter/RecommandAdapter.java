package cn.panda.game.pandastore.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
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
import cn.panda.game.pandastore.tool.GlideTools;
import cn.panda.game.pandastore.untils.ApplicationContext;

public class RecommandAdapter extends MGSVBaseRecyclerViewAdapter<GameListBean.Game>
{
    public RecommandAdapter(Context context, List<GameListBean.Game> dataList) {
        super(context, dataList);
    }

    @Override
    public void bindData(MGSVBaseRecyclerViewHolder holder, GameListBean.Game data)
    {
        if(data != null && holder != null)
        {
            GameListAdapterHolder mGameListAdapterHolder    = (GameListAdapterHolder)holder;
            mGameListAdapterHolder.setData (data);
        }
    }

    @Override
    public MGSVBaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView   = null;
        if (viewType == 110)
        {//空的view，占位
            itemView = layoutInflater.inflate(R.layout.adapter_game_list_recommand_first_item_null, parent, false);
        }
        if (itemView == null)
        {
            itemView = layoutInflater.inflate(R.layout.adapter_game_list_recommand_first_item, parent, false);
        }
        return new GameListAdapterHolder(itemView, viewType);
    }

    @Override
    public int getItemViewType(int position)
    {
        if (dataList != null)
        {
            String name     = dataList.get(position).getName();
            if (!TextUtils.isEmpty(name) && name.equalsIgnoreCase("first"))
            {
                return 110;
            }
            else
            {
                return 1;
            }
        }
        return super.getItemViewType(position);
    }

    public class GameListAdapterHolder extends MGSVBaseRecyclerViewHolder
    {
        private ImageView mGameIcon;
        private TextView mNameView;
        public GameListAdapterHolder(View itemView, int viewType) {
            super(itemView);
            initView (viewType);
        }

        private void initView (int viewType)
        {
            mGameIcon   = (ImageView)itemView.findViewById (R.id.game_icon);
            mNameView   = (TextView)itemView.findViewById (R.id.name);
        }
        private void setData (GameListBean.Game game)
        {
            if (mGameIcon != null && mNameView != null)
            {
                mNameView.setText(game.getName());
                GlideTools.setImageWithGlide (ApplicationContext.mAppContext, game.getIcon (), mGameIcon);
                itemView.setTag (game);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent   = new Intent (mContext, GameDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        GameListBean.Game data  = (GameListBean.Game)view.getTag ();
                        if (data != null)
                        {
                            intent.putExtra ("data", data.getJsonStr ());
                        }

                        mContext.startActivity (intent);
                    }
                });
            }

        }
    }
}
