package com.ijiagoushi.chillies.core.bean;

import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author miles.tang at 2021-01-14
 * @since 1.0
 */
class BeanUtilTest {

    @Test
    void isBeanType() {
        assertFalse(BeanUtil.isBeanType(Bean1.class));
        assertTrue(BeanUtil.isBeanType(Bean2.class));
        assertTrue(BeanUtil.isBeanType(Bean3.class));
        assertFalse(BeanUtil.isBeanType(Bean4.class));
        assertFalse(BeanUtil.isBeanType(Bean5.class));
        assertFalse(BeanUtil.isBeanType(Bean6.class));
        assertTrue(BeanUtil.isBeanType(Bean7.class));
        assertTrue(BeanUtil.isBeanType(Bean8.class));
    }

    @Test
    void getPropertyDescriptorList() {
    }

    @Test
    void getPropertyDescriptorMap() {
    }

    @Test
    void getPropertyDescriptor() {
    }

    @Test
    void getPropertyValue() {
    }

    @Test
    void beanToMap() {
    }

    @Test
    void testBeanToMap() {
    }

    @Test
    void testBeanToMap1() {
    }

    @Test
    void toMapAsValueString() {
    }

    @Test
    void copyProperties() {
    }

    @Test
    void testCopyProperties() {
    }

    public static class Bean1 {
        public static String id;
    }

    @Getter
    @Setter
    public static class Bean2 {
        public static String id;
        public String name;
        public Date date;
    }

    @Getter
    @Setter
    public static class Bean3 {
        public String id;
        public String name;
        public Date date;
    }

    public static class Bean4 {
        private String id;
        private String name;
        private Date date;

    }

    public static class Bean5 {
        private String id;
        private String name;
        private Date date;

    }

    public static class Bean6 {
        private String id;
        private String name;
        private Date date;

    }

    @Getter
    @Setter
    public static class Bean7 {
        private String id;
        private String name;
        private Date date;
    }

    @Getter
    @Setter
    public static class Bean8 {
        private String id;
        private String name;
        private boolean open;
    }

}