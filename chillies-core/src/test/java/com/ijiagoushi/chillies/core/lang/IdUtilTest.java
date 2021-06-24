package com.ijiagoushi.chillies.core.lang;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author miles.tang at 2021-01-21
 * @since 1.0
 */
public class IdUtilTest {

    @Test
    public void uuid() {
        assertNotNull(IdUtil.uuid());
        assertEquals(36, IdUtil.uuid().length());
        System.out.println("IdUtil.uuid() = " + IdUtil.uuid());
    }

    @Test
    public void uuidNoDash() {
        assertNotNull(IdUtil.uuidNoDash());
        assertEquals(32, IdUtil.uuidNoDash().length());
        System.out.println("IdUtil.uuidNoDash() = " + IdUtil.uuidNoDash());
    }

    @Test
    public void secureUuid() {
        assertNotNull(IdUtil.secureUuid());
        assertEquals(36, IdUtil.secureUuid().length());
        System.out.println("IdUtil.secureUuid() = " + IdUtil.secureUuid());
    }

    @Test
    public void secureUuidNoDash() {
        assertNotNull(IdUtil.secureUuidNoDash());
        assertEquals(32, IdUtil.secureUuidNoDash().length());
        System.out.println("IdUtil.secureUuidNoDash() = " + IdUtil.secureUuidNoDash());
    }

    @Test
    public void dateId() {
        assertNotNull(IdUtil.dateId());
        assertEquals(32, IdUtil.dateId().length());
        System.out.println("IdUtil.dateId() = " + IdUtil.dateId());
    }

    @Test
    public void base64Id() {
        assertNotNull(IdUtil.base64Id());
        assertEquals(24, IdUtil.base64Id().length());
        System.out.println("IdUtil.base64Id() = " + IdUtil.base64Id());
    }
}