package com.example.hp.mymusicdemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.INotificationSideChannel;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by HP on 2018/1/6.
 */

//音乐播放器下部那个小的控制界面
public class class_small_control_music_play extends LinearLayout {
//控制暂停开始按钮
    private Button btnplayorpause,btnsonglist;
//圆形头像
    private CircleImageView singerpic;
//歌手与歌词
    private TextView songname,others;
//跳转
    private Intent intent;

    public class_small_control_music_play(Context context) {
        super(context);
    }

    public class_small_control_music_play(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.layout_small_control_music_play, this,
                true);
        singerpic = (CircleImageView) findViewById(R.id.singerpic);
        songname = (TextView) findViewById(R.id.songname);
        others=(TextView)findViewById(R.id.other);
        btnplayorpause=(Button)findViewById(R.id.btnplayorpause);
        btnsonglist=(Button)findViewById(R.id.songlist);
    }

    public void setSingerpic(int picid)
    {
        singerpic.setImageResource(picid);
    }

    public void setSongname(String text)
    {
        songname.setText(text);
    }

    public void setOthers(String text)
    {
        others.setText(text);
    }

    public View.OnClickListener click=new OnClickListener() {
        @Override
        public void onClick(View view) {
            getContext().startActivity(intent);
        }
    };

    public void getIntent(Intent intent)
    {
        this.intent=intent;
    }
}
