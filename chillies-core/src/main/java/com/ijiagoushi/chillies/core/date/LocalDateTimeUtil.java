package com.ijiagoushi.chillies.core.date;

import com.ijiagoushi.chillies.core.lang.ObjectUtil;
import com.ijiagoushi.chillies.core.lang.Preconditions;
import com.ijiagoushi.chillies.core.lang.RegexUtil;
import com.ijiagoushi.chillies.core.lang.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

/**
 * LocalDateTime Util
 *
 * @author miles.tang at 2021-05-12
 * @since 1.0
 */
public class LocalDateTimeUtil {

    // region ============ Create LocalDateTime ============

    /**
     * 当前日期时间，默认时区
     *
     * @return {@link LocalDateTime}
     */
    @NotNull
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 当前日期，默认时区
     *
     * @return {@link LocalDate}
     */
    @NotNull
    public static LocalDate nowDate() {
        return LocalDate.now();
    }

    /**
     * 当前时间，默认时区
     *
     * @return {@link LocalTime}
     */
    @NotNull
    public static LocalTime nowTime() {
        return LocalTime.now();
    }

    /**
     * {@link Date}转{@link LocalDateTime}，使用默认时区
     *
     * @param date Date对象
     * @return {@link LocalDateTime}
     */
    @Nullable
    public static LocalDateTime of(@Nullable Date date) {
        if (date == null) {
            return null;
        }
        return of(date.toInstant());
    }

    /**
     * {@link Instant}转{@link LocalDateTime}，使用默认时区
     *
     * @param instant {@link Instant}
     * @return {@link LocalDateTime}
     */
    @Nullable
    public static LocalDateTime of(@Nullable Instant instant) {
        return of(instant, ZoneId.systemDefault());
    }

    /**
     * {@link Instant}转{@link LocalDateTime}
     *
     * @param instant {@link Instant}
     * @param zoneId  时区
     * @return {@link LocalDateTime}
     */
    @Nullable
    public static LocalDateTime of(@Nullable Instant instant, @Nullable ZoneId zoneId) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, ObjectUtil.getIfNull(zoneId, ZoneId.systemDefault()));
    }

    /**
     * 毫秒数转日期时间对象{@linkplain LocalDateTime}
     *
     * @param epochMilli 毫秒数，从1970-01-01 00:00:00开始
     * @return 日期时间对象
     */
    public static LocalDateTime of(final long epochMilli) {
        Preconditions.checkArgument(epochMilli >= 0, "epochMilli > 0");
        return of(Instant.ofEpochMilli(epochMilli));
    }

    /**
     * {@link TemporalAccessor}转{@link LocalDateTime}，使用默认时区
     *
     * @param temporalAccessor {@link TemporalAccessor}
     * @return {@link LocalDateTime}
     */
    @Nullable
    public static LocalDateTime of(@Nullable TemporalAccessor temporalAccessor) {
        if (null == temporalAccessor) {
            return null;
        } else if (temporalAccessor instanceof LocalDate) {
            return ((LocalDate) temporalAccessor).atStartOfDay();
        } else if (temporalAccessor instanceof LocalDateTime) {
            return (LocalDateTime) temporalAccessor;
        } else {
            return LocalDateTime.of(
                    TemporalAccessorUtil.get(temporalAccessor, ChronoField.YEAR),
                    TemporalAccessorUtil.get(temporalAccessor, ChronoField.MONTH_OF_YEAR),
                    TemporalAccessorUtil.get(temporalAccessor, ChronoField.DAY_OF_MONTH),
                    TemporalAccessorUtil.get(temporalAccessor, ChronoField.HOUR_OF_DAY),
                    TemporalAccessorUtil.get(temporalAccessor, ChronoField.MINUTE_OF_HOUR),
                    TemporalAccessorUtil.get(temporalAccessor, ChronoField.SECOND_OF_MINUTE),
                    TemporalAccessorUtil.get(temporalAccessor, ChronoField.NANO_OF_SECOND));
        }
    }

    // endregion

    // region ============ Parse ============

    /**
     * 解析日期时间字符串为{@link LocalDateTime}，仅支持yyyy-MM-dd'T'HH:mm:ss格式，例如：2007-12-03T10:15:30
     *
     * @param text 日期时间字符串
     * @return {@link LocalDateTime}
     */
    @Nullable
    public static LocalDateTime parse(@Nullable CharSequence text) {
        return parse(text, (DateTimeFormatter) null);
    }

    /**
     * 解析日期时间字符串为{@link LocalDateTime}，格式支持日期时间、日期、时间
     *
     * @param text      日期时间字符串
     * @param formatter 日期格式化器，预定义的格式见：{@link DateTimeFormatter}
     * @return {@link LocalDateTime}
     */
    @Nullable
    public static LocalDateTime parse(@Nullable CharSequence text, @Nullable DateTimeFormatter formatter) {
        if (StringUtil.isBlank(text)) {
            return null;
        }
        if (formatter == null) {
            return LocalDateTime.parse(text);
        }
        return of(formatter.parse(text));
    }

    /**
     * 解析日期时间字符串为{@link LocalDate}，格式支持日期时间、日期、时间
     *
     * @param text      日期时间字符串
     * @param formatter 日期格式化器，预定义的格式见：{@link DateTimeFormatter}
     * @return {@link LocalDate}
     */
    @Nullable
    public static LocalDate parseToDate(@Nullable CharSequence text, @Nullable DateTimeFormatter formatter) {
        LocalDateTime ldt = parse(text, formatter);
        return (null == ldt) ? null : ldt.toLocalDate();
    }

    /**
     * 解析日期时间字符串为{@link LocalDateTime}
     *
     * @param text    日期时间字符串
     * @param pattern 日期格式，类似于yyyy-MM-dd HH:mm:ss
     * @return {@link LocalDateTime}
     */
    @Nullable
    public static LocalDateTime parse(@Nullable CharSequence text, @Nullable String pattern) {
        if (StringUtil.isBlank(text)) {
            return null;
        }
        DateTimeFormatter formatter = null;
        if (StringUtil.hasText(pattern)) {
            // 修复yyyyMMddHHmmssSSS格式不能解析的问题
            //see https://stackoverflow.com/questions/22588051/is-java-time-failing-to-parse-fraction-of-second
            if (StringUtil.startsWithIgnoreCase(pattern, DatePattern.PURE_DATETIME_PATTERN)) {
                String fraction = StringUtil.deletePrefix(pattern, DatePattern.PURE_DATETIME_PATTERN);
                if (RegexUtil.isMatch("[S]{1,2}", fraction)) {
                    //将yyyyMMddHHmmssS、yyyyMMddHHmmssSS的日期统一替换为yyyyMMddHHmmssSSS格式，用0补
                    text += StringUtil.repeat('0', 3 - fraction.length());
                }
                formatter = new DateTimeFormatterBuilder()
                        .append(DatePattern.PURE_DATETIME_FORMATTER)
                        .appendValue(ChronoField.MILLI_OF_SECOND, 3)
                        .toFormatter();
            } else {
                formatter = DatePattern.ofPattern(pattern);
            }
        }
        return parse(text, formatter);
    }

    /**
     * 解析日期时间字符串为{@link LocalDate}
     *
     * @param text    日期时间字符串
     * @param pattern 日期格式，类似于yyyy-MM-dd
     * @return {@link LocalDate}
     */
    @Nullable
    public static LocalDate parseToDate(@Nullable CharSequence text, @Nullable String pattern) {
        LocalDateTime ldt = parse(text, pattern);
        return (ldt == null) ? null : ldt.toLocalDate();
    }

