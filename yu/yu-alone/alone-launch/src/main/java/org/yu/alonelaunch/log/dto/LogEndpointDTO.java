package org.yu.alonelaunch.log.dto;

import lombok.Data;
import org.yu.alonelaunch.log.domain.LogEndpointDO;
import org.yu.common.querydsl.query.annotation.YuDTO;

import java.time.LocalDateTime;

/**
 * @author wangxd
 * @date 2021-12-04 22:19
 */
@Data
@YuDTO(domain = LogEndpointDO.class)
public class LogEndpointDTO {
    private String id;

    private String username;

    private String userId;

    private String ip;

    private String method;

    private String handler;

    private String pattern;

    private String url;

    private String request;

    private String response;

    private Integer httpStatus;

    private Integer time;

    private LocalDateTime createTime;
}
