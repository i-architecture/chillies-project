package com.ijiagoushi.chillies.core.date;

import org.jetbrains.annotations.NotNull;

import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalField;

/**
 * TemporalAccessor Util
 *
 * @author miles.tang at 2021-05-12
 * @since 1.0
 */
public class TemporalAccessorUtil {

    /**
     * 安全获取时间的某个属性，属性不存在返回0
     *
     * @param accessor 需要获取的时间对象
     * @param field    需要获取的属性
     * @return 时间的值，如果无法获取则默认为 0
     */
    public static int get(@NotNull TemporalAccessor accessor, @NotNull TemporalField field) {
        return accessor.isSupported(field) ? accessor.get(field) : ((int) field.range().getMinimum());
    }

}
