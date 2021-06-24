package com.ijiagoushi.chillies.webexample.controller;

import com.ijiagoushi.chillies.http.convenient.HttpClients;
import com.ijiagoushi.chillies.json.TypeRef;
import com.ijiagoushi.chillies.webexample.dto.Employee;
import com.ijiagoushi.chillies.webexample.dto.request.EmployeeCreateRequest;
import com.ijiagoushi.chillies.webexample.dto.request.EmployeeUpdateRequest;
import com.ijiagoushi.chillies.webexample.dto.response.PageResponseData;
import com.ijiagoushi.chillies.webexample.dto.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author miles.tang at 2021-03-05
 * @since 1.0
 */
@Slf4j
public class EmployeeControllerTest {

    String url = "http://localhost:8080/employees";

    @Test
    public void page() {
        PageResponseData<Employee> pageResponseData = HttpClients.get(url)
                .queryParam("pageNo", 1)
                .queryParam("pageSize", 5)
                .bean(new TypeRef<PageResponseData<Employee>>() {
                });
        log.info("请求完成[::]url={},request={}, response={}", url, null, pageResponseData);
        assertNotNull(pageResponseData);
        assertEquals(0, pageResponseData.getCode());
    }

    @Test
    public void findById() {

    }

    @Test
    public void create() {
        EmployeeCreateRequest request = new EmployeeCreateRequest();
        request.setMobile("18321001200");
        request.setName("张三");
        request.setSex("男");
        ResponseData<Employee> responseData = HttpClients.post(url).json(request).bean(new TypeRef<ResponseData<Employee>>() {
        });
        log.info("请求完成[::]url={},request={}, response={}", url, request, responseData);
        assertNotNull(responseData);
        assertEquals(0, responseData.getCode());
        assertNotNull(responseData.getData());

        //

        request = new EmployeeCreateRequest();
        request.setMobile("18321001201");
        request.setName("李四");
        request.setSex("男");
        request.setBirthDate(LocalDate.of(1990, 1, 1));
        request.setDegree("大专");
        responseData = HttpClients.post(url).json(request).bean(new TypeRef<ResponseData<Employee>>() {
        });
        log.info("请求完成[::]url={},request={}, response={}", url, request, responseData);
        assertNotNull(responseData);
        assertEquals(0, responseData.getCode());
        assertNotNull(responseData.getData());
    }

    @Test
    public void update() {
        String url = this.url + "/20210412124015761-0000010ZUwmUVA";
        EmployeeUpdateRequest request = new EmployeeUpdateRequest();
        request.setEmail("zhangsan@gmail.com");
        ResponseData<Employee> responseData = HttpClients.put(url).json(request).bean(new TypeRef<ResponseData<Employee>>() {
        });
        log.info("请求完成[::]url={},request={}, response={}", url, request, responseData);
        assertNotNull(responseData);
        assertEquals(0, responseData.getCode());
        assertNotNull(responseData.getData());
    }

    @Test
    public void changeAvatar() {
        String url = this.url + "/20210412153642417-0000023knRrhQo/form_avatar";
        URL imageUrl = this.getClass().getClassLoader().getResource("frxxz.jpg");
        ResponseData<Employee> responseData = HttpClients.post(url)
                .param("file", imageUrl)
                .bean(new TypeRef<ResponseData<Employee>>() {
                });
        log.info("请求完成[::]url={},request={}, response={}", url, null, responseData);
        assertNotNull(responseData);
        assertEquals(0, responseData.getCode());
    }

}