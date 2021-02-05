package com.ijiagoushi.chillies.core.convert;

import com.ijiagoushi.chillies.core.exceptions.ConverterRuntimeException;
import com.ijiagoushi.chillies.core.lang.NumberUtil;
import com.ijiagoushi.chillies.core.lang.StringUtil;

/**
 * {@linkplain Double} 转换器
 *
 * @author miles.tang
 */
public class DoubleConverter extends AbstractConverter<Object, Double> {

    private static class Holder {
        static final DoubleConverter INSTANCE = new DoubleConverter();
    }

    public static DoubleConverter getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    protected Double execInternal(Object input) {
        if (input == null) {
            return null;
        }
        if (input instanceof Boolean) {
            return ((Boolean) input) ? 1D : 0D;
        } else if (input instanceof Number) {
            return NumberUtil.convert((Number) input, Double.class);
        } else if (input instanceof String) {
            return NumberUtil.convert(NumberUtil.createNumber(input.toString()), Double.class);
        }
        throw new ConverterRuntimeException(3, StringUtil.format("Can't cast {} to java.lang.Double", input));
    }

    @Override
    public String toString() {
        return "DoubleConverter";
    }

}
