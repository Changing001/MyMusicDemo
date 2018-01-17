package com.example.hp.mymusicdemo;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by HP on 2018/1/15.
 */

public class class_myselftv_inmainview extends LinearLayout {
    private TextView textView_up;
    private  TextView textView_down;
    private ImageView imageView_head;


    public class_myselftv_inmainview(Context context) {
        super(context);
    }

    /**
     * 注意获取界面进行操作的类，初始化必须在不是单个环境变量的方法中
     * @param context
     * @param attrs
     */
    public class_myselftv_inmainview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_item_in1list_inmainview,this,
                true);

        inlt();

    }

    public void inlt() {
        textView_up=(TextView)findViewById(R.id.textView_listname);
        textView_down=(TextView)findViewById(R.id.textview_more);
        imageView_head=(ImageView)findViewById(R.id.imageView_head);
    }
    public void setTextView_up(String text)
    {
        textView_up.setText(text);
    }
    public void setTextView_down(String text)
    {
        textView_down.setText(text);
    }
    public void setImageView_head(int image)
    {
        imageView_head.setImageResource(image);
    }
}
