package com.example.hp.mymusicdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

//大的控制界面
public class activity_controlmusicplay extends AppCompatActivity {

    private Button btn_playmode;
    private Button btn_lastsong;
    private Button btn_next_song;
    private Button btn_pause;
    private Button btn_playlist;
    private Button btn_iflikeit;
    private Button btn_back;
    private TextView textView_songname;
    private TextView textView_singername;
    private boolean ifPause = false;
    private boolean ifLikeIt = false;
    private int PlayMode=1;
    private int   RemPlayMode=1396789;//记录本地的播放模式以及转载传输到service的命令数据，数据贼奇怪
    private final static int PAUSEPLAY=12138;
    private final static int CONTINUEPLAY=13145;
    private final static int  LASTSONG=66666;
    private final static int  NEXTSONG=23333;
    private final static int PLAYRANDOM=1391234;
    private final static int  PLAYONELOOP=1392345;
    private final static int  PLAYLOOP=1393456;
    private final static int  PLAYWITHLIST=1396789;
    private final static int WRONGORDER=-99;//初始化一些命令使得便于操作，目前我大多用的是传数字表示命令
    private int int_musictimelong;//    音乐播放的时间控制
    private int int_musictime_minute;
    private int int_musictime_sceond;
    private Thread thread;//获取时间
    private class_songsmessage_receiver receiver;

    private TextView textview_remtime_left;
    private TextView textview_remtime_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_controlmusicplay);

        if(getSupportActionBar()!=null) getSupportActionBar().hide();

        btn_playmode=(Button)findViewById(R.id.btn1);
        btn_lastsong=(Button)findViewById(R.id.btn2);
        btn_next_song=(Button)findViewById(R.id.btn4);
        btn_pause=(Button)findViewById(R.id.btn3);
        btn_playlist=(Button)findViewById(R.id.btn5);

        btn_iflikeit=(Button)findViewById(R.id.btn6);
        btn_back=(Button)findViewById(R.id.btn_back);

        textView_singername=(TextView)findViewById(R.id.textview_singername);
        textView_songname=(TextView)findViewById(R.id.textview_songname);

        textview_remtime_left =(TextView) findViewById(R.id.inlayout_retime_left);
        textview_remtime_right =(TextView)findViewById(R.id.inlayout_retime_right);


//        receiver=new class_songsmessage_receiver();
//从广播中获取数据,此处没有申明变量,但是功能是广播实现的

        SharedPreferences sharedPreferences = getSharedPreferences("password_PUTDATE", Activity.MODE_PRIVATE);
        String str_songname = sharedPreferences.getString("password_SONGNAME", "wrong");
        String str_singername = sharedPreferences.getString("password_SINGERNAME", "wrong");
        int songtime=sharedPreferences.getInt("password_MUSICTIMELONG",-99);
//        从存储数据中取出相关数据，用来使用
        textView_songname.setText(str_songname);
        textView_singername.setText(str_singername);

     /*   从广播中获取到歌曲信息从而实现动态变化，随着歌曲播放而实现
                注意广播需要添加权限设置，还有在权限设置中声明*/


     setmusictime(songtime);

     String str =String.valueOf(int_musictime_minute);
     textview_remtime_right.setText(str);


     btn_back.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Toast.makeText(activity_controlmusicplay.this, "hail hydra", Toast.LENGTH_SHORT).show();
         }
     });

        btn_iflikeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ifLikeIt)
                {
                    ifLikeIt=true;
                    btn_iflikeit.setBackgroundResource(R.drawable.likeit);
                    Toast.makeText(activity_controlmusicplay.this, "已经添加到我喜欢", Toast.LENGTH_SHORT).show();
                }
                else if(ifLikeIt)
                {
                    ifLikeIt=false;
                    btn_iflikeit.setBackgroundResource(R.drawable.notlikeit);
                    Toast.makeText(activity_controlmusicplay.this, "已从我喜欢中移除", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btn_playmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(PlayMode==4)
                    PlayMode=1;
                else PlayMode+=1;
                switch (PlayMode)
                {
                    case 1:
                        btn_playmode.setBackgroundResource(R.drawable.playrandom);
                        RemPlayMode=PLAYRANDOM;
                        Toast.makeText(activity_controlmusicplay.this, "切换到随机播放模式", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        btn_playmode.setBackgroundResource(R.drawable.looponesong);
                        RemPlayMode=PLAYONELOOP;
                        Toast.makeText(activity_controlmusicplay.this, "切换到单曲循环模式", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        btn_playmode.setBackgroundResource(R.drawable.loopplay);
                        RemPlayMode=PLAYLOOP;
                        Toast.makeText(activity_controlmusicplay.this, "切换到循环播放模式", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        btn_playmode.setBackgroundResource(R.drawable.playhavelist);
                        RemPlayMode=PLAYWITHLIST;
                        Toast.makeText(activity_controlmusicplay.this, "切换到顺序播放模式", Toast.LENGTH_SHORT).show();
                        break;
                }
                Intent intent=new Intent(activity_controlmusicplay.this,service_contorlmusicplay.class);
                intent.putExtra("password_PLAYMODE",RemPlayMode);
                startService(intent);
            }
        });

        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ifPause)
                {
                    ifPause=false;
                    Intent intent=new Intent(activity_controlmusicplay.this,service_contorlmusicplay.class);
                    intent.putExtra("password_CONTINUEPLAY",CONTINUEPLAY);
                    startService(intent);
                    btn_pause.setBackgroundResource(R.drawable.pause);
                }
                else if(!ifPause)
                {
                    ifPause=true;
                    Intent intent=new Intent(activity_controlmusicplay.this,service_contorlmusicplay.class);
                    intent.putExtra("password_PAUSEPLAY",PAUSEPLAY);
                    startService(intent);
                    btn_pause.setBackgroundResource(R.drawable.play);
                }
            }
        });

        btn_lastsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_controlmusicplay.this,service_contorlmusicplay.class);
                intent.putExtra("password_PLAYLAST",LASTSONG);
                startService(intent);
            }
        });
        btn_next_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_controlmusicplay.this,service_contorlmusicplay.class);
                intent.putExtra("password_PLAYNEXT",NEXTSONG);
                startService(intent);
            }
        });
    }
    public void setmusictime(int songtime)
    {
        songtime/=1000;
        this.int_musictime_minute=songtime/60;
        this.int_musictime_sceond=songtime%60;
    }
}
