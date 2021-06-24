package com.ijiagoushi.chillies.core.convert;

/**
 * 无实现
 *
 * @author miles.tang
 */
public class NoopConverter extends AbstractConverter<Object, Object> {

    @Override
    public Object execInternal(Object input) {
        return input;
    }

    /**
     * 返回实际目标类型
     *
     * @return 实际目标类型
     */
    @Override
    public Class<Object> getTargetClass() {
        return Object.class;
    }

}
