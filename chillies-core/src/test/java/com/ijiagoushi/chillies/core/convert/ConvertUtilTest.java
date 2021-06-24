package com.ijiagoushi.chillies.core.convert;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author miles.tang at 2021-04-25
 * @since 1.0
 */
public class ConvertUtilTest {

    @Test
    public void toStr() {
        Object obj1 = null;
        assertNull(ConvertUtil.toStr(obj1));
        assertEquals("default", ConvertUtil.toStr(obj1, "default"));

        boolean bool1 = true;
        assertEquals("true", ConvertUtil.toStr(bool1));

        byte b1 = 10;
        assertEquals("10", ConvertUtil.toStr(b1));

        char ch1 = '项';
        assertEquals("项", ConvertUtil.toStr(ch1));

        Short s1 = 1280;
        assertEquals("1280", ConvertUtil.toStr(s1));


        Integer i1 = 100;
        assertEquals("100", ConvertUtil.toStr(i1));

        Float f1 = 15.5f;
        assertEquals("15.5", ConvertUtil.toStr(f1));

        Double d1 = 3.14;
        assertEquals("3.14", ConvertUtil.toStr(d1));

        Long l1 = 1990L;
        assertEquals("1990", ConvertUtil.toStr(l1));

        int i2 = 50;
        assertEquals("50", ConvertUtil.toStr(i2));

        BigInteger bi1 = new BigInteger("999999999999999999999999");
        assertEquals("999999999999999999999999", ConvertUtil.toStr(bi1));

        BigDecimal bd1 = new BigDecimal("999999999999999999999999.99");
        assertEquals("999999999999999999999999.99", ConvertUtil.toStr(bd1));

    }

    @Test
    public void toChar() {
        Object obj1 = null;
        assertNull(ConvertUtil.toChar(obj1));
        assertEquals('D', ConvertUtil.toChar(obj1, 'D'));

        boolean bool1 = true;
        assertEquals('\u0001', ConvertUtil.toChar(bool1));
        bool1 = false;
        assertEquals('\u0000', ConvertUtil.toChar(bool1));

        byte b1 = 10;
        assertEquals("10", ConvertUtil.toStr(b1));

        char ch1 = '项';
        assertEquals("项", ConvertUtil.toStr(ch1));

        Short s1 = 1280;
        assertEquals("1280", ConvertUtil.toStr(s1));


        Integer i1 = 100;
        assertEquals("100", ConvertUtil.toStr(i1));

        Float f1 = 15.5f;
        assertEquals("15.5", ConvertUtil.toStr(f1));

        Double d1 = 3.141592;
        assertEquals("3.141592", ConvertUtil.toStr(d1));

        Long l1 = 1990L;
        assertEquals("1990", ConvertUtil.toStr(l1));

        int i2 = 50;
        assertEquals("50", ConvertUtil.toStr(i2));

        BigInteger bi1 = new BigInteger("999999999999999999999999");
        assertEquals("999999999999999999999999", ConvertUtil.toStr(bi1));

        BigDecimal bd1 = new BigDecimal("999999999999999999999999.99");
        assertEquals("999999999999999999999999.99", ConvertUtil.toStr(bd1));

    }

    @Test
    public void toByte() {
        Object obj1 = null;
        assertNull(ConvertUtil.toChar(obj1));
        assertEquals((byte) 0, ConvertUtil.toByte(obj1, (byte) 0));

        boolean bool1 = true;
        assertEquals((byte) 1, ConvertUtil.toByte(bool1));
        bool1 = false;
        assertEquals((byte) 0, ConvertUtil.toByte(bool1));

        byte b1 = 10;
        assertEquals((byte) 10, ConvertUtil.toByte(b1));

        char ch1 = '项';
        assertNull(ConvertUtil.toByte(ch1));

        //1200 已经超出byte取值范围 [-128,127]
        Short s1 = 1280;
        assertNull(ConvertUtil.toByte(s1));

        Integer i1 = 100;
        assertEquals((byte) 100, ConvertUtil.toByte(i1));

        Float f1 = 15.5f;
        assertEquals((byte) 15, ConvertUtil.toByte(f1));

        Double d1 = 3.141592;
        assertEquals((byte) 3, ConvertUtil.toByte(d1));

        Long l1 = 1990L;
        assertNull(ConvertUtil.toByte(l1));

        int i2 = 50;
        assertEquals((byte) 50, ConvertUtil.toByte(i2));

        BigInteger bi1 = new BigInteger("999999999999999999999999");
        assertNull(ConvertUtil.toByte(bi1));

        BigDecimal bd1 = new BigDecimal("999999999999999999999999.99");
        assertNull(ConvertUtil.toByte(bd1));

    }

