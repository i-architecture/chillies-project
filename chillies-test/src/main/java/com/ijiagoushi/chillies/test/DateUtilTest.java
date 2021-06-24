package com.ijiagoushi.chillies.test;

import cn.hutool.core.date.DateUtil;
import org.junit.Test;

/**
 * @author miles.tang at 2021-05-11
 * @since 1.0
 */
public class DateUtilTest {

    @Test
    public void normalize() {
//        String dateStr = "2021年05月20";
        String dateStr = "2021-05-13T20:01:01+08:00";
        System.out.println(DateUtil.parse(dateStr));

        dateStr = "2021年05月20 22时11分22秒333";
        System.out.println(DateUtil.parse(dateStr).millisecond());
    }
}
