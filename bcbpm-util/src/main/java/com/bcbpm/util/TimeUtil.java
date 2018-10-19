package com.bcbpm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;

final public class TimeUtil{
    final static private SimpleDateFormat Formater_Time14 = new SimpleDateFormat("HHmmss");
    final static private SimpleDateFormat Formater_Date14 = new SimpleDateFormat("yyyyMMdd");

    final static private SimpleDateFormat Formater_Time19 = new SimpleDateFormat("HH:mm:ss");

    final static private SimpleDateFormat Formater_Date19 = new SimpleDateFormat("yyyy-MM-dd");

    final static private String[] DaysOfWeek = new String[] { "日", "一", "二", "三", "四", "五", "六" };

    final static private SimpleDateFormat Formater_DateTime14 = new SimpleDateFormat("yyyyMMddHHmmss");

    final static private SimpleDateFormat Formater_DateTime19 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @SuppressWarnings("unused")
    final static private SimpleDateFormat Formater_DateTime22 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

    final static private Hashtable<String, SimpleDateFormat> Formats = new Hashtable<String, SimpleDateFormat>();

    /**
     * 计算指定时间处于每日5分钟的第几个区间(0~287)<br>
     * 当前日期和时间,yyyy-MM-dd HH:mm:ss
     * @return
     */
    static public int calcRange(String time){
        Date datec = parseDateTime(time);
        return calcRange(datec);
    }

    /**
     * 计算指定时间处于每日5分钟的第几个区间(0~287)<br>
     * 当前日期和时间,Date
     * @return
     */
    static public int calcRange(Date time){
        Calendar date = Calendar.getInstance();
        date.setTime(time);
        int seconds = date.get(Calendar.HOUR_OF_DAY) * 3600;
        seconds += date.get(Calendar.MINUTE) * 60;
        seconds += date.get(Calendar.SECOND);
        int a = seconds / 300;
        a += seconds % 300 > 0 ? 1 : 0;
        return Math.max(a - 1, 0);
    }

    /**
     * 计算指定时间处于每日24小时的第几个区间(0~24)<br>
     * 当前日期和时间,Date
     * @return
     */
    static public int calcRange24(Date time){
        Calendar date = Calendar.getInstance();
        date.setTime(time);
        int index = date.get(Calendar.HOUR_OF_DAY);
        return index;
    }

    /**
     * 计算指定时间处于每日5分钟的第几个区间(0~287)<br>
     * 当前基于2000年秒数
     * @param time
     * @return
     */
    static public int calcRange(int time2000){
        return calcRange(TimeUtil.to2000Date(time2000));
    }

    /**
     * 计算指定时间处于每日24小时的第几个区间(0~24)<br>
     * 当前基于2000年秒数
     * @param time
     * @return
     */
    static public int calcRange24(int time2000){
        return calcRange24(TimeUtil.to2000Date(time2000));
    }

    /**
     * 当前日期,yyyy-MM-dd
     */
    static synchronized public String getDate(){
        return Formater_Date19.format(new Date());
    }

    static synchronized public String getDate(Date date){
        return Formater_Date19.format(date);
    }

    /**
     * 当前时间,HH:mm:ss
     */
    static synchronized public String getTime(){
        return Formater_Time19.format(new Date());
    }

    /**
     * 当前日期和时间,yyyy-MM-dd HH:mm:ss
     */
    static synchronized public String getDateTime(){
        return Formater_DateTime19.format(new Date());
    }

    /**
     * 当前日期,yyyyMMdd
     */
    static synchronized public String getDateEx(){
        return Formater_Date14.format(new Date());
    }

    /**
     * 指定日期,yyyyMMdd
     */
    static public synchronized String getDateEx(Date date){
        return Formater_Date14.format(date);
    }

    /**
     * 格式,yyyyMMdd
     */
    static synchronized public Date parseDateEx(String date){
        try{
            return Formater_Date14.parse(date);
        }catch(Exception ex){
            throw new RuntimeException("解析时间错误", ex);
        }
    }

    /**
     * 当前时间,HHmmss
     */
    static synchronized public String getTimeEx(){
        return Formater_Time14.format(new Date());
    }

