package com.ijiagoushi.chillies.webexample.controller;

import com.ijiagoushi.chillies.webexample.dto.Employee;
import com.ijiagoushi.chillies.webexample.dto.request.EmployeeCreateRequest;
import com.ijiagoushi.chillies.webexample.dto.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author miles.tang at 2021-03-05
 * @since 1.0
 */
@Slf4j
public class RestTemplateEmployeeTest {

    String url = "http://localhost:8080/employees";
    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void create() {
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        ResponseData<Employee> responseData = restTemplate.postForObject(url, request, ResponseData.class);
        log.info("请求完成[::]url={},request={}, response={}", url, request, responseData);
        assertNotNull(responseData);
        assertEquals(0, responseData.getCode());
        assertNotNull(responseData.getData());
    }


}
