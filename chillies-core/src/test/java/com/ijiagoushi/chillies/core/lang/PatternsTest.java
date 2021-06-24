package com.ijiagoushi.chillies.core.lang;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author miles.tang at 2021-01-20
 * @since 1.0
 */
public class PatternsTest {

    @Test
    public void general() {
        assertFalse(Patterns.GENERAL.matcher("").matches());
        assertTrue(Patterns.GENERAL.matcher("9").matches());
        assertTrue(Patterns.GENERAL.matcher("a").find());
        assertTrue(Patterns.GENERAL.matcher("A").find());
        assertTrue(Patterns.GENERAL.matcher("Aa9").find());
        assertTrue(Patterns.GENERAL.matcher("Aa9_").find());
        assertFalse(Patterns.GENERAL.matcher("Aa9-").find());
        assertFalse(Patterns.GENERAL.matcher("-").find());
        assertFalse(Patterns.GENERAL.matcher(".").find());
        assertFalse(Patterns.GENERAL.matcher("*").find());
        assertFalse(Patterns.GENERAL.matcher("^").find());
    }

    @Test
    public void number() {
        assertFalse(Patterns.NUMBERS.matcher("").find());
        assertFalse(Patterns.NUMBERS.matcher("a").find());
        assertFalse(Patterns.NUMBERS.matcher("A").find());
        assertFalse(Patterns.NUMBERS.matcher("-").find());
        assertTrue(Patterns.NUMBERS.matcher("-0").find());
        assertTrue(Patterns.NUMBERS.matcher("+0").find());
        assertTrue(Patterns.NUMBERS.matcher("0").find());
        assertTrue(Patterns.NUMBERS.matcher("010").find());
        assertTrue(Patterns.NUMBERS.matcher("0010").find());
    }

    @Test
    public void chineseAny() {
        assertFalse(Patterns.CHINESE_ANY.matcher("").find());
        assertFalse(Patterns.CHINESE_ANY.matcher("-").find());
        assertFalse(Patterns.CHINESE_ANY.matcher("_").find());
        assertFalse(Patterns.CHINESE_ANY.matcher("0").find());
        assertFalse(Patterns.CHINESE_ANY.matcher("0-").find());
        assertFalse(Patterns.CHINESE_ANY.matcher("Java").find());
        assertTrue(Patterns.CHINESE_ANY.matcher("作").find());
        assertTrue(Patterns.CHINESE_ANY.matcher("作者").find());
        assertTrue(Patterns.CHINESE_ANY.matcher("Java工具").find());
        assertTrue(Patterns.CHINESE_ANY.matcher("《").find());
        assertTrue(Patterns.CHINESE_ANY.matcher("》").find());
        assertTrue(Patterns.CHINESE_ANY.matcher("。").find());
        assertTrue(Patterns.CHINESE_ANY.matcher("？").find());
    }

    @Test
    public void chineses() {
        assertFalse(Patterns.CHINESES.matcher("").find());
        assertFalse(Patterns.CHINESES.matcher("-").find());
        assertFalse(Patterns.CHINESES.matcher("_").find());
        assertFalse(Patterns.CHINESES.matcher("0").find());
        assertFalse(Patterns.CHINESES.matcher("0-").find());
        assertFalse(Patterns.CHINESES.matcher("Java").find());
        assertTrue(Patterns.CHINESES.matcher("作").find());
        assertTrue(Patterns.CHINESES.matcher("作者").find());
        assertTrue(Patterns.CHINESES.matcher("Java工具").find());
        assertTrue(Patterns.CHINESES.matcher("《").find());
        assertTrue(Patterns.CHINESES.matcher("》").find());
        assertTrue(Patterns.CHINESES.matcher("。").find());
        assertTrue(Patterns.CHINESES.matcher("？").find());
    }

