package com.ijiagoushi.chillies.core.utils;

import com.ijiagoushi.chillies.core.lang.Filter;

import java.lang.reflect.Field;

/**
 * Field过滤器
 *
 * @author miles.tang at 2021-03-15
 * @since 1.0
 */
public interface FieldFilter extends Filter<Field> {


    /**
     * 静态属性过滤器
     */
    StaticFieldFilter STATIC_FIELD_FILTER = new StaticFieldFilter();

}
