package com.ijiagoushi.chillies.easypoi.style;

import com.ijiagoushi.chillies.easypoi.config.ExcelCellHeaderConfig;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * Excel单元格样式处理器
 *
 * @author mzlion on 2016-06-20
 */
public interface ExcelCellStyleHandler {

    /**
     * 获取总标题的样式
     *
     * @return {@link CellStyle}
     */
    CellStyle getTitleCellStyle();

    /**
     * 获取二级标题的样式
     *
     * @return {@link CellStyle}
     */
    CellStyle getSecondTitleCellStyle();

    /**
     * 获取标题头的样式
     *
     * @param title     当前标题名称
     * @param cellIndex 所在位置
     * @param cellStyle 框架默认的样式
     * @return 返回样式
     */
    CellStyle getHeaderCellStyle(String title, int cellIndex, CellStyle cellStyle);

    /**
     * 获取数据单元格的样式
     *
     * @param rowIndex              当前行
     * @param originalBean          原对象
     * @param value                 填充的值
     * @param valueClass            填充值的类型
     * @param excelCellHeaderConfig 单元格的配置属性
     * @return 返回样式
     */
    CustomCellStyle getDataCellStyle(int rowIndex, Object originalBean, Object value, Class<?> valueClass, ExcelCellHeaderConfig excelCellHeaderConfig);

    CustomCellStyle getStatisticsCellStyle(int rowIndex, Object cellValue, Class<?> javaType, ExcelCellHeaderConfig excelCellHeaderConfig);

}
