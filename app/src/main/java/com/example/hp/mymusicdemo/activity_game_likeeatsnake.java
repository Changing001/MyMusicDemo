package com.example.hp.mymusicdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class activity_game_likeeatsnake extends AppCompatActivity {
    game_snakeView game;//游戏View，自定义设计
    Button butstart;//按钮可控制暂停，开始、、重生
    TextView textgrade,textjudge;//两个组件显示关键数据
    private boolean ifstop=false;//判断是否暂停
    int rembut=1;//调节按钮背景图
    int remgrade;//显示吃的食物
    String stringgrade;//显示吃的食物数//字符串类型才可以显示
    private Handler mHandler = new Handler() { //thread handler 消息处理
        @Override
        public void handleMessage(Message msg) {//消息传递机制
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            if(msg.arg1 == 1) {
                if(game != null) {
                    game.invalidate();//刷新界面
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getSupportActionBar() != null) {//去掉标题栏
            //因为屏幕标题的原因导致绝对分辨率与相对分辨率存在差异，去掉标题就可以了
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_game_likeeatsnake);
        butstart=(Button)findViewById(R.id.butstart);
        textgrade=(TextView)findViewById(R.id.grade);
        textjudge=(TextView)findViewById(R.id.judge);
        game=findViewById(R.id.game);



        Toast.makeText(this, "made by Changing,略略略略略", Toast.LENGTH_SHORT).show();


        DisplayMetrics dm=new DisplayMetrics();//获取屏幕数据分辨率
        super.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels,height=dm.heightPixels;


        game.getnum(height,width);//传递数据给游戏view




        butstart.setBackgroundResource(R.drawable.stop);








        butstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(game.ifhasdead()==true)//点击一下转变状态和背景图、、如果蛇死了连续点击两次则重新开始
                {
                    game.setsnake();
                    remgrade=0;
                    textjudge.setText("活");
                }
                if(rembut==1)
                {
                    ifstop=true;
                    butstart.setBackgroundResource(R.drawable.start1);
                    rembut=2;
                }
                else
                {
                    ifstop=false;
                    butstart.setBackgroundResource(R.drawable.stop);
                    rembut=1;
                }
            }
        });
//        game.getnum(height,width);//得到屏幕数据
        game.creatsnake();//创造蛇
        game.setdirect(1);//设置蛇的移动方向

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    Message msg = mHandler.obtainMessage();//消息处理机制
                    msg.arg1 = 1;
                    mHandler.sendMessage(msg);
                    try {//必须要用try catch 不知道为啥，否则就有错
                        if(ifstop==false)
                        {
                            game.engine();//未暂停时游戏引擎运行
                        }
                        if(game.ifeat()==true)//如果蛇吃到了食物则得到数据，并刷新显示他的组件
                        {
                            remgrade=game.givegrade();
                            stringgrade=String.valueOf(remgrade);
                            try {//为啥这里要用try catch我不知道
                                textgrade.setText(stringgrade);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            game.setifeatfood();//恢复原来的状态，进行下一次捕食
                        }
                        if(game.ifhasdead()==true)//蛇死了就刷新显示的组件
                        {
                            try {//为啥这里要用try catch我不知道
                                textjudge.setText("死");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        if(remgrade<=5)//根据得分调整蛇的移动速度
                        {
                            Thread.sleep(300);
                        }
                        else if(remgrade>=5&&remgrade<=10)
                        {
                            Thread.sleep(200);
                        }
                        else if(remgrade>10&&remgrade<=20)
                        {
                            Thread.sleep(100);
                        }
                        else
                        {
                            Thread.sleep(50);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();//开启线程
    }
}