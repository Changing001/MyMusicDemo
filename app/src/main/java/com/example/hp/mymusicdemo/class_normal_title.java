package com.example.hp.mymusicdemo;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by HP on 2018/1/16.
 */

public class class_normal_title extends LinearLayout {

    private TextView textView_titletext;
    private Button btn_back;
    private Button btn_menu;
    private Intent intent;

    public class_normal_title(Context context) {
        super(context);
    }

    public class_normal_title(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_normal_title,
                this,true);
      inlt();

        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(intent);
            }
        });
    }

    public void getIntent(Intent intent)
    {
        this.intent=intent;
    }

    public void inlt() {
        textView_titletext=(TextView)findViewById(R.id.textView_titletext);
        btn_back=(Button)findViewById(R.id.btn_back);
        btn_menu=(Button)findViewById(R.id.btn_menu);
    }
    public void setTextView_titletext(String text)
    {
        textView_titletext.setText(text);
    }
    public String getTextView_titletext()
    {
        return this.textView_titletext.getText().toString();
    }
    public void setBtn_back(int image)
    {
        this.btn_back.setBackgroundResource(image);
    }
    public void setBtn_menu(int image)
    {
        this.btn_menu.setBackgroundResource(image);
    }

}