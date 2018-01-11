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

        LayoutInflater.from(context).inflate(R.layout.layout_controlactivity_title,this,
                true);
        btn1=(Button)findViewById(R.id.btnback);
        btn2=(Button)findViewById(R.id.btnmenu);
        textView=(TextView)findViewById(R.id.textviewtitle);

        textView.setText(title);
    }

}
