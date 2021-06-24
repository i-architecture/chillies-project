package com.ijiagoushi.chillies.easypoi.excel;

import com.ijiagoushi.chillies.core.bean.BeanUtil;
import com.ijiagoushi.chillies.core.lang.CollectionUtil;
import com.ijiagoushi.chillies.core.lang.NumberUtil;
import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.core.utils.ClassUtil;
import com.ijiagoushi.chillies.easypoi.constant.ExcelCellType;
import com.ijiagoushi.chillies.easypoi.exceptions.ReflectionException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * 数据读取
 *
 * @param <E> 泛型类型
 * @author mzlion on 2016-06-17
 */
class DataRowReader<E> {
    //sfl4j
    private final Logger logger = LoggerFactory.getLogger(DataRowReader.class);
    private final Map<Integer, ReadExcelCellHeaderConfig> readExcelCellHeaderConfigMap;
    private final ExcelReaderEngine<E> excelReaderEngine;

    private ReadExcelCellHeaderConfig readExcelCellHeaderConfigIsExcelId;

    /**
     * 构建{@link DataRowReader}
     *
     * @param readExcelCellHeaderConfigList Excel cell和Java Property映射关系列表
     * @param excelReaderEngine             Excel解析引起的引用
     */
    DataRowReader(List<ReadExcelCellHeaderConfig> readExcelCellHeaderConfigList, ExcelReaderEngine<E> excelReaderEngine) {
        this.excelReaderEngine = excelReaderEngine;
        this.readExcelCellHeaderConfigMap = new TreeMap<>();
        for (ReadExcelCellHeaderConfig readExcelCellHeaderConfig : readExcelCellHeaderConfigList) {
            this.readExcelCellHeaderConfigMap.put(readExcelCellHeaderConfig.cellIndex, readExcelCellHeaderConfig);
            if (readExcelCellHeaderConfig.isExcelId)
                this.readExcelCellHeaderConfigIsExcelId = readExcelCellHeaderConfig;
        }
    }

    List<E> read(Iterator<Row> rowIterator, Sheet sheet) {
        if (ClassUtil.isAssignable(Map.class, this.excelReaderEngine.readExcelConfig.getRawClass())) {
            return this.doReadForMap(rowIterator, sheet);
        }
        return this.doReadForJavaBean(rowIterator, sheet);
    }

    @SuppressWarnings("unchecked")
    private List<E> doReadForMap(Iterator<Row> rowIterator, Sheet sheet) {
        List<Map<String, Object>> dataset = new ArrayList<>();
        Row row = null;
        while (rowIterator.hasNext() && (row == null || sheet.getLastRowNum() > row.getRowNum() + this.excelReaderEngine.readExcelConfig.getLastInvalidRow())) {
            row = rowIterator.next();
            Map<String, Object> entity = new HashMap<>();
            for (Map.Entry<Integer, ReadExcelCellHeaderConfig> entry : this.readExcelCellHeaderConfigMap.entrySet()) {
                Cell cell = row.getCell(entry.getKey());
                entity.put(entry.getValue().propertyName, this.getCellValue(cell, entry.getValue().excelCellType));
            }
            dataset.add(entity);
        }
        return (List<E>) dataset;
    }

