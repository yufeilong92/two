package com.zzzh.akhalteke_shipper.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author : YFL  is Creating a porject in
 * @Package
 * @Email : yufeilong92@163.com
 * @Time :2019/3/16 11:05
 * @Purpose :工具类
 */
public class TimeSampUtil {
    /**
     * 获取当前时间戳
     *
     * @param date 当时时间
     * @return
     */
    public static String getStringTimeStamp(String date) {
        if (date == null || date.equals("")) {
            return "";
        }
//        TimeUtil timeUtil = TimeUtil.Companion.getInstance();
//        String commonDateStr = timeUtil.getCommonDateStr(date);

//        long nowTime = timeUtil.intervalNow(commonDateStr);
        Long commnedata = Long.parseLong(date);
        Long nowTime = new Date().getTime() - commnedata;
        String result = "";
        long ms = nowTime / 1000;
        BigDecimal decimal = new BigDecimal(ms).setScale(0, BigDecimal.ROUND_HALF_UP);
        //秒数
        long longNow = decimal.longValue();
        long temp = 0;
        if (longNow < 60) {
            result = "刚刚";
        } else if ((temp = longNow / 60) < 60) {
            result = temp + "分前";
        } else if ((temp = temp / 60) < 24) {
            result = temp + "小时前";
        } else if ((temp = temp / 24) < 30) {
            result = temp + "天前";
        } else if ((temp = temp / 30) < 12) {
            result = temp + "月前";
        } else {
            temp = temp / 12;
            result = temp + "年前";
        }
        return result;
    }
    /**
     * 根据当前日期获得是星期几
     * time=yyyy-MM-dd
     * @return
     */
    public static String getWeek(String time) {
        StringBuffer buffer=new StringBuffer();
        String Week = "";
        buffer.append(time.substring(0,4));
        buffer.append("-");
        buffer.append(time.substring(4,6));
        buffer.append("-");
        buffer.append(time.substring(6,8));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            Date parse = format.parse(buffer.toString());
            c.setTime(parse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int wek=c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "星期日";
        }
        if (wek == 2) {
            Week += "星期一";
        }
        if (wek == 3) {
            Week += "星期二";
        }
        if (wek == 4) {
            Week += "星期三";
        }
        if (wek == 5) {
            Week += "星期四";
        }
        if (wek == 6) {
            Week += "星期五";
        }
        if (wek == 7) {
            Week += "星期六";
        }
        return Week;
    }

}
