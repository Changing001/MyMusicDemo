package com.example.hp.mymusicdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

//主界面
public class MainActivity extends AppCompatActivity {

    private class_small_control_music_play smallcontrol; //    下方小的控制布局
    private class_mainactivity_title mytitle;//    标题
    private boolean ifHaveNewSongMessage=false;
    private class_songsmessage_receiver myreceiver;
    private int Playtimenum=0;
    private Thread thread;
    private String str_songname ;
    private String str_singername;
    private ViewPager vp;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar()!=null) getSupportActionBar().hide();//隐藏标题栏
        inlt();

        mytitle.getIntent(new Intent(MainActivity.this,activity_mylikesongs.class));

        List<Fragment> fragments=new ArrayList<Fragment>();  //构造翻页适配器
        fragments.add(new fragement_mainview());
        fragments.add(new fragement_musiccage());
        fragments.add(new fragement_found());
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter); //设定适配器


        //小的控制界面也需要得到歌曲的数据//从存储数据中取出相关数据，用来使用
//        目前没有获得数据
        sharedPreferences = getSharedPreferences("password_PUTDATE", Activity.MODE_PRIVATE);
        str_songname = sharedPreferences.getString("password_SONGNAME", "wrong");
        str_singername = sharedPreferences.getString("password_SINGERNAME", "wrong");



        smallcontrol.setSongname(str_songname);
        smallcontrol.setOthers(str_singername);
        smallcontrol.setSingerpic(R.drawable.hugh);
        smallcontrol.setOnClickListener(new click());
    }

    public class click implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.smallcontrol:
                    Intent intent=new Intent(MainActivity.this,activity_controlmusicplay.class);
                    startActivity(intent);
                    break;
                default:break;
            }
        }
    }

    public void inlt() {
        mytitle=(class_mainactivity_title)findViewById(R.id.maintitle);
        vp = (ViewPager)findViewById(R.id.myViewPaper);
        smallcontrol=(class_small_control_music_play)findViewById(R.id.smallcontrol);
    }

}
