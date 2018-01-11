package com.example.hp.mymusicdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//已下载的歌曲
public class activity_mysongs_havedownload extends AppCompatActivity {

    class_image_and_text_button_hor spebtn1,spebtn2;
    ListView listView_allmysongs;
    ArrayList<class_songs_all_date> arrayList_myallsongsdate=new ArrayList<>();

    ArrayList<String>arrayList_allsongspath=new ArrayList<>();

    private Uri mediaUri=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//    private Uri mediaUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;//获取音乐文件

    private myMusicMessageAdapter myMusicMessageAdapter_myadapter;

    private class_songs_all_date mysong=new class_songs_all_date();

    private static int GIVEDATES=1234567;

    private int judgement_ifgivedate=0;


    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysongs_havedownload);
        if (getSupportActionBar() != null) getSupportActionBar().hide();


        listView_allmysongs = (ListView) findViewById(R.id.listview_myallsongs);

        spebtn1 = (class_image_and_text_button_hor) findViewById(R.id.myview_playcontrol_myallsongs);
        spebtn1.setImageResource(R.drawable.playmusic);
        spebtn1.setTextViewText("全部播放");

        spebtn2 = (class_image_and_text_button_hor) findViewById(R.id.myview_playcontrol2_myallsongs);
        spebtn2.setImageResource(R.drawable.manager);
        spebtn2.setTextViewText("管理");



          getUriData(mediaUri);//获得音乐文件

          setAllKindsDates();
//        将数据分配至不同的容器

          myMusicMessageAdapter_myadapter = new myMusicMessageAdapter(arrayList_myallsongsdate);
/////
          listView_allmysongs.setAdapter(myMusicMessageAdapter_myadapter);


          sharedPreferences=getSharedPreferences("password_JUDGEMENTIFPUTDATE",Activity.MODE_PRIVATE);

          if(sharedPreferences.getInt("password_IFHAVEGIVEDATE",-100)!=100)
        {
          Intent intent = new Intent(activity_mysongs_havedownload.this, service_contorlmusicplay.class);
          intent.putExtra("password_ifGiveDate",GIVEDATES);
          intent.putStringArrayListExtra("password_ALLSONGPATH", arrayList_allsongspath);
          startService(intent);
        }
    }

    private void setAllKindsDates()
    {
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

            mysong.setTimelong(cursor.getString(cursor.getColumnIndex("_size")));

//测试代码获得专辑图片
//            Uri selectedAudio = Uri.parse(url);
//            MediaMetadataRetriever myRetriever = new MediaMetadataRetriever();
//            myRetriever.setDataSource(context, selectedAudio); // the URI of audio file
//            byte[] artwork;
//
//            artwork = myRetriever.getEmbeddedPicture();
//
//            if (artwork != null) {
//                Bitmap bMap = BitmapFactory.decodeByteArray(artwork, 0, artwork.length);
//                ivPic.setImageBitmap(bMap);
//
//                return bMap;
//            } else {
//                ivPic.setImageResource(R.drawable.defult_music);
//                return BitmapFactory.decodeResource(context.getResources(), R.drawable.defult_music);
//            }
//        }
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

            class_songs_all_date TheSongMessage=(class_songs_all_date)getItem(position);


            CircleImageView singerhead=(CircleImageView)convertView.findViewById(R.id.singerhead);

            final class_insonglist_message_twotextview musicmessage=(class_insonglist_message_twotextview)
                    convertView.findViewById(R.id.myView_musicmessage);


            musicmessage.setTextView_songname(TheSongMessage.getName());

            musicmessage.setImageView_ifdownload(R.drawable.havedownloadicon2);


            Button button_more=(Button)convertView.findViewById(R.id.btnmoreinlist);


            singerhead.setImageResource(R.drawable.hugh);

            button_more.setBackgroundResource(R.drawable.moreicon);

//            listview中含有按钮是需要些在适配器中
//            ，在外部使用item的点击监听事件无法响应 且在适配器 内也可以进行startservice

            button_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog;
                    new AlertDialog.Builder(activity_mysongs_havedownload.this)
                            .setTitle("弹出栏")
                    .setTitle("HAILHYDRa")
                   .setIcon(R.drawable.hugh)
                   .show();
                }
            });

            musicmessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(activity_mysongs_havedownload.this,
                            service_contorlmusicplay.class);
                    intent.putExtra("password_SONGPOSTION",position);
                    /////////
                    intent.putExtra("password_MUSICSIZE",
                            arrayList_myallsongsdate.get(position).getTimelong());
                    //////////
                    startService(intent);
                    Intent intent2 = new Intent("myReceiver");
                    intent2.putExtra("password_SINGERNAME",
                            musicmessage.getTextView_singername());
                    intent2.putExtra("password_SONGNAME",
                            musicmessage.getTextView_songname());
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