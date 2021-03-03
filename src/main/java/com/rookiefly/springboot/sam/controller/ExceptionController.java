package com.rookiefly.springboot.sam.controller;

import com.rookiefly.springboot.sam.common.Result;
import com.rookiefly.springboot.sam.exception.CustomAuthenticationException;
import com.rookiefly.springboot.sam.exception.CustomException;
import org.apache.shiro.ShiroException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ShiroException.class)
    public Result handle401(ShiroException ex) {
        LOGGER.error("发生异常", ex);
        return Result.fail("您没有权限访问！");
    }

    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(HttpServletRequest request, Exception ex) {
        LOGGER.error("发生异常", ex);
        /**
         * 不能把所有的异常信息都抛给用户，这会泄漏系统的信息
         * 发生异常时，默认提示 服务暂不可用
         */
        Result result = Result.fail("服务暂不可用");
        if (ex instanceof CustomException) {
            /**
             * 异常为我们自己定义的CustomException，可将异常信息返回给用户
             * CustomException中的异常信息必须要有提示性、引导性，以便用户一看到异常信息就知道为什么会报错、该如何解决错误
             */
            result.setStatus(((CustomException) ex).getCode());
            result.setMessage(ex.getMessage());
        } else if (ex instanceof CustomAuthenticationException) {
            result.setStatus(((CustomAuthenticationException) ex).getCode());
            result.setMessage(ex.getMessage());
        } else {
            result.setStatus(getStatus(request).value());
            result.setMessage("访问出错，无法访问: " + ex.getMessage());
        }
        return result;
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}