    @Test
    public void toShort() {
        Object obj1 = null;
        assertNull(ConvertUtil.toShort(obj1));
        assertEquals((short) 0, ConvertUtil.toShort(obj1, (short) 0));

        boolean bool1 = true;
        assertEquals((short) 1, ConvertUtil.toShort(bool1));
        bool1 = false;
        assertEquals((short) 0, ConvertUtil.toShort(bool1));

        byte b1 = 10;
        assertEquals((short) 10, ConvertUtil.toShort(b1));

        char ch1 = '项';
        assertNull(ConvertUtil.toShort(ch1));

        Short s1 = 1200;
        assertEquals((short) 1200, ConvertUtil.toShort(s1));

        Integer i1 = 100;
        assertEquals((short) 100, ConvertUtil.toShort(i1));

        Float f1 = 15.5f;
        assertEquals((short) 15, ConvertUtil.toShort(f1));

        Double d1 = 3.141592;
        assertEquals((short) 3, ConvertUtil.toShort(d1));

        Long l1 = 1990L;
        assertEquals((short) 1990, ConvertUtil.toShort(l1));

        int i2 = 50;
        assertEquals((short) 50, ConvertUtil.toShort(i2));

        BigInteger bi1 = new BigInteger("999999999999999999999999");
        assertNull(ConvertUtil.toShort(bi1));

        BigDecimal bd1 = new BigDecimal("999999999999999999999999.99");
        assertNull(ConvertUtil.toShort(bd1));

    }

    @Test
    public void toInt() {
        Object obj1 = null;
        assertNull(ConvertUtil.toInt(obj1));
        assertEquals(0, ConvertUtil.toInt(obj1, 0));

        boolean bool1 = true;
        assertEquals(1, ConvertUtil.toInt(bool1));
        bool1 = false;
        assertEquals(0, ConvertUtil.toInt(bool1));

        byte b1 = 10;
        assertEquals(10, ConvertUtil.toInt(b1));

        char ch1 = '项';
        assertNull(ConvertUtil.toInt(ch1));

        Short s1 = 1200;
        assertEquals(1200, ConvertUtil.toInt(s1));

        Integer i1 = 100;
        assertEquals(100, ConvertUtil.toInt(i1));

        Float f1 = 15.5f;
        assertEquals(15, ConvertUtil.toInt(f1));

        Double d1 = 3.141592;
        assertEquals(3, ConvertUtil.toInt(d1));

        Long l1 = 1990L;
        assertEquals(1990, ConvertUtil.toInt(l1));

        int i2 = 50;
        assertEquals(50, ConvertUtil.toInt(i2));

        BigInteger bi1 = new BigInteger("999999999999999999999999");
        assertNull(ConvertUtil.toInt(bi1));

        BigDecimal bd1 = new BigDecimal("999999999999999999999999.99");
        assertNull(ConvertUtil.toInt(bd1));

    }

    @Test
    public void toLong() {
        Object obj1 = null;
        assertNull(ConvertUtil.toLong(obj1));
        assertEquals(0L, ConvertUtil.toLong(obj1, 0L));

        boolean bool1 = true;
        assertEquals(1L, ConvertUtil.toLong(bool1));
        bool1 = false;
        assertEquals(0L, ConvertUtil.toLong(bool1));

        byte b1 = 10;
        assertEquals(10L, ConvertUtil.toLong(b1));

        char ch1 = '项';
        assertNull(ConvertUtil.toLong(ch1));

        Short s1 = 1200;
        assertEquals(1200L, ConvertUtil.toLong(s1));

        Integer i1 = 100;
        assertEquals(100L, ConvertUtil.toLong(i1));

        Float f1 = 15.5f;
        assertEquals(15L, ConvertUtil.toLong(f1));

        Double d1 = 3.141592;
        assertEquals(3L, ConvertUtil.toLong(d1));

        Long l1 = 1990L;
        assertEquals(1990L, ConvertUtil.toLong(l1));

        int i2 = 50;
        assertEquals(50L, ConvertUtil.toLong(i2));

        BigInteger bi1 = new BigInteger("999999999999999999999999");
        assertNull(ConvertUtil.toLong(bi1));

        BigDecimal bd1 = new BigDecimal("999999999999999999999999.99");
        assertNull(ConvertUtil.toLong(bd1));
    }

