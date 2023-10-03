package org.example.system.exception;

import org.example.common.result.Result;
import org.example.common.result.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by 27 on 2023/9/30
 */
/*
全局异常处理类
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    //处理XXX异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }

    //处理算术异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result error(ArithmeticException e){
        e.printStackTrace();
        return Result.fail().message("执行了特定异常处理");
    }

    @ExceptionHandler(KareninaException.class)
    @ResponseBody
    public Result error(KareninaException e){
        e.printStackTrace();//输出toString方法
        return Result.fail().message(e.getMessage()).code(e.getCode());
    }

    /**
     * spring security异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result error(AccessDeniedException e) throws AccessDeniedException {
        //return Result.build(null, ResultCodeEnum.PERMISSION);
        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message("没有当前功能操作权限");
    }
}
