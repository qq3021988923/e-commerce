package com.yang.service;

import com.yang.annotation.AutoFill;
import com.yang.dto.EmployeeDTO;
import com.yang.dto.EmployeeLoginDTO;
import com.yang.dto.EmployeePageQueryDTO;
import com.yang.entity.Employee;
import com.yang.enumeration.OperationType;
import com.yang.result.PageResult;
import com.yang.vo.EmployeeLoginVO;

public interface EmployeeService {

    Employee getByUsername(EmployeeLoginDTO dto);

    void save(Employee DTO);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);


    void update(Employee emp);

    void startOrStop(Integer status,Long id);

    Employee getById(Long id);
}
