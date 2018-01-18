package com.example.hp.mymusicdemo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

//已下载的歌曲
public class activity_mysongs_havedownload extends AppCompatActivity {

    private static int GIVEDATES=1234567;

    private SharedPreferences sharedPreferences;
    private Uri mediaUri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

    private ArrayList<class_songs_all_date> arrayList_myallsongsdate=new ArrayList<>();
    private class_songs_all_date mysong=new class_songs_all_date();
    private ArrayList<String>arrayList_allsongspath=new ArrayList<>();

    private myMusicMessageAdapter myMusicMessageAdapter_myadapter;

    private class_image_and_text_button_hor spebtn1;
    private class_image_and_text_button_hor spebtn2;
    private ListView listView_allmysongs;


    private TextView textview_title;
    private Button btn_back;
    private Button btn_menu;

    private Intent intent;



    private SQLiteDatabase db;  //    数据库对象
    private MyDBOpenHelper myDBHelper;
    private StringBuilder sb;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysongs_havedownload);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        mContext=activity_mysongs_havedownload.this;
        myDBHelper = new MyDBOpenHelper(mContext, "my.db", null, 1);//测绘数据库


        inlt();


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.setClass(activity_mysongs_havedownload.this,
                        MainActivity.class);
              startActivity(intent);
            }
        });
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity_mysongs_havedownload.this,
                        "显示菜单", Toast.LENGTH_SHORT).show();
            }
        });
        getUriData(mediaUri);//获得音乐文件
        setAllKindsDates();//  将数据分配至不同的容器
        myMusicMessageAdapter_myadapter = new myMusicMessageAdapter(arrayList_myallsongsdate);
        listView_allmysongs.setAdapter(myMusicMessageAdapter_myadapter);
        sharedPreferences=getSharedPreferences("password_JUDGEMENTIFPUTDATE",Activity.MODE_PRIVATE);
        intent = new Intent(activity_mysongs_havedownload.this,
                  service_contorlmusicplay.class);
        intent.putExtra("password_ifGiveDate",GIVEDATES);
        intent.putExtra("password_NOTHING",13141516);
        intent.putStringArrayListExtra("password_ALLSONGPATH", arrayList_allsongspath);
        startService(intent);


