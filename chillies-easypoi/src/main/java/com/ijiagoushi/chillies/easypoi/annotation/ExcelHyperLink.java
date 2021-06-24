package com.ijiagoushi.chillies.easypoi.annotation;

import com.ijiagoushi.chillies.easypoi.constant.ExcelHyperLinkType;

import java.lang.annotation.*;

/**
 * <p>
 * Excel cell hyperlink annotation.
 * </p>
 *
 * @author mzlion on 2016/6/13.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelHyperLink {

    /**
     * EExcel的Cell链接类型
     *
     * @return {@link ExcelHyperLinkType}
     */
    ExcelHyperLinkType value() default ExcelHyperLinkType.URL;

    /**
     * 链接名称
     *
     * @return 链接名称
     */
    String linkName() default "";
}