    private List<E> doReadForJavaBean(Iterator<Row> rowIterator, Sheet sheet) {
        List<E> result = new ArrayList<>();
        Row row = null;
        while (rowIterator.hasNext() && (row == null || sheet.getLastRowNum() > row.getRowNum() + this.excelReaderEngine.readExcelConfig.getLastInvalidRow())) {
            try {
                row = rowIterator.next();
                E entity = (E) this.excelReaderEngine.readExcelConfig.getRawClass().newInstance();
                if (this.readExcelCellHeaderConfigIsExcelId != null) {
                    Cell excelIdCell = row.getCell(readExcelCellHeaderConfigIsExcelId.cellIndex);
                    CellRangeAddress cellRangeAddress = this.excelReaderEngine.getCellRangeAddress(sheet, excelIdCell.getRowIndex(), excelIdCell.getColumnIndex());
                    int skipRows = cellRangeAddress == null ? 0 : cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow();
                    for (Map.Entry<Integer, ReadExcelCellHeaderConfig> entry : this.readExcelCellHeaderConfigMap.entrySet()) {
                        ReadExcelCellHeaderConfig entryValue = entry.getValue();
                        List<ReadExcelCellHeaderConfig> children = entryValue.children;
                        if (CollectionUtil.isEmpty(children)) {
                            Cell cell = row.getCell(entry.getKey());
                            if (cell != null) this.invokeByEntity(entity, entryValue, cell);
                        } else {
                            PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(
                                    excelReaderEngine.readExcelConfig.getRawClass(), entryValue.propertyName);
                            Class<?> targetClass = entryValue.targetClass;
                            if (ClassUtil.isAssignable(Collection.class, propertyDescriptor.getPropertyType())) {
                                Collection<Object> collection;
                                if (ClassUtil.isAssignable(List.class, propertyDescriptor.getPropertyType())) {
                                    collection = new ArrayList<>();
                                } else {
                                    collection = new HashSet<>();
                                }
                                Object obj = targetClass.newInstance();
                                for (ReadExcelCellHeaderConfig child : children) {
                                    Cell subCell = row.getCell(child.cellIndex);
                                    if (subCell != null) this.invokeByEntity(obj, child, subCell);
                                }
                                collection.add(obj);
                                propertyDescriptor.getWriteMethod().invoke(entity, collection);
                                for (int i = 1; i <= skipRows; i++) {
                                    Row subRow = sheet.getRow(row.getRowNum() + i);
                                    obj = targetClass.newInstance();
                                    for (ReadExcelCellHeaderConfig child : children) {
                                        Cell subCell = subRow.getCell(child.cellIndex);
                                        if (subCell != null) this.invokeByEntity(obj, child, subCell);
                                    }
                                    collection.add(obj);
                                }
                            }
                        }
                    }
                    while (skipRows > 0) {
                        rowIterator.next();
                        skipRows--;
                    }
                } else {
                    for (Map.Entry<Integer, ReadExcelCellHeaderConfig> entry : this.readExcelCellHeaderConfigMap.entrySet()) {
                        Cell cell = row.getCell(entry.getKey());
                        if (cell != null) this.invokeByEntity(entity, entry.getValue(), cell);
                    }
                }
                result.add(entity);
            } catch (ReflectiveOperationException e) {
                throw new ReflectionException(e);
            }
        }
        return result;
    }

