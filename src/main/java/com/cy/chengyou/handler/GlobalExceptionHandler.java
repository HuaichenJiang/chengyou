package com.cy.chengyou.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author HuaichenJiang
 * @Title
 * @Description 整个web请求项目全局捕获异常，使用AOP中的异常通知
 * @date 2018/11/9  11:33
 */
@ControllerAdvice(basePackages = "com.cy.chengyou.controllers")
public class GlobalExceptionHandler {


    //@ResponseBody 适用于返回json异常
    //@ExceptionHandler(需要捕获的异常类型)
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity errorJsonResult(){
        Map<String, Object> errorResultMap = new HashMap<String, Object>();
        errorResultMap.put("errorMessage", "系统错误");
        return new ResponseEntity(errorResultMap, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}