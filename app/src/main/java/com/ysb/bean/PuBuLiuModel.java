package com.ysb.bean;

import java.util.List;

/**
 * Created by Jacky on 2016/10/31.
 * 瀑布流数据
 */
public class PuBuLiuModel {
    private int totalNum;
    private int start_index;
    private int return_number;
    private List<StarModel> data;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getStart_index() {
        return start_index;
    }

    public void setStart_index(int start_index) {
        this.start_index = start_index;
    }

    public int getReturn_number() {
        return return_number;
    }

    public void setReturn_number(int return_number) {
        this.return_number = return_number;
    }

    public List<StarModel> getData() {
        return data;
    }

    public void setData(List<StarModel> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PuBuLiuModel{" +
                "totalNum=" + totalNum +
                ", start_index=" + start_index +
                ", return_number=" + return_number +
                ", data=" + data +
                '}';
    }
}
