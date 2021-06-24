package com.ijiagoushi.chillies.easypoi.style;

/**
 * 自定义单元格样式
 */
public class CustomCellStyle {

    /**
     * 字体名称
     */
    private String fontName;

    /**
     * 字体颜色
     */
    private short fontColor;

    /**
     * 字体大小
     */
    private short fontSize;

    /**
     * 字体加粗
     */
    private boolean bold;

    /**
     * 数据类型,int,double,date,string
     */
    private String dataType;

    /**
     * 重新赋值
     */
    private Object value;

    /**
     * 表格样式：上边框
     */
    private Border borderTop;

    /**
     * 表格样式：下边框
     */
    private Border borderBottom;

    /**
     * 表格样式：左边框
     */
    private Border borderLeft;

    /**
     * 表格样式：右边框
     */
    private Border borderRight;

    /**
     * 数据格式
     */
    private String format;

    /**
     * 左右对齐方式
     */
    private short alignment;

    /**
     * 垂直对齐方式
     */
    private short verticalAlignment;

    public short getAlignment() {
        return alignment;
    }

    public void setAlignment(short alignment) {
        this.alignment = alignment;
    }

    public short getVerticalAlignment() {
        return verticalAlignment;
    }

    public void setVerticalAlignment(short verticalAlignment) {
        this.verticalAlignment = verticalAlignment;
    }

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getDataType() {
        if (dataType == null)
            dataType = "string";
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public short getFontColor() {
        return fontColor;
    }

    public void setFontColor(short fontColor) {
        this.fontColor = fontColor;

    }

    public Integer getCacheKey() {
        StringBuilder builder = new StringBuilder();
        builder.append(getFontName()).append(isBold()).append(getFontSize()).append(getAlignment()).append(getVerticalAlignment())
                .append(getAlignment()).append(getBorderBottom()).append(getBorderLeft())
                .append(getBorderRight()).append(getBorderTop()).append(getFontColor())
                .append(getFormat()).append(getDataType());
        return builder.toString().hashCode();
    }

    public Border getBorderTop() {
        return borderTop;
    }

    public void setBorderTop(Border borderTop) {
        this.borderTop = borderTop;
    }

    public Border getBorderBottom() {
        return borderBottom;
    }

    public void setBorderBottom(Border borderBottom) {
        this.borderBottom = borderBottom;
    }

    public Border getBorderLeft() {
        return borderLeft;
    }

    public void setBorderLeft(Border borderLeft) {
        this.borderLeft = borderLeft;
    }

    public Border getBorderRight() {
        return borderRight;
    }

    public void setBorderRight(Border borderRight) {
        this.borderRight = borderRight;
    }

    public short getFontSize() {
        return fontSize;
    }

    public void setFontSize(short fontSize) {
        this.fontSize = fontSize;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public CustomCellStyle() {
    }

    public CustomCellStyle(CustomCellStyle parent) {
        this.fontName = parent.fontName;
        this.fontColor = parent.fontColor;
        this.fontSize = parent.fontSize;
        this.bold = parent.bold;
        this.dataType = parent.dataType;
        this.value = parent.value;
        this.borderTop = parent.borderTop;
        this.borderBottom = parent.borderBottom;
        this.borderLeft = parent.borderLeft;
        this.borderRight = parent.borderRight;
        this.format = parent.format;
        this.alignment = parent.alignment;
        this.verticalAlignment = parent.verticalAlignment;
    }

    public static class Border {
        private short size;

        private short color;

        public Border(short size, short color) {
            this.size = size;
            this.color = color;
        }

        public short getSize() {
            return size;
        }

        public void setSize(short size) {
            this.size = size;
        }

        public short getColor() {
            return color;
        }

        public void setColor(short color) {
            this.color = color;
        }

        public Border() {
        }

        @Override
        public String toString() {
            return "Border{" +
                    "size=" + size +
                    ", color=" + color +
                    '}';
        }
    }
}
