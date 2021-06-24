package com.ijiagoushi.chillies.webexample.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 员工表
 *
 * @author miles.tang at 2021-03-05
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee implements Serializable {

    /**
     * 员工ID
     */
    private String id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 性别
     */
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

//    /**
//     * 头像地址
//     */
//    private String avatarUrl;

    /**
     * 入职日期
     */
    private LocalDate hireDate;

    /**
     * 入职部门
     */
    private String department;

    @JsonIgnore
    private String avatarFile;

}
