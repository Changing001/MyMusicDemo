package com.example.hp.mymusicdemo;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class service_contorlmusicplay extends Service {

    private MediaPlayer mediaPlayer=new MediaPlayer();
    private ArrayList<String>arrayList_inservice_songsdate=new ArrayList<>();
    private boolean bool_ifGetDate =false;
    private boolean bool_ifStartPlayMusic =false;
    private boolean bool_IfPlayNewSong=false;//    必须要初始化否则老是报错贼坑
    private boolean bool_IfMediaPlayerIsNew =false;
    private boolean bool_IfMediaPlayerIsPause=false;

    private final static int PAUSEPLAY=12138;
    private final static int CONTINUEPLAY=13145;
    private final static int  LASTSONG=66666;
    private final static int  NEXTSONG=23333;
    private final static int PLAYRANDOM=1391234;
    private final static int  PLAYONELOOP=1392345;
    private final static int  PLAYLOOP=1393456;
    private final static int  PLAYWITHLIST=1396789;
    private final static int WRONGORDER=-99;//初始化一些命令使得便于操作，目前我大多用的是传数字表示命令

    private int int_MyOrder=-99;
    private int int_DateOrder=-99;
    private int int_PlayMode =1;
    private int int_music_size;
    private int songspos=-99;
    private int remsongpos=0;//    因为很多重命令都要经过这里，所以需要用一个数记录命令

    public service_contorlmusicplay() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        int int_order=0;//记录外部传来的命令，每次只能点击事件发生一次，
        // 但需要避免其他组件开启service，使用下部的NOTHING命令判断

//        int_music_size =intent.getIntExtra("password_MUSICSIZE",-99);
//        if(int_music_size !=-99) //歌曲的大小，应该是以毫秒记录
//            Toast.makeText(this, "size"+ int_music_size, Toast.LENGTH_SHORT).show();

        int_DateOrder =intent.getIntExtra("password_ifGiveDate",-99);
        if(int_DateOrder !=-99&&!bool_ifGetDate)
        {
            bool_ifGetDate =true;
            arrayList_inservice_songsdate=intent.getStringArrayListExtra("password_ALLSONGPATH");
        }
        

        songspos=intent.getIntExtra("password_SONGPOSTION",-99);
        if(songspos!=-99)
        {
            int_MyOrder =songspos;
            remsongpos= int_MyOrder;
            bool_IfPlayNewSong=true;//            是否播放新歌
        }
        else bool_IfPlayNewSong=false;

        int_order =intent.getIntExtra("password_PAUSEPLAY",-99);
        if(int_order !=-99) int_MyOrder = int_order;//一个name只能出现在一个地方否则会出错

        int_order=intent.getIntExtra("password_CONTINUEPLAY",-99);
        if(int_order!=-99) int_MyOrder =int_order;

        int_order =intent.getIntExtra("password_PLAYLAST",-99);
        if(int_order !=-99) int_MyOrder = int_order;

        int_order=intent.getIntExtra("password_PLAYNEXT",-99);
        if(int_order!=-99) int_MyOrder =int_order;

        int_order =intent.getIntExtra("password_PLAYMODE",-99);
        if(int_order !=-99) int_MyOrder = int_order;

        int_order=intent.getIntExtra("password_NOTHING",-99);
        if(int_order!=-99)int_MyOrder=-99;//判断是否是activity开启来的命令，放置activity开启service
//        时使得播放器反复启动


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        switch (int_MyOrder)
        {
            case PAUSEPLAY:
                if(mediaPlayer.isPlaying()&& bool_IfMediaPlayerIsNew)
                {
                    mediaPlayer.pause();
                    bool_IfMediaPlayerIsPause=true;
                }
                break;
            case CONTINUEPLAY:
                if(bool_IfMediaPlayerIsNew && bool_ifStartPlayMusic &&!mediaPlayer.isPlaying())
                {
                    mediaPlayer.start();
                    bool_IfMediaPlayerIsPause=false;
                }
                break;
            case PLAYRANDOM:
                int_PlayMode =2;
                break;
            case PLAYONELOOP:
                int_PlayMode =3;
                break;
            case PLAYLOOP:
                int_PlayMode =4;
                break;
            case PLAYWITHLIST:
                int_PlayMode =1;
                break;
            case LASTSONG:
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
            case NEXTSONG:
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
            case -99:
                break;
            default:
                try {
                    if(mediaPlayer.isPlaying()&& bool_IfPlayNewSong)
                        mediaPlayer.stop();
                        mediaPlayer=new MediaPlayer();
                        mediaPlayer.setDataSource(arrayList_inservice_songsdate.get(remsongpos));
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        bool_IfMediaPlayerIsPause=false;
                        bool_ifStartPlayMusic =true;
                        bool_IfMediaPlayerIsNew =true;

                    switch (int_PlayMode)
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
           }
           break;
        }
    }
}