    private <T> void invokeByEntity(T entity, ReadExcelCellHeaderConfig readExcelCellHeaderConfig, Cell cell) {
        PropertyDescriptor propertyDescriptor = BeanUtil.getPropertyDescriptor(entity.getClass(), readExcelCellHeaderConfig.propertyName);
        Class<?> propertyTypeClass = propertyDescriptor.getPropertyType();
        Object cellValue = this.getCellValue(cell, readExcelCellHeaderConfig.excelCellType);
        if (cellValue == null) {
            return;
        }
        try {
            Method writeMethod = propertyDescriptor.getWriteMethod();
            if (Date.class.equals(propertyTypeClass)) {
                if (cellValue instanceof Date) {
                    writeMethod.invoke(entity, cellValue);
                } else if (cellValue instanceof String) {
                    if (StringUtil.hasLength(readExcelCellHeaderConfig.excelDateFormat)) {
                        writeMethod.invoke(entity, com.ijiagoushi.chillies.core.date.DateUtil.parse(
                                (String) cellValue, readExcelCellHeaderConfig.excelDateFormat));
                    } else {
                        logger.warn(" ===> That cell [{}] value is [{}] type mismatch java.util.Date on property [{}]",
                                new CellReference(cell.getRowIndex(), cell.getColumnIndex()).formatAsString(), cellValue, readExcelCellHeaderConfig.propertyName);
                    }
                } else {
                    logger.warn(" ===> That cell [{}] value is [{}] type mismatch java.util.Date on property [{}]",
                            new CellReference(cell.getRowIndex(), cell.getColumnIndex()).formatAsString(), cellValue, readExcelCellHeaderConfig.propertyName);
                }
            } else if (boolean.class.equals(propertyTypeClass) || Boolean.class.equals(propertyTypeClass)) {
                if (cellValue instanceof Boolean) {
                    writeMethod.invoke(entity, cellValue);
                } else if (cellValue instanceof String) {
                    writeMethod.invoke(entity, Boolean.parseBoolean((String) cellValue));
                } else {
                    logger.warn(" ===> That cell [{}] value is [{}] type mismatch java.util.Boolean on property [{}]",
                            new CellReference(cell.getRowIndex(), cell.getColumnIndex()).formatAsString(), cellValue, readExcelCellHeaderConfig.propertyName);
                }
            } else if (byte.class.equals(propertyTypeClass) || Byte.class.equals(propertyTypeClass)) {
                //ignore
            } else if (char.class.equals(propertyTypeClass) || Character.class.equals(propertyTypeClass)) {
                //ignore
            } else if (int.class.equals(propertyTypeClass) || Integer.class.equals(propertyTypeClass)) {
                writeMethod.invoke(entity, Integer.valueOf(String.valueOf(cellValue)));
            } else if (float.class.equals(propertyTypeClass) || Float.class.equals(propertyTypeClass)) {
                writeMethod.invoke(entity, Float.valueOf(String.valueOf(cellValue)));
            } else if (double.class.equals(propertyTypeClass) || Double.class.equals(propertyTypeClass)) {
                writeMethod.invoke(entity, Double.valueOf(String.valueOf(cellValue)));
            } else if (long.class.equals(propertyTypeClass) || Long.class.equals(propertyTypeClass)) {
                writeMethod.invoke(entity, Long.valueOf(String.valueOf(cellValue)));
            } else if (short.class.equals(propertyTypeClass) || Short.class.equals(propertyTypeClass)) {
                writeMethod.invoke(entity, Short.valueOf(String.valueOf(cellValue)));
            } else if (BigDecimal.class.equals(propertyTypeClass)) {
                writeMethod.invoke(entity, new BigDecimal(String.valueOf(cellValue)));
            } else if (String.class.equals(propertyTypeClass)) {
                if (cellValue instanceof String) {
                    writeMethod.invoke(entity, cellValue);
                } else if (cellValue instanceof Double) {
                    writeMethod.invoke(entity, NumberUtil.avoidScientificNotation(String.valueOf(cellValue)));
                } else {
                    writeMethod.invoke(entity, String.valueOf(cellValue));
                }
            } else {
                writeMethod.invoke(entity, cellValue);
            }
        } catch (ReflectiveOperationException e) {
            throw new ReflectionException(e);
        }
    }

    /**
     * 解析单元格的值
     *
     * @return 返回单元格的值
     */
    private Object getCellValue(Cell cell, ExcelCellType excelCellType) {
        switch (excelCellType) {
            case AUTO:
            case FORMULA:
                CellValue cellValue = this.excelReaderEngine.formulaEvaluator.evaluate(cell);
                if (cellValue == null) return null;
                switch (cellValue.getCellType()) {
                    case Cell.CELL_TYPE_BLANK:
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        return cellValue.getBooleanValue();
                    case Cell.CELL_TYPE_ERROR:
                        break;
                    //CELL_TYPE_FORMULA will never happen
                    case Cell.CELL_TYPE_FORMULA:
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            return DateUtil.getJavaDate(cellValue.getNumberValue());
                        }
                        return cellValue.getNumberValue();
                    case Cell.CELL_TYPE_STRING:
                        return cellValue.getStringValue();
                }
                break;
            case TEXT:
                cell.setCellType(Cell.CELL_TYPE_STRING);
                return cell.getStringCellValue();
            case HYPER_LINK:
                break;
        }
        return null;
    }

}
