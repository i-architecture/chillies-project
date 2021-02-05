package com.ijiagoushi.chillies.core.lang;

import com.ijiagoushi.chillies.core.id.Base64IdGenerator;
import com.ijiagoushi.chillies.core.id.DateIdGenerator;
import com.ijiagoushi.chillies.core.id.SecureUuidGenerator;
import com.ijiagoushi.chillies.core.id.UuidGenerator;

/**
 * ID生成器工具类
 *
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
public class IdUtil {

    /**
     * 返回UUID
     *
     * @return UUID
     */
    public static String uuid() {
        return UuidGenerator.getInstance().get();
    }

    /**
     * 返回不带-的UUID
     *
     * @return UUID No Dash
     */
    public static String uuidNoDash() {
        return UuidGenerator.getInstanceNoDash().get();
    }

    /**
     * 返回UUID
     *
     * @return UUID
     */
    public static String secureUuid() {
        return SecureUuidGenerator.getInstance().get();
    }

    /**
     * 返回不带-的UUID
     *
     * @return UUID No Dash
     */
    public static String secureUuidNoDash() {
        return SecureUuidGenerator.getInstanceNoDash().get();
    }

    /**
     * 返回带有日期标识的随机ID
     *
     * @return id contains date
     */
    public static String dateId() {
        return DateIdGenerator.getInstance().get();
    }

    /**
     * 返回一个BASE64的ID
     *
     * @return id With BASE64
     */
    public static String base64Id() {
        return Base64IdGenerator.getInstance().get();
    }

}
