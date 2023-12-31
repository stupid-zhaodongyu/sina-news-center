package com.zhaodongyu.news;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;
public class SinaNews {
    private static String url
            = "https://feed.mix.sina.com.cn/api/roll/get?pageid=153&lid=2509&k=&num=50&page=1&r=0.19278015324177455";

    public static void main(String[] args) throws IOException, ParseException {
        String content = HttpUtls.getHttpContent(url);
        JSONObject jsonObject = JSON.parseObject(content).getJSONObject("result");
        JSONArray jsonArray = jsonObject
            .getJSONArray("data");

        TimeZone time = TimeZone.getTimeZone("Etc/GMT-8");  //转换为中国时区
        TimeZone.setDefault(time);

        Long timeLong = Long.parseLong(jsonObject.get("start").toString())*1000L;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = sdf.format(timeLong);


        List<SinaNewsPojo> sinaNewsList = JSON.parseArray(jsonArray.toString(), SinaNewsPojo.class);
//        for (SinaNewsPojo sinaNewsPojo : sinaNewsList) {
//            sinaNewsPojo.setUpdatetime(dateString);
//        }
        String sinaNews = sinaNewsList.stream().map(SinaNewsPojo::toString).collect(Collectors.joining());
        String sinaHtml = new String(Files.readAllBytes(Paths.get("sina.html")), "utf-8");
        sinaHtml = sinaHtml.replace("${content}", sinaNews).replace("${time}",dateString);

        Path indexPath = Paths.get("index.html");
        Files.deleteIfExists(indexPath);
        Files.createFile(indexPath);
        Files.write(indexPath, sinaHtml.getBytes("utf-8"));
    }
}
