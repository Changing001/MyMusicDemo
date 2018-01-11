package com.example.hp.mymusicdemo;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class service_contorlmusicplay extends Service {

    private ArrayList<String>arrayList_inservice_songsdate=new ArrayList<>();
    private boolean ifGetDate=false,
            ifStartPlayMusic=false;
    private MediaPlayer mediaPlayer=new MediaPlayer();
//    必须要初始化否则老是报错贼坑


    private int music_size;

    private boolean ifMediaPlayerIsNew=false,
    isIfMediaPlayerIsPause=false;
    private int
            songspos=-99,remsongpos;
//    因为很多重命令都要经过这里，所以需要用一个数记录命令
    private static int
            PAUSEPLAY=12138,
            CONTINUEPLAY=13145,
            LASTSONG=66666,
            NEXTSONG=23333,
            PLAYRANDOM=1391234,
            PLAYONELOOP=1392345,
            PLAYLOOP=1393456,
            PLAYWITHLIST=1396789;

    //    初始化一些命令使得便于操作，目前我大多用的是传数字表示命令
    private int
            Pause_order=-99, //给予其初始化数据，若通过相关的名字的命令不是此数则表示触发该命令
            Last_order=-99,
            Next_order=-99,
            Continue_order=-99,
            PlayMode_order=-99;
    private int MyOrder=-100,DateOrder=-100;

    private int PlayMode=1;

    public service_contorlmusicplay() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        music_size=intent.getIntExtra("password_MUSICSIZE",-99);
        if(music_size!=-99) Toast.makeText(this, "size"+music_size, Toast.LENGTH_SHORT).show();


        DateOrder=intent.getIntExtra("password_ifGiveDate",-99);
        if(DateOrder!=-99&&!ifGetDate)
        {
            ifGetDate=true;
            arrayList_inservice_songsdate=intent.getStringArrayListExtra("password_ALLSONGPATH");

            SharedPreferences mySharedPreferences = this.getSharedPreferences
                    ("password_JUDGEMENTIFPUTDATE", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.putInt("password_IFHAVEGIVEDATE",100);
        }

        songspos=intent.getIntExtra("password_SONGPOSTION",-99);
        if(songspos!=-99)
        {
            MyOrder=songspos;
            remsongpos=MyOrder;
        }

        Pause_order=intent.getIntExtra("password_PAUSEPLAY",-99);
        if(Pause_order!=-99)MyOrder=Pause_order;
//一个name只能出现在一个地方否则会出错

        Continue_order=intent.getIntExtra("password_CONTINUEPLAY",-99);
        if(Continue_order!=-99)MyOrder=Continue_order;

        Last_order=intent.getIntExtra("password_PLAYLAST",-99);
        if(Last_order!=-99)MyOrder=Last_order;

        Next_order=intent.getIntExtra("password_PLAYNEXT",-99);
        if(Next_order!=-99)MyOrder=Next_order;


        PlayMode_order=intent.getIntExtra("password_PLAYMODE",-99);
        if(PlayMode_order!=-99)MyOrder=PlayMode_order;

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        switch (MyOrder)
        {
            case 12138:
                if(mediaPlayer.isPlaying()&&ifMediaPlayerIsNew)
                {
                    mediaPlayer.pause();
                    isIfMediaPlayerIsPause=true;
                }
                break;
            case 13145:
                if(ifMediaPlayerIsNew&&ifStartPlayMusic&&!mediaPlayer.isPlaying())
                {
                    mediaPlayer.start();
                    isIfMediaPlayerIsPause=false;
                }
                break;
            case 1391234:
                PlayMode=2;
                break;
            case 1392345:
                PlayMode=3;
                break;
            case 1393456:
                PlayMode=4;
                break;
            case 1396789:
                PlayMode=1;
                break;
//            PLAYRANDOM=1391234,
//                    PLAYONELOOP=1392345,
//                    PLAYLOOP=1393456,
//                    PLAYWITHLIST=1396789;
            case 66666:
                if(mediaPlayer.isPlaying())
                {
                    try {
                        mediaPlayer.stop();
                        mediaPlayer=new MediaPlayer();
                        remsongpos-=1;
//                        播放上一首歌防止数据转变为负数，设置成循环
                        if(remsongpos<0)
                        {
                            remsongpos=arrayList_inservice_songsdate.size()-1;
                        }
                        mediaPlayer.setDataSource(arrayList_inservice_songsdate.get(remsongpos));
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 23333:
                if(mediaPlayer.isPlaying())
                {
                    try {
                        mediaPlayer.stop();
                        mediaPlayer=new MediaPlayer();
                        remsongpos+=1;
//                        播放下一首歌防止越界
                        if(remsongpos>arrayList_inservice_songsdate.size()-1)
                        {
                            remsongpos=0;
                        }
                        mediaPlayer.setDataSource(arrayList_inservice_songsdate.get(remsongpos));
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case -100:
                break;
            default:
                try {
                    if(mediaPlayer.isPlaying())mediaPlayer.stop();

                    mediaPlayer=new MediaPlayer();
                    mediaPlayer.setDataSource(arrayList_inservice_songsdate.get(MyOrder));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    isIfMediaPlayerIsPause=false;
                    ifStartPlayMusic=true;
                    ifMediaPlayerIsNew=true;


                    switch (PlayMode)
                    {
                        case 1:
                           /* if(!isIfMediaPlayerIsPause&&!mediaPlayer.isPlaying())
                            {
                                mediaPlayer=new MediaPlayer();
                                mediaPlayer.setDataSource(arrayList_inservice_songsdate.get(++remsongpos));
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                                isIfMediaPlayerIsPause=false;
                            }*/
//                           切换播放模式，目前还没有实现
                            break;
                        case 2:
                            break;
                        case 3:
                            mediaPlayer.setLooping(true);
//                            单曲循环
                            break;
                        case 4:
                            break;
                    }
           } catch (IOException e) {
               e.printStackTrace();
           } break;

        }
    }
}