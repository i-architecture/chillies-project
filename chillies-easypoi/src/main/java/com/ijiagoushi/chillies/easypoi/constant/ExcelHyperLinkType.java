package com.ijiagoushi.chillies.easypoi.constant;

import org.apache.poi.sl.usermodel.Hyperlink;

/**
 * <p>
 * Excel单元格链接类型
 * </p>
 *
 * @author mzlion on 2016/6/13.
 */
public enum ExcelHyperLinkType {
    URL(Hyperlink.LINK_URL),
    EMAIL(Hyperlink.LINK_EMAIL),
    FILE(Hyperlink.LINK_FILE),;

    private final int type;

    ExcelHyperLinkType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
