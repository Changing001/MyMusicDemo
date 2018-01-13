package com.example.hp.mymusicdemo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by HP on 2018/1/9.
 */

public class fragement_musiccage extends Fragment {

    private class_image_and_text_button_hor spebtn1;
    private class_image_and_text_button_hor spebtn2;
    private class_image_and_text_button_hor spebtn3;
    private class_image_and_text_button_hor spebtn4;
    private class_image_and_text_button_hor spebtn5;
    private class_image_and_text_button_hor spebtn6;
    private ImageView imageView_fornow;


    private class_image_and_text_button spebtn7;
    private class_image_and_text_button spebtn8;
    private class_image_and_text_button spebtn9;

    private class_image_and_text_button spebtn10;
    private class_image_and_text_button spebtn11;
    private class_image_and_text_button spebtn12;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view=inflater.inflate(R.layout.layout_fragement_musiccage, container, false);
        spebtn1=(class_image_and_text_button_hor)view.findViewById(R.id.spebtn1);
        spebtn2=(class_image_and_text_button_hor)view.findViewById(R.id.spebtn2);
        spebtn3=(class_image_and_text_button_hor)view.findViewById(R.id.spebtn3);
        spebtn4=(class_image_and_text_button_hor)view.findViewById(R.id.spebtn4);
        spebtn5=(class_image_and_text_button_hor)view.findViewById(R.id.spebtn5);
        spebtn6=(class_image_and_text_button_hor)view.findViewById(R.id.spebtn6);


        spebtn7=(class_image_and_text_button)view.findViewById(R.id.spebtn7);
        spebtn8=(class_image_and_text_button)view.findViewById(R.id.spebtn8);
        spebtn9=(class_image_and_text_button)view.findViewById(R.id.spebtn9);
        spebtn10=(class_image_and_text_button)view.findViewById(R.id.spebtn10);
        spebtn11=(class_image_and_text_button)view.findViewById(R.id.spebtn11);
        spebtn12=(class_image_and_text_button)view.findViewById(R.id.spebtn12);


        spebtn1.setImageResource(R.drawable.ic_singer_incage);
        spebtn1.setTextViewText("流行歌手");
        spebtn2.setImageResource(R.drawable.ic_mingci);
        spebtn2.setTextViewText("热歌排行");
        spebtn3.setImageResource(R.drawable.ic_tv_incage);
        spebtn3.setTextViewText("随便命名");
        spebtn4.setImageResource(R.drawable.ic_cube_incage);
        spebtn4.setTextViewText("分类歌单");
        spebtn5.setImageResource(R.drawable.ic_mv);
        spebtn5.setTextViewText("视频MV");
        spebtn6.setImageResource(R.drawable.ic_cd);
        spebtn6.setTextViewText("数字专辑");


        spebtn7.setTextViewText("每日新歌：请查收！俺叔洗脑神曲已到达");
        spebtn7.setImageResource(R.drawable.hugh);
        spebtn8.setTextViewText("每日新歌：请查收！俺叔洗脑神曲已到达");
        spebtn8.setImageResource(R.drawable.hugh);
        spebtn9.setTextViewText("每日新歌：请查收！俺叔洗脑神曲已到达");
        spebtn9.setImageResource(R.drawable.hugh);

        spebtn10.setTextViewText("每日新歌：请查收！俺叔洗脑神曲已到达");
        spebtn10.setImageResource(R.drawable.hugh);
        spebtn11.setTextViewText("每日新歌：请查收！俺叔洗脑神曲已到达");
        spebtn11.setImageResource(R.drawable.hugh);
        spebtn12.setTextViewText("每日新歌：请查收！俺叔洗脑神曲已到达");
        spebtn12.setImageResource(R.drawable.hugh);
        return view;
    }

}

