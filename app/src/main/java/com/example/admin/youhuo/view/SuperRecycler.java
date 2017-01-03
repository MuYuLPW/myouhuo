package com.example.admin.youhuo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.admin.youhuo.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/12/27.
 */

public class SuperRecycler extends RecyclerView {

    private GestureDetector detector;
    private OnItemClickListener listener;
    private List<View> heads=new ArrayList<>();
    private List<View> footers=new ArrayList<>();
    public static final int HEADTYPE=-1000;
    public static final int FOOTERTYPE=1000;

    public SuperRecycler(Context context) {
        super(context);
        init();
    }

    public SuperRecycler(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SuperRecycler(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        detector = new GestureDetector(getContext(),new MyDetector());
        setLayoutManager(new LinearLayoutManager(getContext()) );
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        detector.onTouchEvent(e);
        return super.onTouchEvent(e);
    }

    class MyDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            int position=pointToPosition(e.getX(),e.getY());
            View view = pointToChild(e.getX(), e.getY());
            if (listener!=null && view !=null){
                listener.onItemClick(SuperRecycler.this,view,position);
            }
            return true;
        }
    }
    public interface OnItemClickListener{
        void onItemClick(RecyclerView recyclerView,View view,int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.listener=onItemClickListener;
    }

    private int pointToPosition(float x, float y) {
        View view=pointToChild(x,y);
        if (view==null) return -1;
        ViewHolder containingViewHolder = findContainingViewHolder(view);
        return containingViewHolder.getAdapterPosition();
    }

    private View pointToChild(float x, float y) {
        return findChildViewUnder(x,y);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        RecyclerView.Adapter a=adapter;
        if (a!=null ){
            if (heads.size()>0|| footers.size()>0){
                a=new HeadFooterAdapter(adapter);
            }
        }
        super.setAdapter(a);
    }
    class HeadFooterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        public RecyclerView.Adapter rawAdapter;

        public HeadFooterAdapter(Adapter rawAdapter) {
            this.rawAdapter = rawAdapter;
        }
        public RecyclerView.Adapter getRawAdapter(){
            return rawAdapter;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType<=HEADTYPE){
                LogUtils.log("tag","---------"+(HEADTYPE-viewType));
                return new ViewHolder(heads.get(HEADTYPE-viewType)) {};
            }else if (viewType>=FOOTERTYPE){
                return new ViewHolder(footers.get(viewType-FOOTERTYPE)) {};
            }
            return rawAdapter.onCreateViewHolder(parent,viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(position>=heads.size()&&position<heads.size()+rawAdapter.getItemCount()){
                rawAdapter.onBindViewHolder(holder,position-heads.size());
            }
        }

        @Override
        public int getItemCount() {
            return rawAdapter.getItemCount()+heads.size()+footers.size();
        }

        @Override
        public int getItemViewType(int position) {
            if (position<heads.size()){
                //头
                return HEADTYPE-position;
            }else if (position<rawAdapter.getItemCount()+heads.size()){
                //正常
                return rawAdapter.getItemViewType(position-heads.size());
            }
                return FOOTERTYPE+position-rawAdapter.getItemCount()-heads.size();
        }
    }
    public void addHead(View v){
        check();
        heads.add(v);

    }
    public void addFooter(View v){
        check();
        footers.add(v);
    }
    public void removeHead(View v){
        heads.remove(v);
        getAdapter().notifyDataSetChanged();
    }
    public void clearHead(View v){
        heads.clear();
        getAdapter().notifyDataSetChanged();
    }
    public void removeFooter(View v){
        footers.remove(v);
        getAdapter().notifyDataSetChanged();
    }
    public void clearFooter(View v){
        footers.clear();
        getAdapter().notifyDataSetChanged();
    }

    private void check() {
        Adapter adapter = getAdapter();
        if (!(adapter==null|| adapter.getClass() == HeadFooterAdapter.class)){
            throw new RuntimeException("addHead必须在setAdapter之前");
        }
    }
    public int getHeadsCount(){
        return heads.size();
    }
    public int getFooterCount(){
        return footers.size();
    }
    public int getLastVisiavleChildPosition(){
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager){
            return ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }else if (layoutManager instanceof GridLayoutManager){
            return ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        }else {
            throw new RuntimeException("不支持瀑布流");
        }

    }
    public int getFirstVisiavleChildPosition(){
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager){
            return ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }else if (layoutManager instanceof GridLayoutManager){
            return ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
        }else {
            throw new RuntimeException("不支持瀑布流");
        }

    }

    @Override
    public void setLayoutManager(final LayoutManager layout) {

        post(new Runnable() {
            @Override
            public void run() {
                if (layout instanceof GridLayoutManager&&(getHeadsCount()>0||getFooterCount()>0)){
                    final HeadFooterAdapter adapter = (HeadFooterAdapter) getAdapter();
                    final GridLayoutManager.SpanSizeLookup spanSizeLookup = ((GridLayoutManager) layout).getSpanSizeLookup();
                    ((GridLayoutManager) layout).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            int itemViewType = adapter.getItemViewType(position);
                            if (itemViewType<=HEADTYPE|| itemViewType>=FOOTERTYPE){
                                Log.e("tag",((GridLayoutManager) layout).getSpanCount()+"-----------");
                                return ((GridLayoutManager) layout).getSpanCount();
                            }
                            if (spanSizeLookup==null){
                                return 1;
                            }else {
                                return spanSizeLookup.getSpanSize(position-heads.size());
                            }
//                            if (spanSizeLookup==null){
//                               // Log.e("tag",((GridLayoutManager) layout).getSpanCount()+"-----------");
//                                return 1;
//                            }else {
//                                int itemViewType = adapter.getItemViewType(position);
//
//                                if (itemViewType<=HEADTYPE|| itemViewType>=FOOTERTYPE){
//                                    Log.e("tag",((GridLayoutManager) layout).getSpanCount()+"-----------");
//                                    return ((GridLayoutManager) layout).getSpanCount();
//                                }else {
//                                    return spanSizeLookup.getSpanSize(position-heads.size());
//                                }
//                            }
                        }
                    });
                }
            }
        });
        super.setLayoutManager(layout);
    }
}
