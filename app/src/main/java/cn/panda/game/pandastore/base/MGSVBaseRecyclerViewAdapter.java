package cn.panda.game.pandastore.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public abstract class MGSVBaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<MGSVBaseRecyclerViewHolder> {

    private static String TAG = "MGSVBaseRecyclerViewAdapter";
    protected LayoutInflater layoutInflater;

    protected List<T> dataList;

    protected int layoutId;
    protected Context mContext;
    protected int mVideoNumber;
    protected static int VIDEO_NUMBER = 5;

    public MGSVBaseRecyclerViewAdapter(Context context, List<T> dataList, int layoutId) {
        mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
        this.layoutId = layoutId;
    }

    public MGSVBaseRecyclerViewAdapter(Context context, List<T> dataList) {
        mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    public MGSVBaseRecyclerViewAdapter(Context context,int layoutId) {
        mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
    }
    public MGSVBaseRecyclerViewAdapter(Context context) {
        mContext = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        mVideoNumber = position;
        return super.getItemViewType(position);
    }

    @Override
    public MGSVBaseRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(layoutId, parent, false);
        return new MGSVBaseRecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MGSVBaseRecyclerViewHolder holder, int position) {
        if(null != dataList) {
            bindData(holder, dataList.get(position));
        }else {
            bindData(holder, null);
        }
    }

    @Override
    public int getItemCount() {
        if(null != dataList) {
            return dataList.size();
        }
        return VIDEO_NUMBER;
    }

    public boolean updateItem(int position) {
        if (position < 0 || position >= dataList.size()) {
            return false;
        }
        notifyItemChanged(position);
        return true;
    }

    public List<T> getAdapterData(){
        return dataList;
    }

    public abstract void bindData(MGSVBaseRecyclerViewHolder holder, T data);
//    public abstract void bindData(MGSVBaseRecyclerViewHolder holder, List<T> data,int position);

}
