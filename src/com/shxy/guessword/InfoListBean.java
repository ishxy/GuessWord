package com.shxy.guessword;

import java.util.List;

public class InfoListBean {
    private String msg;
    private Integer state;
    private List<InfoBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public List<InfoBean> getData() {
        return data;
    }

    public void setData(List<InfoBean> data) {
        this.data = data;
    }
}
