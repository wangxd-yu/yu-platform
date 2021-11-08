package org.yu.common.web.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author wangxd
 * @date 2018-11-23
 * 业务异常
 */
@Getter
public class ServiceException extends RuntimeException{

    private Integer status = BAD_REQUEST.value();

    private Integer code;

    public ServiceException(String msg){
        super(msg);
    }

    public ServiceException(Integer code, String msg){
        super(msg);
        this.code = code;
    }

    public ServiceException(HttpStatus status, String msg){
        super(msg);
        this.status = status.value();
    }
}
