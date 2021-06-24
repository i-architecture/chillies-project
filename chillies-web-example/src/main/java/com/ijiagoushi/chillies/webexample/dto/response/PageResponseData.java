package com.ijiagoushi.chillies.webexample.dto.response;

import lombok.*;

import java.util.List;

/**
 * @author miles.tang at 2021-03-05
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class PageResponseData<T> extends ResponseData<List<T>> {

    private long total;

}
