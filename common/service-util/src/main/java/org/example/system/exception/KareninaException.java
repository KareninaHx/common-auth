package org.example.system.exception;

import lombok.Data;
import org.example.common.result.ResultCodeEnum;

/**
 * Created by 27 on 2023/9/30
 */
@Data
/*
自定义全局异常类
 */
public class KareninaException extends RuntimeException{

    private Integer code;

    private String message;

    /**
     * 通过状态码和错误消息创建异常对象
     * @param code
     * @param message
     */
    public KareninaException(Integer code, String message) {
        super(message);//调用父类的构造方法 进行初始化
        this.code = code;
        this.message = message;
    }

    /**
     * 接收枚举类型对象
     * @param resultCodeEnum
     */
    public KareninaException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());//调用父类的构造方法 进行初始化
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    @Override
    public String toString() {
        return "KareninaException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }

}