    @Test
    public void urlHttpOrFtp() {
        assertFalse(Patterns.URL_HTTP_OR_FTP.matcher("").find());
        assertFalse(Patterns.URL_HTTP_OR_FTP.matcher("http").find());
        assertFalse(Patterns.URL_HTTP_OR_FTP.matcher("http://").find());
        assertFalse(Patterns.URL_HTTP_OR_FTP.matcher("www").find());
        assertTrue(Patterns.URL_HTTP_OR_FTP.matcher("www.github.com").find());
        assertTrue(Patterns.URL_HTTP_OR_FTP.matcher("httpwww.github.com").find());
        assertTrue(Patterns.URL_HTTP_OR_FTP.matcher("http://www.github.com").find());
        assertTrue(Patterns.URL_HTTP_OR_FTP.matcher("https://www.github.com").find());
        assertTrue(Patterns.URL_HTTP_OR_FTP.matcher("ftp://github.com").find());
        assertTrue(Patterns.URL_HTTP_OR_FTP.matcher("ftps://github.com").find());
    }

    @Test
    public void ipv4() {
        assertFalse(Patterns.IPV4.matcher("").find());
        assertFalse(Patterns.IPV4.matcher("1").find());
        assertFalse(Patterns.IPV4.matcher("s").find());
        assertFalse(Patterns.IPV4.matcher("s.s.s.s").find());
        assertTrue(Patterns.IPV4.matcher("1.1.1.1").find());
        assertFalse(Patterns.IPV4.matcher("1.1.1.1111").find());
        assertTrue(Patterns.IPV4.matcher("0.0.0.0").find());
        assertTrue(Patterns.IPV4.matcher("255.255.255.255").find());
    }

    @Test
    public void ipv6() {
        assertFalse(Patterns.IPV6.matcher("").find());
        assertFalse(Patterns.IPV6.matcher("1").find());
        assertFalse(Patterns.IPV6.matcher("s").find());
        assertFalse(Patterns.IPV6.matcher("s.s.s.s").find());
        assertTrue(Patterns.IPV6.matcher("2000::1:2345:6789:abcd").find());
        assertTrue(Patterns.IPV6.matcher("FE80::1").find());
        assertTrue(Patterns.IPV6.matcher("0:0:0:0:0:0:0:0").find());
    }

    @Test
    public void trainCode() {
        assertFalse(Patterns.TRAIN_CODE.matcher("").find());
        assertFalse(Patterns.TRAIN_CODE.matcher("1").find());
        assertTrue(Patterns.TRAIN_CODE.matcher("12").find());
        assertTrue(Patterns.TRAIN_CODE.matcher("123").find());
        assertTrue(Patterns.TRAIN_CODE.matcher("1234").find());
        assertTrue(Patterns.TRAIN_CODE.matcher("12345").find());
        assertFalse(Patterns.TRAIN_CODE.matcher("123456").find());
        assertTrue(Patterns.TRAIN_CODE.matcher("G12").find());
        assertFalse(Patterns.TRAIN_CODE.matcher("g12").find());
        assertFalse(Patterns.TRAIN_CODE.matcher("z12").find());
        assertTrue(Patterns.TRAIN_CODE.matcher("K12").find());
        assertTrue(Patterns.TRAIN_CODE.matcher("D12").find());
        assertFalse(Patterns.TRAIN_CODE.matcher("d12").find());
    }

    @Test
    public void imei() {
        assertFalse(Patterns.IMEI.matcher("").find());
        assertFalse(Patterns.IMEI.matcher("1").find());
        assertFalse(Patterns.IMEI.matcher("12").find());
        assertFalse(Patterns.IMEI.matcher("123").find());
        assertTrue(Patterns.IMEI.matcher("1234123412341234").find());
    }

    @Test
    public void plateCode() {
        assertFalse(Patterns.IMEI.matcher("").matches());
        assertFalse(Patterns.IMEI.matcher("沪ADAC12D").find());
    }

