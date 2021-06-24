package com.ijiagoushi.chillies.easypoi.style;

import com.ijiagoushi.chillies.easypoi.config.ExcelCellHeaderConfig;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 框架默认实现的样式风格
 */
public class DefaultExcelCellStyleHandler implements ExcelCellStyleHandler {

    private Workbook workbook;
    private CustomCellStyle defaultCustomCellStyle;

    public DefaultExcelCellStyleHandler(Workbook workbook) {
        this.workbook = workbook;
        defaultCustomCellStyle = new CustomCellStyle();
        CustomCellStyle.Border border = new CustomCellStyle.Border();
        border.setSize(CellStyle.BORDER_THIN);
        defaultCustomCellStyle.setBorderTop(border);
        defaultCustomCellStyle.setBorderRight(border);
        defaultCustomCellStyle.setBorderBottom(border);
        defaultCustomCellStyle.setBorderLeft(border);
    }

    /**
     * 获取总标题的样式
     *
     * @return {@linkplain CellStyle}
     */
    @Override
    public CellStyle getTitleCellStyle() {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);

        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 16);
        font.setFontName("宋体");
        cellStyle.setFont(font);

        return cellStyle;
    }

    /**
     * 获取二级标题的样式
     *
     * @return {@link CellStyle}
     */
    @Override
    public CellStyle getSecondTitleCellStyle() {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);

        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);

        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 14);
        font.setFontName("宋体");
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 获取标题头的样式
     *
     * @param title     当前标题名称
     * @param cellIndex 所在位置
     * @param cellStyle 框架默认的样式
     * @return 返回样式
     */
    @Override
    public CellStyle getHeaderCellStyle(String title, int cellIndex, CellStyle cellStyle) {
        cellStyle = this.workbook.createCellStyle();
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);

        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);

        Font font = this.workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setFontName("宋体");
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * 获取数据单元格的样式
     *
     * @param rowIndex              当前行
     * @param value                 填充的值
     * @param valueClass            填充值的类型
     * @param excelCellHeaderConfig 单元格的配置属性
     * @return 返回样式
     */
    @Override
    public CustomCellStyle getDataCellStyle(int rowIndex, Object originalBean, Object value, Class<?> valueClass, ExcelCellHeaderConfig excelCellHeaderConfig) {
        return defaultCustomCellStyle;
    }

    @Override
    public CustomCellStyle getStatisticsCellStyle(int rowIndex, Object cellValue, Class<?> javaType, ExcelCellHeaderConfig excelCellHeaderConfig) {
        return defaultCustomCellStyle;
    }
}
