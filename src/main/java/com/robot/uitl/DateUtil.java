package com.robot.uitl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 获取时间类
 * @author LPS
 */
public class DateUtil {
    public static String currentTime(String format) throws ParseException {
        // 格式化模版
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern(format);
        // 时间转字符串
        String dtStr = dateTime.format(LocalDateTime.now());
        // 字符串转时间
        //SimpleDateFormat ft = new SimpleDateFormat(format);
        //Date date = ft.parse(dtStr);
        return dtStr;
    }
    public static Date currentTimeDate(String format) throws ParseException {
        // 格式化模版
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern(format);
        // 时间转字符串
        String dtStr = dateTime.format(LocalDateTime.now());
        // 字符串转时间
        SimpleDateFormat ft = new SimpleDateFormat(format);
        Date date = ft.parse(dtStr);
        return date;
    }
}
