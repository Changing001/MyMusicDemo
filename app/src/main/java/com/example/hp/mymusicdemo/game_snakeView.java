package com.example.hp.mymusicdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class game_snakeView extends View {
    private Paint edge,snake;//画边界的画笔与画蛇与食物的画笔
    private int height,width,direct=1,speed=30;//记录屏幕高度宽度与蛇的方向1,2,3,4和蛇移动步伐大小
    private float foodpos_x=800,foodpos_y=800;//记录食物的位置
    Random random;//随机数使用前必须刷新
    private boolean ifcreatfood=true;//判断条件是否允许产生食物
    private boolean ifdead=false;//判断蛇是否死亡
    private boolean ifstop=false;//游戏是否暂停//可能为暂停，也可能为蛇死亡
    private boolean ifeatfood=false;//判断是否吃到了食物
    private ArrayList<Point> mSnakeList;  //蛇体可以看做是很多食物组成的
    private int grade=0;//记录吃的食物个数
    private int ifbenew=0;//它是信号，显示是否刷新画布
    private float jx,jy,jx2,jy2;//记录手指的位置，以此调节蛇的方向


    private float snakeBody;//设置蛇的身材，随着屏幕不同蛇，的胖瘦也不同

    public game_snakeView(Context context)//画笔初始化不在这里，我也不知道为啥
    {
        super(context);
    }
    public game_snakeView(Context context, AttributeSet set)//画笔初始化在这里，我还是不知道为啥，另外少了这段代码无法运行成功
    {
        super(context,set);

        mSnakeList = new ArrayList<Point>();

        edge=new Paint();//画笔初始化
        edge.setColor(Color.BLACK);
        edge.setStrokeWidth(20);
        edge.setStyle(Paint.Style.STROKE);//设置画笔风格，空心或实心

        snake=new Paint();//画笔初始化
        snake.setColor(Color.RED);
        snake.setStrokeWidth(30);
        snake.setStyle(Paint.Style.FILL);
    }
    public void creatsnake()//创造蛇
    {
        mSnakeList.add(new Point(500,500));
        ifcreatfood=true;//初始化蛇的数据
        ifdead=false;
    }
    public void ifsnakedead()//判断蛇是否死亡
    {
        int test=1;
        if(mSnakeList.get(0).x<=30||mSnakeList.get(0).x>=width-30||mSnakeList.get(0).y<=30||mSnakeList.get(0).y>=height-30)//判断是否越过边界
        {
            ifdead= true;
        }
        else
        {
            ifdead=false;
        }
        for(int i=4;i<mSnakeList.size();i++)
        {
            if(mSnakeList.get(0).x==mSnakeList.get(i).x&&mSnakeList.get(0).y==mSnakeList.get(i).y)
            {
                ifdead= true;
            }
        }
    }

    //给外界信号作为判断依据，并有函数从外界来调节信号
    public boolean ifhasdead()
    {
        return ifdead;
    }

    public void setsnake()//复活蛇
    {
        ifdead=false;
        mSnakeList=new ArrayList<>();//
        mSnakeList.add(new Point(500,500));
        ifcreatfood=true;//初始化蛇的数据
        grade=0;
        ifdead=false;
    }
    public boolean ifeat()
    {
        return ifeatfood;
    }

    public void setifeatfood()
    {
        ifeatfood=false;
    }

    public void snakeeat()
    {
        Point oldhead=mSnakeList.get(0);
        //如果蛇头与食物距离很近就将他吃掉
        if (oldhead.x - foodpos_x <= 30&&oldhead.x-foodpos_x>=0 || foodpos_x - oldhead.x <= 30&&foodpos_x - oldhead.x >=0) {
            if (oldhead.y - foodpos_y <= 30&&oldhead.y-foodpos_y>=0 || foodpos_y-oldhead.y>=0&&foodpos_y - oldhead.y <= 30) {
                Point newhead=new Point();
                switch (direct)//上下左右、 头动
                {
                    case 1:
                        newhead.x = oldhead.x;
                        newhead.y = oldhead.y  - speed ;
                        break;
                    case 2:
                        newhead.x = oldhead.x;
                        newhead.y = oldhead.y  + speed ;
                        break;
                    case 3:
                        newhead.x = oldhead.x  - speed;
                        newhead.y = oldhead.y;
                        break;
                    case 4:
                        newhead.x = oldhead.x + speed ;
                        newhead.y = oldhead.y;
                        break;
                    default:
                        break;
                }
                mSnakeList.add(0,newhead);//吃东西头部加一，尾部不管
                grade+=1;ifeatfood=true;
                ifcreatfood=true;//准备生产新的食物
            }
        }
    }
    public int givegrade()
    {
        return grade;
    }
    public boolean ifcancreatfood()//告诉系统是否生成食物
    {
        return ifcreatfood;
    }
    public void snakemove()//控制蛇的移动
    {
        if(ifdead==false)
        {
            Point oldhead = mSnakeList.get(0);
            Point newhead = new Point();
            //身体除了头外全部移动
            switch (direct)//上下左右、 头动
            {
                case 1:
                    newhead.x = oldhead.x;
                    newhead.y = oldhead.y  - speed ;
                    break;
                case 2:
                    newhead.x = oldhead.x;
                    newhead.y = oldhead.y  + speed ;
                    break;
                case 3:
                    newhead.x = oldhead.x  - speed;
                    newhead.y = oldhead.y;
                    break;
                case 4:
                    newhead.x = oldhead.x + speed ;
                    newhead.y = oldhead.y;
                    break;
                default:
                    break;
            }
            mSnakeList.add(0,newhead);//头部加一，尾部减一实现移动
            mSnakeList.remove(mSnakeList.size() - 1);
        }
    }
    public void creatfood()//状态允许的话使用随机数生成下一个事物的位置
    {
        Point head=mSnakeList.get(0);
        if(ifcreatfood==true)
        {
            int ok;
            while(true)//食物的位置不能与蛇的身体重合
            {
                ok=0;
                random=new Random();foodpos_x = random.nextInt(width-300) + 50;
                random=new Random();foodpos_y = random.nextInt(height-300) + 50;
                for(int i=0;i<mSnakeList.size();i++)
                {
                    if(foodpos_x!=head.x||foodpos_y!=head.y)
                    {
                        ok++;
                    }
                }
                if(ok==mSnakeList.size())break;
            }
            ifcreatfood=false;
        }
    }
    public void getnum(int height,int width)//从外界得到屏幕的数据
    {
        this.height=height/6*5;
        this.width=width-10;//处理数据使得游戏界面更加合理

        snakeBody=width/38;
    }
    public void setdirect(int direct)//从外界得到一个数字表示方向
    {
        this.direct=direct;
    }
    public void engine()//游戏引擎
    {
        ifsnakedead();
        snakeeat();
        creatfood();
        snakemove();
        ifbenew=1;
    }
    public void drawedge(Canvas canvas)//画出边界
    {
        canvas.drawRect(10,10,width,height,edge);
    }
    public void drawfood(Canvas canvas)//画食物
    {
        snake.setColor(Color.BLUE);
        canvas.drawCircle(foodpos_x,foodpos_y,snakeBody,snake);//坐标，半径，使用的画笔
    }
    public void drawsnake(Canvas canvas)//画蛇
    {
        snake.setColor(Color.GREEN);//画出蛇的头且稍微大一点
        Point point = mSnakeList.get(0);
        canvas.drawCircle(point.x,point.y,snakeBody+5,snake);


        snake.setColor(Color.RED);
        for(int i=1;i<mSnakeList.size();i++)//画蛇
        {
            Point point1 = mSnakeList.get(i);
            canvas.drawCircle(point1.x,point1.y,snakeBody,snake);
        }
    }
    @Override
    protected void onDraw(Canvas canvas) //画画
    {
        super.onDraw(canvas);
        drawedge(canvas);
        drawfood(canvas);
        drawsnake(canvas);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) //根据触屏滑动改变方向
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                jx=event.getX();
                jy=event.getY();
                break;
            case MotionEvent.ACTION_UP:
                jx2=event.getX();
                jy2=event.getY();
                float endx=jx-jx2,endy=jy-jy2;
                if(endx>0)
                {
                    if(endy>0)
                    {
                        if(endx>endy) direct=3;
                        else direct=1;//
                    }
                    else if(endy<0)
                    {
                        endy*=-1;
                        if(endx>endy) direct=3;
                        else direct=2;
                    }
                }
                else if(endx<0)
                {
                    endx*=-1;
                    if(endy>0)
                    {
                        if(endx<endy) direct=1;
                        else direct=4;
                    }
                    else if(endy<0)
                    {
                        endy*=-1;
                        if(endx<endy) direct=2;
                        else direct=4;
                    }
                }
                break;
        }
        return true;
    }
}
