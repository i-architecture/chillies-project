package com.ijiagoushi.chillies.webexample.controller;

import com.ijiagoushi.chillies.core.bean.BeanUtil;
import com.ijiagoushi.chillies.core.bean.CopyOption;
import com.ijiagoushi.chillies.core.id.DateIdGenerator;
import com.ijiagoushi.chillies.core.io.FileUtil;
import com.ijiagoushi.chillies.core.lang.StringUtil;
import com.ijiagoushi.chillies.webexample.dto.Employee;
import com.ijiagoushi.chillies.webexample.dto.request.EmployeeCreateRequest;
import com.ijiagoushi.chillies.webexample.dto.request.EmployeeUpdateRequest;
import com.ijiagoushi.chillies.webexample.dto.response.PageResponseData;
import com.ijiagoushi.chillies.webexample.dto.response.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON Controller
 *
 * @author miles.tang at 2021-03-05
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    /**
     * 员工数据
     */
    private final List<Employee> employees = new ArrayList<>(1024);

    /**
     * 员工分页查询
     *
     * @param mobile   手机号
     * @param name     姓名
     * @param hireDate 入职日期
     * @return 员工
     */
    @GetMapping
    public PageResponseData<Employee> page(String mobile, String name, String hireDate, Integer pageNo, Integer pageSize) {
        log.info("分页查询[::]mobile = {}, name = {}, hireDate = {}, pageNo = {}, pageSize = {}", mobile, name, hireDate, pageNo, pageSize);
        if (pageNo == null || pageNo <= 0) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        LocalDate hireDateLocalDate = null;
        if (StringUtil.hasLength(hireDate)) {
            hireDateLocalDate = LocalDate.parse(hireDate);
        }
        long offset = (pageNo - 1) * pageSize;
        LocalDate finalHireDateLocalDate = hireDateLocalDate;
        List<Employee> allMatchedResult = employees.stream()
                .filter(employee -> StringUtil.isEmpty(mobile) || employee.getMobile().startsWith(mobile))
                .filter(employee -> StringUtil.isEmpty(name) || employee.getName().startsWith(name))
                .filter(employee -> finalHireDateLocalDate == null || finalHireDateLocalDate.equals(employee.getHireDate()))
                .collect(Collectors.toList());

        PageResponseData<Employee> pageResponseData = new PageResponseData<>(allMatchedResult.size());
        if (pageResponseData.getTotal() > 0) {
            if (offset + pageSize > pageResponseData.getTotal()) {
                pageResponseData.setData(allMatchedResult.stream()
                        .skip(offset)
                        .limit(pageResponseData.getTotal() - offset)
                        .collect(Collectors.toList()));
            } else {
                pageResponseData.setData(allMatchedResult.stream()
                        .skip(offset)
                        .limit(pageSize)
                        .collect(Collectors.toList()));
            }
        }
        pageResponseData.setCode(0);
        return pageResponseData;
    }

    @GetMapping("/{id}")
    public ResponseData<Employee> findById(@PathVariable("id") String id) {
        log.info("根据员工ID查询[::]id = {}", id);
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .map(employee -> new ResponseData<>(0, null, employee))
                .orElse(new ResponseData<>(1, "员工不存在"));
    }

    @PostMapping
    public ResponseData<Employee> create(@Valid @RequestBody EmployeeCreateRequest request) {
        log.info("创建员工[::]request = {}", request);
        if (employees.stream().anyMatch(employee -> request.getMobile().equals(employee.getMobile()))) {
            return new ResponseData<>(2, "手机号已存在");
        }

        Employee employee = new Employee();
        BeanUtil.copyProperties(request, employee);
        employee.setId(DateIdGenerator.getInstance().get());
        if (employees.size() > 1024) {
            employees.set(0, employee);
        } else {
            employees.add(employee);
        }
        return new ResponseData<>(0, null, employee);
    }

    @PutMapping("/{id}")
    public ResponseData<Employee> update(@PathVariable("id") String id,
                                         @Valid @RequestBody EmployeeUpdateRequest request) {
        log.info("更新员工[::]id = {}, request = {}", id, request);
        if (StringUtil.hasLength(request.getMobile())) {
            if (employees.stream().anyMatch(employee -> request.getMobile().equals(employee.getMobile()))) {
                return new ResponseData<>(2, "手机号已存在");
            }
        }

        int foundIndex = -1;
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId().equals(id)) {
                foundIndex = i;
                break;
            }
        }
        if (foundIndex == -1) {
            return new ResponseData<>(1, "员工不存在");
        }

        Employee employee = employees.get(foundIndex);
        CopyOption copyOption = CopyOption.builder().ignoreEmptyString(true).ignoreNullValue(true).build();
        BeanUtil.copyProperties(request, employee, copyOption);

        return new ResponseData<>(0, null, employee);
    }

    @PostMapping("/{id}/form_avatar")
    public ResponseData<Employee> changeAvatar(@PathVariable("id") String id, EmployeeUpdateRequest request,
                                               @RequestParam("file") MultipartFile file) throws IOException {
        log.info("更新员工头像和信息[::]id = {}, request = {},filename = {}", id, request, file.getOriginalFilename());
        ResponseData<Employee> responseData = this.findById(id);
        if (responseData.getCode() != 0) {
            return responseData;
        }
        Employee employee = responseData.getData();
        if (StringUtil.hasLength(employee.getAvatarFile())) {
            FileUtil.delete(new File(employee.getAvatarFile()));
        }
        String userHomeDir = System.getProperty("user.home");
        String dir = userHomeDir + "/Downloads/web-example/employee/{}/avatar/";
        String path = StringUtil.format(dir, id);
        FileUtil.forceMakeDir(path);
        file.transferTo(new File(path, file.getOriginalFilename()));
        employee.setAvatarFile(path);

        CopyOption copyOption = CopyOption.builder().ignoreEmptyString(true).ignoreNullValue(true).build();
        BeanUtil.copyProperties(request, employee, copyOption);

        return responseData;
    }


}
