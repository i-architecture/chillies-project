package com.ijiagoushi.chillies.core.date;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class DateUtilTest {

    @Test
    public void normalize() {
        String dateStr = null;
        assertNull(DateUtil.normalize(dateStr));

        dateStr = "";
        assertEquals("", DateUtil.normalize(dateStr));

        dateStr = "           ";
        assertEquals("           ", DateUtil.normalize(dateStr));

        dateStr = "2021年5月11";
        assertEquals("2021-5-11", DateUtil.normalize(dateStr));

        dateStr = "2021年5月11日 22时";
        assertEquals("2021-5-11 22", DateUtil.normalize(dateStr));

        dateStr = "2021年5月11日 22时22分";
        assertEquals("2021-5-11 22:22", DateUtil.normalize(dateStr));

        dateStr = "2021年5月11日 22时22分22秒";
        assertEquals("2021-5-11 22:22:22", DateUtil.normalize(dateStr));

        dateStr = "2021年5月11日22时22分22秒";
        assertEquals("2021-5-11 22:22:22", DateUtil.normalize(dateStr));

        dateStr = "2021.05.11";
        assertEquals("2021-05-11", DateUtil.normalize(dateStr));

        dateStr = "2021/5/11";
        assertEquals("2021-5-11", DateUtil.normalize(dateStr));

        dateStr = "2021-5-11";
        assertEquals("2021-5-11", DateUtil.normalize(dateStr));

        dateStr = "22:00";
        assertEquals("22:00", DateUtil.normalize(dateStr));

        dateStr = "2021/5/11 22:00";
        assertEquals("2021-5-11 22:00", DateUtil.normalize(dateStr));

        dateStr = "2021/5/11 22:00:30";
        assertEquals("2021-5-11 22:00:30", DateUtil.normalize(dateStr));

        dateStr = "2021.5/11 22:00.333";
        assertEquals("2021-5-11 22:00.333", DateUtil.normalize(dateStr));

        dateStr = "2021/5/11 22:00.333";
        assertEquals("2021-5-11 22:00.333", DateUtil.normalize(dateStr));
    }

    @Test
    public void format() {
        String actual = DateUtil.format();
        LocalDateTime now = LocalDateTime.now();
        String expected = now.getYear() + "-" +
                String.format("%02d", now.getMonthValue()) + "-" +
                String.format("%02d", now.getDayOfMonth()) + " " +
                String.format("%02d", now.getHour()) + ":" +
                String.format("%02d", now.getMinute()) + ":" +
                String.format("%02d", now.getSecond());
        assertEquals(expected, actual);
    }

    @Test
    public void formatPattern() {
        String pattern = "yyyy/MM/dd";
        String actual = DateUtil.format(pattern);
        LocalDateTime now = LocalDateTime.now();
        String expected = now.getYear() + "/" +
                String.format("%02d", now.getMonthValue()) + "/" +
                String.format("%02d", now.getDayOfMonth());
        assertEquals(expected, actual);
    }

    @Test
    public void formatEpochMilli() {
        long epochMill = 1620896797370L;
        String expected = "2021-05-13 17:06:37";
        String actual = DateUtil.format(epochMill);
        assertEquals(expected, actual);
    }

    @Test
    public void formatEpochMilliPattern() {
        String pattern = "yyyy|MM|dd'T'HH:mm:ss";
        long epochMill = 1620896797370L;
        String expected = "2021|05|13T17:06:37";
        String actual = DateUtil.format(epochMill, pattern);
        assertEquals(expected, actual);
    }

    @Test
    public void formatDate() {
        String actual = DateUtil.format(new Date());
        LocalDateTime now = LocalDateTime.now();
        String expected = now.getYear() + "-" + String.format("%02d", now.getMonthValue()) + "-" + String.format("%02d", now.getDayOfMonth()) + " " +
                String.format("%02d", now.getHour()) + ":" + String.format("%02d", now.getMinute()) + ":" + String.format("%02d", now.getSecond());
        assertEquals(expected, actual);
    }

    @Test
    public void formatDatePattern() {
        String pattern = "yyyy-MM/dd HH:mm:ss";
        String actual = DateUtil.format(new Date(), pattern);
        LocalDateTime now = LocalDateTime.now();
        String expected = now.getYear() + "-" + String.format("%02d", now.getMonthValue()) + "/" + String.format("%02d", now.getDayOfMonth()) + " " +
                String.format("%02d", now.getHour()) + ":" + String.format("%02d", now.getMinute()) + ":" + String.format("%02d", now.getSecond());
        assertEquals(expected, actual);
    }

    @Test
    public void swapFormat() {
        final String strDate = "2019/04/10 12:00:00";
        final String pattern = "yyyy/MM/dd HH:mm:ss";
        final String destPattern = "yyyy-MM-dd";
        assertEquals(DateUtil.swapFormat(strDate, pattern, destPattern), "2019-04-10");
    }

    @Test
    public void tryParse() {
        String text = "1990-1-1";
        LocalDateTime expectedDate = LocalDateTime.of(1990, 1, 1, 0, 0, 0);
        Date actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "1990-1-10";
        expectedDate = LocalDateTime.of(1990, 1, 10, 0, 0, 0);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "1990-10-10";
        expectedDate = LocalDateTime.of(1990, 10, 10, 0, 0, 0);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "1990-10-1";
        expectedDate = LocalDateTime.of(1990, 10, 1, 0, 0, 0);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "1990-01-1";
        expectedDate = LocalDateTime.of(1990, 1, 1, 0, 0, 0);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);


        text = "2:3";
        expectedDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(2, 3));
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "2:30";
        expectedDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(2, 30));
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "12:30";
        expectedDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 30));
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "12:3";
        expectedDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 3));
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "12:03";
        expectedDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 3));
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "12:03:1";
        expectedDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 3, 1));
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "12:03:11";
        expectedDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 3, 11));
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "2:30";
        expectedDate = LocalDateTime.of(LocalDate.now(), LocalTime.of(2, 30, 0));
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);


        text = "1990-1-1 12:01";
        expectedDate = LocalDateTime.of(1990, 1, 1, 12, 1);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "1990-1-1 12:01:11";
        expectedDate = LocalDateTime.of(1990, 1, 1, 12, 1, 11);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "1990-1-1 12:01:11.666";
        expectedDate = LocalDateTime.of(1990, 1, 1, 12, 1, 11);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);


        text = "2021/5/13";
        expectedDate = LocalDateTime.of(2021, 5, 13, 0, 0, 0);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "2021/5/13 11:00";
        expectedDate = LocalDateTime.of(2021, 5, 13, 11, 0, 0);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "2021-5/13 11:00";
        expectedDate = LocalDateTime.of(2021, 5, 13, 11, 0, 0);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "2021年5月13日11时00分00秒";
        expectedDate = LocalDateTime.of(2021, 5, 13, 11, 0, 0);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "2021年5月13日 11时00分00秒";
        expectedDate = LocalDateTime.of(2021, 5, 13, 11, 0, 0);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "Wed May 13 20:00:00 CST 2021";
        expectedDate = LocalDateTime.of(2021, 5, 13, 20, 0, 0);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "Wed May 13 20:00:00 GMT+08:00 2021";
        expectedDate = LocalDateTime.of(2021, 5, 13, 20, 0, 0);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "2021-05-13T20:20:20";
        expectedDate = LocalDateTime.of(2021, 5, 13, 20, 20, 20);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "2021-05-13T20:01:01+0800";
        expectedDate = LocalDateTime.of(2021, 5, 13, 20, 1, 1);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

        text = "2021-05-13T20:01:01+08:00";
        expectedDate = LocalDateTime.of(2021, 5, 13, 20, 1, 1);
        actualDate = DateUtil.tryParse(text);
        assertEquals(expectedDate.toEpochSecond(ZoneOffsetConstant.BEIJING_ZONE_OFFSET), actualDate.getTime() / 1000L);

    }

