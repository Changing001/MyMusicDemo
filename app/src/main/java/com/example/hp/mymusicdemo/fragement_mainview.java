package com.example.hp.mymusicdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by HP on 2018/1/9.
 */

public class fragement_mainview extends Fragment {

    private class_image_and_text_button spebtn1;
    private class_image_and_text_button spebtn2;
    private class_image_and_text_button spebtn3;
    private class_image_and_text_button spebtn4;
    private class_image_and_text_button spebtn5;
    private class_image_and_text_button spebtn6;



    private class_myselftv_inmainview myself_one_inmainview;
    private class_myselftv_inmainview myself_two_inmainview;


//    private ListView listView_mysonglist_inmainview;
//
//    //private MyAdapter_listview_songlist_inmainview myadapter_songlist;
//
//    private ArrayList<class_listitem_message>arrayList_songlists_messages=new ArrayList<>();
//
//    private class_listitem_message message_test;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view= inflater.inflate(R.layout.layout_fragement_mainview, container, false);

        spebtn1=(class_image_and_text_button)view.findViewById(R.id.bu1);
        spebtn1.setImageResource(R.drawable.llike);
        spebtn1.setTextViewText("我喜欢");

        spebtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(container.getContext(),activity_mylikesongs.class);
//                从装载它的容器中找到环境，从而实现跳转
                startActivity(intent);
            }
        });

        spebtn2=(class_image_and_text_button)view.findViewById(R.id.bu2);
        spebtn2.setImageResource(R.drawable.download);
        spebtn2.setTextViewText("下载歌曲");

        spebtn3=(class_image_and_text_button)view.findViewById(R.id.bu3);
        spebtn3.setImageResource(R.drawable.recentplay);
        spebtn3.setTextViewText("最近播放");

        spebtn4=(class_image_and_text_button)view.findViewById(R.id.bu4);
        spebtn4.setImageResource(R.drawable.music);
        spebtn4.setTextViewText("本地歌曲");

        spebtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(container.getContext(),activity_mysongs_havedownload.class);
                startActivity(intent);
            }
        });

        spebtn5=(class_image_and_text_button)view.findViewById(R.id.bu5);
        spebtn5.setImageResource(R.drawable.songlist);
        spebtn5.setTextViewText("歌单");

        spebtn6=(class_image_and_text_button)view.findViewById(R.id.bu6);
        spebtn6.setImageResource(R.drawable.moremunu);
        spebtn6.setTextViewText("更多");

        myself_one_inmainview =(class_myselftv_inmainview)view.findViewById(R.id.myselftv);
        myself_one_inmainview.setTextView_up("个性电台");
        myself_one_inmainview.setTextView_down("阿瓦达基金大");
        myself_one_inmainview.setImageView_head(R.drawable.hugh);

        myself_two_inmainview=(class_myselftv_inmainview)view.findViewById(R.id.myselftv2);
        myself_two_inmainview.setTextView_up("正在开发");
        myself_two_inmainview.setTextView_down("我证明他确实正在开发");
        myself_two_inmainview.setImageView_head(R.drawable.hugh);
        myself_two_inmainview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "hail hydra", Toast.LENGTH_SHORT).show();
            }
        });


//        message_test=new class_listitem_message();
//        message_test.setUp("歌单1");
//        message_test.setDown("真的是歌单啊");
//        message_test.setImage(R.drawable.hugh);
//        message_test.setImage2(R.drawable.havedownloadicon2);
//        message_test.setImage3(R.drawable.ic_more_inmainview);
//        arrayList_songlists_messages.add(message_test);
//
//        message_test=new class_listitem_message();
//        message_test.setUp("歌单2");
//        message_test.setDown("真的是歌单2啊");
//        message_test.setImage(R.drawable.hugh);
//        message_test.setImage2(R.drawable.havedownloadicon2);
//        message_test.setImage3(R.drawable.ic_more_inmainview);
//        arrayList_songlists_messages.add(message_test);
//
//        listView_mysonglist_inmainview=(ListView)view.findViewById(R.id.listview_inlayout_inmainview);
//        myadapter_songlist=new MyAdapter_listview_songlist_inmainview(arrayList_songlists_messages);
//
//        listView_mysonglist_inmainview.setAdapter(myadapter_songlist);

        return view;
    }


    /**
     * mainview 歌单的适配器，但存在问题，目前猜测是scrollview与listview的兼容问题
     * 记录一下
     */
  /*  public class MyAdapter_listview_songlist_inmainview extends BaseAdapter
    {
        ArrayList<class_listitem_message> messages_copy =new ArrayList<>();

        public MyAdapter_listview_songlist_inmainview(ArrayList<class_listitem_message>messages)
        {
            this.messages_copy =messages;
        }
        @Override
        public int getCount() {
            return messages_copy.size();
        }

        @Override
        public Object getItem(int position) {
            return messages_copy.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
            {
                convertView = getLayoutInflater()
                        .inflate(R.layout.layout_item_in2list_inmainview, parent, false);
            }

            ImageView imageView=(ImageView)convertView.findViewById(R.id.imageView_icon);
            ImageView imageView1=(ImageView)convertView.findViewById(R.id.imageView_ifdownload);
            ImageView imageView2=(ImageView)convertView.findViewById(R.id.imageView_more);
            TextView textView_one=(TextView)convertView.findViewById(R.id.textView_name);
            TextView textView_two=(TextView)convertView.findViewById(R.id.textview_more);

//            class_songs_all_date TheSongMessage=(class_songs_all_date)getItem(position);
            class_listitem_message message=(class_listitem_message)getItem(position);

            textView_one.setText(message.getUp());
            textView_two.setText(message.getDown());
            imageView.setImageResource(message.getImage());
            imageView1.setImageResource(message.getImage2());
            imageView2.setImageResource(message.getImage3());

            return convertView;
        }
    }
*/



}