//经过测试得知每次跳转界面都会初始化所有数据，浪费内存，且导致命令重读，个人认为需要使用数据库存储
    }


    private void inlt(){
        btn_back=(Button)findViewById(R.id.btn_back);
        btn_menu=(Button)findViewById(R.id.btn_menu);
        listView_allmysongs = (ListView) findViewById(R.id.listview_myallsongs);
        spebtn1 = (class_image_and_text_button_hor) findViewById(R.id.myview_playcontrol_myallsongs);
        spebtn1.setImageResource(R.drawable.playmusic);
        spebtn1.setTextViewText("全部播放");
        spebtn2 = (class_image_and_text_button_hor) findViewById(R.id.myview_playcontrol2_myallsongs);
        spebtn2.setImageResource(R.drawable.manager);
        spebtn2.setTextViewText("管理");
    }

    private void setAllKindsDates() {
        for(int i=0;i<arrayList_myallsongsdate.size();i++)
        {
            arrayList_allsongspath.add(arrayList_myallsongsdate.get(i).getPath());
        }
    }

    private void getUriData(Uri uri){

        arrayList_myallsongsdate=new ArrayList<>();

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

            arrayList_myallsongsdate.add(mysong);
//            System.out.println("_data = "+cursor.getString(cursor.getColumnIndex("_data")));
//            System.out.println("_display_name = "+cursor.getString(cursor.getColumnIndex("_display_name")));
//            System.out.println("_size = "+cursor.getString(cursor.getColumnIndex("_size")));
//            System.out.println("mime_type = "+cursor.getString(cursor.getColumnIndex("mime_type")));
//            System.out.println("title = "+cursor.getString(cursor.getColumnIndex("title")));
//            System.out.println("duration = "+cursor.getString(cursor.getColumnIndex("duration")));
        } while (cursor.moveToNext());
    }

    public class myMusicMessageAdapter extends BaseAdapter {

        ArrayList<class_songs_all_date> songlist=new ArrayList<class_songs_all_date>();

        public myMusicMessageAdapter(ArrayList<class_songs_all_date>songlist)
        {
            this.songlist=songlist;
        }
        @Override
        public Object getItem(int position)
        {
            // 获取每一项的数据
            return songlist.get(position);
        }
        @Override
        public long getItemId(int position)
        {
            // 获取某项对应的内部 ID, 如果不需要，则直接把 position 作为 ID
            return position;
        }
        @Override
        public int getCount() {
            return songlist.size();
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null)
            {
                convertView = getLayoutInflater()
                        .inflate(R.layout.layout_musiclist_messages, parent, false);
            }
//获取小界面 的布局文件实现适配器使用

            class_songs_all_date TheSongMessage=(class_songs_all_date)getItem(position);

            CircleImageView singerhead=(CircleImageView)convertView.findViewById(R.id.singerhead);
            final class_insonglist_message_twotextview musicmessage=
                    (class_insonglist_message_twotextview)
                    convertView.findViewById(R.id.myView_musicmessage);
            Button button_more=(Button)convertView.findViewById(R.id.btnmoreinlist);


            musicmessage.setTextView_songname(TheSongMessage.getName());
            musicmessage.setImageView_ifdownload(R.drawable.havedownloadicon2);
            singerhead.setImageResource(R.drawable.hugh);
            button_more.setBackgroundResource(R.drawable.moreicon);

//            listview中含有按钮是需要些在适配器中
//            ，在外部使用item的点击监听事件无法响应 且在适配器 内也可以进行startservice

            button_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog;
                    new AlertDialog.Builder(activity_mysongs_havedownload.this)
                            .setTitle("您的操作是？")
                   .setIcon(R.drawable.hugh)
                            .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(activity_mysongs_havedownload.this,
                                            "删除成功", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("下一首播放", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(activity_mysongs_havedownload.this,
                                            "修改成功", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setPositiveButton("添加到我喜欢", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(activity_mysongs_havedownload.this,
                                            "添加成功", Toast.LENGTH_SHORT).show();




                                    db = myDBHelper.getWritableDatabase();
                                    ContentValues values1 = new ContentValues();
                                    values1.put("name", position);//name指的是内容
                                    db.insert("person", null, values1);


                                }
                            })
                   .show();
                }
            });

            musicmessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(activity_mysongs_havedownload.this,
                            service_contorlmusicplay.class);
                    intent.putExtra("password_SONGPOSTION",position);

                    startService(intent);



                    Intent intent2 = new Intent("myReceiver");
                    intent2.putExtra("password_SINGERNAME",
                            musicmessage.getTextView_singername());
                    intent2.putExtra("password_SONGNAME",
                            musicmessage.getTextView_songname());

                    intent2.putExtra("password_MUSICTIMELONG",
                            arrayList_myallsongsdate.get(position).getTimelong());




                    intent2.putExtra("password_SONGPOSFORCONTROL",position);



                  //  intent2.putExtra("password_SONGPOTION",position);
                    sendBroadcast(intent2);
//                    发送广播使得歌曲信息得以记录，但现在看来根本不必要，以后升级吧










                }
            });

/*            singerhead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(activity_mysongs_havedownload.this,activity_controlmusicplay.class);
                    startActivity(intent);
                }
            });
            未来期望是做成点击头像，播放一个动画将头像放大的效果。。。*/

//            System.out.println(str.substring(str.lastIndexOf(" ") + 1));提取子字符串用于歌手名字


            /*注意歌手名字中可能带有括号以及空格，
             但并不绝对（至少我的手机是这样），
             所以如果取出子字符串如果没有这个字符的话会导致错误，
             所以需要事先判断*/


            //通过算法实现获取歌手的名字，防止特殊崩溃情况，改用全部名称（包括歌曲）
            String remsinger=TheSongMessage.getSinger();
            int rem_,remkuohao;
            rem_=remsinger.indexOf(" ");
            remkuohao=remsinger.indexOf("-");
            int min=rem_<remkuohao?rem_:remkuohao;
            if(min>0)
            { musicmessage.setTextView_singername(TheSongMessage.getSinger().substring(0,min));
//                textView_singername.setText(TheSongMessage.getSinger().substring(0,min));
            }
            else
            {
                musicmessage.setTextView_singername(TheSongMessage.getSinger());
               // textView_singername.setText(TheSongMessage.getSinger());
            }
            return convertView;
        }
    }
}