    @Test
    public void mobilePhoneStrict() {
        assertFalse(Patterns.MOBILE_PHONE_STRICT.matcher("").find());
        assertFalse(Patterns.MOBILE_PHONE_STRICT.matcher("+008613312341234").find());
        assertFalse(Patterns.MOBILE_PHONE_STRICT.matcher("+08613312341234").find());
        assertFalse(Patterns.MOBILE_PHONE_STRICT.matcher("8613312341234").find());
        assertTrue(Patterns.MOBILE_PHONE_STRICT.matcher("+8613312341234").find());
        assertFalse(Patterns.MOBILE_PHONE_STRICT.matcher("+8616312341234").find());
    }

    @Test
    public void mobilePhone() {
        assertFalse(Patterns.MOBILE_PHONE.matcher("").find());
        assertFalse(Patterns.MOBILE_PHONE.matcher("+008613312341234").find());
        assertFalse(Patterns.MOBILE_PHONE.matcher("+08613312341234").find());
        assertFalse(Patterns.MOBILE_PHONE.matcher("8613312341234").find());
        assertTrue(Patterns.MOBILE_PHONE.matcher("+8613312341234").find());
        assertTrue(Patterns.MOBILE_PHONE.matcher("+8616312341234").find());
    }

    @Test
    public void date() {
        assertFalse(Patterns.DATE.matcher("").find());
        assertFalse(Patterns.DATE.matcher("1990").find());
        assertFalse(Patterns.DATE.matcher("1990-01").find());
        assertTrue(Patterns.DATE.matcher("1990-01-1").find());
        assertTrue(Patterns.DATE.matcher("1990-1-1").find());
        assertTrue(Patterns.DATE.matcher("1990-1-01").find());
        assertFalse(Patterns.DATE.matcher("1990-13-1").find());
        assertFalse(Patterns.DATE.matcher("1990-13-41").find());
    }

    @Test
    public void email() {
        assertFalse(Patterns.EMAIL.matcher("").find());
        assertFalse(Patterns.EMAIL.matcher("1").find());
        assertFalse(Patterns.EMAIL.matcher("e").find());
        assertFalse(Patterns.EMAIL.matcher("tm@tm").find());
        assertTrue(Patterns.EMAIL.matcher("tm@tm.com").find());
    }

    @Test
    public void creditCode() {
        assertFalse(Patterns.CREDIT_CODE.matcher("").find());
        assertFalse(Patterns.CREDIT_CODE.matcher("1").find());
        assertTrue(Patterns.CREDIT_CODE.matcher("91110101784769490C").find());
    }

    @Test
    public void identityNumber() {
        assertFalse(Patterns.IDENTITY_NUMBER.matcher("").find());
        assertFalse(Patterns.IDENTITY_NUMBER.matcher("1").find());
        assertTrue(Patterns.IDENTITY_NUMBER.matcher("310112202101206892").find());
    }

    @Test
    public void zipcode() {
        assertFalse(Patterns.ZIPCODE.matcher("").find());
        assertFalse(Patterns.ZIPCODE.matcher("1").find());
        assertFalse(Patterns.ZIPCODE.matcher("12").find());
        assertFalse(Patterns.ZIPCODE.matcher("123").find());
        assertFalse(Patterns.ZIPCODE.matcher("1234").find());
        assertFalse(Patterns.ZIPCODE.matcher("12345").find());
        assertTrue(Patterns.ZIPCODE.matcher("123456").find());
        assertFalse(Patterns.ZIPCODE.matcher("x").find());
    }

    @Test
    public void macAddr() {
        assertFalse(Patterns.MAC_ADDR.matcher("").find());
        assertFalse(Patterns.MAC_ADDR.matcher("").find());
        assertTrue(Patterns.MAC_ADDR.matcher("00:11:22:33:44:55").find());
    }

    @Test
    public void get() {
        Pattern pattern = Patterns.get("[\\w\\d]{6,18}");
        assertNotNull(pattern);
    }

}