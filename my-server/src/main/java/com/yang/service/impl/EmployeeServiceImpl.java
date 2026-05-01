package com.yang.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yang.annotation.AutoFill;
import com.yang.constant.PasswordConstant;
import com.yang.constant.StatusConstant;
import com.yang.context.BaseContext;
import com.yang.dto.EmployeeDTO;
import com.yang.dto.EmployeeLoginDTO;
import com.yang.dto.EmployeePageQueryDTO;
import com.yang.entity.Employee;
import com.yang.enumeration.OperationType;
import com.yang.exception.AccountLockedException;
import com.yang.exception.AccountNotFoundException;
import com.yang.exception.BaseException;
import com.yang.mapper.EmployeeMapper;
import com.yang.result.PageResult;
import com.yang.service.EmployeeService;
import com.yang.vo.EmployeeLoginVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;


    @Override
    public Employee getByUsername(EmployeeLoginDTO dto) {

        String username = dto.getUsername();
        String password = dto.getPassword();
        Employee emp = employeeMapper.getByUsername(username);

        if(emp==null){
            throw new AccountNotFoundException("账号不存在");
        }
        password= DigestUtils.md5DigestAsHex(password.getBytes());
        if(!password.equals(emp.getPassword())){// 加密对加密
            throw new BaseException("密码异常");
        }
        if(emp.getStatus()==0){
            throw new AccountLockedException("账号被锁");
        }
        return emp;
    }

    @Override
    public void save(Employee emp) {
        // 1正常  0锁定
        emp.setStatus(StatusConstant.ENABLE);
        // 使用md5加密
        emp.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));

        employeeMapper.insert(emp);
    }

    @Override
    public PageResult pageQuery(EmployeePageQueryDTO dto) {

        PageHelper.startPage(dto.getPage(),dto.getPageSize());

        Page<Employee> page = employeeMapper.pageQuery(dto);
        System.out.println("分页查询后" + page);
        long total = page.getTotal();
        List<Employee> records = page.getResult();

        return new PageResult(total,records);
    }

    @Override
    public void update(Employee emp) {

      //  System.out.println("看我时间和id" + emp);


       // emp.setUpdateTime(LocalDateTime.now());
       // emp.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.update(emp);
    }

    @Override
    public void startOrStop(Integer status,Long id) {
        Employee emp=Employee.builder()
                .status(status)
                .id(id)
                .build();
        employeeMapper.update(emp);

    }

    @Override
    public Employee getById(Long id) {
        Employee emp = employeeMapper.getById(id);
        emp.setPassword("************");
        return emp;
    }
}
