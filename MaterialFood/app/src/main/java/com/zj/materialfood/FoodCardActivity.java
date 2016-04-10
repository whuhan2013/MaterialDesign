package com.zj.materialfood;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jjx on 2016/4/10.
 */
public class FoodCardActivity extends AppCompatActivity{


    Toolbar toolbar;
    RecyclerView recyclerView;
    MyAdapter mAdapter;
    int img_ids[]=new int[]{R.drawable.lvfood1,R.drawable.lvfood2,R.drawable.lvfood3,R.drawable.lvfood4,R.drawable.lvfood5,R.drawable.lvfood6,R.drawable.lvfood7,R.drawable.lvfood8,R.drawable.lvfood9,R.drawable.lvfood10};
    String titles[]=new String[]{"麻婆豆腐","灯影牛肉","夫妻肺片","蒜泥白肉","白油豆腐","鱼香肉丝","泉水豆花","宫保鸡丁 ","东坡墨鱼 ","麻辣香锅"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodcard);
        toolbar= (Toolbar) findViewById(R.id.food_toolbar);
        setSupportActionBar(toolbar);
        setTitle("美食菜单");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView= (RecyclerView) findViewById(R.id.food_recyclerview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(FoodCardActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);



        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new MyAdapter();
        recyclerView.setAdapter(mAdapter);


    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {



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
                mTextView = (TextView) v.findViewById(R.id.tvTitle);
                iv_food= (ImageView) v.findViewById(R.id.iv_food_item);
                textDesc= (TextView) v.findViewById(R.id.tvDesc);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                String text = "您的"+mTextView.getText() + "已下单";
                Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
                int id=this.pos;
                Log.i("sharpreses",id+"\n");

                //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
                SharedPreferences sharedPreferences= getSharedPreferences("orders",
                        Activity.MODE_PRIVATE);
                int num=sharedPreferences.getInt("food"+(id+1),0);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("food"+(id+1),num+1);
                editor.commit();
// 使用getString方法获得value，注意第2个参数是value的默认值

//使用toast信息提示框显示信息


            }

        }

        // Provide a suitable constructor (depends on the kind of dataset)

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.food_item, parent, false);

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
            holder.mTextView.setText(titles[position]);
            holder.iv_food.setBackgroundResource(img_ids[position]);
            holder.textDesc.setText("很好吃的一道菜 \n东南第一佳味，天下之至美 \n地址：武珞路30号 \n234人吃过 \n价格: 88.00元");
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return 10;
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
