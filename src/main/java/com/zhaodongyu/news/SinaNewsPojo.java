package com.zhaodongyu.news;

import com.alibaba.fastjson.annotation.JSONField;

public class SinaNewsPojo {
    @JSONField(name = "title")
    private String title;
    private String updatetime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JSONField(name = "url")
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public String toString() {
//        return "<div class=\"w3-container w3-justify\"><p>" + title.trim() + "</p></div>";
        return "<div class=\"w3-container w3-justify\"><a href="+url+"><p>" + title.trim() + "</p></a></div>";
    }
}
