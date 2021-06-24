package com.ijiagoushi.chillies.core.bean;

import com.ijiagoushi.chillies.core.lang.ArrayUtil;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 复制的配置
 *
 * @author miles.tang at 2021-01-17
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@SuperBuilder
public class CopyOption implements Serializable {

    /**
     * 是否忽略空值，当源对象的值为null时，true: 忽略而不注入此值，false: 注入null
     */
    @Builder.Default
    private boolean ignoreNullValue = false;

    /**
     * 忽略空字符串
     */
    @Builder.Default
    private boolean ignoreEmptyString = false;

    /**
     * 忽略的目标对象中属性列表，设置一个属性列表，不拷贝这些属性值
     */
    @Builder.Default
    private List<String> ignoreProperties = new ArrayList<>();

    public CopyOption(boolean ignoreNullValue) {
        this();
        this.ignoreNullValue = ignoreNullValue;
    }

    public CopyOption(String... ignoreProperties) {
        this();
        if (ArrayUtil.isNotEmpty(ignoreProperties)) {
            this.ignoreProperties.addAll(Arrays.asList(ignoreProperties));
        }
    }

    public CopyOption(boolean ignoreNullValue, String... ignoreProperties) {
        this(ignoreProperties);
        this.ignoreNullValue = ignoreNullValue;
    }

}
