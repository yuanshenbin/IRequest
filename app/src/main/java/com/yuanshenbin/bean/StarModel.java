package com.yuanshenbin.bean;

/**
 * Created by Jacky on 2016/10/31.
 * 测试请求的对象
 */
public class StarModel {

    /**
     * tag1 : 明星
     * tag2 : 全部
     * totalNum : 24901
     * start_index : 1
     */

    private String tag1;
    private String tag2;
    private int totalNum;
    private int start_index;
    private String image_url;
    private int image_width;
    private int image_height;

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getImage_width() {
        return image_width;
    }

    public void setImage_width(int image_width) {
        this.image_width = image_width;
    }

    public int getImage_height() {
        return image_height;
    }

    public void setImage_height(int image_height) {
        this.image_height = image_height;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public void setStart_index(int start_index) {
        this.start_index = start_index;
    }

    public String getTag1() {
        return tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public int getStart_index() {
        return start_index;
    }

    @Override
    public String toString() {
        return "StarModel{" +
                "tag1='" + tag1 + '\'' +
                ", tag2='" + tag2 + '\'' +
                ", totalNum=" + totalNum +
                ", start_index=" + start_index +
                ", image_url='" + image_url + '\'' +
                ", image_width=" + image_width +
                ", image_height=" + image_height +
                '}';
    }
}
