package com.ijiagoushi.chillies.core.convert;

import com.ijiagoushi.chillies.core.lang.NumberUtil;

import java.math.BigInteger;

/**
 * BigInteger Converter
 *
 * @author miles.tang at 2021-01-23
 * @since 1.0
 */
public class BigIntegerConverter extends AbstractConverter<Object, BigInteger> {

    @Override
    protected BigInteger execInternal(Object value) {
        if (value instanceof Long) {
            return BigInteger.valueOf((Long) value);
        } else if (value instanceof Boolean) {
            return BigInteger.valueOf(((Boolean) value) ? 1 : 0);
        } else {
            return NumberUtil.createBigInteger(execToStr(value));
        }
    }

    private static class Holder {
        static final BigIntegerConverter INSTANCE = new BigIntegerConverter();
    }

    public static BigIntegerConverter getInstance() {
        return BigIntegerConverter.Holder.INSTANCE;
    }

}
