package com.ijiagoushi.chillies.core.convert;

import com.ijiagoushi.chillies.core.bean.BeanUtil;
import com.ijiagoushi.chillies.core.utils.ReflectUtil;

/**
 * Bean Converter
 *
 * @author miles.tang at 2021-01-25
 * @since 1.0
 */
public class BeanConverter<T> extends AbstractConverter<Object, T> {

    private final Class<T> beanClass;

    public BeanConverter(Class<T> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    protected T execInternal(Object value) {
        T result = ReflectUtil.tryNewInstance(beanClass);
        BeanUtil.copyProperties(value, result);
        return result;
    }

    @Override
    public Class<T> getTargetClass() {
        return beanClass;
    }
}
