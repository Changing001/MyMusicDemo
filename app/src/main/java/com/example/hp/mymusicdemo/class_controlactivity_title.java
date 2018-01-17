package com.example.hp.mymusicdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by HP on 2018/1/6.
 */
//控制播放界面的标题
public class class_controlactivity_title extends LinearLayout {
    private Button btn1,btn2;
    private TextView textView;
    private String title;

    public class_controlactivity_title(Context context) {
        super(context);
    }

    public class_controlactivity_title(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_normal_title,this,
                true);
      inlt();
    }
    public void inlt()
    {
        btn1=(Button)findViewById(R.id.btn_back);
        btn2=(Button)findViewById(R.id.btn_menu);
        textView=(TextView)findViewById(R.id.textview_title);
        textView.setText(title);
    }

}
