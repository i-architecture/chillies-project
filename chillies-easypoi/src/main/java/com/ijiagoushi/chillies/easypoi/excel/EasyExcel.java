package com.ijiagoushi.chillies.easypoi.excel;

import com.ijiagoushi.chillies.core.io.FileUtil;
import com.ijiagoushi.chillies.core.io.IOUtil;
import com.ijiagoushi.chillies.core.lang.Preconditions;
import com.ijiagoushi.chillies.easypoi.config.ExcelWriterConfig;
import com.ijiagoushi.chillies.easypoi.config.ReadExcelConfig;
import com.ijiagoushi.chillies.easypoi.constant.ExcelType;
import com.ijiagoushi.chillies.easypoi.exceptions.WriteExcelException;

import java.io.*;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * Excel的处理工具类
 * </p>
 *
 * @author mzlion on 2016-06-07
 */
public class EasyExcel {

    /**
     * 读取Excel的内容，并且填充到集合中
     *
     * @param excelFile Excel文件
     * @param beanClass Java对象类型
     * @param <E>       泛型类型
     * @return 集合
     */
    public static <E> List<E> read(File excelFile, Class<E> beanClass) {
        //https://git.oschina.net/bingyulei007/bingExcel
        Preconditions.checkArgument(beanClass != null, "The bean class must not be null.");
        ReadExcelConfig readExcelConfig = new ReadExcelConfig.Builder()
                .rawClass(beanClass).build();
        return read(excelFile, readExcelConfig);
    }

    /**
     * 读取Excel的内容，并且填充到集合中
     *
     * @param excelFile       Excel文件
     * @param <E>             泛型类型
     * @param readExcelConfig Excel读取配置选项
     * @return 集合
     */
    public static <E> List<E> read(File excelFile, ReadExcelConfig readExcelConfig) {
        Preconditions.requireNonNull(excelFile, "ExcelEntity file must not be null.");
        FileInputStream in = FileUtil.openFileInputStream(excelFile);
        try {
            return read(in, readExcelConfig);
        } finally {
            //close file stream
            IOUtil.closeQuietly(in);
        }
    }

    /**
     * 读取Excel的内容，并且填充到集合中
     *
     * @param excelInputStream 必须是Excel的
     * @param beanClass        Java对象类型
     * @param <E>              泛型类型
     * @return 集合
     */
    public static <E> List<E> read(InputStream excelInputStream, Class<E> beanClass) {
        return read(excelInputStream, new ReadExcelConfig.Builder().rawClass(beanClass).build());
    }

    /**
     * 读取Excel的内容，并且填充到集合中
     *
     * @param excelInputStream 必须是Excel的
     * @param <E>              泛型类型
     * @param readExcelConfig  Excel读取配置选项
     * @return 集合
     */
    public static <E> List<E> read(InputStream excelInputStream, ReadExcelConfig readExcelConfig) {
        Preconditions.requireNonNull(excelInputStream, "ExcelEntity inputStream must not be null.");
        Preconditions.requireNonNull(readExcelConfig, "ExcelReadConfig must not be null.");
        ExcelReaderEngine<E> excelReaderEngine = new ExcelReaderEngine<>(readExcelConfig);
        return excelReaderEngine.read(excelInputStream);
    }

    /**
     * 将数据写入到Excel中
     *
     * @param dataSet   待写入的数据
     * @param title     Excel标题
     * @param beanClass JavaBean类型
     * @param output    Excel文件保存
     * @param <E>       泛型类型
     */
    public static <E> void write(Collection<E> dataSet, String title, Class<E> beanClass, File output) {
        ExcelWriterConfig writeExcelConfig = new ExcelWriterConfig.Builder()
                .rawClass(beanClass)
                .title(title)
                .build();
        write(dataSet, writeExcelConfig, output);
    }

    /**
     * 将数据写入到Excel中
     *
     * @param dataSet          待写入的数据
     * @param writeExcelConfig Excel导出配置参数对象
     * @param output           Excel文件保存
     * @param <E>              泛型类型
     */
    public static <E> void write(Collection<E> dataSet, ExcelWriterConfig writeExcelConfig, File output) {
        Preconditions.requireNonNull(output, "Output file must not be null.");
        Preconditions.requireNonNull(writeExcelConfig, "WriteExcelConfig must not be null.");
        FileOutputStream outputStream = null;
        try {
            outputStream = FileUtil.openFileOutputStream(output);
            String suffix = FileUtil.getFileExt(output);
            ExcelType excelType = ExcelType.parse(suffix);
            if (excelType == null) {
                throw new WriteExcelException("The file [" + output + "] is not a xls or xlsx suffix");
            }
            writeExcelConfig = writeExcelConfig.newBuilder().excelType(excelType).build();
            write(dataSet, writeExcelConfig, outputStream);
        } finally {
            IOUtil.closeQuietly(outputStream);
        }
    }

    /**
     * 将数据写入到Excel中
     *
     * @param dataSet          待写入的数据
     * @param writeExcelConfig Excel导出配置参数对象
     * @param output           Excel输出流
     * @param <E>              泛型类型
     */
    public static <E> void write(Collection<E> dataSet, ExcelWriterConfig writeExcelConfig, OutputStream output) {
        Preconditions.requireNotEmpty(dataSet, "The dataset must not be null or empty.");
        Preconditions.requireNonNull(writeExcelConfig, "WriteExcelConfig must not be null.");
        Preconditions.requireNonNull(output, "Output must not be null.");
        ExcelWriterEngine writeExcelEngine = new ExcelWriterEngine(writeExcelConfig);
        writeExcelEngine.write(dataSet, output);
    }

}