//    @Test
//    public void formatToDate() {
////        LocalDate now = LocalDate.now();
////        String formatToDate = DateUtil.formatToDate();
////        assertEquals(formatToDate, now.toString());
//
////        String str = "1990/1/1";
////        String str =  "1990/1/10";
////        String str =  "1990/10/1";
////        String str =  "1990/10/10";
////        String str =  "1990-10-10 12:10";
////        String str =  "1990-10-1 12:10";
//////        String str =  "1990-1-10";
//////        String str =  "1990-1-1";
//////        String str = "20200101";
////
//////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d[[ hh][:mm][:ss]]");
////        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
////        System.out.println(LocalDate.parse(str, formatter));
//
//        String timeStr = "11:4";
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:m");
//        System.out.println(LocalTime.parse(timeStr, formatter));
//    }
//
//    @Test
//    public void formatToDateTime() {
//        LocalDateTime now = LocalDateTime.now();
//        String formatToDateTime = DateUtil.formatToDateTime();
//        int year = now.getYear(),
//                month = now.getMonthValue(),
//                dayOfMonth = now.getDayOfMonth(),
//                hour = now.getHour(),
//                minute = now.getMinute(),
//                second = now.getSecond();
//        String expectedFormat = year +
//                (month < 10 ? "-0" : "-") + month +
//                (dayOfMonth < 10 ? "-0" : "-") + dayOfMonth +
//                (hour < 10 ? " 0" : " ") + hour +
//                (minute < 10 ? ":0" : ":") + minute +
//                (second < 10 ? ":0" : ":") + second;
//        assertEquals(formatToDateTime, expectedFormat);
//    }
//
//    @Test
//    public void formatToDateForLong() {
//        Date date = DateUtil.ofUtilDate(2018, 2, 28);
//        String formatToDate = DateUtil.formatToDate(date.getTime());
//        assertEquals(formatToDate, "2018-02-28");
//    }
//
//    @Test
//    public void formatToDateTimeForLong() {
//        Date date = DateUtil.ofUtilDate(2018, 2, 28, 18, 0, 0);
//        String formatToDate = DateUtil.formatToDateTime(date.getTime());
//        assertEquals(formatToDate, "2018-02-28 18:00:00");
//
//    }
//
//    @Test
//    public void formatPatternString() {
//        String pattern = "yyyy/MM/dd";
//        LocalDateTime now = LocalDateTime.now();
//        int year = now.getYear(),
//                month = now.getMonthValue(),
//                dayOfMonth = now.getDayOfMonth();
//        String format = DateUtil.format(pattern);
//        String expectedFormat = year +
//                (month < 10 ? "/0" : "/") + month +
//                (dayOfMonth < 10 ? "/0" : "/") + dayOfMonth;
//
//        assertEquals(format, expectedFormat);
//    }

