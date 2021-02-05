package com.ijiagoushi.chillies.core.lang;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
public class CollectionUtilTest {

    @Test
    public void isEmpty() {
        List<String> list = null;
        assertTrue(CollectionUtil.isEmpty(list));

        list = new ArrayList<>();
        assertTrue(CollectionUtil.isEmpty(list));

        list.add(null);
        assertFalse(CollectionUtil.isEmpty(list));

        Set<String> set = null;
        assertTrue(CollectionUtil.isEmpty(set));

        set = new HashSet<>();
        assertTrue(CollectionUtil.isEmpty(set));

        set.add(null);
        assertFalse(CollectionUtil.isEmpty(set));

        Map<String, String> map = null;
        assertTrue(CollectionUtil.isEmpty(map));

        map = new HashMap<>();
        assertTrue(CollectionUtil.isEmpty(map));

        map.put(null, null);
        assertFalse(CollectionUtil.isEmpty(map));

    }

    @Test
    public void isNotEmpty() {
        List<String> list = null;
        assertFalse(CollectionUtil.isNotEmpty(list));

        list = new ArrayList<>();
        assertFalse(CollectionUtil.isNotEmpty(list));

        list.add(null);
        assertTrue(CollectionUtil.isNotEmpty(list));

        Set<String> set = null;
        assertFalse(CollectionUtil.isNotEmpty(set));

        set = new HashSet<>();
        assertFalse(CollectionUtil.isNotEmpty(set));

        set.add(null);
        assertTrue(CollectionUtil.isNotEmpty(set));

        Map<String, String> map = null;
        assertFalse(CollectionUtil.isNotEmpty(map));

        map = new HashMap<>();
        assertFalse(CollectionUtil.isNotEmpty(map));

        map.put(null, null);
        assertTrue(CollectionUtil.isNotEmpty(map));

    }

    @Test
    public void join() {
        List<String> list = null;
        assertNull(CollectionUtil.join(list));

        list = new ArrayList<>();
        assertEquals(CollectionUtil.join(list), "");

        list = new ArrayList<>();
        list.add(null);
        assertEquals(CollectionUtil.join(list), "");

        list.add(null);
        assertEquals(CollectionUtil.join(list), "");

        list.add("python");
        assertEquals(CollectionUtil.join(list), "python");

        list.add("");
        assertEquals(CollectionUtil.join(list), "python,");

        list.add("java");
        assertEquals(CollectionUtil.join(list), "python,,java");

        assertEquals(CollectionUtil.join(list, false), "null,null,python,,java");

        list.clear();
        list.add("python");
        list.add("");
        list.add("java");
        assertEquals(CollectionUtil.join(list, StringUtil.COMMA, false, true), ",java,python");
    }

}
