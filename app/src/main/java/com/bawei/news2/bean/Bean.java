package com.bawei.news2.bean;

/**
 * Created by 设计风格 on 2017/8/30.
 */
public class Bean {
    private String title;
    private String img;

    public Bean(String title, String img) {
        this.title = title;
        this.img = img;
    }

    public Bean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "title='" + title + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
