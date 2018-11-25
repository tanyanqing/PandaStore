package cn.panda.game.pandastore.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
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
import cn.panda.game.pandastore.bean.GameDetailBean;
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
        else if (viewType == Type.RECOMMAND_1)
        {
            itemView = layoutInflater.inflate(R.layout.adapter_game_list_recommand_first, parent, false);
        }
        else if (viewType == Type.RECOMMAND_2)
        {
            itemView = layoutInflater.inflate(R.layout.adapter_game_list_recommand_second, parent, false);
        }
        else if (viewType == Type.TITLE)
        {
            itemView = layoutInflater.inflate(R.layout.adapter_game_list_title, parent, false);
        }
        else if (viewType == Type.COMMON_1)
        {
            itemView = layoutInflater.inflate(R.layout.adapter_game_list_common_first, parent, false);
        }
        else if (viewType == Type.COMMON_2)
        {
            itemView = layoutInflater.inflate(R.layout.adapter_game_list_common_second, parent, false);
        }
        if (itemView == null)
        {
            itemView = layoutInflater.inflate(R.layout.adapter_game_list_common_first, parent, false);
        }
        return new GameListAdapterHolder(itemView, viewType);
    }


    @Override
    public int getItemViewType (int position)
    {
        if (dataList != null)
        {
            String type     = dataList.get (position).getShowType();
            return Type.getType(type);
        }
        return super.getItemViewType (position);
    }


    public class GameListAdapterHolder extends MGSVBaseRecyclerViewHolder
    {
        //title
        private TextView mTitle;
        private TextView mMore;
        //common
        private ImageView mGameBanner;
        private ImageView mGameIcon;
        private TextView mNameView;
        private TextView mDescView;
        private TextView mSizeView;
        //recommand
        private RecyclerView mRecommandContainer;
        private RecommandAdapter mAdapter;
        private List<GameListBean.Game> dataList;
        private TextView mLeftTitle;
        private int distanceX;
        float total   = ApplicationContext.mAppContext.getResources().getDimension(R.dimen.dp_120);


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
            mGameBanner     = (ImageView)itemView.findViewById(R.id.banner_image);
            mGameIcon       = (ImageView)itemView.findViewById (R.id.game_icon);
            mNameView       = (TextView)itemView.findViewById (R.id.name);
            mDescView       = (TextView)itemView.findViewById (R.id.desc);
            mSizeView       = (TextView)itemView.findViewById (R.id.size);

            //recommand
            mRecommandContainer     = (RecyclerView)itemView.findViewById(R.id.recommand_continer);
            if (mRecommandContainer != null)
            {
                InitRecyclerViewLayout.initLinearLayoutHorizontal(mContext, mRecommandContainer);
                dataList    = new ArrayList<>();
                mAdapter    = new RecommandAdapter (mContext, dataList);
                mRecommandContainer.setAdapter (mAdapter);

                distanceX   = 0;

                mRecommandContainer.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        distanceX   += dx;
                        if (distanceX > 0 && mLeftTitle != null)
                        {
                            float alpha     = ((float)(total-distanceX)/total);
                            if (alpha < 0)
                            {
                                alpha   = 0;
                            }
                            mLeftTitle.setAlpha(alpha);
                        }
                        else if (mLeftTitle != null)
                        {
                            mLeftTitle.setAlpha(1);
                        }
                    }
                });
            }
            mLeftTitle  = (TextView)itemView.findViewById(R.id.left_title);

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
            int type     = Type.getType(page.getShowType());
            if (type == Type.TITLE)
            {
                mTitle.setText(page.getTitle());
                if (!TextUtils.isEmpty(page.getMore()) && !page.getMore().equalsIgnoreCase("null"))
                {
                    mMore.setTag(page.getMore());
                    mMore.setVisibility(View.VISIBLE);
                    mMore.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(mContext, "more", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    mMore.setVisibility(View.INVISIBLE);
                }

            }
            else if (type == Type.RECOMMAND_1 || type == Type.RECOMMAND_2)
            {
                if (page.getGameslist() != null)
                {
                    dataList.clear();
                    if (type == Type.RECOMMAND_2)
                    {
                        GameListBean.Game game  = new GameListBean.Game();
                        game.setName("first");
                        dataList.add(game);
                    }
                    int size    = page.getGameslist().size();
                    for (int i = 0; i < size; i ++)
                    {
                        dataList.add(page.getGameslist().get(i));
                    }
                    mAdapter.notifyDataSetChanged();
                }
                if (type == Type.RECOMMAND_2)
                {
                    mLeftTitle.setText(page.getTitle());
                    itemView.setBackgroundResource(R.drawable.profit_coupon_03);
                }
            }
            else if (type == Type.BANNER)
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
            else if (type == Type.COMMON_1 || type == Type.COMMON_2)
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
                    if (type == Type.COMMON_2)
                    {
                        GlideTools.setImageWithGlide (ApplicationContext.mAppContext, game.getBanner (), mGameBanner);
                    }

                }
            }
        }


    }
}
