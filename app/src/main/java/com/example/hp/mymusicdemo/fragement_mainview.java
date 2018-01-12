package com.example.hp.mymusicdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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

        return view;
    }
}


