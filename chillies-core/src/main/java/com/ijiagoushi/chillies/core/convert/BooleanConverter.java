package com.ijiagoushi.chillies.core.convert;

import com.ijiagoushi.chillies.core.lang.BooleanEvaluator;

/**
 * 布尔转换器
 *
 * @author miles.tang at 2021-01-22
 * @since 1.0
 */
public class BooleanConverter extends AbstractConverter<Object, Boolean> {

    // lazy, thread-safe instatiation
    private static class Holder {
        static final BooleanConverter INSTANCE = new BooleanConverter();
    }

    public static BooleanConverter getInstance() {
        return Holder.INSTANCE;
    }

    @Override
    protected Boolean execInternal(Object value) {
        if (value instanceof Number) {
            // 0为false，其它数字为true
            return 0 != ((Number) value).doubleValue();
        }
        return BooleanEvaluator.DEFAULT_TRUE_EVALUATOR.evalTrue(execToStr(value));
    }

    /**
     * 获取此类实现类的反省类型
     *
     * @return 此类的泛型类型，坑你为{@code null}
     */
    @Override
    public Class<Boolean> getTargetClass() {
        return Boolean.class;
    }

    @Override
    public String toString() {
        return "BooleanConverter";
    }

}
