package com.ijiagoushi.chillies.easypoi.excel;

import com.ijiagoushi.chillies.core.bean.BeanUtil;
import com.ijiagoushi.chillies.core.date.DateUtil;
import com.ijiagoushi.chillies.core.lang.CollectionUtil;
import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.core.utils.ClassUtil;
import com.ijiagoushi.chillies.easypoi.exceptions.ReflectionException;
import com.ijiagoushi.chillies.easypoi.style.CustomCellStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据写入
 *
 * @param <E> 泛型类型
 * @author mzlion on 2016-06-20
 */
class DataRowWriter<E> {
    //sfl4j
    private final Logger logger = LoggerFactory.getLogger(DataRowWriter.class);
    private final ExcelWriterEngine writeExcelEngine;
    private Map<Integer, CellStyle> sharedCellStyles;
    private CellStyle defaultCellStyle;

    /**
     * 构建{@link DataRowReader}
     *
     * @param writeExcelEngine {@linkplain ExcelWriterEngine}的引用
     */
    DataRowWriter(ExcelWriterEngine writeExcelEngine) {
        this.writeExcelEngine = writeExcelEngine;
        this.sharedCellStyles = new ConcurrentHashMap<>();
        this.defaultCellStyle = writeExcelEngine.workbook.createCellStyle();
    }

    /**
     * 将数据写入Excel
     *
     * @param dataSet       待写入的数据集合
     * @param sheet         Excel's sheet
     * @param startRowIndex 数据行的起始行
     */
    @SuppressWarnings("unchecked")
    void write(Collection<E> dataSet, Sheet sheet, int startRowIndex) {
        logger.debug(" ===> The raw class is {}", this.writeExcelEngine.excelWriterConfig.getRawClass());
        if (ClassUtil.isAssignable(Map.class, this.writeExcelEngine.excelWriterConfig.getRawClass())) {
            this.doWriteForMap((Collection<Map<String, Object>>) dataSet, sheet, startRowIndex);
        } else {
            try {
                this.doWriteForBean(dataSet, sheet, startRowIndex);
            } catch (ReflectiveOperationException e) {
                throw new ReflectionException(e);
            }
        }
    }

    /**
     * 将JavaBean类型的数据集合写入Excel的Row
     * <p>
     * Map类型的数据仅支持简单格式
     * </p>
     *
     * @param dataSet 待写入的数据集合
     * @param sheet   Excel's sheet
     * @param index   数据行的起始行
     */
    private void doWriteForMap(Collection<Map<String, Object>> dataSet, Sheet sheet, int index) {
        for (Map<String, Object> entity : dataSet) {
            Row row = sheet.createRow(index++);
            Object value;
            Cell cell;
            for (ExcelWriterCellHeaderConfig writeExcelCellHeaderConfig : this.writeExcelEngine.excelWriterCellHeaderConfigList) {
                value = entity.get(writeExcelCellHeaderConfig.propertyName);
                cell = row.createCell(writeExcelCellHeaderConfig.cellIndex);
                this.setCell(cell, value, value != null ? value.getClass() : null, writeExcelCellHeaderConfig);
                if (this.writeExcelEngine.styleHandler != null) {
                    CustomCellStyle customCellStyle = this.writeExcelEngine.styleHandler.getDataCellStyle(cell.getRowIndex(), entity, value, value == null ? null : value.getClass(),
                            writeExcelCellHeaderConfig.convertToExcelCellHeaderConfig());
                    if (customCellStyle != null) {
                        CellStyle cellStyle = this.getCellStyle(customCellStyle);
                        cellStyle.setWrapText(writeExcelCellHeaderConfig.autoWrap);
                        cell.setCellStyle(cellStyle);
                    }
                }
            }
        }

    }

