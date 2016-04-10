package com.zj.materialfood;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Xml;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

/**
 * Created by jjx on 2016/4/9.
 */
public class StartActivity extends AppCompatActivity{


    ImageView image;
    ImageView mountain;
    TextView text;
    TextView percent;
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
        setContentView(R.layout.activity_start);

        image = (ImageView) findViewById(R.id.image1);
        mountain = (ImageView) findViewById(R.id.mountain);
        text = (TextView) findViewById(R.id.text1);
        percent = (TextView) findViewById(R.id.percent);

        //实例化SharedPreferences对象（第一步）

        SharedPreferences mySharedPreferences= getSharedPreferences("orders",
                Activity.MODE_PRIVATE);
//实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
//用putString的方法保存数据
        editor.putInt("food1",0);
        editor.putInt("food2", 0);
        editor.putInt("food3",0);
        editor.putInt("food4",0);
        editor.putInt("food5",0);
        editor.putInt("food6",0);
        editor.putInt("food7",0);
        editor.putInt("food8",0);
        editor.putInt("food9",0);
        editor.putInt("food10",0);


//提交当前数据
        editor.commit();
        animateParallel();




        /*handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                //进入主页面
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);

            }
        }, 6000);*/


    }

    private void initXML() {

        // 获得序列化对象
        XmlSerializer serializer = Xml.newSerializer();

        try {
            File path = new File(Environment.getExternalStorageDirectory(), "orders.xml");
            FileOutputStream fos = new FileOutputStream(path);
            // 指定序列化对象输出的位置和编码
            serializer.setOutput(fos, "utf-8");

            serializer.startDocument("utf-8", true);	// 写开始 <?xml version='1.0' encoding='utf-8' standalone='yes' ?>

            serializer.startTag(null, "orders");		// <persons>



            serializer.endTag(null, "orders");			// </persons>

            serializer.endDocument();		// 结束
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void animateParallel() {
        ViewAnimator.animate(mountain, image)
                .dp().translationY(-1000, 0)
                .alpha(0, 1)

                .andAnimate(percent)
                .scale(0, 1)

                .andAnimate(text)
                .dp().translationY(1000, 0)
                .textColor(Color.BLACK, Color.WHITE)
                .backgroundColor(Color.WHITE, Color.BLACK)

                .waitForHeight()
                .interpolator(new AccelerateDecelerateInterpolator())
                .duration(1000)

                .thenAnimate(percent)
                .custom(new AnimationListener.Update<TextView>() {
                    @Override
                    public void update(TextView view, float value) {
                        value = value * 100;
                        view.setText(String.format(Locale.US, "%.0f%%", value));
                    }
                }, 0, 1)

                .andAnimate(image)
                .rotation(0, 360)
                .onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        Intent intent = new Intent(StartActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                })

                .duration(1000)



                .start();






    }


}
