package com.yang.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeePageQueryDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    //员工姓名
    private String name;

    //页码
    private int page=1;

    //每页显示记录数
    private int pageSize=10;

}
