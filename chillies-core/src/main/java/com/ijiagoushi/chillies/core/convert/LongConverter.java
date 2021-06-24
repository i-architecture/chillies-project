package com.ijiagoushi.chillies.core.convert;

import com.ijiagoushi.chillies.core.exceptions.ConverterRuntimeException;
import com.ijiagoushi.chillies.core.lang.NumberUtil;
import com.ijiagoushi.chillies.core.lang.StringUtil;

import java.util.Date;

/**
 * {@linkplain Long} 转换器
 *
 * @author miles.tang
 */
public class LongConverter extends AbstractConverter<Object, Long> {

    private static class Holder {
        static final LongConverter INSTANCE = new LongConverter();
    }

    public static LongConverter getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    protected Long execInternal(Object input) {
        if (input == null) {
            return null;
        } else if (input instanceof Boolean) {
            return ((Boolean) input) ? 1L : 0L;
        } else if (input instanceof Number) {
            return NumberUtil.convert((Number) input, Long.class);
        } else if (input instanceof String) {
            return NumberUtil.convert(NumberUtil.createNumber(input.toString()), Long.class);
        } else if (input instanceof Date) {
            return ((Date) input).getTime();
        }
        throw new ConverterRuntimeException(3, StringUtil.format("Can't cast {} to java.lang.Long", input));
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Long> getTargetClass() {
        return Long.class;
    }

    @Override
    public String toString() {
        return "LongConverter";
    }

}
