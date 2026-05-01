package com.yang.controller.user;


import com.yang.dto.UserLoginDTO;
import com.yang.entity.User;
import com.yang.exception.BaseException;
import com.yang.mapper.UserMapper;
import com.yang.result.Result;
import com.yang.service.UserService;
import com.yang.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "用户端相关接口")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO dto){

        UserLoginVO login = userService.login(dto);
        return Result.success(login);
    }

    @PostMapping("/register")
    @Operation(summary = "用户注册")
    public Result Register(@RequestBody UserLoginDTO dto){

        if(dto.getIdNumber().length()<18){
            throw new BaseException("身份证号码不能小于18位");
        }

        User u=User.builder()
                .name(dto.getName())
                        .idNumber(dto.getIdNumber())
                                .build();

        userMapper.insert(u);
        return Result.success(dto);
    }


}
