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
 * Created by HP on 2018/1/7.
 */

public class class_mainactivity_title extends LinearLayout {
    private Button btn1,btn2;
    private TextView textView1,textView2,textView3;
    private Intent intent;
    public class_mainactivity_title(Context context) {
        super(context);
    }

    public class_mainactivity_title(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        LayoutInflater.from(context).inflate(R.layout.layout_control_musicplay_title,this,
//                true);
        LayoutInflater.from(context).inflate(R.layout.layout_mainactivity_title,this,true);
        textView1=(TextView)findViewById(R.id.my);
        textView2=(TextView)findViewById(R.id.songlist);
        textView3=(TextView)findViewById(R.id.found);
        btn1=(Button)findViewById(R.id.menu);
        btn2=(Button)findViewById(R.id.more);

        btn2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
               context.startActivity(intent);
            }
        });
    }
    void getIntent(Intent intent)
    {
        this.intent=intent;
    }
}
