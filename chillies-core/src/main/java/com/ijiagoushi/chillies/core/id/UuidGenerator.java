package com.ijiagoushi.chillies.core.id;

import com.ijiagoushi.chillies.core.lang.StringUtil;

import java.util.UUID;

/**
 * 基于{@linkplain UUID}的ID生成器
 *
 * @author miles.tang
 */
public class UuidGenerator implements IdGenerator<Object> {

    /**
     * 是否忽略中划线
     */
    protected final boolean ignoreDash;

    public UuidGenerator() {
        this(false);
    }

    public UuidGenerator(boolean ignoreDash) {
        this.ignoreDash = ignoreDash;
    }

    /**
     * 返回ID
     *
     * @param ignoreObj 参数
     * @return ID
     */
    @Override
    public String get(Object ignoreObj) {
        String id = UUID.randomUUID().toString();
        return ignoreDash ? StringUtil.replace(id, StringUtil.DASH, StringUtil.EMPTY_STRING) : id;
    }

    /**
     * 返回ID
     *
     * @return ID
     */
    @Override
    public String get() {
        return get(null);
    }

    /**
     * Holder
     */
    private static class Holder {
        static final UuidGenerator INSTANCE = new UuidGenerator();
        static final UuidGenerator INSTANCE_NO_DASH = new UuidGenerator(true);
    }

    /**
     * 返回一个默认的实例
     *
     * @return UUID Generator
     */
    public static UuidGenerator getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 返回一个不带-的默认实例
     *
     * @return UUID Generator
     */
    public static UuidGenerator getInstanceNoDash() {
        return Holder.INSTANCE_NO_DASH;
    }

}
