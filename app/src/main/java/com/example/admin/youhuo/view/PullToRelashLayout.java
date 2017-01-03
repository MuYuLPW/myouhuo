package com.example.admin.youhuo.view;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.admin.youhuo.R;
import com.example.admin.youhuo.utils.IDUtils;
import com.example.admin.youhuo.utils.LogUtils;

/**
 * Created by admin on 2016/12/28.
 */

public class PullToRelashLayout extends RelativeLayout {

    private float dimension;
    private GestureDetector detector;
    private int[] images=new int[13];
    private ImageView headView;
    private LayoutParams headParams;
    private ImageView footerView;
    private LayoutParams footerParams;
    private SuperRecycler recyclerView;
    private LayoutParams recyclerParams;
    private int startY;
    private int startX;
    private int lasttopMargin;
    private int lastbottomMargin;
    private OnPullToRelashListener listener;
    private boolean isLoading;
    private boolean isPulling;
    private boolean hasHeadView;
    private boolean hasFooterView;

    public PullToRelashLayout(Context context) {
        super(context);
        init();
    }

    public PullToRelashLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRelashLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dimension = getResources().getDimension(R.dimen.headAndFooterSize);
        detector = new GestureDetector(getContext(),new MyDetector());
        initImage();
        initHead();
        initFooter();
        initContent();
    }

    private void initContent() {
        recyclerView = new SuperRecycler(getContext());
        recyclerParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        recyclerParams.addRule(ABOVE,R.id.footer);
        recyclerParams.addRule(BELOW,R.id.head);
        recyclerView.setLayoutParams(recyclerParams);
        addView(recyclerView);
    }
    public void setAdapter(RecyclerView.Adapter adapter){
        recyclerView.setAdapter(adapter);
    }

    private void initFooter() {
        footerView = new ImageView(getContext());
        footerParams = new LayoutParams(LayoutParams.WRAP_CONTENT, (int) dimension);
        footerParams.addRule(CENTER_HORIZONTAL);
        footerParams.addRule(ALIGN_PARENT_BOTTOM);
        footerParams.bottomMargin= (int) -dimension;
        footerView.setLayoutParams(footerParams);
        footerView.setId(R.id.footer);
        footerView.setImageResource(images[0]);
        addView(footerView);
        hasFooterView=true;
    }

    private void initHead() {
        headView = new ImageView(getContext());
        headParams = new LayoutParams(LayoutParams.WRAP_CONTENT, (int) dimension);
        headParams.addRule(CENTER_HORIZONTAL);
        headParams.topMargin= (int) -dimension;
        headView.setLayoutParams(headParams);
        headView.setId(R.id.head);
        headView.setImageResource(images[0]);
        addView(headView);
        hasHeadView=true;
    }

    private void initImage() {
        for (int i = 1; i <=images.length; i++) {
            images[i-1]= IDUtils.getDrawableId("js_classify_images_loading_icon_loading_frame_"+i);
        }
    }

    class MyDetector extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float v = (e2.getY() - e1.getY())/2;
            if (hasHeadView) {
                if (recyclerView.getFirstVisiavleChildPosition() == 0) {
                    // LogUtils.log("yyy",""+v+"--"+lasttopMargin);
                    headParams.topMargin = (int) Math.max(lasttopMargin + v, -dimension);
                    headView.setLayoutParams(headParams);
                    int i = (headParams.topMargin +headView.getHeight())/5;
                    headView.setImageResource(images[i%images.length]);
                }
            }
            if (hasFooterView) {
                if (recyclerView.getLastVisiavleChildPosition() == recyclerView.getAdapter().getItemCount() - 1) {
                    footerParams.bottomMargin = (int) Math.max(-dimension, lastbottomMargin - v);
                    recyclerParams.topMargin = (int) -(footerParams.bottomMargin + dimension);
                    footerView.setLayoutParams(footerParams);
                    recyclerView.setLayoutParams(recyclerParams);
                    int i = (footerParams.bottomMargin +footerView.getHeight())/5;
                    footerView.setImageResource(images[i%images.length]);
                }
            }
            return false;
        }
    }
    public SuperRecycler getRecyclerView(){
        return recyclerView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isPulling||isLoading)return true;
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getRawX();
                startY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int rawX = (int) ev.getRawX();
                int rawY= (int) ev.getRawY();
                if (isVertical(rawX-startX,rawY-startY)&&((recyclerView.getFirstVisiavleChildPosition()==0&& rawY-startY>0)|| (recyclerView.getLastVisiavleChildPosition()==recyclerView.getAdapter().getItemCount()-1&& rawY-startY<0))){
                    startX= (int) ev.getRawX();
                    startY= (int) ev.getRawY();
                    lasttopMargin = headParams.topMargin;
                    lastbottomMargin = footerParams.bottomMargin;
                    ev.setAction(MotionEvent.ACTION_DOWN);
                    detector.onTouchEvent(ev);
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (isPulling||isLoading)return true;
       // detector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX= (int) event.getRawX();
                startY= (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                detector.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_UP:
                if (headParams.topMargin!=(int)-dimension){
                    if (headParams.topMargin>=0){
                        headParams.topMargin=0;
                        if (listener!=null){
                            listener.pullMore();
                            isPulling=true;
                            startAnimation(headView);
                        }
                    }else {
                        headParams.topMargin=(int)-dimension;
                    }
                    headView.setLayoutParams(headParams);
                }
                if (footerParams.bottomMargin!=(int)-dimension){
                    if (footerParams.bottomMargin>=0){
                        footerParams.bottomMargin=0;
                        recyclerParams.topMargin=-(int)dimension;
                        if (listener!=null){
                            listener.loadMore();
                            isLoading=true;
                            startAnimation(footerView);
                        }
                    }else {
                        footerParams.bottomMargin=-(int)dimension;
                        recyclerParams.topMargin=0;
                    }
                }
                recyclerView.setLayoutParams(recyclerParams);
                footerView.setLayoutParams(footerParams);

                break;
        }
        return true;
    }

    private void startAnimation(ImageView iv) {
        iv.setImageResource(R.drawable.loading);
        AnimationDrawable drawable = (AnimationDrawable) iv.getDrawable();
        drawable.start();
    }

    private boolean isVertical(int x, int y) {
        return Math.abs(y)>Math.abs(x)&& Math.abs(y)>8;
    }
    public interface  OnPullToRelashListener{
        void pullMore();
        void loadMore();
    }
    public void setOnPullToRelashListener(OnPullToRelashListener listener){
        this.listener=listener;
    }
    public void pullSeccess(){
        headParams.topMargin=(int)-dimension;
        headView.setLayoutParams(headParams);
        isPulling=false;
    }
    public void loadSeccess(){
        footerParams.bottomMargin=-(int)dimension;
        recyclerParams.topMargin=0;
        recyclerView.setLayoutParams(recyclerParams);
        footerView.setLayoutParams(footerParams);
        isLoading=false;
    }
    public void removeHead(){
        removeView(headView);
        hasHeadView=false;
    }
    public void removeFooter(){
        removeView(footerView);
        hasFooterView=false;
    }

}
