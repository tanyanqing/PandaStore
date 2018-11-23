package cn.panda.game.pandastore.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.donkingliang.banner.CustomBanner;

import java.util.ArrayList;
import java.util.List;

import cn.panda.game.pandastore.GameDetailActivity;
import cn.panda.game.pandastore.R;
import cn.panda.game.pandastore.base.MGSVBaseRecyclerViewAdapter;
import cn.panda.game.pandastore.base.MGSVBaseRecyclerViewHolder;
import cn.panda.game.pandastore.bean.GameListBean;
import cn.panda.game.pandastore.tool.GlideTools;
import cn.panda.game.pandastore.tool.InitRecyclerViewLayout;
import cn.panda.game.pandastore.untils.ApplicationContext;
import cn.panda.game.pandastore.untils.Type;

public class GameListAdapter extends MGSVBaseRecyclerViewAdapter<GameListBean.Page>
{

    public GameListAdapter (Context context, List<GameListBean.Page> dataList) {
        super (context, dataList);
    }

    @Override
    public void bindData (MGSVBaseRecyclerViewHolder holder, GameListBean.Page page)
    {
        if(page != null && holder != null)
        {
            GameListAdapterHolder mGameListAdapterHolder    = (GameListAdapterHolder)holder;
            mGameListAdapterHolder.setData (page);
        }
    }

    @Override
    public MGSVBaseRecyclerViewHolder onCreateViewHolder (ViewGroup parent, int viewType)
    {
        View itemView = null;
        if (viewType == Type.BANNER)
        {
            itemView = layoutInflater.inflate(R.layout.adapter_game_list_banner, parent, false);
        }
        else if (viewType == Type.RECOMMAND)
        {
            itemView = layoutInflater.inflate(R.layout.adapter_game_list_recommand, parent, false);
        }
        else if (viewType == Type.TITLE)
        {
            itemView = layoutInflater.inflate(R.layout.adapter_game_list_title, parent, false);
        }
        else if (viewType == Type.COMMON)
        {
            itemView = layoutInflater.inflate(R.layout.adapter_game_list_common, parent, false);
        }
        if (itemView == null)
        {
            itemView = layoutInflater.inflate(R.layout.adapter_game_list_common, parent, false);
        }
        return new GameListAdapterHolder(itemView, viewType);
    }


    @Override
    public int getItemViewType (int position)
    {
        if (dataList != null)
        {
            String type     = dataList.get (position).getShowType();
            if (type.equalsIgnoreCase("title"))
            {
                return Type.TITLE;
            }
            else if (type.equalsIgnoreCase("recommand"))
            {
                return Type.RECOMMAND;
            }
            else if (type.equalsIgnoreCase("banner"))
            {
                return Type.BANNER;
            }
            else if (type.equalsIgnoreCase("common"))
            {
                return Type.COMMON;
            }
        }
        return super.getItemViewType (position);
    }


    public class GameListAdapterHolder extends MGSVBaseRecyclerViewHolder
    {
        //title
        private TextView mTitle;
        private TextView mMore;
        //common
        private ImageView mGameIcon;
        private TextView mNameView;
        private TextView mDescView;
        private TextView mSizeView;
        //recommand
        private RecyclerView mRecommandContainer;
        private RecommandAdapter mAdapter;
        private List<GameListBean.Game> dataList;

        //banner
        private CustomBanner mCustomBanner;
        public GameListAdapterHolder (View itemView, int viewType)
        {
            super (itemView);
            initView (viewType);
        }

        private void initView (int viewType)
        {
            //title
            mTitle  = (TextView) itemView.findViewById(R.id.title);
            mMore   = (TextView) itemView.findViewById(R.id.more);

            //common
            mGameIcon   = (ImageView)itemView.findViewById (R.id.game_icon);
            mNameView   = (TextView)itemView.findViewById (R.id.name);
            mDescView   = (TextView)itemView.findViewById (R.id.desc);
            mSizeView   = (TextView)itemView.findViewById (R.id.size);

            //recommand
            mRecommandContainer     = (RecyclerView)itemView.findViewById(R.id.recommand_continer);
            if (mRecommandContainer != null)
            {
                InitRecyclerViewLayout.initLinearLayoutHorizontal(mContext, mRecommandContainer);
                dataList    = new ArrayList<>();
                mAdapter    = new RecommandAdapter (mContext, dataList);
                mRecommandContainer.setAdapter (mAdapter);
            }

            //banner
            mCustomBanner   = (CustomBanner)itemView.findViewById(R.id.banner);
            if (mCustomBanner != null)
            {
                mCustomBanner.setOnPageClickListener(new CustomBanner.OnPageClickListener<GameListBean.Game>() {
                    @Override
                    public void onPageClick(int i, GameListBean.Game game)
                    {
                        Intent intent   = new Intent (mContext, GameDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra ("data", game.getJsonStr());
                        mContext.startActivity (intent);
                    }
                });
                mCustomBanner.startTurning(10000);
                mCustomBanner.setScrollDuration(500);
            }

        }
        private void setData (GameListBean.Page page)
        {
            String type     = page.getShowType();
            if (type.equalsIgnoreCase("title"))
            {
                mTitle.setText(page.getTitle());
                mMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext, "more", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            else if (type.equalsIgnoreCase("recommand"))
            {
                if (page.getGameslist() != null)
                {
                    dataList.clear();
                    int size    = page.getGameslist().size();
                    for (int i = 0; i < size; i ++)
                    {
                        dataList.add(page.getGameslist().get(i));
                    }
                    mAdapter.notifyDataSetChanged();
                }
            }
            else if (type.equalsIgnoreCase("banner"))
            {
                mCustomBanner.setPages(new CustomBanner.ViewCreator<GameListBean.Game>() {
                    @Override
                    public View createView(Context context, int i) {
                        ImageView imageView = new ImageView(context);
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        return imageView;
                    }

                    @Override
                    public void updateUI(Context context, View view, int i, GameListBean.Game game)
                    {
//                        Glide.with(context).load(entity).into((ImageView) view);
                        GlideTools.setImageWithGlide(mContext, game.getBanner(), (ImageView)view);
                    }

                }, page.getGameslist());
            }
            else if (type.equalsIgnoreCase("common"))
            {
                if (page.getGameslist() != null && page.getGameslist().size() > 0)
                {
                    GameListBean.Game game  = page.getGameslist().get(0);
                    mNameView.setText (game.getName ());
                    mDescView.setText (game.getCategory ());
                    mSizeView.setText (game.getSize ());
                    itemView.setTag (game);
                    itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent   = new Intent (mContext, GameDetailActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            GameListBean.Game data  = (GameListBean.Game)view.getTag ();
                            if (data != null)
                            {
                                intent.putExtra ("data",data.getJsonStr ());
                            }

                            mContext.startActivity (intent);
                        }
                    });
                    GlideTools.setImageWithGlide (ApplicationContext.mAppContext, game.getIcon (), mGameIcon);

                }

            }
        }


    }
}
