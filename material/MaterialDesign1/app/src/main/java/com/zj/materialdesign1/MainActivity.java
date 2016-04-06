package com.zj.materialdesign1;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jjx on 2016/4/5.
 */
public class MainActivity extends AppCompatActivity{

    Toolbar toolbar;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    String myDataset[];
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        mRecyclerView= (RecyclerView) findViewById(R.id.recyclerView);

        // App Logo
        toolbar.setLogo(R.drawable.ic_launcher);
// Title
        toolbar.setTitle("My Title2");
// Sub Title
        toolbar.setSubtitle("Sub title");

        setSupportActionBar(toolbar);

// Navigation Icon 要設定在 setSupoortActionBar 才有作用
// 否則會出現 back button
        toolbar.setNavigationIcon(R.drawable.ab_android);


        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        initView();

    }

    private void initView() {

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        myDataset = new String[]{"JAVA", "Objective-C", "C", "C++", "Swift",
                "GO", "JavaScript", "Python", "Ruby", "HTML", "SQL"};
        mAdapter = new MyAdapter(this,myDataset);
        mRecyclerView.setAdapter(mAdapter);
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private final int mBackground;
        private String[] mDataset;

        private final TypedValue mTypedValue = new TypedValue();

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            // each data item is just a string in this case
            public TextView mTextView;

            public ViewHolder(View v) {
                super(v);
                mTextView = (TextView) v.findViewById(R.id.textView);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                String text = "I Love " + mTextView.getText() + ".";
                Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show();
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(Context context ,String[] myDataset) {
            mDataset = myDataset;
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
        }

        @Override
        public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_view, parent, false);
            v.setBackgroundResource(mBackground);
            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.mTextView.setText(mDataset[position]);

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.length;
        }
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_edit:
                    msg += "Click edit";
                    break;
                case R.id.action_share:
                    msg += "Click share";
                    break;
                case R.id.action_settings:
                    msg += "Click setting";
                    break;
            }

            if(!msg.equals("")) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
