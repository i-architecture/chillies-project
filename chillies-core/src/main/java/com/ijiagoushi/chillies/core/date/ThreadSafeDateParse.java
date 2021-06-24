package com.ijiagoushi.chillies.core.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author miles.tang at 2021-05-08
 * @since 1.0
 */
class ThreadSafeDateParse {
    //线程
    private static final ThreadLocal<Map<String, DateFormat>> PARSERS = ThreadLocal.withInitial(HashMap::new);


    /**
     * 得到日期格式化类
     *
     * @param pattern 日期格式化风格
     * @return {@linkplain DateFormat}
     */
    private static DateFormat getParser(String pattern) {
        Map<String, DateFormat> parserMap = PARSERS.get();
        DateFormat df = parserMap.get(pattern);
        if (df == null) {
//            logger.debug("Date Format Pattern {} was not found in the current thread:{}", pattern, Thread.currentThread().getId());
            df = new SimpleDateFormat(pattern);
            parserMap.put(pattern, df);
        }
        return df;
    }

    /**
     * 解析日期字符串
     *
     * @param srcDate 日期字符串
     * @param pattern 解析格式
     * @return 日期对象
     * @throws ParseException
     */
    public static Date parse(String srcDate, String pattern) throws ParseException {
        return getParser(pattern).parse(srcDate);
    }

    public static Date parse(String srcDate, String pattern, TimeZone timeZone) throws ParseException {
        DateFormat dateFormat = getParser(pattern);
        dateFormat.setTimeZone(timeZone);
        return dateFormat.parse(srcDate);
    }


    public static Date parse(String text, String pattern, Locale locale) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        return sdf.parse(text);
    }

    /**
     * 解析日期字符串
     *
     * @param srcDate 日期字符串
     * @param pattern 解析格式
     * @return 日期
     * @throws ParseException
     */
    public static long parseLongDate(String srcDate, String pattern) throws ParseException {
        return parse(srcDate, pattern).getTime();
    }

    /**
     * 格式化日期，转为字符串
     *
     * @param theDate 日期
     * @param pattern 格式化规则
     * @return 格式化后的日期字符串
     */
    public static String format(Date theDate, String pattern) {
        return getParser(pattern).format(theDate);
    }

    /**
     * 格式化日期，转为字符串
     *
     * @param epochMilli 从1970-01-01 00:00:00开始的毫秒数
     * @param pattern    格式化规则
     * @return 格式化后的日期字符串
     */
    public static String format(long epochMilli, String pattern) {
        return getParser(pattern).format(new Date(epochMilli));
    }

}
