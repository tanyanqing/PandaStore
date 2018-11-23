package cn.panda.game.pandastore.tool;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class InitRecyclerViewLayout {
    public static void initLinearLayoutVERTICAL(Context context, RecyclerView recyclerView){
        LinearLayoutManager layoutManager = new LinearLayoutManager (context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator ());
    }

    public static void initLinearLayoutWithoutDivide(Context context,RecyclerView recyclerView){
        LinearLayoutManager layoutManager = new LinearLayoutManager (context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator ());
    }

    public static LinearLayoutManager initLinearLayoutHorizontal(Context context, RecyclerView recyclerView){
        LinearLayoutManager layoutManager = new LinearLayoutManager (context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator ());
        return layoutManager;
    }

    public static void initGridLayoutManager(Context context, RecyclerView recyclerView, int counts){
        GridLayoutManager sgm = new GridLayoutManager (context,counts){

            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(sgm);
        recyclerView.setItemAnimator(new DefaultItemAnimator ());
    }
    public static void initGridLayoutManagerScroll(Context context, RecyclerView recyclerView, int counts){
        GridLayoutManager sgm = new GridLayoutManager (context,counts);
        recyclerView.setLayoutManager(sgm);
        recyclerView.setItemAnimator(new DefaultItemAnimator ());
    }


}