    /**
     * 将JavaBean类型的数据集合写入Excel的Row
     * <p>
     * 针对JavaBean支持较为复杂的行列关系
     * </p>
     *
     * @param dataSet       待写入的数据集合
     * @param sheet         Excel's sheet
     * @param startRowIndex 数据行的起始行
     * @throws ReflectiveOperationException 反射获取Bean的值抛出异常
     */
    @SuppressWarnings("unchecked")
    private void doWriteForBean(Collection<E> dataSet, Sheet sheet, int startRowIndex) throws ReflectiveOperationException {
        int index = startRowIndex;
        for (E entity : dataSet) {
            int maxMergeRowCount = 0;

            for (ExcelWriterCellHeaderConfig writeExcelCellHeaderConfig : this.writeExcelEngine.excelWriterCellHeaderConfigList) {
                if (CollectionUtil.isNotEmpty(writeExcelCellHeaderConfig.children)) {

                    PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(entity.getClass(), writeExcelCellHeaderConfig.propertyName);
                    //1.判断Bean是否存在集合，如果存在则取出最大的集合数量
                    if (ClassUtil.isAssignable(Collection.class, propertyDescriptor.getPropertyType())) {
                        Method readMethod = propertyDescriptor.getReadMethod();
                        Object value = readMethod.invoke(entity);
                        Collection<Object> collection = (Collection<Object>) value;
                        if (collection != null && maxMergeRowCount < collection.size())
                            maxMergeRowCount = collection.size();
                    }
                }
            }

            //2.不存在集合，按照普通的处理
            if (maxMergeRowCount == 0) {
                Row row = sheet.createRow(index++);
                for (ExcelWriterCellHeaderConfig writeExcelCellHeaderConfig : this.writeExcelEngine.excelWriterCellHeaderConfigList) {
                    PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(entity.getClass(), writeExcelCellHeaderConfig.propertyName);
                    Method readMethod = propertyDescriptor.getReadMethod();
                    Object value = readMethod.invoke(entity);

                    //判断类型是否是JavaBean
                    if (value != null && CollectionUtil.isNotEmpty(writeExcelCellHeaderConfig.children)) {
                        for (ExcelWriterCellHeaderConfig child : writeExcelCellHeaderConfig.children) {
                            PropertyDescriptor childPropertyDescriptor = BeanUtil.getPropertyDescriptor(value.getClass(), child.propertyName);
                            Object childValue = childPropertyDescriptor.getReadMethod().invoke(value);
                            Cell cell = row.createCell(child.cellIndex);
                            this.setCell(cell, childValue, childPropertyDescriptor.getPropertyType(), child);
                            if (this.writeExcelEngine.styleHandler != null) {
                                CustomCellStyle dataCellStyle = this.writeExcelEngine.styleHandler.getDataCellStyle(cell.getRowIndex(), entity, childValue,
                                        childPropertyDescriptor.getPropertyType(), child.convertToExcelCellHeaderConfig());
                                if (dataCellStyle != null) {
                                    CellStyle cellStyle = this.getCellStyle(dataCellStyle);
                                    cellStyle.setWrapText(writeExcelCellHeaderConfig.autoWrap);
                                    cell.setCellStyle(cellStyle);
                                }
                            }
                        }
                    } else {
                        Cell cell = row.createCell(writeExcelCellHeaderConfig.cellIndex);
                        this.setCell(cell, value, propertyDescriptor.getPropertyType(), writeExcelCellHeaderConfig);
                        if (this.writeExcelEngine.styleHandler != null) {
                            CustomCellStyle dataCellStyle = this.writeExcelEngine.styleHandler.getDataCellStyle(cell.getRowIndex(), entity, value, readMethod.getReturnType(),
                                    writeExcelCellHeaderConfig.convertToExcelCellHeaderConfig());
                            if (dataCellStyle != null) {
                                CellStyle cellStyle = this.getCellStyle(dataCellStyle);
                                cellStyle.setWrapText(writeExcelCellHeaderConfig.autoWrap);
                                cell.setCellStyle(cellStyle);
                            }
                        }
                    }
                }
            } else {
                //3.存在集合
                List<Row> rows = new ArrayList<>(maxMergeRowCount);
                //3.1预创建当前Bean对象值占用的行数
                for (int i = 0; i < maxMergeRowCount; i++) {
                    rows.add(sheet.createRow(index++));
                }
                for (ExcelWriterCellHeaderConfig writeExcelCellHeaderConfig : this.writeExcelEngine.excelWriterCellHeaderConfigList) {
                    PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(entity.getClass(), writeExcelCellHeaderConfig.propertyName);
                    Method readMethod = propertyDescriptor.getReadMethod();
                    Object value = readMethod.invoke(entity);
                    List<ExcelWriterCellHeaderConfig> children = writeExcelCellHeaderConfig.children;
                    //值不为空
                    if (value != null && CollectionUtil.isNotEmpty(children)) {
                        if (ClassUtil.isAssignable(Collection.class, propertyDescriptor.getPropertyType())) {
                            Collection<Object> collection = (Collection<Object>) value;
                            int innerIndex = 0;
                            for (Object obj : collection) {
                                Row row = rows.get(innerIndex);
                                for (ExcelWriterCellHeaderConfig child : children) {
                                    PropertyDescriptor childPropertyDescriptor = BeanUtil.getPropertyDescriptor(obj.getClass(), child.propertyName);
                                    Method childReadMethod = childPropertyDescriptor.getReadMethod();
                                    Cell cell = row.createCell(child.cellIndex);
                                    Object childValue = childReadMethod.invoke(obj);
                                    this.setCell(cell, childValue, childPropertyDescriptor.getPropertyType(), child);
                                    if (this.writeExcelEngine.styleHandler != null) {
                                        CustomCellStyle dataCellStyle = this.writeExcelEngine.styleHandler.getDataCellStyle(cell.getRowIndex(),
                                                entity, childValue, childPropertyDescriptor.getPropertyType(), child.convertToExcelCellHeaderConfig());
                                        if (dataCellStyle != null) {
                                            CellStyle cellStyle = this.getCellStyle(dataCellStyle);
                                            cellStyle.setWrapText(writeExcelCellHeaderConfig.autoWrap);
                                            cell.setCellStyle(cellStyle);
                                        }
                                    }
                                }
                                innerIndex++;
                            }
                            //当集合不是最大时，应该自动创建空单元格
                            if (innerIndex < maxMergeRowCount) {
                                for (; innerIndex < maxMergeRowCount; innerIndex++) {
                                    Row row = rows.get(innerIndex);
                                    for (ExcelWriterCellHeaderConfig child : children) {
                                        Cell cell = row.createCell(child.cellIndex);
                                        this.setCell(cell, null, null, child);
                                        CustomCellStyle dataCellStyle = this.writeExcelEngine.styleHandler.getDataCellStyle(cell.getRowIndex(), entity, null,
                                                propertyDescriptor.getPropertyType(), child.convertToExcelCellHeaderConfig());
                                        if (dataCellStyle != null) {
                                            CellStyle cellStyle = this.getCellStyle(dataCellStyle);
                                            cellStyle.setWrapText(writeExcelCellHeaderConfig.autoWrap);
                                            cell.setCellStyle(cellStyle);
                                        }
                                    }
                                }

                            }
                        }
                    } else {
                        for (Row row : rows) {
                            Cell cell = row.createCell(writeExcelCellHeaderConfig.cellIndex);
                            this.setCell(cell, value, propertyDescriptor.getPropertyType(), writeExcelCellHeaderConfig);
                            if (this.writeExcelEngine.styleHandler != null) {
                                CustomCellStyle dataCellStyle = this.writeExcelEngine.styleHandler.getDataCellStyle(cell.getRowIndex(), entity, value,
                                        propertyDescriptor.getPropertyType(), writeExcelCellHeaderConfig.convertToExcelCellHeaderConfig());
                                if (dataCellStyle != null) {
                                    CellStyle cellStyle = this.getCellStyle(dataCellStyle);
                                    cellStyle.setWrapText(writeExcelCellHeaderConfig.autoWrap);
                                    cell.setCellStyle(cellStyle);
                                }
                            }
                        }
                        if (rows.size() > 1)
                            sheet.addMergedRegion(new CellRangeAddress(rows.get(0).getRowNum(), rows.get(rows.size() - 1).getRowNum(),
                                    writeExcelCellHeaderConfig.cellIndex, writeExcelCellHeaderConfig.cellIndex));
                    }
                }
            }
        }

        //统计
    }

