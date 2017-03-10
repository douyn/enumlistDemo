package com.qiwo.enumlistdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by l on 2016/7/11.
 */
public class SwipeRycyclerActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipe_refresh_layout;

    RecyclerTouchListener onTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_recyclerview);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(SwipeRycyclerActivity.this));
        SwipeRecyclerAdapter adapter = new SwipeRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        swipe_refresh_layout.setOnRefreshListener(this);
        onTouchListener = new RecyclerTouchListener(this, recyclerView);
        onTouchListener.setIndependentViews(R.id.item_btn)
                .setViewsToFade(R.id.item_btn)
                .setClickable(new RecyclerTouchListener.OnRowClickListener() {
                    @Override
                    public void onRowClicked(int position) {
                        // Do something
                    }

                    @Override
                    public void onIndependentViewClicked(int independentViewID, int position) {
                        // Do something
                    }
                })
                .setSwipeOptionViews(R.id.add, R.id.edit, R.id.change)
                .setSwipeable(R.id.rowFG, R.id.rowBG, new RecyclerTouchListener.OnSwipeOptionsClickListener() {
                    @Override
                    public void onSwipeOptionClicked(int viewID, int position) {
                        if (viewID == R.id.add) {
                            // Do something
                        } else if (viewID == R.id.edit) {
                            // Do something
                        } else if (viewID == R.id.change) {
                            // Do something
                        }
                    }
                })
                .setLongClickable(true, new RecyclerTouchListener.OnRowLongClickListener() {
                    @Override
                    public void onRowLongClicked(int position) {
                        Toast.makeText(SwipeRycyclerActivity.this, "Row " + (position + 1) + " long clicked!", Toast.LENGTH_SHORT).show();
                    }
                }).setUnSwipeableRows(0,2,4,26);

    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipe_refresh_layout.setRefreshing(false);
            }
        }, 1500);
    }

    public class SwipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_FOOTER = 0;
        private static final int TYPE_ITEM = 1;

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == TYPE_ITEM){
                View view = getLayoutInflater().inflate(R.layout.item_swipe_recyler, parent, false);
                ItemViewHolder holder = new ItemViewHolder(view);
                return holder;
            } else if(viewType == TYPE_FOOTER){
                View view = getLayoutInflater().inflate(R.layout.item_footer, parent, false);
                FooterHolder holder = new FooterHolder(view);
                return holder;
            }
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof  ItemViewHolder){
                ((ItemViewHolder)holder).item_btn.setText("Button " + position);
                ((ItemViewHolder)holder).item_des.setText("Item " + position);
            }
        }

        @Override
        public int getItemCount() {
            return 25 + 1;
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            TextView item_des;

            Button item_btn;

            public ItemViewHolder(View itemView) {
                super(itemView);
                item_des = (TextView) itemView.findViewById(R.id.item_des);
                item_btn = (Button) itemView.findViewById(R.id.item_btn);
            }
        }

        public  class FooterHolder extends RecyclerView.ViewHolder{
            TextView tv_loading;
            ProgressBar pb_loading;

            public FooterHolder(View itemView) {
                super(itemView);
            }
        }
        @Override
        public int getItemViewType(int position) {
            if(position + 1 == getItemCount()){
                return TYPE_FOOTER;
            } else{
                return TYPE_ITEM;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.addOnItemTouchListener(onTouchListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        recyclerView.removeOnItemTouchListener(onTouchListener);
    }


}
