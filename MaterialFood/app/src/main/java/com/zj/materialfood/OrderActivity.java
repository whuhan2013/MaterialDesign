package com.zj.materialfood;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjx on 2016/4/10.
 */
public class OrderActivity extends AppCompatActivity{

    Toolbar toolbar;
    RecyclerView recyclerView;
    OrderAdapter mAdapter;
    List<Integer> orderList;
    String titles[]=new String[]{"麻婆豆腐","灯影牛肉","夫妻肺片","蒜泥白肉","白油豆腐","鱼香肉丝","泉水豆花","宫保鸡丁 ","东坡墨鱼 ","麻辣香锅"};
    int img_ids[]=new int[]{R.drawable.lvfood1,R.drawable.lvfood2,R.drawable.lvfood3,R.drawable.lvfood4,R.drawable.lvfood5,R.drawable.lvfood6,R.drawable.lvfood7,R.drawable.lvfood8,R.drawable.lvfood9,R.drawable.lvfood10};
    List<Integer> orderNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);

        toolbar= (Toolbar) findViewById(R.id.order_toolbar);
        setSupportActionBar(toolbar);
        setTitle("我的订单");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        initData();

        recyclerView= (RecyclerView) findViewById(R.id.order_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(OrderActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);



        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new OrderAdapter();
        recyclerView.setAdapter(mAdapter);



    }

    private void initData() {

        orderList=new ArrayList<>();
        orderNum=new ArrayList<>();

        for (int i=0;i<10;i++)
        {
            SharedPreferences sharedPreferences= getSharedPreferences("orders",
                    Activity.MODE_PRIVATE);
            int num=sharedPreferences.getInt("food"+(i+1),0);
            if(num!=0)
            {
              orderList.add(i);
                orderNum.add(num);
            }
        }
    }


    public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {



        private final TypedValue mTypedValue = new TypedValue();

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case
            public TextView mTextView;
            public ImageView iv_food;
            public TextView textDesc;
            public int pos=0;

            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.orderTitle);
                iv_food= (ImageView) v.findViewById(R.id.iv_order_item);
                textDesc= (TextView) v.findViewById(R.id.orderPrice);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {



            }

        }

        // Provide a suitable constructor (depends on the kind of dataset)

        @Override
        public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.orderitem, parent, false);

            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            //holder.mTextView.setText(mDataset[position]);
            holder.pos=position;
            holder.mTextView.setText(titles[orderList.get(position)]);
            holder.iv_food.setBackgroundResource(img_ids[orderList.get(position)]);
            holder.textDesc.setText("数量:"+orderNum.get(position)+"份\n"+"总价"+88*orderNum.get(position)+"元");
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return orderList.size();
        }
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(upIntent)
                            .startActivities();
                } else {
                    upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

