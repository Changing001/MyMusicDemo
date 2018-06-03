package com.example.hp.mymusicdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

//我喜欢的歌曲
public class activity_mylikesongs extends AppCompatActivity {

    private class_image_and_text_button_hor mannagerlikesongs1;
    private class_image_and_text_button_hor mannagerlikesongs2;
    private class_image_and_text_button_hor mannagerlikesongs3;
    private ListView listview_mylikesongs;

    private MyLikeSongsAdapter myLikeSongsAdapter;

    private TextView textView_title;
    private Button btn_back;
    private Button btn_menu;

    private Intent intent;

    private ArrayList<class_songs_all_date> arrayList_mylikesongs_date = new ArrayList<>();
    private ArrayList<class_songs_all_date>arrayList_songdate=new ArrayList<>();



    private class_songs_all_date mysong=new class_songs_all_date();
    private Uri mediaUri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;



    private Context mContext;
    private SQLiteDatabase db;//    数据库对象

    private MyDBOpenHelper myDBHelper;

    private StringBuilder sb;


    private ArrayList arrayList_mylikesongs_num=new ArrayList();//存储我喜欢的歌曲的下标容器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylikesongs);
        if (getSupportActionBar() != null) getSupportActionBar().hide();


        inlt();
        getUriData(mediaUri);
        mContext=activity_mylikesongs.this;
        myDBHelper = new MyDBOpenHelper(mContext, "my.db", null, 1);
        db = myDBHelper.getWritableDatabase();//获取可操作性的数据库文件
        arrayList_mylikesongs_date=new ArrayList<>();



