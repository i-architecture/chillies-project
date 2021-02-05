package com.ijiagoushi.chillies.core.convert;

import com.ijiagoushi.chillies.core.lang.NumberUtil;

/**
 * 字节转换器
 *
 * @author miles.tang
 */
public class ByteConverter extends AbstractConverter<Object, Byte> {

    private static class Holder {
        static final ByteConverter INSTANCE = new ByteConverter();
    }

    public static ByteConverter getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    protected Byte execInternal(Object input) {
        Integer output = IntegerConverter.getInstance().execInternal(input);
        if (output == null) {
            return null;
        }
        return NumberUtil.convert(output, Byte.class);
    }

    @Override
    public String toString() {
        return "ByteConverter";
    }

}
