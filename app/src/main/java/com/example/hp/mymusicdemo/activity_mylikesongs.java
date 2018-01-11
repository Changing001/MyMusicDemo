package com.example.hp.mymusicdemo;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

//我喜欢的歌曲
public class activity_mylikesongs extends AppCompatActivity {

    class_image_and_text_button_hor mannagerlikesongs1, mannagerlikesongs2, mannagerlikesongs3;
    ListView listview_mylikesongs;
    ArrayList<class_songs_all_date> arrayList_mylikesongs_date = new ArrayList<>();

    MyLikeSongsAdapter myLikeSongsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylikesongs);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

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


//        从数据库中取出数据加载至界面


        myLikeSongsAdapter=new MyLikeSongsAdapter(arrayList_mylikesongs_date);
        listview_mylikesongs.setAdapter(myLikeSongsAdapter);


//        添加数据，无数据时也测试成功

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
            final class_insonglist_message_twotextview musicmessage = (class_insonglist_message_twotextview)
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
                            .setTitle("弹出栏")
                            .setTitle("test测试")
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


                    /////////
                    intent.putExtra("password_MUSICSIZE",
                            songlist.get(position).getTimelong());
                    //////////

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


            /*注意歌手名字中可能带有括号以及空格，
但并不绝对（至少我的手机是这样），
所以如果取出子字符串如果没有这个字符的话会导致错误，
所以需要事先判断*/

            String remsinger = TheSongMessage.getSinger();
            int rem_, remkuohao;
            rem_ = remsinger.indexOf(" ");
            remkuohao = remsinger.indexOf("-");
            int min = rem_ < remkuohao ? rem_ : remkuohao;
            if (min > 0) {
                musicmessage.setTextView_singername(TheSongMessage.getSinger().substring(0, min));
//                textView_singername.setText(TheSongMessage.getSinger().substring(0,min));
            } else {
                musicmessage.setTextView_singername(TheSongMessage.getSinger());
                // textView_singername.setText(TheSongMessage.getSinger());
            }
            return convertView;
        }
    }
}