package com.zj.materialfood;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import it.gmariotti.recyclerview.adapter.SlideInBottomAnimatorAdapter;
import it.gmariotti.recyclerview.adapter.SlideInRightAnimatorAdapter;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;


    private List<String> mDatas;
    private HomeAdapter mAdapter;
    int ids[]=new int[]{R.drawable.food1,R.drawable.food2,R.drawable.food3,R.drawable.food4,R.drawable.food5,R.drawable.food6,R.drawable.food7,R.drawable.food8};
    Toolbar toolbar;

    SlideInBottomAnimatorAdapter slideInBottomAnimatorAdapter;
    SlideInRightAnimatorAdapter slideInRightAnimatorAdapter;

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    NavigationView mNavigationView;

    android.os.Handler handler=new android.os.Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("天天美食");


        //设置DrawerLayout
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close)
        {
            //关闭侧边栏时响应
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
            //打开侧边栏时响应
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //设置NavigationView点击事件
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupDrawerContent(mNavigationView);
        //设置NavigationView点击事件


        recyclerView= (RecyclerView) findViewById(R.id.id_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter=new HomeAdapter();
        slideInBottomAnimatorAdapter=new SlideInBottomAnimatorAdapter(mAdapter,recyclerView);

        //recyclerView.setAdapter(slideInRightAnimatorAdapter);
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //进入主页面
                recyclerView.setAdapter(slideInBottomAnimatorAdapter);
            }
        }, 1000);

        //recyclerView.setAdapter(mAdapter);



    }

    private void setupDrawerContent(NavigationView navigationView) {

        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navigation_item_example:
                                //switchToExample();
                                switchToOrder();
                                break;
                            case R.id.navigation_item_blog:

                                break;
                            case R.id.navigation_item_about:
                                //switchToAbout();
                                break;

                        }
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });

    }

    private void switchToOrder() {
               Intent intent=new Intent(MainActivity.this,OrderActivity.class);
        startActivity(intent);

    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {




        @Override
        public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            MyViewHolder holder=new MyViewHolder(LayoutInflater.from(MainActivity.this).inflate(R.layout.item_main,parent,false));
            return holder;
        }

        @Override
        public void onBindViewHolder(HomeAdapter.MyViewHolder holder, int position) {



            holder.iv.setBackgroundResource(ids[position]);

        }

        @Override
        public int getItemCount() {
            return 8;
        }

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
        {

            ImageView iv;

            public MyViewHolder(View view)
            {
                super(view);
                iv = (ImageView) view.findViewById(R.id.iv_food);
                view.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this,FoodCardActivity.class);
                startActivity(intent);
            }
        }
    }
}