//    @Test
//    public void wtb() {
//        String text = "Wed May 13 20:00:00 CST 2021";
//        String[] wtb = DateUtil.WTB;
//        assertTrue(StringUtil.containsIgnoreCase(text, wtb));
//    }

//    @Test
//    public void formatToDateTimeForDate() {
//        Date date = DateUtil.ofUtilDate(2018, 2, 28, 18, 0, 0);
//        String format = DateUtil.formatToDateTime(date);
//        assertEquals(format, "2018-02-28 18:00:00");
//    }
//
//    @Test
//    public void formatToTime() {
//        LocalDateTime now = LocalDateTime.now();
//        int year = now.getYear(),
//                month = now.getMonthValue(),
//                dayOfMonth = now.getDayOfMonth(),
//                hour = now.getHour(),
//                minute = now.getMinute(),
//                second = now.getSecond();
//        String expectedFormat =
//                (hour < 10 ? "0" : "") + hour +
//                        (minute < 10 ? ":0" : ":") + minute +
//                        (second < 10 ? ":0" : ":") + second;
//        String formatToTime = DateUtil.formatToTime();
//        assertEquals(formatToTime, expectedFormat);
//    }
//
//    @Test
//    public void parseToDate() {
//        final String strDate = "2021-01-01 00:00:00";
//        Date parseFromDate = DateUtil.parseToUtilDate(strDate);
//
//        Date date = DateUtil.ofUtilDate(2021, 1, 1);
//        assertEquals(parseFromDate, date);
//    }
//
//    @Test
//    public void parseToLocalDateTime() {
//        final String strDate = "2019/04/10 12:00:00";
//        final String pattern = "yyyy-MM-dd";
//        assertThrows(DateTimeParseException.class, () -> DateUtil.parseToLocalDateTime(strDate, pattern));
//    }
//

    @Test
    public void betweenDays() {
        long start = 1621061305000L, end = 1589525289000L;
        Date startDate = new Date(start), endDate = new Date(end);
        assertEquals(365, DateUtil.betweenDays(startDate, endDate));
    }

    @Test
    public void isLeapYear() {
        long currentTime = 1621061305000L; // 2021-5-15 14:48:25
        assertFalse(DateUtil.isLeapYear(new Date(currentTime)));
        currentTime = 1589525289000L; // 2020-5-15 14:48:09
        assertTrue(DateUtil.isLeapYear(new Date(currentTime)));
        currentTime = 4114046889000L; // 2100-5-15 14:48:09
        assertFalse(DateUtil.isLeapYear(new Date(currentTime)));
    }

    @Test
    public void getYear() {
        long currentTime = System.currentTimeMillis();
        Date currentDate = new Date(currentTime);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), ZoneIdConstant.ASIA_SHANGHHAI);
        assertEquals(localDateTime.getYear(), DateUtil.getYear(currentDate));
    }

    @Test
    public void getMonth() {
        long currentTime = System.currentTimeMillis();
        Date currentDate = new Date(currentTime);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), ZoneIdConstant.ASIA_SHANGHHAI);
        assertEquals(localDateTime.getMonthValue(), DateUtil.getMonth(currentDate));
    }

    @Test
    public void getDay() {
        long currentTime = System.currentTimeMillis();
        Date currentDate = new Date(currentTime);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), ZoneIdConstant.ASIA_SHANGHHAI);
        assertEquals(localDateTime.getDayOfMonth(), DateUtil.getDay(currentDate));
    }

    @Test
    public void get24Hour() {
        long currentTime = System.currentTimeMillis();
        Date currentDate = new Date(currentTime);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), ZoneIdConstant.ASIA_SHANGHHAI);
        assertEquals(localDateTime.getHour(), DateUtil.get24Hour(currentDate));
    }

    @Test
    public void getMinute() {
        long currentTime = System.currentTimeMillis();
        Date currentDate = new Date(currentTime);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), ZoneIdConstant.ASIA_SHANGHHAI);
        assertEquals(localDateTime.getMinute(), DateUtil.getMinute(currentDate));
    }

    @Test
    public void getSecond() {
        long currentTime = System.currentTimeMillis();
        Date currentDate = new Date(currentTime);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), ZoneIdConstant.ASIA_SHANGHHAI);
        assertEquals(localDateTime.getLong(ChronoField.SECOND_OF_MINUTE), DateUtil.getSecond(currentDate));
    }

    @Test
    public void getMilliSecond() {
        long currentTime = System.currentTimeMillis();
        Date currentDate = new Date(currentTime);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(currentTime), ZoneIdConstant.ASIA_SHANGHHAI);
        assertEquals(localDateTime.getLong(ChronoField.MILLI_OF_SECOND), DateUtil.getMilliSecond(currentDate));
    }

}
