package com.ijiagoushi.chillies.core.id;

import com.ijiagoushi.chillies.core.lang.StringUtil;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 较为安全的UUID，采用{@linkplain java.security.SecureRandom}获取更加安全的随机码，当然其性能相对慢一些。
 *
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
public class SecureUuidGenerator extends UuidGenerator {

    public SecureUuidGenerator() {
        super();
    }

    public SecureUuidGenerator(boolean ignoreDash) {
        super(ignoreDash);
    }

    @Override
    public String get(Object ignoreObj) {
        final byte[] randomBytes = new byte[16];
        Holder.SECURE_RANDOM.nextBytes(randomBytes);
        String id = UUID.nameUUIDFromBytes(randomBytes).toString();
        return ignoreDash ? StringUtil.replace(id, StringUtil.DASH, StringUtil.EMPTY_STRING) : id;
    }

    /**
     * Holder
     */
    private static class Holder {
        static final SecureUuidGenerator INSTANCE = new SecureUuidGenerator();
        static final SecureUuidGenerator INSTANCE_NO_DASH = new SecureUuidGenerator(true);
        static final SecureRandom SECURE_RANDOM = new SecureRandom();
    }

    /**
     * 返回一个默认的实例
     *
     * @return UUID Generator
     */
    public static SecureUuidGenerator getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 返回一个不带-的默认实例
     *
     * @return UUID Generator
     */
    public static SecureUuidGenerator getInstanceNoDash() {
        return Holder.INSTANCE_NO_DASH;
    }

}
