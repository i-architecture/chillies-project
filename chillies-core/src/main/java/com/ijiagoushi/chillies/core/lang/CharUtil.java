package com.ijiagoushi.chillies.core.lang;

/**
 * Character Utilities
 *
 * @author miles.tang
 */
public class CharUtil {

    private CharUtil() {
        throw new AssertionError("Cannot create instance!");
    }

    //region 定义的公共的字符相关的常量

    /**
     * 点
     */
    public static final char DOT = '.';

    /**
     * 逗号
     */
    public static final char COMMA = ',';

    /**
     * 下划线
     */
    public static final char UNDERLINE = '_';

    /**
     * 中划线/破折号
     */
    public static final char DASH = '-';

    /**
     * 斜杆
     */
    public static final char SLASH = '/';

    /**
     * 反斜杠
     */
    public static final char BACKSLASH = '\\';

    /**
     * 左边大括号
     */
    public static final char BIG_BRACKETS_START = '{';

    /**
     * 右边大括号
     */
    public static final char BIG_BRACKETS_END = '}';

    /**
     * 等号
     */
    public static final char EQUALS = '=';

    /**
     * 空格
     */
    public static final char SPACE = ' ';

    private static final int ASCII_LENGTH = 128;
    private static final String[] CACHE = new String[ASCII_LENGTH];

    static {
        for (char i = 0; i < ASCII_LENGTH; i++) {
            CACHE[i] = String.valueOf(i);
        }
    }

    //endregion

    /**
     * 是否为Windows或者Linux（Unix）文件分隔符<br>
     * Windows平台下分隔符为\，Linux（Unix）为/
     *
     * @param ch 字符
     * @return 是否为Windows或者Linux（Unix）文件分隔符
     */
    public static boolean isFileSeparator(char ch) {
        return BACKSLASH == ch || SLASH == ch;
    }

    /**
     * 给定对象对应的类是否为字符类，字符类包括：
     *
     * <pre>
     * Character.class
     * char.class
     * </pre>
     *
     * @param value 被检查的对象
     * @return true表示为字符类
     */
    public static boolean isChar(Object value) {
        //noinspection ConstantConditions
        return value instanceof Character || value.getClass().equals(char.class);
    }

    /**
     * 字符转为字符串
     *
     * @param ch 字符
     * @return 字符串
     */
    public static String toString(char ch) {
        return ch < ASCII_LENGTH ? CACHE[ch] : String.valueOf(ch);
    }

}
