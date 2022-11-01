package pers.zitianqiong.utils;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: Date操作工具类
 */
public class DateUtil {


    private DateUtil() {
    }

    public static final String yyyyMMdd = "yyyyMMdd";
    /**
     * 英文简写（默认）如：2022-10-09
     */
    public static final String FORMAT_SHORT = "yyyy-MM-dd";
    public static final DateTimeFormatter defaultDateFormatter=DateTimeFormatter.ofPattern(FORMAT_SHORT);
    /**
     * 2022/10/09
     */
    public static  final String FORMAT_SHORT_J="yyyy/MM/dd";
    public static final DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern(FORMAT_SHORT_J);
    /**
     * 英文全称 如：2022-10-09 23:15:06
     */
    public static final String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter defaultFormatter=DateTimeFormatter.ofPattern(FORMAT_LONG);
    /**
     * 2022-10-09 23:15:06
     */
    public static final String FORMAT_LONG_J = "yyyy/MM/dd HH:mm:ss";
    public static final DateTimeFormatter formatter=DateTimeFormatter.ofPattern(FORMAT_LONG_J);
    /**
     * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
     */
    public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
    /**
     * 英文简写（默认）如：2022-10
     */
    public static final String FORMAT_YEAR_MONTH = "yyyy-MM";
    /**
     * 中文简写 如：2022年10月08日
     */
    public static final String FORMAT_SHORT_CN = "yyyy年MM月dd";
    /**
     * 中文全称 如：2022年10月09日 23时15分06秒
     */
    public static final String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
    /**
     * 精确到毫秒的完整中文时间
     */
    public static final String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";
    public static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";
    public static final String yyyyMMdd_HHmmss = "yyyyMMdd HHmmss";

    public static final String yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return FORMAT_LONG;
    }

    /**
     * 使用预设格式格式化日期
     *
     * @param date
     * @return
     */
    public static String format(Date date) {
        return format(date, getDatePattern());
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String format(LocalDateTime date, String pattern) {
        String returnValue = "";
        if (date != null) {
            DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 使用用户格式格式化日期
     *
     * @param date    日期
     * @param pattern 日期格式
     * @return
     */
    public static String format(Date date, String pattern) {
        String returnValue = "";
        if (date != null) {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    public static String format(String date) {
        String returnValue = "";
        if (StringUtils.isNotEmpty(date)) {
            returnValue = date.replace("-", "");
        }
        return returnValue;
    }

    /**
     * 使用预设格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @return
     */
    public static Date parse(String strDate) {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用用户格式提取字符串日期
     *
     * @param strDate 日期字符串
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String strDate, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(strDate);
        } catch (ParseException e) {

            return null;
        }
    }


    public static Date strToDate(String strDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = simpleDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 在日期上增加数个整月
     *
     * @param date 日期
     * @param n    要增加的月数
     * @return
     */
    public static Date addMonth(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, n);
        return cal.getTime();
    }

    /**
     * 在日期上增加天数
     *
     * @param date 日期
     * @param n    要增加的天数
     * @return
     */
    public static Date addDay(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, n);
        return cal.getTime();
    }


    /**
     * 在秒
     *
     * @param date 日期
     * @param n    要增加秒
     * @return
     */
    public static Date addSecond(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, n);
        return cal.getTime();
    }


    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
        Calendar calendar = Calendar.getInstance();
        return df.format(calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     * @return
     */
    public static String getYear(Date date) {
        return format(date).substring(0, 4);
    }

    /**
     * 计算两个日期相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int countDays(Date date1, Date date2) {
        Calendar c = Calendar.getInstance();
        c.setTime(date1);
        long t1 = c.getTimeInMillis();
        c.setTime(date2);
        long t2 = c.getTime().getTime();
        return (int) (t1 / 1000 - t2 / 1000) / 3600 / 24;
    }

    /**
     * 按默认格式的字符串距离今天的天数
     *
     * @param date 日期字符串
     * @return
     */
    public static int countDays(String date) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    /**
     * 按用户格式字符串距离今天的天数
     *
     * @param date   日期字符串
     * @param format 日期格式
     * @return
     */
    public static int countDays(String date, String format) {
        long t = Calendar.getInstance().getTime().getTime();
        Calendar c = Calendar.getInstance();
        c.setTime(parse(date, format));
        long t1 = c.getTime().getTime();
        return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
    }

    public static Date addMinute(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, n);
        return cal.getTime();
    }

    public static Date addHour(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, n);
        return cal.getTime();
    }

    public static Date addYear(Date date, int n) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, n);
        return cal.getTime();
    }

    public static boolean isBefore(Date date1, Date date2) {
        if (null == date1 || null == date2) return false;
        return date1.before(date2);
    }

    public static boolean isAfter(Date date1, Date date2) {
        if (null == date1 || null == date2) return false;
        return date1.after(date2);
    }
    
    /**
     * 查询是否在日期范围内
     * @param date 查询的日期
     * @param start 开始时间
     * @param end 结束时间
     * @return boolean
     */
    public static boolean isBetween(Date date, Date start, Date end) {
        if (null == date || null == start || null == end) return false;
        return isAfter(date, start) && isBefore(date, end);
    }

    /**
     * 获取一天开始时间
     *
     * @return 传入2022/10/09则传出20221009000000
     */
    public static Date getDayStart(Date date) {
        return DateUtil.parse(DateUtil.format(date, DateUtil.yyyyMMdd) + "000000", DateUtil.yyyyMMddHHmmss);
    }

    /**
     * 获取一天结束时间
     *
     * @return 传入2022/10/09则传出20221009235959
     */
    public static Date getDayEnd(Date date) {
        return DateUtil.parse(DateUtil.format(date, DateUtil.yyyyMMdd) + "235959", DateUtil.yyyyMMddHHmmss);
    }

    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     *
     * @param nowTime   当前时间
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
    
        return date.after(begin) && date.before(end);
    }

    public static Long getSeconds(Date date) {
        if (null == date) {
            return 0L;
        }
        String timestamp = String.valueOf(date.getTime() / 1000);
        return Long.valueOf(timestamp);
    }

    //获取一天的最小时间字符串 例如2022-01-14 17:17:17 则返回2022-01-14 00:00:00
    public static String getMinTimeWithDate(){
        return getMinTimeWithDate(null);
    }
    public static String getMinTimeWithDate(LocalDateTime now){
        if (null==now){
            now=LocalDateTime.now();
        }
        LocalDateTime temp=LocalDateTime.of(now.toLocalDate(), LocalTime.MIN);
        return temp.format(formatter);
    }
    
    /**
     * 获得当天0点时间
     * @param date 当前时间
     * @return 当天零点时间
     */
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    //获取日期字符串
    public static String getDateFormatShort(){
        return getDateFormatShort(null);

    }
    public static String getDateFormatShort(LocalDate today){
        if (null==today){
            today=LocalDate.now();
        }
        return today.format(dateFormatter);

    }

}

