package com.example.admin.youhuo.fragment;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.youhuo.R;
import com.example.admin.youhuo.view.PullToRelashLayout;
import com.example.admin.youhuo.view.SuperRecycler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/12/27.
 */

public class ShouYeFragment extends BaseFragment {

    private PullToRelashLayout inflate;
    private List<String> list;
    public Handler handler=new Handler();
    private MyAdapter myAdapter;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        inflate = (PullToRelashLayout) inflater.inflate(R.layout.fragment_shouye, container, false);
        ImageView iv=new ImageView(a);
        iv.setImageResource(R.mipmap.ic_launcher);
        inflate.getRecyclerView().addHead(iv);

        TextView tv=new TextView(a);
        tv.setText("fdsfsdfs");
        inflate.getRecyclerView().addHead(tv);

        Button b=new Button(a);
        b.setText("adsad");
        inflate.getRecyclerView().addHead(b);
        ImageView iv2=new ImageView(a);
        iv2.setImageResource(R.mipmap.homepage_select_boys_p);
        inflate.getRecyclerView().addHead(iv2);
        final GridLayoutManager gridLayoutManager=new GridLayoutManager(a,2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position<5){
                    return gridLayoutManager.getSpanCount();
                }
                return 1;
            }
        });
        inflate.getRecyclerView().setLayoutManager(gridLayoutManager);
        inflate.setOnPullToRelashListener(new PullToRelashLayout.OnPullToRelashListener() {
            @Override
            public void pullMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.add(0,"fdsfdsa");
                        inflate.getRecyclerView().getAdapter().notifyDataSetChanged();
                        inflate.pullSeccess();
                    }
                }, 30000);
            }

            @Override
            public void loadMore() {

            }
        });
        return inflate;
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        for (int i=0;i<100;i++){
            list.add("item"+i);
        }
    }

    @Override
    public void initAdapter() {
        myAdapter = new MyAdapter();
        inflate.setAdapter(myAdapter);

        myAdapter.notifyDataSetChanged();

    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHold>{

        @Override
        public MyViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHold(View.inflate(a,android.R.layout.simple_list_item_1,null));
        }

        @Override
        public void onBindViewHolder(MyViewHold holder, int position) {
            holder.tv.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    private class MyViewHold extends RecyclerView.ViewHolder {
        TextView tv;
        public MyViewHold(View itemView) {
            super(itemView);
            tv= (TextView) itemView;
        }
    }
}
