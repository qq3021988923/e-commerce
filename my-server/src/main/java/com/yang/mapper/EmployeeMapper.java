package com.yang.mapper;

import com.github.pagehelper.Page;
import com.yang.annotation.AutoFill;
import com.yang.dto.EmployeePageQueryDTO;
import com.yang.entity.Employee;
import com.yang.enumeration.OperationType;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapper {

    Employee getByUsername(String username);

    @AutoFill(OperationType.INSERT)
    void insert(Employee emp);

    Page<Employee> pageQuery(EmployeePageQueryDTO  dto);

    @AutoFill(OperationType.UPDATE)
    void update(Employee emp);

    Employee getById(Long id);
}
