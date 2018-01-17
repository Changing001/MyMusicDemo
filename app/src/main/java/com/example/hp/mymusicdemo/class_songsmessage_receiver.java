package com.example.hp.mymusicdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

/**
 * Created by HP on 2018/1/9.
 */

public class class_songsmessage_receiver extends BroadcastReceiver {
    private String str_songname;
    private String str_singername;
    private int int_musictimelong;
    private int int_position;
    private SharedPreferences mySharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    public void onReceive(Context context, Intent intent) {

//       获得歌曲歌手以及歌曲的新消息//       获取广播传送的音乐信息
        inlt(intent);
        Toast.makeText(context,
                "播放来自"+str_singername+"的"+str_songname, Toast.LENGTH_SHORT).show();

         mySharedPreferences = context.getSharedPreferences("password_PUTDATE",
                Activity.MODE_PRIVATE);
        editor = mySharedPreferences.edit();
        editor.putInt("password_MUSICPOSTION",int_position);//放入歌曲下标，在大的控制界面使用

        editor.putString("password_SONGNAME",str_songname);//在小的控制界面使用
        editor.putString("password_SINGERNAME",str_singername);
        editor.commit();

      /*  因为广播的生命周期较短所以该用数据存储模式
                将数据存储起来再进行打开操作，
        便于理解，
        也比较方便使用*/

    }

    public void inlt(Intent intent) {
        this.int_position=intent.getIntExtra("password_SONGPOSFORCONTROL",-99);
        this.str_songname= intent.getStringExtra("password_SONGNAME");
        this.str_singername= intent.getStringExtra("password_SINGERNAME");
        this.int_musictimelong=intent.getIntExtra("password_MUSICTIMELONG",-100);
    }
}
