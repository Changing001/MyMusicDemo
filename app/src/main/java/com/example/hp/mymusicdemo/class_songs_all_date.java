package com.example.hp.mymusicdemo;

/**
 * Created by HP on 2018/1/6.
 */
//歌曲类存储所有歌曲数据
public class class_songs_all_date {

    private String songname;
    private String songpath;
    boolean ifBeChoose=false;
    private int pos;
    private String singername;
    private String timelong;


    public String getName() {
        return songname;
    }

    public void setName(String name) {
        this.songname = name;
    }

    public int getPos()
    {
        return pos;
    }

    public void setPos(int pos)
    {
        this.pos=pos;
    }

    public String getPath() {
        return songpath;
    }

    public void setPath(String path) {
        this.songpath = path;
    }

    public String getSinger()
    {
        return singername;
    }

    public void setSinger(String singer)
    {
        this.singername=singer;
    }

    public String getTimelong()
    {
        return timelong;
    }
    
    public void setTimelong(String timelong)
    {
        this.timelong=timelong;
    }

}
