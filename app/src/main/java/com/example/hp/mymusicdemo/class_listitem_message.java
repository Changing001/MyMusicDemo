package com.example.hp.mymusicdemo;

/**
 * Created by HP on 2018/1/15.
 */

public class class_listitem_message {
    private String up;
    private String down;
    private int image;
    private int image2;
    private int image3;



    public void setImage(int image)
    {
        this.image=image;
    }
    public void setImage2(int image)
    {
        this.image2=image;
    }
    public void setImage3(int image)
    {
        this.image3=image;
    }
    public void setUp(String text)
    {
        this.up=text;
    }
    public void setDown(String text)
    {
        this.down=text;
    }
    public String getUp()
    {
        return up;
    }
    public String getDown()
    {
        return down;
    }
    public int getImage()
    {
        return image;
    }
    public int getImage2()
    {
        return image2;
    }
    public int getImage3()
    {
        return image3;
    }

}
