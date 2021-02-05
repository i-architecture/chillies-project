package com.ijiagoushi.chillies.json;

import com.ijiagoushi.chillies.core.exceptions.GenericRuntimeException;

/**
 * Json RuntimeException
 *
 * @author miles.tang
 */
public class JSONRuntimeException extends GenericRuntimeException {

    public JSONRuntimeException(Exception e) {
        super(e);
    }

}
