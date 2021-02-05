package com.ijiagoushi.chillies.core.convert;

import com.ijiagoushi.chillies.core.lang.NumberUtil;

/**
 * {@linkplain Float} 转换器
 *
 * @author miles.tang
 */
public class FloatConverter extends AbstractConverter<Object, Float> {

    private static class Holder {
        static final FloatConverter INSTANCE = new FloatConverter();
    }

    public static FloatConverter getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    protected Float execInternal(Object input) {
        Double output = DoubleConverter.getInstance().execInternal(input);
        return NumberUtil.convert(output, Float.class);
    }

    @Override
    public String toString() {
        return "FloatConverter";
    }

}