    /**
     * 当前时间,HHmmss
     */
    static synchronized public String getTimeEx(String d){
        return Formater_Time14.format(new Date());
    }

    /**
     * 当前日期和时间,yyyyMMddHHmmss
     */
    static synchronized public String getDateTimeEx(){
        return Formater_DateTime14.format(new Date());
    }

    /**
     * 指定格式的当前日期和时间
     */
    static public String getDateTime(String format){
        try{
            synchronized (Formats){
                if(!Formats.containsKey(format)){
                    Formats.put(format, new SimpleDateFormat(format));
                }
            }
            return Formats.get(format).format(new Date());
        }catch(Exception ex){
            throw new RuntimeException("时间格式(" + format + ")错误", ex);
        }
    }

    /**
     * 当前日期的相对days天
     */
    static public Date nextDate(int days){
        return nextDate(new Date(), days);
    }

    /**
     * 指定日期的相对days天
     */
    static public Date nextDate(Date date, int days){
        long oned = 24 * 60 * 60 * 1000;
        long time = date.getTime();
        return new Date(time + days * oned);
    }

    /**
     * 一天的毫秒(ms)数
     */
    static public int oneday(){
        return 24 * 60 * 60 * 1000;
    }

    /**
     * 当天偏移起始毫秒数
     * @return
     */
    static public long currday(int off){
        return parseDateEx(getDateEx()).getTime() + off * oneday();
    }

    /**
     * 年中第几天
     */
    static public int dayOfYear(){
        return dayOfYear(Calendar.getInstance());
    }

    static public int dayOfYear(Date date){
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(date.getTime());
        return cl.get(Calendar.DAY_OF_YEAR);
    }

