package com.example.hp.mymusicdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;

public class activity_morethings extends AppCompatActivity {

    private Button btn_back;
    private Button btn_menu;
    private TextView textView_title;
    private class_image_and_text_button spebtn_more_1;
    private class_image_and_text_button spebtn_more_2;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_morethings);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        btn_back=findViewById(R.id.btn_back);
        btn_menu=findViewById(R.id.btn_menu);
        textView_title=findViewById(R.id.textView_titletext);
        spebtn_more_1=findViewById(R.id.spebtn_more_1);
        spebtn_more_1.setTextViewText("狂踩黑块");
        spebtn_more_1.setImageResource(R.drawable.more_dontstep);
        spebtn_more_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(activity_morethings.this,
                        activity_dont_step_white.class);
                intent.setFlags(FLAG_ACTIVITY_NO_HISTORY);//设置跳转不保存上一个activity
                startActivity(intent);
            }
        });
        spebtn_more_2=findViewById(R.id.spebtn_more_2);
        spebtn_more_2.setTextViewText("贪吃蛇");
        spebtn_more_2.setImageResource(R.drawable.game_likeeatsnake);
        spebtn_more_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent=new Intent(activity_morethings.this,
                        activity_game_likeeatsnake.class);
                intent.setFlags(FLAG_ACTIVITY_NO_HISTORY);//设置跳转不保存上一个activity
                startActivity(intent);
            }
        });

    }
}