//    public static LocalDateTime parse(@Nullable CharSequence text, @NotNull String... patterns) {
//        if (StringUtil.isBlank(text) || ArrayUtil.isEmpty(patterns)) {
//            return null;
//        }
//
//    }

    // endregion


    // region ============ Format ============

    /**
     * 对当前日期时间格式化，格式为：yyyy-MM-dd HH:mm:ss
     *
     * @return 格式化后的字符串
     */
    public static String format() {
        return format(LocalDateTime.now());
    }

    /**
     * 对当前日期时间采用指定格式格式化
     *
     * @param pattern 格式，为空时默认采用格式：yyyy-MM-dd HH:mm:ss
     * @return 格式化后的字符串
     */
    public static String format(@Nullable String pattern) {
        return format(LocalDateTime.now(), pattern);
    }

    /**
     * 对当前日期时间采用指定格式化器格式化
     *
     * @param formatter 格式化器，为空默认采用 yyyy-MM-dd HH:mm:ss
     * @return 格式化后的字符串
     */
    public static String format(@Nullable DateTimeFormatter formatter) {
        return format(LocalDateTime.now(), formatter);
    }

    /**
     * 格式化日期时间为yyyy-MM-dd HH:mm:ss格式
     *
     * @param localDateTime 日期时间
     * @return 格式化后的字符串或{@code null}
     */
    public static String format(@Nullable LocalDateTime localDateTime) {
        return format(localDateTime, DatePattern.NORMAL_DATETIME_FORMATTER);
    }

    /**
     * 按指定格式格式化日期
     *
     * @param localDateTime 日期
     * @param pattern       格式，为空时采用yyyy-MM-dd HH:mm:ss
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(@Nullable LocalDateTime localDateTime, @Nullable String pattern) {
        if (localDateTime == null) {
            return null;
        }
        return format(localDateTime, StringUtil.isBlank(pattern) ? DatePattern.NORMAL_DATETIME_FORMATTER : DatePattern.ofPattern(pattern));
    }

    /**
     * 按指定格式格式化日期
     *
     * @param localDateTime 日期
     * @param formatter     格式，为空时采用yyyy-MM-dd HH:mm:ss
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(@Nullable LocalDateTime localDateTime, @Nullable DateTimeFormatter formatter) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.format(ObjectUtil.getIfNull(formatter, DatePattern.NORMAL_DATETIME_FORMATTER));
    }

    /**
     * 格式化日期时间为yyyy-MM-dd格式
     *
     * @param localDate 日期
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(@Nullable LocalDate localDate) {
        return format(localDate, DatePattern.NORMAL_DATE_FORMATTER);
    }

    /**
     * 按指定格式格式化日期
     *
     * @param localDate 日期
     * @param pattern   格式，为空时采用yyyy-MM-dd
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(@Nullable LocalDate localDate, @Nullable String pattern) {
        if (localDate == null) {
            return null;
        }
        return format(localDate, StringUtil.isBlank(pattern) ? DatePattern.NORMAL_DATE_FORMATTER : DatePattern.ofPattern(pattern));
    }

    /**
     * 按指定格式格式化日期
     *
     * @param localDate 日期
     * @param formatter 格式化器，为空时采用yyyy-MM-dd
     * @return 格式化后的日期字符串或{@code null}
     */
    public static String format(@Nullable LocalDate localDate, @Nullable DateTimeFormatter formatter) {
        if (localDate == null) {
            return null;
        }
        return localDate.format(ObjectUtil.getIfNull(formatter, DatePattern.NORMAL_DATE_FORMATTER));
    }

    // endregion

    /**
     * 将{@code LocalDateTime}转为时间戳
     *
     * @param dateTime 时间
     * @return 时间戳
     */
    public static long toEpochMilli(@NotNull LocalDateTime dateTime) {
        return toInstant(dateTime).toEpochMilli();
    }

    /**
     * p
     * 将{@linkplain LocalDateTime}转为{@linkplain Instant}
     * <p>采用北京时区作为转换默认时区</p>
     *
     * @param localDateTime 日期时间
     * @return 时刻
     */
    public static Instant toInstant(@Nullable LocalDateTime localDateTime) {
        return toInstant(localDateTime, null);
    }

    /**
     * 将{@linkplain LocalDateTime}转为{@linkplain Instant}
     *
     * @param localDateTime 日期时间
     * @param zoneId        指定时区
     * @return 时刻
     */
    public static Instant toInstant(@Nullable LocalDateTime localDateTime, @Nullable ZoneId zoneId) {
        if (ObjectUtil.isAnyNull(localDateTime, zoneId)) {
            return null;
        }
        return localDateTime.atZone(ObjectUtil.getIfNull(zoneId, ZoneId.systemDefault())).toInstant();
    }

    /**
     * 将{@linkplain LocalDateTime}转为{@linkplain Date}，采用默认时区
     *
     * @param localDateTime 日期时间
     * @return {@linkplain Date}
     */
    public static Date toDate(@Nullable LocalDateTime localDateTime) {
        return toDate(localDateTime, null);
    }

    /**
     * 将{@linkplain LocalDateTime}转为{@linkplain Date}
     *
     * @param localDateTime 日期时间
     * @param zoneOffset    时区
     * @return {@linkplain Date}
     */
    public static Date toDate(@Nullable LocalDateTime localDateTime, ZoneOffset zoneOffset) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.toInstant(ObjectUtil.getIfNull(zoneOffset, ZoneOffsetConstant.DEFAULT_ZONE_OFFSET)));
    }


    /**
     * 判断是否是闰年，闰年规则：<a href="http://zh.wikipedia.org/wiki/%E9%97%B0%E5%B9%B4">闰年查看</a>
     * <pre>
     *     比如时间2021-05-15 22:10:00  LocalDateTimeUtil.isLeapYear(localDateTime); false
     * </pre>
     *
     * @param localDateTime 日期对象
     * @return 是否为闰年
     */
    public static boolean isLeapYear(@Nullable LocalDateTime localDateTime) {
        return (localDateTime != null && isLeapYear(localDateTime.toLocalDate()));
    }

    /**
     * 判断是否是闰年，闰年规则：<a href="http://zh.wikipedia.org/wiki/%E9%97%B0%E5%B9%B4">闰年查看</a>
     * <pre>
     *     比如时间2021-05-15  LocalDateTimeUtil.isLeapYear(localDate); false
     * </pre>
     *
     * @param localDate 日期对象
     * @return 是否为闰年
     */
    public static boolean isLeapYear(@Nullable LocalDate localDate) {
        if (localDate == null) {
            return false;
        }
        return localDate.isLeapYear();
    }

    /**
     * 计算两个日期之间的相差天数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 相差的天数
     */
    public static int betweenDays(@NotNull Temporal start, @NotNull Temporal end) {
        return (int) ChronoUnit.DAYS.between(start, end);
    }


}