    static public int dayOfYear(Calendar date){
        return date.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 月中第几天
     */
    static public int dayOfMonth(){
        return dayOfMonth(Calendar.getInstance());
    }

    static public int dayOfMonth(Date date){
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(date.getTime());
        return cl.get(Calendar.DAY_OF_MONTH);
    }

    static public int dayOfMonth(Calendar date){
        return date.get(Calendar.DAY_OF_MONTH);
    }

    // 判断闰平年
    static boolean isLeap(int year){
        if(((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))
            return true;
        else
            return false;
    }

    //返月数天数
    static public int getDays(int year, int month){
        int days;
        int FebDay = 28;
        if(isLeap(year))
            FebDay = 29;
        switch(month){
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
            days = 31;
            break;
        case 4:
        case 6:
        case 9:
        case 11:
            days = 30;
            break;
        case 2:
            days = FebDay;
            break;
        default:
            days = 0;
            break;
        }
        return days;
    }

    /**
     * 周中第几天
     */
    static public int dayOfWeek(){
        return dayOfWeek(Calendar.getInstance());
    }

    static public int dayOfWeek(Date date){
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(date.getTime());
        return cl.get(Calendar.DAY_OF_WEEK);
    }

    static public int dayOfWeek(Calendar date){
        return date.get(Calendar.DAY_OF_WEEK);
    }

    static public int year(){
        Calendar cl = Calendar.getInstance();
        return cl.get(Calendar.YEAR);
    }

    static public int month(){
        Calendar cl = Calendar.getInstance();
        return cl.get(Calendar.MONTH) + 1;
    }

    /**
     * 星期几
     */
    static public String weekOfDay(){
        return weekOfDay(Calendar.getInstance());
    }

    @SuppressWarnings("deprecation")
    static public String weekOfDay(Date date){
        return DaysOfWeek[date.getDay()];
    }

    static public String weekOfDay(Calendar date){
        return DaysOfWeek[date.get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 日期和时间,yyyy-MM-dd HH:mm:ss
     */
    static synchronized public String getDateTime(Date date){
        return Formater_DateTime19.format(date);
    }

    /**
     * 日期和时间,yyyy-MM-dd HH:mm:ss
     */
    static synchronized public String getDateTime(Calendar date){
        return Formater_DateTime19.format(date.getTime());
    }

    /**
     * 日期和时间,yyyyMMddHHmmss
     */
    static synchronized public String getDateTimeEx(Date date){
        return Formater_DateTime14.format(date);
    }

    /**
     * 日期和时间,yyyyMMddHHmmss
     */
    static synchronized public String getDateTimeEx(Calendar date){
        return Formater_DateTime14.format(date.getTime());
    }

    /**
     * 日期和时间
     */
    static public String getDateTime(Date date, String format){
        try{
            synchronized (Formats){
                if(!Formats.containsKey(format)){
                    Formats.put(format, new SimpleDateFormat(format));
                }
            }
            return Formats.get(format).format(date);
        }catch(Exception ex){
            throw new RuntimeException("日期时间格式(" + format + ")错误", ex);
        }
    }

    static public String getDateTime(Calendar date, String format){
        try{
            synchronized (Formats){
                if(!Formats.containsKey(format)){
                    Formats.put(format, new SimpleDateFormat(format));
                }
            }
            SimpleDateFormat formater = Formats.get(format);
            return formater.format(date.getTime());
        }catch(Exception ex){
            throw new RuntimeException("日期时间格式(" + format + ")错误", ex);
        }
    }

    /**
     * 将字符串转换成日期,yyyy-MM-dd HH:mm:ss
     */
    static synchronized public Date parseDateTime(String datetime){
        try{
            return Formater_DateTime19.parse(datetime);
        }catch(ParseException ex){
            throw new RuntimeException("解析时间错误", ex);
        }
    }

    /**
     * 将字符串转换成日期,yyyy-MM-dd
     */
    static synchronized public Date parseDate(String date){
        try{
            return Formater_Date19.parse(date);
        }catch(ParseException ex){
            throw new RuntimeException("解析时间错误", ex);
        }
    }

    /**
     * 将字符串转换成日期,yyyyMMddHHmmss
     */
    static synchronized public Date parseDateTimeEx(String datetime){
        try{
            return Formater_DateTime14.parse(datetime);
        }catch(ParseException ex){
            throw new RuntimeException("解析时间错误", ex);
        }
    }

    /**
     * 将字符串转换成日期
     */
    static synchronized public Date parseDateTime(String datetime, String format){
        try{
            synchronized (Formats){
                if(!Formats.containsKey(format)){
                    Formats.put(format, new SimpleDateFormat(format));
                }
            }
            return Formats.get(format).parse(datetime);
        }catch(ParseException ex){
            throw new RuntimeException("解析时间错误", ex);
        }
    }

    /**
     * 时间前推或后推分钟,其中addTime表示分钟.
     * @param format : "yyyy-MM-dd HH:mm:ss"
     * @param srcTime : "2011-03-08 12:00:00"
     * @param format : 5
     * @return
     */
    static public final Date getPreTime(Date srcDate, int addTime){
        Date destDate = null;
        try{
            destDate = new Date();
            long Time = (srcDate.getTime() / 1000) + addTime * 60;
            destDate.setTime(Time * 1000);
        }catch(Exception e){
        }
        return destDate;
    }

    /**
     * 1970.01.01 00.00.00基准时间(单位=秒)
     */
    static public Date toDate(long time){
        return new Date(time);
    }

    /**
     * 1970.01.01 00.00.00基准时间 yyyy-MM-dd HH:mm:ss(单位=秒)
     */
    static public String toDateString(long time){
        return getDateTime(new Date(time));
    }

    /**
     * 1970.01.01 00.00.00基准时间(单位=秒)
     */
    static public int toTime(){
        return (int) (System.currentTimeMillis() / 1000);
    }

    /**
     * 1970.01.01 00.00.00基准时间(单位=秒)
     */
    static public int toTime(Date date){
        return (int) (date.getTime() / 1000);
    }

    /**
     * 2000年时间基数
     */
    static final public int Time2000 = 946656000;

    /**
     * 2000.01.01 00.00.00基准时间(单位=秒)
     */
    static public Date to2000Date(int time){
        return new Date((long) (time + Time2000) * 1000);
    }

    static public Date to2000Date(long time){
        return new Date(time + Time2000);
    }

    static public long to2000MM(){
        return (((new Date()).getTime() / 1000) - 946656000) / 60;
    }

    /**
     * 2000.01.01 00.00.00基准时间 yyyy-MM-dd HH:mm:ss(单位=秒)
     */
    static public String to2000DateString(int time){
        return getDateTime(new Date((long) (time + Time2000) * 1000));
    }

    /**
     * 2000.01.01 00.00.00基准时间(单位=秒)
     */
    static public int to2000Time(){
        return (int) (System.currentTimeMillis() / 1000) - Time2000;
    }

    /**
     * 2000.01.01 00.00.00基准时间(单位=秒)
     */
    static public int to2000Time(Date date){
        return (int) (date.getTime() / 1000) - Time2000;
    }

    /**
     * 格式转化
     */
    static public String convert(String time, String format){
        return getDateTime(parseDateTime(time), format);
    }

    /**
     * 格式转化
     */
    static public String convert(String time, String oformat, String nformat){
        return getDateTime(parseDateTime(time, oformat), nformat);
    }

    static public long timeDiff(String time){
        return parseDateTime(time).getTime() - new Date().getTime();
    }

    static public long timeDiff(String time, String format){
        return parseDateTime(time, format).getTime() - new Date().getTime();
    }

    static public long timeDiff(String atime, String btime, String format){
        return parseDateTime(atime, format).getTime() - parseDateTime(btime, format).getTime();
    }

    static public long timeDiff(String atime, String aformat, String btime, String bformat){
        return parseDateTime(atime, aformat).getTime() - parseDateTime(btime, bformat).getTime();
    }

    /**
     * 判断这个日期是周几
     * */
    public static int dayForWeek(String pTime) throws Exception{
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(format.parse(pTime));
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    // 获得当前日期与本周一相差的天数
    public static int getMondayPlus(){
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if(dayOfWeek == 1){
            return -6;
        }else{
            return 2 - dayOfWeek;
        }
    }

    // 获得当前周- 周一的日期
    public static String getCurrentMonday(String time) throws Exception{
        int mondayPlus = getMondayPlus();
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = timeFormat.parse(time);
        date = new Date(date.getTime());
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(date);
        currentDate.add(GregorianCalendar.DATE, mondayPlus);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    // 获得当前周- 周日  的日期
    public static String getPreviousSunday(String time) throws Exception{
        int mondayPlus = getMondayPlus();
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = timeFormat.parse(time);
        date = new Date(date.getTime());
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.setTime(date);
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
        Date monday = currentDate.getTime();
        DateFormat df = DateFormat.getDateInstance();
        String preMonday = df.format(monday);
        return preMonday;
    }

    public static String convertWeekByDate(String time) throws Exception{
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = timeFormat.parse(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式  
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了  
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        if(1 == dayWeek){
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // logger.info("要计算日期为:"+sdf.format(cal.getTime())); //输出要计算日期  
        cal.setFirstDayOfWeek(Calendar.MONDAY);//设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一  
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天  
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值   
        String imptimeBegin = sdf.format(cal.getTime());
        //        BaseLog.i(TimeUtil.class, "所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = sdf.format(cal.getTime());
        //        BaseLog.i(TimeUtil.class, "所在周星期日的日期：" + imptimeEnd);
        return imptimeBegin + "," + imptimeEnd;

    }

    /* 
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException{
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /* 
     * 将时间戳转换为时间
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 
     *
     * @Author:       [wpp]     
     * @Company:      [益联云]
     * @Version:      [v8.5]           
     * @CreateDate:   [2018年8月16日 下午4:42:51]  
     * @Param:        []
     * @return:       [boolean]
     * @Description:  [校验日期的合法性]
     *
     */
    public static boolean isValidDate(String str, String dataFormat){
        boolean convertSuccess = true;
        // 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat(dataFormat);
        try{
            // 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        }catch(ParseException e){
            // e.printStackTrace();
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }

    public static void main(String[] args) throws Exception{
        //  logger.info(TimeUtil.calcRange24(TimeUtil.parseDateTime("2013-04-18 23:00:00")));
        //    String date = "2008-04-19";
        //    logger.info(TimeUtil.convertWeekByDate("2016-04-07"));	
        //    logger.info(TimeUtil.getPreviousSunday("2016-04-17"));	
        long endTimeMs = System.currentTimeMillis();
        System.out.println(TimeUtil.toDate(endTimeMs));
    }
}
