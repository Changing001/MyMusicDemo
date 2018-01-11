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
    private String str_songname,str_singername;
    private int PlayTime=0;
    @Override
    public void onReceive(Context context, Intent intent) {

//       获得歌曲歌手以及歌曲的新消息
//       获取广播传送的音乐信息
        setStr_singername(intent);
        setStr_songname(intent);
        Toast.makeText(context, "播放来自"+str_singername+"的"+str_songname, Toast.LENGTH_SHORT).show();


        SharedPreferences mySharedPreferences = context.getSharedPreferences("password_PUTDATE", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        editor.putString("password_SONGNAME", str_songname);
        editor.putString("password_SINGERNAME", str_singername);


        PlayTime+=1;
        editor.putInt("password_PLAYTIME",PlayTime);
//        记录播放数据，当数据不统一时表示开启新的路径记录

        editor.commit();

      /*  因为广播的生命周期较短所以该用数据存储模式
                将数据存储起来再进行打开操作，
        便于理解，
        也比较方便使用*/

    }
    public void setStr_songname(Intent intent)
    {
        this.str_songname= intent.getStringExtra("password_SONGNAME");
    }
    public void setStr_singername(Intent intent)
    {
        this.str_singername= intent.getStringExtra("password_SINGERNAME");
    }
    public String getStr_songname()
    {
        return str_songname;
    }
    public String getStr_singername()
    {
        return str_singername;
    }
}
