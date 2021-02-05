package com.ijiagoushi.chillies.core.convert;

import com.ijiagoushi.chillies.core.lang.NumberUtil;

/**
 * {@linkplain Short}转换器
 *
 * @author miles.tang
 */
public class ShortConverter extends AbstractConverter<Object, Short> {

    private static class Holder {
        static final ShortConverter INSTANCE = new ShortConverter();
    }

    public static ShortConverter getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    protected Short execInternal(Object input) {
        Integer output = IntegerConverter.getInstance().execInternal(input);
        return NumberUtil.convert(output, Short.class);
    }

    @Override
    public String toString() {
        return "ShortConverter";
    }

}