    @Test
    public void toDouble() {

        Object obj1 = null;
        assertNull(ConvertUtil.toDouble(obj1));
        assertEquals(0, ConvertUtil.toDouble(obj1, 0D));

        boolean bool1 = true;
        assertEquals(1, ConvertUtil.toDouble(bool1));
        bool1 = false;
        assertEquals(0, ConvertUtil.toDouble(bool1));

        byte b1 = 10;
        assertEquals(10, ConvertUtil.toDouble(b1));

        char ch1 = '项';
        assertNull(ConvertUtil.toDouble(ch1));

        Short s1 = 1200;
        assertEquals(1200, ConvertUtil.toDouble(s1));

        Integer i1 = 100;
        assertEquals(100, ConvertUtil.toDouble(i1));

        Float f1 = 15.5f;
        assertEquals(15.5D, ConvertUtil.toDouble(f1));

        Double d1 = 3.141592D;
        assertEquals(3.141592D, ConvertUtil.toDouble(d1));

        Long l1 = 1990L;
        assertEquals(1990, ConvertUtil.toDouble(l1));

        int i2 = 50;
        assertEquals(50, ConvertUtil.toDouble(i2));

        BigInteger bi1 = new BigInteger("999999999999999999999999");
        assertEquals(1.0E24, ConvertUtil.toDouble(bi1));

        BigDecimal bd1 = new BigDecimal("999999999999999999999999.99");
        assertEquals(1.0E24, ConvertUtil.toDouble(bd1));
    }

    @Test
    public void toFloat() {

        Object obj1 = null;
        assertNull(ConvertUtil.toFloat(obj1));
        assertEquals(0F, ConvertUtil.toFloat(obj1, 0F));

        boolean bool1 = true;
        assertEquals(1, ConvertUtil.toFloat(bool1));
        bool1 = false;
        assertEquals(0, ConvertUtil.toFloat(bool1));

        byte b1 = 10;
        assertEquals(10, ConvertUtil.toFloat(b1));

        char ch1 = '项';
        assertNull(ConvertUtil.toFloat(ch1));

        Short s1 = 1200;
        assertEquals(1200, ConvertUtil.toFloat(s1));

        Integer i1 = 100;
        assertEquals(100, ConvertUtil.toFloat(i1));

        Float f1 = 15.5f;
        assertEquals(15.5F, ConvertUtil.toFloat(f1));

        Double d1 = 3.141592;
        assertEquals(3.141592F, ConvertUtil.toFloat(d1));

        Long l1 = 1990L;
        assertEquals(1990, ConvertUtil.toFloat(l1));

        int i2 = 50;
        assertEquals(50, ConvertUtil.toFloat(i2));

    }

