package com.ijiagoushi.chillies.core.convert;

import com.ijiagoushi.chillies.core.lang.NumberUtil;

import java.math.BigDecimal;

/**
 * BigDecimal Converter
 *
 * @author miles.tang at 2021-01-23
 * @since 1.0
 */
public class BigDecimalConverter extends AbstractConverter<Object, BigDecimal> {

    @Override
    protected BigDecimal execInternal(Object value) {
        if (value instanceof Number) {
            return NumberUtil.createBigDecimal((Number) value);
        }
        return NumberUtil.createBigDecimal(execToStr(value));
    }

    private static class Holder {
        static final BigDecimalConverter INSTANCE = new BigDecimalConverter();
    }

    public static BigDecimalConverter getInstance() {
        return BigDecimalConverter.Holder.INSTANCE;
    }

}
