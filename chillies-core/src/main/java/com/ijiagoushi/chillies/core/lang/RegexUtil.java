package com.ijiagoushi.chillies.core.lang;

import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 *
 * @author miles.tang at 2021-05-12
 * @since 1.0
 */
public class RegexUtil {

    /**
     * 给定内容是否匹配正则表达式
     *
     * @param regex 正则表达式
     * @param cse   字符串
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    public static boolean isMatch(@Nullable String regex, @Nullable CharSequence cse) {
        if (StringUtil.isEmpty(regex)) {
            return false;
        }
        if (cse == null) {
            return false;
        }
        return isMatch(Patterns.get(regex), cse);
    }

    /**
     * 给定内容是否匹配正则
     *
     * @param pattern 模式
     * @param cse     字符串
     * @return 正则为null或者""则不检查，返回true，内容为null返回false
     */
    public static boolean isMatch(@Nullable Pattern pattern, @Nullable CharSequence cse) {
        if (ObjectUtil.isAnyNull(pattern, cse)) {
            return false;
        }
        return pattern.matcher(cse).matches();
    }


}
