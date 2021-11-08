package org.yu.common.web.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yu.common.web.exception.EntityExistException;
import org.yu.common.web.exception.EntityNotFoundException;
import org.yu.common.web.exception.ServiceException;
import org.yu.common.core.util.ThrowableUtil;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * @author wangxd
 * @date 2018-11-23
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<?> handleException(Throwable e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        ApiError apiError = new ApiError(BAD_REQUEST.value(), e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(value = ServiceException.class)
    public ResponseEntity<ApiError> badRequestException(ServiceException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        ApiError apiError;
        if (e.getCode() != null) {
            apiError = new ApiError(e.getStatus(), e.getCode(), e.getMessage());
        } else {
            apiError = new ApiError(e.getStatus(), e.getMessage());
        }
        return buildResponseEntity(apiError);
    }

    /**
     * 处理 EntityExist
     */
    @ExceptionHandler(value = EntityExistException.class)
    public ResponseEntity<ApiError> entityExistException(EntityExistException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        ApiError apiError = new ApiError(BAD_REQUEST.value(), e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 处理 EntityNotFound
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ApiError> entityNotFoundException(EntityNotFoundException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        ApiError apiError = new ApiError(NOT_FOUND.value(), e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 处理Get请求中 使用@Valid 验证路径中请求实体校验失败后抛出的异常
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> bindExceptionHandler(BindException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        //获得绑定的返回对象
        BindingResult bindingResult = e.getBindingResult();
        //获得校验未通过的所有提示
        Set<String> messageSet = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toSet());
        ApiError apiError = new ApiError(BAD_REQUEST.value(), BAD_REQUEST.value(), "参数校验失败", messageSet);
        return buildResponseEntity(apiError);
    }

    /**
     * 处理请求参数格式错误 @RequestParam上validate失败后抛出的异常是javax.validation.ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> ConstraintViolationExceptionHandler(ConstraintViolationException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        //获得校验未通过的所有提示
        Set<String> messageSet = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
        ApiError apiError = new ApiError(BAD_REQUEST.value(), BAD_REQUEST.value(), "参数校验失败", messageSet);
        return buildResponseEntity(apiError);
    }

    /**
     * 处理请求参数格式错误 @RequestBody上validate失败后抛出的异常是MethodArgumentNotValidException异常。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        //获得绑定的返回对象
        BindingResult bindingResult = e.getBindingResult();
        //获得校验未通过的所有提示
        Set<String> messageSet = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toSet());
        ApiError apiError = new ApiError(BAD_REQUEST.value(), BAD_REQUEST.value(), "参数校验失败", messageSet);
        return buildResponseEntity(apiError);
    }

    /**
     * 统一返回
     */
    private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, HttpStatus.valueOf(apiError.getStatus()));
    }
}
