package com.ijiagoushi.chillies.core.lang;

import org.jetbrains.annotations.Nullable;

/**
 * 校验
 *
 * @author miles.tang at 2021-03-10
 * @since 1.0
 */
public class Validator {

    /**
     * 验证是否为手机号码（中国）
     *
     * @param value 值
     * @return 是否为手机号码（中国）
     */
    public static boolean isMobile(@Nullable CharSequence value) {
        return RegexUtil.isMatch(Patterns.MOBILE_PHONE, value);
    }


}
