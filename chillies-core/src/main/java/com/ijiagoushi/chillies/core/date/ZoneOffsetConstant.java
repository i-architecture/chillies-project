package com.ijiagoushi.chillies.core.date;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * 时区偏移
 *
 * @author miles.tang
 */
public interface ZoneOffsetConstant {

    /**
     * 系统默认时区
     */
    ZoneOffset DEFAULT_ZONE_OFFSET = OffsetDateTime.now().getOffset();

    /**
     * 北京时区，即东八区
     */
    ZoneOffset BEIJING_ZONE_OFFSET = ZoneOffset.ofHours(8);

}
