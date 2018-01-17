package com.example.hp.mymusicdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by HP on 2018/1/6.
 */
//图标既有图片也有文字的图标
public class class_image_and_text_button extends LinearLayout {
    private ImageView iv;
    private TextView tv;

    public class_image_and_text_button(Context context) {
        super(context);
    }

    public class_image_and_text_button(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_image_and_text_button, this,
                true);
      inlt();
    }

    public void inlt() {
        iv = (ImageView) findViewById(R.id.iv);
        tv = (TextView) findViewById(R.id.te);
    }

    public void setDefaultImageResource(int resId) {
        iv.setImageResource(resId);
    }

    public void setDefaultTextViewText(String text) {
        tv.setText(text);
    }

    public void setImageResource(int resId) {
        iv.setImageResource(resId);
    }

    public void setTextViewText(String text) {
        tv.setText(text);
    }

    public void setTextColor(int color) {
        tv.setTextColor(color);
    }

}