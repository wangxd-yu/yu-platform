package com.yu.common.querydsl.exception;

/**
 * Yu 查询注解异常
 *
 * @author wangxd
 * @date 2021-04-26
 */
public class YuQueryException extends RuntimeException {
    public YuQueryException(String message) {
        super(message);
    }

    public YuQueryException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
