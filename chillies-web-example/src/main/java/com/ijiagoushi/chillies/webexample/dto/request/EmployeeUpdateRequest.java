package com.ijiagoushi.chillies.webexample.dto.request;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 员工创建
 *
 * @author miles.tang at 2021-03-05
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeUpdateRequest implements Serializable {

    /**
     * 姓名
     */
//    @NotBlank(message = "姓名不能为空")
    private String name;

    /**
     * 手机号
     */
//    @NotBlank(message = "手机号不能为空")
    private String mobile;

    /**
     * 性别
     */
//    @NotBlank(message = "性别不能为空")
    private String sex;

    /**
     * 身份证号码
     */
    private String idCardNumber;

    /**
     * 生日
     */
    private LocalDate birthDate;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 学历
     */
    private String degree;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 入职日期
     */
    private LocalDate hireDate;

    /**
     * 入职部门
     */
    private String department;

}
