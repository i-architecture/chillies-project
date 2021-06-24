package com.ijiagoushi.chillies.test;

import cn.hutool.core.convert.Convert;
import org.apache.commons.beanutils.ConvertUtils;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Date;

import static org.junit.Assert.assertNull;

/**
 * @author miles.tang at 2021-04-25
 * @since 1.0
 */
public class ConvertUtilTest {

    @Test
    public void toStr() {
//        Object obj1 = null;
//        assertNull(Convert.toStr(obj1));
//        assertEquals("default", Convert.toStr(obj1, "default"));

//        boolean bool1 = true;
//        assertEquals("true", Convert.toStr(bool1));

//        byte b1 = 10;
//        assertEquals("10", Convert.toStr(b1));
//
//        char ch1 = '项';
//        assertEquals("项", Convert.toStr(ch1));
//
//        Short s1 = 1280;
//        assertEquals("1280", Convert.toStr(s1));
//
//
//        Integer i1 = 100;
//        assertEquals("100", Convert.toStr(i1));
//
//        Float f1 = 15.5f;
//        assertEquals("15.5", Convert.toStr(f1));
//
//        Double d1 = 3.14;
//        assertEquals("3.14", Convert.toStr(d1));
//
//        Long l1 = 1990L;
//        assertEquals("1990", Convert.toStr(l1));
//
//        int i2 = 50;
//        assertEquals("50", Convert.toStr(i2));

        LocalDate localDate = LocalDate.now();
        String str = Convert.toStr(localDate);
        System.out.println("str = " + str);

        str = ConvertUtils.convert(localDate);
        System.out.println("str = " + str);

        Date date1 = new Date(System.currentTimeMillis());
        str = Convert.toStr(date1);
        System.out.println("str = " + str);

        str = ConvertUtils.convert(date1);
        System.out.println("str = " + str);

    }

    @Test
    public void toChar() {
    }

    @Test
    public void testToChar() {
    }

    @Test
    public void toByte() {
    }

    @Test
    public void testToByte() {
    }

    @Test
    public void toShort() {
    }

    @Test
    public void testToShort() {
    }

    @Test
    public void toInt() {
    }

    @Test
    public void testToInt() {
    }

    @Test
    public void toLong() {
    }

    @Test
    public void testToLong() {
    }

    @Test
    public void toDouble() {
    }

    @Test
    public void testToDouble() {
    }

    @Test
    public void toFloat() {
    }

    @Test
    public void testToFloat() {
    }

    @Test
    public void toBool() {
    }

    @Test
    public void testToBool() {
    }

    @Test
    public void toBigInteger() {
    }

    @Test
    public void testToBigInteger() {
    }

    @Test
    public void toBigDecimal() {
    }

    @Test
    public void testToBigDecimal() {
    }

    @Test
    public void toDate() {
//        Object obj1 = null;
//        assertNull(Convert.toDate(obj1));
//
//        int a1 = 1;
//
//        assertEquals(new Date(1L),Convert.toDate(a1));
//
//        float f1 = 10;
//        assertEquals(new Date(10), Convert.toDate(f1));
//
//        String str = "";
//        assertNull(Convert.toDate(str));

//        String str3 = "1990";
//        assertNull(Convert.toDate(str3));

        String str4 = "1990/1/01";
        Date result = Convert.toDate(str4);
        assertNull(result);

    }

    @Test
    public void testToDate() {
    }

    @Test
    public void toLocalDateTime() {
    }

    @Test
    public void testToLocalDateTime() {
    }

    @Test
    public void toLocalDate() {
    }

    @Test
    public void testToLocalDate() {
    }

    @Test
    public void toLocalTime() {
    }

    @Test
    public void testToLocalTime() {
    }

    @Test
    public void toInstant() {
    }

    @Test
    public void testToInstant() {
    }

    @Test
    public void convertQuietly() {
    }

    @Test
    public void doConvert() {
    }

    @Test
    public void toHexStr() {
    }

    @Test
    public void testToHexStr() {
    }

    @Test
    public void testToHexStr1() {
    }

    @Test
    public void hexToBytes() {
    }

    @Test
    public void hexToStr() {
    }
}