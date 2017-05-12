package com.android.frame.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/20.
 * 日期操作类
 */
public class DateTool {

    /**
     * 获取现在时间
     *
     * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
     */
    public static Date getNowDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        ParsePosition pos = new ParsePosition(8);
        Date currentTime_2 = formatter.parse(dateString, pos);
        return currentTime_2;
    }


    /**
     * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
     *
     * @param sformat
     *            yyyyMMddhhmmss
     * @return
     */
    public static String getformatDate(String sformat) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(sformat);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 将时间戳转为字符串单位为秒
     *
     * @param timestamp
     *            时间戳
     *  @param format 返回格式 可不传
     * @return String
     */
    public static String timestampToStrTime(String timestamp,String format) {
        if (null==format||format.length()==0){
            format="yyyy-MM-dd HH:mm:ss";
        }
        if (TextUtils.isEmpty(timestamp)){
            return "";
        }

        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long lcc_time = Long.valueOf(timestamp);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }


    /**
     * 将短时间格式时间转换为字符串 yyyy-MM-dd
     *
     * @param dateDate
     * @param format  可不传，为默认格式
     * @return
     */
    public static String dateToStr(Date dateDate,String format) {
        if (null==format||format.length()==0){
            format="yyyy-MM-dd HH:mm:ss";
        }

        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(dateDate);
        return dateString;
    }

    /**
     * 将字符串转为时间戳
     *
     * @param strTime
     *            时间字符串
     * @return String
     */
    public static long strTimeToTimestamp(String strTime) {
        // String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = null;
        try {
            d = sdf.parse(strTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d == null ? 0 : d.getTime();
    }


    /**
     * 得到现在分钟
     *
     * @return
     */
    public static String getNowMin() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String min;
        min = dateString.substring(14, 16);
        return min;
    }


    /**
     * 将时间戳转为字符串单位为毫秒
     *
     * @param timestamp
     *            时间戳
     * @return String
     */
    public static String timesToStrTime(String timestamp,String format) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long lcc_time = Long.valueOf(timestamp);
        re_StrTime = sdf.format(new Date(lcc_time));
        return re_StrTime;
    }



    /**
     * 将时间戳转为代表"距现在多久之前"的字符串
     * @param timeStr   时间戳
     * @return
     */
    public static String getStandardDate(String timeStr) {

        StringBuffer sb = new StringBuffer();

        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - (t*1000);
        long mill = (long) Math.ceil(time /1000);//秒前

        long minute = (long) Math.ceil(time/60/1000.0f);// 分钟前

        long hour = (long) Math.ceil(time/60/60/1000.0f);// 小时

        long day = (long) Math.ceil(time/24/60/60/1000.0f);// 天前

        if (day - 1 > 0) {
            sb.append(day + "天");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1天");
            } else {
                sb.append(hour + "小时");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1小时");
            } else {
                sb.append(minute + "分钟");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1分钟");
            } else {
                sb.append(mill + "秒");
            }
        } else {
            sb.append("刚刚");
        }
        if (!sb.toString().equals("刚刚")) {
            sb.append("前");
        }
        return sb.toString();
    }
}