    @Test
    public void toBool() {

        Object obj1 = null;
        assertNull(ConvertUtil.toBool(obj1));
        assertFalse(ConvertUtil.toBool(obj1, false));

        boolean bool1 = true;
        assertTrue(ConvertUtil.toBool(bool1));
        bool1 = false;
        assertFalse(ConvertUtil.toBool(bool1));

        byte b1 = 10;
        assertTrue(ConvertUtil.toBool(b1));

        char ch1 = '项';
        assertTrue(ConvertUtil.toBool(ch1));

        Short s1 = 1200;
        assertTrue(ConvertUtil.toBool(s1));

        Integer i1 = 100;
        assertTrue(ConvertUtil.toBool(i1));

        Float f1 = 15.5f;
        assertTrue(ConvertUtil.toBool(f1));

        Double d1 = 3.141592;
        assertTrue(ConvertUtil.toBool(d1));

        Long l1 = 1990L;
        assertTrue(ConvertUtil.toBool(l1));

        int i2 = 50;
        assertTrue(ConvertUtil.toBool(i2));

        BigInteger bi1 = new BigInteger("999999999999999999999999");
        assertTrue(ConvertUtil.toBool(bi1));

        BigDecimal bd1 = new BigDecimal("999999999999999999999999.99");
        assertTrue(ConvertUtil.toBool(bd1));

        String str = "是";
        assertTrue(ConvertUtil.toBool(str));

        str = "对";
        assertTrue(ConvertUtil.toBool(str));

        str = "Y";
        assertTrue(ConvertUtil.toBool(str));

        str = "y";
        assertTrue(ConvertUtil.toBool(str));

    }

    @Test
    public void toBigInteger() {

        Object obj1 = null;
        assertNull(ConvertUtil.toBigInteger(obj1));
        assertEquals(new BigInteger("0"), ConvertUtil.toBigInteger(obj1, new BigInteger("0")));

        boolean bool1 = true;
        assertEquals(new BigInteger("1"), ConvertUtil.toBigInteger(bool1));
        bool1 = false;
        assertEquals(new BigInteger("0"), ConvertUtil.toBigInteger(bool1));

        byte b1 = 10;
        assertEquals(new BigInteger("10"), ConvertUtil.toBigInteger(b1));

        char ch1 = '项';
        assertNull(ConvertUtil.toBigInteger(ch1));

        Short s1 = 1200;
        assertEquals(new BigInteger("1200"), ConvertUtil.toBigInteger(s1));

        Integer i1 = 100;
        assertEquals(new BigInteger("100"), ConvertUtil.toBigInteger(i1));

        Float f1 = 15.5f;
        assertNull(ConvertUtil.toBigInteger(f1));

        Double d1 = 3.141592;
        assertNull(ConvertUtil.toBigInteger(d1));

        Long l1 = 1990L;
        assertEquals(new BigInteger("1990"), ConvertUtil.toBigInteger(l1));

        int i2 = 50;
        assertEquals(new BigInteger("50"), ConvertUtil.toBigInteger(i2));

    }

    @Test
    public void toBigDecimal() {

        Object obj1 = null;
        assertNull(ConvertUtil.toBigDecimal(obj1));
        assertEquals(new BigDecimal("0"), ConvertUtil.toBigDecimal(obj1, new BigDecimal("0")));

        boolean bool1 = true;
        assertEquals(new BigDecimal("1"), ConvertUtil.toBigDecimal(bool1));
        bool1 = false;
        assertEquals(new BigDecimal("0"), ConvertUtil.toBigDecimal(bool1));

        byte b1 = 10;
        assertEquals(new BigDecimal("10"), ConvertUtil.toBigDecimal(b1));

        char ch1 = '项';
        assertNull(ConvertUtil.toBigDecimal(ch1));

        Short s1 = 1200;
        assertEquals(new BigDecimal("1200"), ConvertUtil.toBigDecimal(s1));

        Integer i1 = 100;
        assertEquals(new BigDecimal("100"), ConvertUtil.toBigDecimal(i1));

        Float f1 = 15.5f;
        assertEquals(new BigDecimal("15.5"), ConvertUtil.toBigDecimal(f1));

        Double d1 = 3.141592;
        assertEquals(new BigDecimal("3.141592"), ConvertUtil.toBigDecimal(d1));

        Long l1 = 1990L;
        assertEquals(new BigDecimal("1990"), ConvertUtil.toBigDecimal(l1));

        int i2 = 50;
        assertEquals(new BigDecimal("50"), ConvertUtil.toBigDecimal(i2));

    }

    @Test
    public void toDate() {
        Object obj1 = null;
        assertNull(ConvertUtil.toDate(obj1));
        assertNull(ConvertUtil.toDate(obj1, null));

        String str = "1990-1-1";
        assertEquals(new Date(1990, 0, 1), ConvertUtil.toDate(str));


    }

    @Test
    public void toLocalDateTime() {
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