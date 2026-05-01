package com.yang.controller.admin;
import com.yang.constant.JwtClaimsConstant;
import com.yang.dto.EmployeeLoginDTO;
import com.yang.dto.EmployeePageQueryDTO;
import com.yang.entity.Employee;
import com.yang.properties.JwtProperties;
import com.yang.result.PageResult;
import com.yang.result.Result;
import com.yang.utils.JwtUtil;
import com.yang.vo.EmployeeLoginVO;
import com.yang.service.EmployeeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Tag(name  = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    // http://localhost:1234/employee/login
    @PostMapping("/login")
    @Operation( summary ="员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);
        Employee emp = employeeService.getByUsername(employeeLoginDTO);

        Map<String,Object> map = new HashMap<>();
        map.put(JwtClaimsConstant.EMP_ID,emp.getId());

        // 将员工id放到令牌中
        String token = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), map);

        EmployeeLoginVO empVo = EmployeeLoginVO.builder()
                .id(emp.getId())
                .name(emp.getName())
                .userName(emp.getUsername())
                .token(token)
                .build();



        return Result.success(empVo);
    }

    @PostMapping("/save")
    @Operation(summary = "新增员工")
    public Result save(@RequestBody Employee empDTO) {
        log.info("新增员工{}",empDTO);
        employeeService.save(empDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO dto){
        log.info("分页查询参数:{}",dto);
        PageResult pageResult = employeeService.pageQuery(dto);
        return Result.success(pageResult);
    }

    @PostMapping("/update")
    @Operation(summary = "编辑员工信息")
    public Result update(@RequestBody Employee empDTO) {
        log.info("编辑员工信息：{}", empDTO);
        employeeService.update(empDTO);
        return Result.success();
    }

    // POST /admin/employee/status/0?id=100
    @PostMapping("/status/{status}") // 两个都是必传参数
    @Operation(summary = "启用禁用员工账号")
    public Result status(@PathVariable Integer status, @RequestParam Long id){
        log.info("启用禁用员工账号：{}", status==1?"启用账号":"禁用账号");
        employeeService.startOrStop(status,id);
        return Result.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据id查询员工信息")
    public Result getById(@PathVariable Long id){
        log.info("根据id查询员工信息：{}", id);
       return Result.success(employeeService.getById(id)) ;
    }


    @PostMapping("/logout")
    @Operation(summary = "员工退出")
    public Result<String> logout(){
        return Result.success();
    }

}
