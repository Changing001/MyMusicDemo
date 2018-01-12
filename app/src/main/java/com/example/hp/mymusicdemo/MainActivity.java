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

    //    下方小的控制布局
    private class_small_control_music_play smallcontrol;
//    标题
    private class_mainactivity_title mytitle;

    private boolean ifHaveNewSongMessage=false;

    private class_songsmessage_receiver myreceiver;


    private int Playtimenum=0;
    private Thread thread;

    private String str_songname ;
    private String str_singername;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportActionBar()!=null) getSupportActionBar().hide();
//隐藏标题栏

        mytitle=(class_mainactivity_title)findViewById(R.id.maintitle);
        mytitle.getIntent(new Intent(MainActivity.this,activity_mylikesongs.class));


        //构造适配器
        List<Fragment> fragments=new ArrayList<Fragment>();
        fragments.add(new fragement_mainview());
        fragments.add(new fragement_musiccage());
        fragments.add(new fragement_found());
        FragAdapter adapter = new FragAdapter(getSupportFragmentManager(), fragments);
        //设定适配器
        ViewPager vp = (ViewPager)findViewById(R.id.myViewPaper);
        vp.setAdapter(adapter);



        smallcontrol=(class_small_control_music_play)findViewById(R.id.smallcontrol);

        //小的控制界面也需要得到歌曲的数据


        sharedPreferences = getSharedPreferences("password_PUTDATE", Activity.MODE_PRIVATE);


        str_songname = sharedPreferences.getString("password_SONGNAME", "wrong");
        str_singername = sharedPreferences.getString("password_SINGERNAME", "wrong");

//        从存储数据中取出相关数据，用来使用


/*        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(Playtimenum!=sharedPreferences.getInt("password_PLAYTIME",-99))
                    {
                        str_songname = sharedPreferences.getString("password_SONGNAME", "wrong");
                        str_singername = sharedPreferences.getString("password_SINGERNAME", "wrong");
                        smallcontrol.setSongname(str_songname);
                        smallcontrol.setOthers(str_singername);
                        Playtimenum+=1;
                        thread.sleep(200);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();*/
//        开启新的线程防止歌曲信息不对应


        smallcontrol.setSongname(str_songname);
        smallcontrol.setOthers(str_singername);

        smallcontrol.setSingerpic(R.drawable.hugh);
        smallcontrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,activity_controlmusicplay.class);
                startActivity(intent);
            }
        });
    }
}