        sb = new StringBuilder();
        Cursor cursor = db.query("person", null, null,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            do {

                int pid = cursor.getInt(cursor.getColumnIndex("personid"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                sb.append("id：" + pid + "：" + name + "\n");

                int songpos = cursor.getInt(cursor.getColumnIndex("name"));
//                添加我喜欢列表到本地


                /**
                 * 从所有音乐文件中获取喜欢列表，以及从自己的数据库中获取下标列表以备用
                 */
                arrayList_mylikesongs_num.add(songpos);
               arrayList_mylikesongs_date.add(arrayList_songdate.get(songpos));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        Toast.makeText(mContext, sb.toString(), Toast.LENGTH_SHORT).show();

//  从数据库中取出数据加载至界面//添加数据，无数据时也测试成功
        myLikeSongsAdapter=new MyLikeSongsAdapter(arrayList_mylikesongs_date);
        listview_mylikesongs.setAdapter(myLikeSongsAdapter);




        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(activity_mylikesongs.this,MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });
    }
    public void inlt() {
        textView_title=(TextView)findViewById(R.id.textView_titletext);
        textView_title.setText("我喜欢");
        btn_back=(Button)findViewById(R.id.btn_back);
        btn_menu=(Button)findViewById(R.id.btn_menu);
        mannagerlikesongs1 = (class_image_and_text_button_hor) findViewById(R.id.playcontrol);
        mannagerlikesongs1.setImageResource(R.drawable.playmusic);
        mannagerlikesongs1.setTextViewText("全部播放");
        mannagerlikesongs2 = (class_image_and_text_button_hor) findViewById(R.id.playcontro2);
        mannagerlikesongs2.setImageResource(R.drawable.download);
        mannagerlikesongs2.setTextViewText("下载");
        mannagerlikesongs3 = (class_image_and_text_button_hor) findViewById(R.id.playcontro3);
        mannagerlikesongs3.setImageResource(R.drawable.manager);
        mannagerlikesongs3.setTextViewText("管理");
        listview_mylikesongs = (ListView) findViewById(R.id.listview_mylikesongs);
    }

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

    public class MyLikeSongsAdapter extends BaseAdapter {

        ArrayList<class_songs_all_date> songlist = new ArrayList<class_songs_all_date>();

        public MyLikeSongsAdapter(ArrayList<class_songs_all_date> songlist) {
            this.songlist = songlist;
        }

        @Override
        public Object getItem(int position) {
            // 获取每一项的数据
            return songlist.get(position);
        }

        @Override
        public long getItemId(int position) {
            // 获取某项对应的内部 ID, 如果不需要，则直接把 position 作为 ID
            return position;
        }

        @Override
        public int getCount() {
            return songlist.size();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater()
                        .inflate(R.layout.layout_musiclist_messages, parent, false);
            }

            class_songs_all_date TheSongMessage = (class_songs_all_date) getItem(position);
            CircleImageView singerhead = (CircleImageView) convertView.findViewById(R.id.singerhead);
            final class_insonglist_message_twotextview musicmessage =
                    (class_insonglist_message_twotextview)
                            convertView.findViewById(R.id.myView_musicmessage);
            musicmessage.setTextView_songname(TheSongMessage.getName());
            musicmessage.setImageView_ifdownload(R.drawable.havedownloadicon2);
            Button button_more = (Button) convertView.findViewById(R.id.btnmoreinlist);
            singerhead.setImageResource(R.drawable.hugh);
            button_more.setBackgroundResource(R.drawable.moreicon);
//            listview中含有按钮是需要些在适配器中
//            ，在外部使用item的点击监听事件无法响应 且在适配器 内也可以进行startservice

            button_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog;
                    new AlertDialog.Builder(activity_mylikesongs.this)
                            .setTitle("您的操作是？")
                          .setPositiveButton("从我喜欢中移除",
                                  new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialogInterface, int i) {

                                  /**
                                   * 注意在listview中的position与歌曲列表下标不一样，
                                   * 若是混淆会导致数据库操作无效
                                   * 所以从存储进入在另一个界面再次取出实现
                                   */
                                  String str=String.valueOf( arrayList_mylikesongs_num.get(position));
                                  db = myDBHelper.getWritableDatabase(); //参数依次是表名，以及where条件与约束
                                  db.delete("person", "name = ?",
                                          new String[]{str});
                                  sb = new StringBuilder();
                                  Cursor cursor = db.query("person", null, null,
                                          null, null, null, null);
                                  if (cursor.moveToFirst()) {
                                      do {
                                          int pid = cursor.getInt(cursor.getColumnIndex("personid"));
                                          String name = cursor.getString(cursor.getColumnIndex("name"));
                                          sb.append("id：" + pid + "：" + name + "\n");

                                          int songpos = cursor.getInt(cursor.getColumnIndex("name"));
//                添加我喜欢列表到本地
                                          arrayList_mylikesongs_date.add(arrayList_songdate.get(songpos));
                                      }
                                      while (cursor.moveToNext());
                                  }
                                  cursor.close();
                                  Toast.makeText(mContext, sb.toString(), Toast.LENGTH_SHORT).show();

                                  Toast.makeText(mContext, "移除成功", Toast.LENGTH_SHORT).show();
                              }
                          })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            })
                            .setIcon(R.drawable.hugh)
                            .show();
                }
            });

            musicmessage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity_mylikesongs.this,
                            service_contorlmusicplay.class);
                    intent.putExtra("password_SONGPOSTION", position);

                    intent.putExtra("password_MUSICSIZE",
                            songlist.get(position).getTimelong());

                    startService(intent);

                    Intent intent2 = new Intent("myReceiver");
                    intent2.putExtra("password_SINGERNAME",
                            musicmessage.getTextView_singername());
                    intent2.putExtra("password_SONGNAME",
                            musicmessage.getTextView_songname());
//       期望实现存储取出得出数据
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




            /**注意歌手名字中可能带有括号以及空格，
             * 但并不绝对（至少我的手机是这样），
             *所以如果取出子字符串如果没有这个字符的话会导致错误，
             *所以需要事先判断
             *
             * 小算法实现取出歌手名字
             */
            String remsinger = TheSongMessage.getSinger();
            int rem_, remkuohao;
            rem_ = remsinger.indexOf(" ");
            remkuohao = remsinger.indexOf("-");
            int min = rem_ < remkuohao ? rem_ : remkuohao;
            if (min > 0) {
                musicmessage.setTextView_singername(TheSongMessage.getSinger().substring(0, min));
            } else {
                musicmessage.setTextView_singername(TheSongMessage.getSinger());
            }

            return convertView;
        }
    }
}