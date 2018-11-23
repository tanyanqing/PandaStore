package cn.panda.game.pandastore.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MGSVBaseRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    // SparseArray 比 HashMap 更省内存，在某些条件下性能更好，只能存储 key 为 int 类型的数据，
    // 用来存放 View 以减少 findViewById 的次数
    private SparseArray<View> mViewSparseArray;

    private onItemCommonClickListener mCommonClickListener;

    public MGSVBaseRecyclerViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
        mViewSparseArray = new SparseArray<>();
    }

    /**
     * 根据 ID 来获取 View
     *
     * @param viewId viewID
     * @param <T>    泛型
     * @return 将结果强转为 View 或 View 的子类型
     */
    public <T extends View> T getView(int viewId) {
        // 先从缓存中找，找打的话则直接返回
        // 如果找不到则 findViewById ，再把结果存入缓存中
        View view = mViewSparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            mViewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public MGSVBaseRecyclerViewHolder setText(int viewId, CharSequence text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public MGSVBaseRecyclerViewHolder setTextSize(int viewId, float textSize) {
        TextView tv = getView(viewId);
        tv.setTextSize(textSize);
        return this;
    }
    public MGSVBaseRecyclerViewHolder setTextGravity(int viewId, int gravity) {
        TextView tv = getView(viewId);
        tv.setGravity(gravity);
        return this;
    }

    public MGSVBaseRecyclerViewHolder setViewVisibility(int viewId, int visibility) {
        getView(viewId).setVisibility(visibility);
        return this;
    }

    public MGSVBaseRecyclerViewHolder setImageResource(int viewId, int resourceId) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    public MGSVBaseRecyclerViewHolder setImageResource(Context context, int viewId, String url) {
//        ImageView imageView = getView(viewId);
//        GlideTools.setImageWithGlide (context, url, R.drawable.ic_tip, imageView);
        return this;
    }

    public interface onItemCommonClickListener {

        void onItemClickListener (View view, int position);

        void onItemLongClickListener (View view, int position);

    }

    public void setRecycleViewItemClickListener(onItemCommonClickListener mCommonClickListener) {
        this.mCommonClickListener = mCommonClickListener;
    }

    @Override
    public void onClick(View v) {
        if (mCommonClickListener != null) {
            mCommonClickListener.onItemClickListener(v,getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (mCommonClickListener != null) {
            mCommonClickListener.onItemLongClickListener(v,getAdapterPosition());
        }
        return false;
    }


}
