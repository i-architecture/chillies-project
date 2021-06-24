package com.ijiagoushi.chillies.webexample.dto.response;

import lombok.*;

import java.io.Serializable;

/**
 * @author miles.tang at 2021-03-05
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseData<T> implements Serializable {

    private int code;

    private String message;

    private T data;

    public ResponseData(int code, String message) {
        this();
        this.code = code;
        this.message = message;
    }

}
