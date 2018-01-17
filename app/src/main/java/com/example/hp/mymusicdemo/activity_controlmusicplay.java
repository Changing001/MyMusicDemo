package com.example.hp.mymusicdemo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//大的控制界面
public class activity_controlmusicplay extends AppCompatActivity {

    private final static int PAUSEPLAY=12138;
    private final static int CONTINUEPLAY=13145;
    private final static int  LASTSONG=66666;
    private final static int  NEXTSONG=23333;
    private final static int PLAYRANDOM=1391234;
    private final static int  PLAYONELOOP=1392345;
    private final static int  PLAYLOOP=1393456;
    private final static int  PLAYWITHLIST=1396789;
    private final static int WRONGORDER=-99;//初始化一些命令使得便于操作，目前我大多用的是传数字表示命令

    private Button btn_playmode;
    private Button btn_lastsong;
    private Button btn_next_song;
    private Button btn_pause;
    private Button btn_playlist;
    private Button btn_iflikeit;
    private Button btn_back;

    private TextView textView_songname;
    private TextView textView_singername;
    private TextView textview_remtime_left;
    private TextView textview_remtime_right;

    private boolean ifPause = false;
    private boolean ifLikeIt = false;

    private int PlayMode=1;
    private int   RemPlayMode=1396789;//记录本地的播放模式以及转载传输到service的命令数据，数据贼奇怪
    private int int_musictimelong;//    音乐播放的时间控制
    private int int_musictime_minute;
    private int int_musictime_sceond;

    private Thread thread;//获取时间
    private class_songsmessage_receiver receiver;

    private SharedPreferences sharedPreferences;

    private String str_songname;
    private String str_singername;
    private int songtime;

    private ArrayList<class_songs_all_date>arrayList_songdate=new ArrayList<>();

    private class_songs_all_date mysong=new class_songs_all_date();

    private Uri mediaUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    private int int_songpos;


    private ArrayList arrayList_songposnum=new ArrayList();


