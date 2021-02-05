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

}
