package com.yang.aspect;

import com.yang.annotation.AutoFill;
import com.yang.constant.AutoFillConstant;
import com.yang.context.BaseContext;
import com.yang.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 自定义切面，实现公共字段自动填充处理逻辑
 * AOP 拦截 + 反射赋值； 用于操作添加和编辑操作
 */
@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * 切入点：对哪些方法进行拦截
     * 拦截目标：所有加了 @AutoFill 注解的方法；
     */
    @Before("@annotation(com.yang.annotation.AutoFill)")
    public void autoFill(JoinPoint joinPoint) {

        log.info("开始进行公共字段自动填充...");

        //拿到被拦截的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        System.out.println("被拦截的方法"+signature);
        // 被拦截的注解名字
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        System.out.println("被拦截的注解名字"+autoFill);
        //从注解里拿到操作类型（这里是INSERT 还是 UPDATE）
        OperationType operationType = autoFill.value();
        System.out.println("注解的操作类型是" + operationType);

        // 获取到当前被拦截的方法的参数--实体对象 只有一个参数
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        // 方法参数的实体对象
        Object entity = args[0];
        System.out.println("我是方法参数的实体对象" + entity);

        // 准备赋值的数据
        LocalDateTime now = LocalDateTime.now(); // 当前时间
        Long currentId = BaseContext.getCurrentId();
        System.out.println("我是当前登录id3333" + currentId);
        // 根据当前不同的操作类型，为对应的属性通过反射来赋值
        if (operationType == OperationType.INSERT) {
            // 为4个公共字段赋值
            try {

                // 第二个参数就是方法的参数类型 通过类对象找要调用的 set 方法

                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                System.out.println("我是set方法"+setCreateTime);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);

                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // 通过反射为对象属性赋值
                setCreateTime.invoke(entity, now);
                setUpdateTime.invoke(entity, now);

                setCreateUser.invoke(entity, currentId);
                setUpdateUser.invoke(entity, currentId);


            } catch (Exception e) {
                log.error("公共字段自动填充失败：{}", e.getMessage());
            }
        } else if (operationType == OperationType.UPDATE) {
            // 为2个公共字段赋值
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                // 通过反射为对象属性赋值
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);

            } catch (Exception e) {
                log.error("公共字段自动填充失败：{}", e.getMessage());
            }
        }
    }
}