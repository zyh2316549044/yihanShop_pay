package com.demo.yihan_shop.exceptions;

import com.demo.yihan_shop.enums.ResponseEnum;
import com.demo.yihan_shop.vo.ResponseVo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

import static com.demo.yihan_shop.enums.ResponseEnum.ERROR;

/**
 * @description:
 * @author: zhangyihan
 * @createDate: 2020-05-02 19:17
 * @version: 1.0
 */
@ControllerAdvice//标明是一个处理注解类，将异常自己定义
public class RuntimeExceptionHealer {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseVo handle(RuntimeException e){
        return ResponseVo.errer(ERROR,e.getMessage());
    }


    @ExceptionHandler(UserloginExecption.class)
    @ResponseBody
    public ResponseVo userLoginHandle(){
        return ResponseVo.errer(ResponseEnum.NEED_LOGIN);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseVo methodArgumentNotValidException(MethodArgumentNotValidException e){
        //获取from验证的信息
        BindingResult bindingResult = e.getBindingResult();
        Objects.requireNonNull(bindingResult.getFieldError());
        return ResponseVo.errer(ResponseEnum.PARAM_ERROR,bindingResult.getFieldError().getField()+" "+bindingResult
                .getFieldError().getDefaultMessage());
    }
}
