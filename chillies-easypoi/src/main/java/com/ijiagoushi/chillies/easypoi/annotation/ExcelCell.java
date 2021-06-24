package com.ijiagoushi.chillies.easypoi.annotation;

import com.ijiagoushi.chillies.easypoi.constant.ExcelCellType;

import java.lang.annotation.*;

/**
 * Excel的列注解，标记java属性和column的关系
 *
 * @author mzlion on 2016-06-11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelCell {

    /**
     * cell的标题
     *
     * @return cell的标题
     */
    String value();

//    /**
//     * 该cell 列是否是必须的
//     */
//    boolean required() default false;

    /**
     * cell填充或解析的数据类型
     *
     * @return {@link ExcelCellType}
     */
    ExcelCellType excelCellType() default ExcelCellType.AUTO;

    /**
     * 导出时cell的宽度，单位为字符，一个汉字为2个字符
     *
     * @return 单元格宽度
     */
    float width() default 8.38f;

    /**
     * 对应Excel的顺序
     *
     * @return cell的顺序
     */
    int order() default 0;

    /**
     * 是否换行 即支持\n
     *
     * @return {@code true}标识换行
     */
    boolean autoWrap() default false;

    /**
     * 在Excel文件日期的格式化风格,默认为空则表示忽略
     *
     * @return excel展示的日期格式化规则
     */
    String excelDateFormat() default "";

    /**
     * 在JavaBean日期的格式化风格，默认为空则表示忽略
     *
     * @return 在java中日期格式化规则
     */
    String javaDateFormat() default "";

}
