package com.ijiagoushi.chillies.core.date;

import com.ijiagoushi.chillies.core.lang.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;

/**
 * 日期计算
 *
 * @author miles.tang
 */
public class DateCalculator {

    private transient LocalDateTime localDateTime;

    public DateCalculator() {
        localDateTime = LocalDateTime.now();
    }

    public DateCalculator(@NotNull Date date) {
        Preconditions.requireNonNull(date, "date == null");
        localDateTime = LocalDateTimeUtil.of(date);
    }

    public DateCalculator(LocalDateTime localDateTime) {
        this.localDateTime = Preconditions.requireNonNull(localDateTime, "localDateTime == null");
    }

    public DateCalculator(LocalDate localDate) {
        localDateTime = Preconditions.requireNonNull(localDate, "localDate == null").atStartOfDay();
    }

    public DateCalculator(long epochMilli) {
        localDateTime = LocalDateTimeUtil.of(epochMilli);
    }

    public DateCalculator(int year, int month, int day, int hour, int minute, int second) {
        localDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
    }

    /**
     * 重新设置月份的第几天
     *
     * @param dayOfMonth 第几天
     * @return {@link DateCalculator}
     */
    public DateCalculator withDayOfMonth(int dayOfMonth) {
        localDateTime = localDateTime.withDayOfMonth(dayOfMonth);
        return this;
    }

    /**
     * 当前时间的月份的第一天
     *
     * @return {@link DateCalculator}
     */
    public DateCalculator firstDayOfMonth() {
        return withDayOfMonth(1);
    }

    /**
     * 当前时间的月份最后一天
     *
     * @return {@link DateCalculator}
     */
    public DateCalculator lastDayOfMonth() {
        localDateTime = localDateTime.with(TemporalAdjusters.lastDayOfMonth());
        return this;
    }

    /**
     * 对当前时间的调整
     *
     * @param adjuster 调整对象
     * @return {@link DateCalculator}
     * @see TemporalAdjusters
     */
    public DateCalculator with(@NotNull TemporalAdjuster adjuster) {
        localDateTime = localDateTime.with(adjuster);
        return this;
    }

    /**
     * 给指定日期加（减）年份数
     *
     * @return {@link DateCalculator}
     */
    public DateCalculator years(int years) {
        localDateTime = localDateTime.plusYears(years);
        return this;
    }

    /**
     * 给指定日期加（减）月数
     *
     * @param months 月，如果为正整数则添加，否则相减
     * @return {@link DateCalculator}
     */
    public DateCalculator months(int months) {
        localDateTime = localDateTime.plusMonths(months);
        return this;
    }

    /**
     * 给指定日期加（减）天数
     *
     * @param days 天，如果为正整数则添加，否则相减
     * @return {@link DateCalculator}
     */
    public DateCalculator days(int days) {
        localDateTime = localDateTime.plusDays(days);
        return this;
    }

    /**
     * 给指定日期加（减）小时数
     *
     * @param hours 小时，如果为正整数则添加，否则相减
     * @return {@link DateCalculator}
     */
    public DateCalculator hours(int hours) {
        localDateTime = localDateTime.plusHours(hours);
        return this;
    }

    /**
     * 给指定日期加（减）分钟数
     *
     * @param minutes 分钟，如果为正整数则添加，否则相减
     * @return {@link DateCalculator}
     */
    public DateCalculator minutes(int minutes) {
        localDateTime = localDateTime.plusMinutes(minutes);
        return this;
    }

    /**
     * 给指定日期加（减）秒数
     *
     * @param seconds 秒，如果为正整数则添加，否则相减
     * @return {@link DateCalculator}
     */
    public DateCalculator seconds(int seconds) {
        localDateTime = localDateTime.plusSeconds(seconds);
        return this;
    }

    /**
     * 返回秒
     *
     * @return 秒
     */
    public int getSecond() {
        return localDateTime.getSecond();
    }

    /**
     * 返回分钟
     *
     * @return 分钟
     */
    public int getMinute() {
        return localDateTime.getMinute();
    }

    /**
     * 返回小时
     *
     * @return 小时
     */
    public int getHour() {
        return localDateTime.getHour();
    }

    /**
     * 返回几号
     *
     * @return 天（几号）
     */
    public int getDay() {
        return localDateTime.getDayOfMonth();
    }

    /**
     * 返回月份值
     *
     * @return 月份值
     */
    public int getMonthValue() {
        return localDateTime.getMonthValue();
    }

    /**
     * 返回月份
     *
     * @return 月份
     */
    public Month getMonth() {
        return Month.of(getMonthValue());
    }

    /**
     * 返回周
     *
     * @return 周
     */
    public Week getWeek() {
        return Week.of(localDateTime.getDayOfWeek().getValue());
    }

    /**
     * 返回年
     *
     * @return 年份
     */
    public int getYear() {
        return localDateTime.getYear();
    }

    /**
     * 转为{@linkplain Date}类型的日期
     *
     * @return 日期
     */
    public Date toDate() {
        return LocalDateTimeUtil.toDate(localDateTime);
    }

    /**
     * 转为{@linkplain LocalDate}类型的日期
     *
     * @return 日期
     */
    public LocalDate toLocalDate() {
        return toLocalDateTime().toLocalDate();
    }

    /**
     * 转为{@linkplain LocalDateTime}类型的日期时间
     *
     * @return 日期时间
     */
    public LocalDateTime toLocalDateTime() {
        return localDateTime;
    }

    /**
     * 格式化日期时间
     *
     * @param pattern 格式化规则
     * @return 日期时间字符串
     */
    public String format(String pattern) {
        return LocalDateTimeUtil.format(localDateTime, pattern);
    }

    /**
     * 格式化日期时间
     *
     * @param formatter 格式化规则
     * @return 日期时间字符串
     */
    public String format(DateTimeFormatter formatter) {
        return LocalDateTimeUtil.format(localDateTime, formatter);
    }

    /**
     * 判断对象的日期时间是否在目标的日期时间之前
     *
     * @param other 目标日期时间
     * @return 如果是则返回{@code true}，否则返回{@code false}
     */
    public boolean isBefore(LocalDateTime other) {
        return localDateTime.isBefore(Preconditions.requireNonNull(other, "other == null"));
    }

    /**
     * 判断对象的日期时间是否在目标的日期时间之后
     *
     * @param other 目标日期时间
     * @return 如果是则返回{@code true}，否则返回{@code false}
     */
    public boolean isAfter(LocalDateTime other) {
        return localDateTime.isAfter(Preconditions.requireNonNull(other, "other == null"));
    }

    /**
     * 判断对象的日期时间是否在目标的日期时间之钱
     *
     * @param target 目标日期时间
     * @return 如果是则返回{@code true}，否则返回{@code false}
     */
    public boolean isBefore(@NotNull DateCalculator target) {
        return this.localDateTime.isBefore(Preconditions.requireNonNull(target, "other == null").localDateTime);
    }

    /**
     * 判断对象的日期时间是否在目标的日期时间之后
     *
     * @param target 目标日期时间
     * @return 如果是则返回{@code true}，否则返回{@code false}
     */
    public boolean isAfter(@NotNull DateCalculator target) {
        return this.localDateTime.isAfter(Preconditions.requireNonNull(target, "other == null").localDateTime);
    }

}
