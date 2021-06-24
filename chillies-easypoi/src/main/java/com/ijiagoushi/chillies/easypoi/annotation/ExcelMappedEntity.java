package com.ijiagoushi.chillies.easypoi.annotation;

import java.lang.annotation.*;

/**
 * 针对JavaBean的属性类型也是JavaBean时注解
 *
 * @author mzlion on 2016-06-11.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelMappedEntity {

    /**
     * 需要解析属性列表，不可为空
     *
     * @return 返回需要解析的属性名列表
     */
    String[] propertyNames() default {"*"};

    /**
     * 对应的目标类型
     *
     * @return 对应的目标类型
     */
    Class<?> targetClass();

}
