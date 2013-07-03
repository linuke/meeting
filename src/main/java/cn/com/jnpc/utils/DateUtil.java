package cn.com.jnpc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期和时间操作类</br>
 * 
 * @author linuke
 *         <p>
 *         日期和时间模式
 *         <table>
 *         <tbody>
 *         <tr >
 *         <th align="left">字母</th>
 *         <th align="left">日期或时间元素</th>
 *         <th align="left">表示</th>
 *         <th align="left">示例</th>
 *         </tr>
 *         <tr>
 *         <td>G</td>
 *         <td>Era 标志符</td>
 *         <td>Text</td>
 *         <td>AD</td>
 *         </tr>
 *         <tr>
 *         <td>y</td>
 *         <td>年</td>
 *         <td>Year</td>
 *         <td>1996; 96</td>
 *         </tr>
 *         <tr>
 *         <td>M</td>
 *         <td>年中的月份</td>
 *         <td>Month</td>
 *         <td>July; Jul; 07</td>
 *         </tr>
 *         <tr>
 *         <td>w</td>
 *         <td>年中的周数</td>
 *         <td>Number</td>
 *         <td>27</td>
 *         </tr>
 *         <tr>
 *         <td>W</td>
 *         <td>月份中的周数</td>
 *         <td>Number</td>
 *         <td>2</td>
 *         </tr>
 *         <tr>
 *         <td>D</td>
 *         <td>年中的天数</td>
 *         <td>Number</td>
 *         <td>189</td>
 *         </tr>
 *         <tr>
 *         <td>d</td>
 *         <td>月份中的天数</td>
 *         <td>Number</td>
 *         <td>10</td>
 *         </tr>
 *         <tr>
 *         <td>F</td>
 *         <td>月份中的星期</td>
 *         <td>Number</td>
 *         <td>2</td>
 *         </tr>
 *         <tr>
 *         <td>E</td>
 *         <td>星期中的天数</td>
 *         <td>Text</td>
 *         <td>Tuesday; Tue</td>
 *         </tr>
 *         <tr>
 *         <td>a</td>
 *         <td>Am/pm 标记</td>
 *         <td>Text</td>
 *         <td>PM</td>
 *         </tr>
 *         <tr>
 *         <td>H</td>
 *         <td>一天中的小时数（0-23）</td>
 *         <td>Number</td>
 *         <td>0</td>
 *         </tr>
 *         <tr>
 *         <td>k</td>
 *         <td>一天中的小时数（1-24）</td>
 *         <td>Number</td>
 *         <td>24</td>
 *         </tr>
 *         <tr>
 *         <td>K</td>
 *         <td>am/pm 中的小时数（0-11）</td>
 *         <td>Number</td>
 *         <td>0</td>
 *         </tr>
 *         <tr>
 *         <td>h</td>
 *         <td>am/pm 中的小时数（1-12）</td>
 *         <td>Number</td>
 *         <td>12</td>
 *         </tr>
 *         <tr>
 *         <td>m</td>
 *         <td>小时中的分钟数</td>
 *         <td>Number</td>
 *         <td>30</td>
 *         </tr>
 *         <tr>
 *         <td>s</td>
 *         <td>分钟中的秒数</td>
 *         <td>Number</td>
 *         <td>55</td>
 *         </tr>
 *         <tr>
 *         <td>S</td>
 *         <td>毫秒数</td>
 *         <td>Number</td>
 *         <td>978</td>
 *         </tr>
 *         <tr>
 *         <td>z</td>
 *         <td>时区</td>
 *         <td>General time zone</td>
 *         <td>Pacific Standard Time; PST; GMT-08:00</td>
 *         </tr>
 *         <tr>
 *         <td>Z</td>
 *         <td>时区</td>
 *         <td>RFC 822 time zone</td>
 *         <td>-0800</td>
 *         </tr>
 *         </tbody>
 *         </table>
 *         </p>
 */
public final class DateUtil {

