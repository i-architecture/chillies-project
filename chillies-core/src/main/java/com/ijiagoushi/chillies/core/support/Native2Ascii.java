package com.ijiagoushi.chillies.core.support;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Convert Native to ASCII
 *
 * @author miles.tang at 2021-04-02
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Native2Ascii {

    /**
     * prefix of ascii string of native character
     */
    private static final String PREFIX = "\\u";

    private boolean reverse = false;

    public String exec(String src) {
        return reverse ? ascii2Native(src) : native2Ascii(src);
    }

    public String ascii2Native(String src) {
        StringBuilder buffer = new StringBuilder(src.length());
        int begin = 0, index = src.indexOf(PREFIX);
        while (index != -1) {
            buffer.append(src, begin, index);
            buffer.append(ascii2Char(src.substring(index, index + 6)));
            begin = index + 6;
            index = src.indexOf(PREFIX, begin);
        }
        buffer.append(src.substring(begin));
        return buffer.toString();
    }

    private char ascii2Char(String str) {
        if (str.length() != 6) {
            throw new IllegalArgumentException("Ascii string of a native character must be 6 character.");
        }
        if (!str.startsWith(PREFIX)) {
            throw new IllegalArgumentException("Ascii string of a native character must be start with \"\\u\".");
        }
        String tmp = str.substring(2, 4);
        int code = Integer.parseInt(tmp, 16) << 8;
        tmp = str.substring(4, 6);
        code += Integer.parseInt(tmp, 16);
        return (char) code;
    }

    public String native2Ascii(String src) {
        char[] chars = src.toCharArray();
        StringBuilder buffer = new StringBuilder();
        for (char ch : chars) {
            buffer.append(char2Ascii(ch));
        }
        return buffer.toString();
    }

    private String char2Ascii(char ch) {
        if (ch > 255) {
            StringBuilder buffer = new StringBuilder();
            buffer.append(PREFIX);
            int code = (ch >> 8);
            String tmp = Integer.toHexString(code);
            if (tmp.length() == 1) {
                buffer.append("0");
            }
            buffer.append(tmp);
            code = (ch & 0xFF);
            tmp = Integer.toHexString(code);
            if (tmp.length() == 1) {
                buffer.append("0");
            }
            buffer.append(tmp);
            return buffer.toString();
        } else {
            return Character.toString(ch);
        }
    }

}