    /**
     * 将JavaBean属性/Map的值写入Excel的单元格里，并且会创建一个默认样式
     *
     * @param cell                       单元格
     * @param value                      待写入的值
     * @param valueType                  值的类型
     * @param writeExcelCellHeaderConfig 写入Cell的配置选项
     */
    private void setCell(Cell cell, Object value, Class<?> valueType, ExcelWriterCellHeaderConfig writeExcelCellHeaderConfig) {
//        cell.setCellStyle(this.writeExcelEngine.workbook.createCellStyle());
        //1.判断是否为空
        if (value == null) {
            return;
        }

        //2.根据配置的{@code ExcelCellType}进行分流处理
        switch (writeExcelCellHeaderConfig.excelCellType) {
            case AUTO:
                if (valueType.equals(int.class) || valueType.equals(Integer.class) ||
                        valueType.equals(float.class) || valueType.equals(Float.class) ||
                        valueType.equals(double.class) || valueType.equals(Double.class) ||
                        valueType.equals(long.class) || valueType.equals(Long.class) ||
                        valueType.equals(short.class) || valueType.equals(Short.class) ||
                        valueType.equals(BigDecimal.class)) {
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    Number number = (Number) value;
                    cell.setCellValue(number.doubleValue());
                } else if (valueType.equals(boolean.class) || valueType.equals(Boolean.class)) {
                    cell.setCellType(Cell.CELL_TYPE_BOOLEAN);
                    cell.setCellValue((Boolean) value);
                } else if (valueType.equals(String.class)) {
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    if (StringUtil.hasLength(writeExcelCellHeaderConfig.excelDateFormat) &&
                            StringUtil.hasLength(writeExcelCellHeaderConfig.javaDateFormat))
                        cell.setCellValue(DateUtil.swapFormat(value.toString(), writeExcelCellHeaderConfig.javaDateFormat, writeExcelCellHeaderConfig.excelDateFormat));
                    else cell.setCellValue(value.toString());
                    //cell.getCellStyle().setDataFormat(this.writeExcelEngine.workbook.getCreationHelper().createDataFormat().getFormat("@"));
                } else if (valueType.equals(Date.class)) {
                    if (StringUtil.hasLength(writeExcelCellHeaderConfig.excelDateFormat)) {
                        cell.setCellValue((Date) value);
                        //cell.getCellStyle().setDataFormat(this.writeExcelEngine.workbook.getCreationHelper().createDataFormat().getFormat(writeExcelCellHeaderConfig.excelDateFormat));
                    } else {
                        cell.setCellValue((Date) value);
                    }
                } else {
                    cell.setCellValue(value.toString());
                }
                break;
            case TEXT:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cell.setCellValue(value.toString());
                //cell.getCellStyle().setDataFormat(this.writeExcelEngine.workbook.getCreationHelper().createDataFormat().getFormat("@"));
                break;
            case HYPER_LINK:
//                if (returnType.equals(String.class)) {
//                    String link = (String) value;
//                    cell.setCellValue(link);
//                    Hyperlink hyperlink = this.excelWriterEngine.workbook.getCreationHelper().createHyperlink(propertyCellMapping.getExcelHyperLinkType().getType());
//                    hyperlink.setAddress(link);
//                    hyperlink.setLabel(StringUtils.isEmpty(propertyCellMapping.getHyperlinkName()) ? link : propertyCellMapping.getHyperlinkName());
//                    cell.setHyperlink(hyperlink);
//                } else {
//                    logger.warn(" ===> This value [propertyName={},value={}] convert to String.class failed.", propertyCellMapping.getPropertyName(), value);
//                }
                break;
            case FORMULA:
                cell.setCellType(Cell.CELL_TYPE_FORMULA);
                cell.setCellFormula(value.toString());
                break;
            default:
                cell.setCellType(Cell.CELL_TYPE_BLANK);
                break;
        }

    }

