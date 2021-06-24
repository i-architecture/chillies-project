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

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Byte> getTargetClass() {
        return Byte.class;
    }

    @Override
    public String toString() {
        return "ByteConverter";
    }

}