    private SQLiteDatabase db;  //    数据库对象
    private MyDBOpenHelper myDBHelper;
    private StringBuilder sb;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_controlmusicplay);
        if(getSupportActionBar()!=null) getSupportActionBar().hide();
        inlt();
        getUriData(mediaUri);//获取音乐所有数据。


        mContext=activity_controlmusicplay.this;
        myDBHelper = new MyDBOpenHelper(mContext, "my.db", null, 1);//测绘数据库


        sharedPreferences = getSharedPreferences("password_PUTDATE",
                Activity.MODE_PRIVATE);
        int_songpos=sharedPreferences.getInt("password_MUSICPOSTION",-99);

        /**
         * 上面从广播处获取数据，从而改变界面
         * 下面通过算法使得歌手名字较短，显示在界面上
         */
        String remsinger=arrayList_songdate.get(int_songpos).getSinger();
        int rem_,remkuohao;
        rem_=remsinger.indexOf(" ");
        remkuohao=remsinger.indexOf("-");
        int min=rem_<remkuohao?rem_:remkuohao;
        if(min>0) textView_singername.setText(arrayList_songdate.get(int_songpos).getSinger().substring(0,min));
        else textView_singername.setText(arrayList_songdate.get(int_songpos).getSinger());


        textView_songname.setText(arrayList_songdate.get(int_songpos).getName());

        //获取歌曲数据


     /*   从广播中获取到歌曲信息从而实现动态变化，随着歌曲播放而实现
                注意广播需要添加权限设置，还有在权限设置中声明*/


     setmusictime(songtime);
     String str =String.valueOf(int_musictime_minute);
     textview_remtime_right.setText(str);



     btn_back.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Toast.makeText(activity_controlmusicplay.this,
                     "hail hydra", Toast.LENGTH_SHORT).show();
         }
     });
        btn_iflikeit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!ifLikeIt)
                {
                    ifLikeIt=true;
                    btn_iflikeit.setBackgroundResource(R.drawable.likeit);
                    Toast.makeText(activity_controlmusicplay.this,
                            "已经添加到我喜欢", Toast.LENGTH_SHORT).show();
                    arrayList_songposnum.add(int_songpos);

                    /**
                     * 放入喜欢的歌曲下标到数据库。
                     */
                    db = myDBHelper.getWritableDatabase();
                    ContentValues values1 = new ContentValues();
                    values1.put("name", int_songpos);//name指的是内容
                    db.insert("person", null, values1);
                    /**
                     * 准备添加至数据库，记录
                     */
                }
                else if(ifLikeIt)
                {
                    ifLikeIt=false;
                    btn_iflikeit.setBackgroundResource(R.drawable.notlikeit);
                    Toast.makeText(activity_controlmusicplay.this,
                            "已从我喜欢中移除", Toast.LENGTH_SHORT).show();

                    int pos=arrayList_songposnum.size()-1;
                    arrayList_songposnum.remove(pos);    //标签列表我喜欢的歌单

                    db = myDBHelper.getWritableDatabase(); //参数依次是表名，以及where条件与约束
                    db.delete("person", "personid = ?", new String[]{"18"});
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
                        Toast.makeText(activity_controlmusicplay.this,
                                "切换到随机播放模式", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        btn_playmode.setBackgroundResource(R.drawable.looponesong);
                        RemPlayMode=PLAYONELOOP;
                        Toast.makeText(activity_controlmusicplay.this,
                                "切换到单曲循环模式", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        btn_playmode.setBackgroundResource(R.drawable.loopplay);
                        RemPlayMode=PLAYLOOP;
                        Toast.makeText(activity_controlmusicplay.this,
                                "切换到循环播放模式", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        btn_playmode.setBackgroundResource(R.drawable.playhavelist);
                        RemPlayMode=PLAYWITHLIST;
                        Toast.makeText(activity_controlmusicplay.this,
                                "切换到顺序播放模式", Toast.LENGTH_SHORT).show();
                        break;
                }
                Intent intent=new Intent(activity_controlmusicplay.this,
                        service_contorlmusicplay.class);
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
                    Intent intent=new Intent(activity_controlmusicplay.this,
                            service_contorlmusicplay.class);
                    intent.putExtra("password_CONTINUEPLAY",CONTINUEPLAY);
                    startService(intent);
                    btn_pause.setBackgroundResource(R.drawable.pause);
                }
                else if(!ifPause)
                {
                    ifPause=true;
                    Intent intent=new Intent(activity_controlmusicplay.this,
                            service_contorlmusicplay.class);
                    intent.putExtra("password_PAUSEPLAY",PAUSEPLAY);
                    startService(intent);
                    btn_pause.setBackgroundResource(R.drawable.play);
                }
            }
        });
        btn_lastsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_controlmusicplay.this,
                        service_contorlmusicplay.class);
                intent.putExtra("password_PLAYLAST",LASTSONG);
                startService(intent);
            }
        });
        btn_next_song.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(activity_controlmusicplay.this,
                        service_contorlmusicplay.class);
                intent.putExtra("password_PLAYNEXT",NEXTSONG);
                startService(intent);
            }
        });
    }

//    private class click implements View.OnClickListener{
//        @Override
//        public void onClick(View v) {
//        }
//    }

    private void getUriData(Uri uri){

        arrayList_songdate=new ArrayList<>();

        String[] projection = {"_data","_display_name","_size","mime_type","title","duration"};
        Cursor cursor = getContentResolver().query(uri, projection, null,
                null, null);
        cursor.moveToFirst();
        do {
            mysong=new class_songs_all_date();
            mysong.setName(cursor.getString(cursor.getColumnIndex("title")));
            mysong.setPath(cursor.getString(cursor.getColumnIndex("_data")));
            mysong.setSinger(cursor.getString(cursor.getColumnIndex("_display_name")));
            mysong.setTimelong(cursor.getString(cursor.getColumnIndex("duration")));

            arrayList_songdate.add(mysong);
//            System.out.println("_data = "+cursor.getString(cursor.getColumnIndex("_data")));
//            System.out.println("_display_name = "+cursor.getString(cursor.getColumnIndex("_display_name")));
//            System.out.println("_size = "+cursor.getString(cursor.getColumnIndex("_size")));
//            System.out.println("mime_type = "+cursor.getString(cursor.getColumnIndex("mime_type")));
//            System.out.println("title = "+cursor.getString(cursor.getColumnIndex("title")));
//            System.out.println("duration = "+cursor.getString(cursor.getColumnIndex("duration")));
        } while (cursor.moveToNext());
    }

    void inlt() {
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
    }


    public void setmusictime(int songtime) {
        songtime/=1000;
        this.int_musictime_minute=songtime/60;
        this.int_musictime_sceond=songtime%60;
    }
}
