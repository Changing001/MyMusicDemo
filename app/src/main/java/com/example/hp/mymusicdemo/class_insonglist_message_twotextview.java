package com.example.hp.mymusicdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.zip.Inflater;

/**
 * Created by HP on 2018/1/9.
 */

public class class_insonglist_message_twotextview extends LinearLayout {
    private TextView textView_songname;
    private TextView  textView_singername;
    private ImageView imageView_ifdownload;
    public class_insonglist_message_twotextview(Context context) {
        super(context);
    }

    public class_insonglist_message_twotextview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_insonglist_message, this,
                true);
      inlt();
    }

    public void inlt() {
        textView_songname=(TextView)findViewById(R.id.songname);
        textView_singername=(TextView)findViewById(R.id.singername);
        imageView_ifdownload=(ImageView) findViewById(R.id.ifdownload);
    }

    public  void setTextView_songname(String songname)
   {
       textView_songname.setText(songname);
   }
    public void setTextView_singername(String singername) {
       textView_singername.setText(singername);
   }
    public void setImageView_ifdownload(int id)
   {
       imageView_ifdownload.setImageResource(id);
   }
    public  String getTextView_songname()
   {
       return textView_songname.getText().toString();
   }
    public String getTextView_singername()
   {
       return textView_singername.getText().toString();
   }
}
