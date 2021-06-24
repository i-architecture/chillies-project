package com.ijiagoushi.chillies.easypoi.annotation;

import java.lang.annotation.*;

/**
 * Excel标记注解，仅用于标记JavaBean是能够被该框架解析的
 *
 * @author mzlion on 2016-06-10
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ExcelEntity {


}
