package com.ijiagoushi.chillies.core.convert;

import com.ijiagoushi.chillies.core.lang.BooleanUtil;
import com.ijiagoushi.chillies.core.lang.StringUtil;

/**
 * 字符转换器
 *
 * @author miles.tang at 2021-01-23
 * @since 1.0
 */
public class CharacterConverter extends AbstractConverter<Object, Character> {

    @Override
    protected Character execInternal(Object value) {
        if (value instanceof Boolean) {
            return BooleanUtil.toCharacter((Boolean) value);
        }
        final String valueStr = execToStr(value);
        if (StringUtil.hasText(valueStr)) {
            return valueStr.charAt(0);
        }
        return null;
    }

    private static class Holder {
        static final CharacterConverter INSTANCE = new CharacterConverter();
    }

    public static CharacterConverter getInstance() {
        return CharacterConverter.Holder.INSTANCE;
    }

}
