package com.ijiagoushi.chillies.easypoi.constant;

/**
 * <p>
 * Excel support types(.xls,.xlsx)
 * </p>
 *
 * @author mzlion on 2016/6/7.
 */
public enum ExcelType {
    XLS("xls"), XLSX("xlsx");

    private String type;

    public String getType() {
        return type;
    }

    ExcelType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.type;
    }

    public static ExcelType parse(String type) {
        for (ExcelType excelType : ExcelType.values()) {
            if (excelType.type.equalsIgnoreCase(type)) {
                return excelType;
            }
        }
        return null;
    }
}