    private CellStyle getCellStyle(CustomCellStyle customCellStyle) {
        CellStyle cellStyle = this.sharedCellStyles.get(customCellStyle.getCacheKey());
        if (cellStyle == null) {
            Workbook workbook = this.writeExcelEngine.workbook;
            cellStyle = workbook.createCellStyle();
            //字体
            if (customCellStyle.getFontName() != null || customCellStyle.getFontColor() != 0) {
                Font font = workbook.createFont();
                if (customCellStyle.getFontName() != null) {
                    font.setFontName(customCellStyle.getFontName());
                }
                if (customCellStyle.getFontColor() != 0) {
                    font.setColor(customCellStyle.getFontColor());
                }
                font.setBold(customCellStyle.isBold());
                if (customCellStyle.getFontSize() != 0) font.setFontHeightInPoints(customCellStyle.getFontSize());
                cellStyle.setFont(font);
            }
            //表格
            if (customCellStyle.getBorderTop() != null) {
                cellStyle.setBorderTop(customCellStyle.getBorderTop().getSize());
                cellStyle.setTopBorderColor(customCellStyle.getBorderTop().getColor());
            }
            if (customCellStyle.getBorderBottom() != null) {
                cellStyle.setBorderBottom(customCellStyle.getBorderBottom().getSize());
                cellStyle.setBottomBorderColor(customCellStyle.getBorderBottom().getColor());
            }
            if (customCellStyle.getBorderLeft() != null) {
                cellStyle.setBorderLeft(customCellStyle.getBorderTop().getSize());
                cellStyle.setLeftBorderColor(customCellStyle.getBorderTop().getColor());
            }
            if (customCellStyle.getBorderRight() != null) {
                cellStyle.setBorderRight(customCellStyle.getBorderTop().getSize());
                cellStyle.setRightBorderColor(customCellStyle.getBorderTop().getColor());
            }
            //数据格式
            if (customCellStyle.getFormat() != null) {
                DataFormat dataFormat = workbook.createDataFormat();
                cellStyle.setDataFormat(dataFormat.getFormat(customCellStyle.getFormat()));
            }
            // 水平
            cellStyle.setAlignment(customCellStyle.getAlignment());
            // 垂直
            cellStyle.setVerticalAlignment(customCellStyle.getVerticalAlignment());
            //放入缓存
            this.sharedCellStyles.put(customCellStyle.getCacheKey(), cellStyle);
        }
        return cellStyle;
    }
}