    private static final String DEFALUTE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 格式化字符串类型的日期
     * 
     * @param sDate
     *        需要格式的字符串类型的日期
     * @param f
     *        sDate的模式
     * @param pattern
     *        需要格式的模式
     * @return
     */
    public static String format(String sDate, String f, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(f);
            Date date = format.parse(sDate);
            format.applyPattern(pattern);
            return format.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("DateUtil  format:" + ex.getMessage());
            return "";
        }
    }

    /**
     * @param pattern
     *        需要返回日期的模式
     * @return
     */
    public static String getCurrentDate(String pattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            Date currentDate = new Date();
            return df.format(currentDate);
        } catch (Exception e) {
            System.out.println("DateUtil  getCurrentDate" + e.getMessage());
            return null;
        }
    }

    /**
     * String类型转成Date类型
     * 
     * @param sDate
     * @param pattern
     * @return
     */
    public static Date stringToDate(String sDate, String pattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.parse(sDate);
        } catch (Exception e) {
            System.out.println("DateUtil  stringToDate" + e.getMessage());
            return null;
        }
    }

    /**
     * Date转成String
     * 
     * @param date
     * @param pattern
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        } catch (Exception e) {
            System.out.println("DateUtil  DateToString" + e.getMessage());
            return null;
        }
    }

    /**
     * 在一个日期上加上秒
     * 
     * @param date
     * @param seconds
     * @return 返回 Mon Jan 07 11:10:58 CST 2013 类型的时间
     */
    public static Date addSecond(Date date, int seconds) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.SECOND, seconds);
            return cal.getTime();
        } catch (Exception e) {
            System.out.println("DateUtil  addSecond:" + e.getMessage());
            return null;
        }
    }

    /**
     * 在一个日期上加上分钟
     * 
     * @param date
     * @param minutes
     * @return
     */
    public static String addMinute(Date date, int minutes) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MINUTE, minutes);
            return dateToString(cal.getTime(), DEFALUTE_PATTERN);
        } catch (Exception e) {
            System.out.println("DateUtil  addMinute:" + e.getMessage());
            return null;
        }
    }

    /**
     * 在一个日期上加上小时
     * 
     * @param date
     * @param hours
     * @return 返回 Mon Jan 07 11:10:58 CST 2013 类型的时间
     */
    public static Date addHours(Date date, int hours) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR, hours);
            return cal.getTime();
        } catch (Exception e) {
            System.out.println("DateUtil  addMinute:" + e.getMessage());
            return null;
        }
    }

    /**
     * 在一个日期上加上天数
     * 
     * @param date
     * @param days
     *        要加的天数
     * @return 返回 Mon Jan 07 11:10:58 CST 2013 类型的时间
     */
    public static Date addDay(Date date, int days) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, days);
            return cal.getTime();
        } catch (Exception e) {
            System.out.println("DateUtil  addDay:" + e.getMessage());
            return null;
        }
    }

    /**
     * 在一个日期上加上月数
     * 
     * @param date
     * @param months
     *        要加的月数
     * @return 返回 Mon Jan 07 11:10:58 CST 2013 类型的时间
     */
    public static Date addMonth(Date date, int months) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.MONTH, months);
            return cal.getTime();
        } catch (Exception e) {
            System.out.println("DateUtil  addMonth" + e.getMessage());
            return null;
        }
    }

    /**
     * 在一个日期上加上年数
     * 
     * @param date
     * @param years
     *        要加的年数
     * @return 返回 Mon Jan 07 11:10:58 CST 2013 类型的时间
     */
    public static Date addYear(Date date, int years) {
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.YEAR, years);
            return cal.getTime();
        } catch (Exception e) {
            System.out.println("DateUtil  addYear" + e.getMessage());
            return null;
        }
    }

    /**
     * 返回两个日期的天数的绝对值 .
     * 
     * @param startTime
     *        开始时间
     * @param endTime
     *        结束时间
     * @param format
     *        比较时间的格式
     * @return 返回,-1: 发生ParseException异常.
     */
    public static long dateDiff(String startTime, String endTime, String pattern) {
        long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
        // long nh = 1000 * 60 * 60;// 一小时的毫秒数
        // long nm = 1000 * 60;// 一分钟的毫秒数
        // long ns = 1000;// 一秒钟的毫秒数
        long diff;
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            // 获得两个时间的毫秒时间差异
            diff = format.parse(endTime).getTime() - format.parse(startTime).getTime();
            long day = diff / nd;// 计算差多少天
            // long hour = diff % nd / nh;// 计算差多少小时
            // long min = diff % nd % nh / nm;// 计算差多少分钟
            // long sec = diff % nd % nh % nm / ns;// 计算差多少秒
            // 输出结果
            return Math.abs(day);
        } catch (ParseException e) {
            System.out.println("DateUtil  dateDiff" + e.getMessage());
            return -1;
        }
    }

    public static int compare(String d1, String d2, String pattern) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            return (int) (format.parse(d2).getTime() - format.parse(d1).getTime());
        } catch (Exception e) {
            System.out.println("DateUtil  compare" + e.getMessage());
            return 0;
        }
    }

    public static Date getFirstDate(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        return cal.getTime();
    }

    public static Date getLastDate(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, 1);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(year, month - 1, lastDay);
        return cal.getTime();
    }

    public static Date getNextMonth(Date now) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MONTH, 1);
        return cal.getTime();
    }

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        return cal.getTime();
    }

    public static void main(String[] args) {
        // System.out.println(getFullDateWeekTime("2013-01-06 09:00:00"));
//		Date d = new Date();
//		System.out.println(dateToString(d,  "yyyy-MM-dd"));
//		System.out.println(dateDiff("2013-01-06 09:00:00", "2013-01-06 09:00:00","yyyy-MM-dd"));
//		System.out.println(format("2013-01-05","yyyy-MM-dd", "E yyyy-MM-dd"));
//	    System.out.println(format("2013-03-29 星期五 09:30", "yyyy-MM-dd E HH:mm", "yyyy-MM-dd HH:mm"));
//	    System.out.println(new Date("yyyy-MM-dd"));
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println(sdf.format(getFirstDate(2013, 5)));
//        System.out.println(sdf.format(getLastDate(2013, 5)));
//        System.out.println(dateToString(getNextMonth(new Date()), "yyyy-MM-dd"));
//        System.out.println(DateUtil.dateToString(DateUtil.addDay(new Date(), 5), "yyyy-MM-dd HH:mm"));
        System.out.println(dateToString(getDate(2013, 0, 1), "yyyy-MM-dd HH:mm"));
        System.out.println(dateToString(addMonth(getDate(2013, 12, 0), -1), "yyyy-MM-dd HH:mm"));
    }
}
