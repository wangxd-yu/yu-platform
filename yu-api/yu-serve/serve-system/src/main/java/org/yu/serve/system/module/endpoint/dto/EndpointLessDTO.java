package org.yu.serve.system.module.endpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * This is Description
 *
 * @author wangxd
 * @date 2021-09-16
 */
@Data
@AllArgsConstructor
public class EndpointLessDTO {
    private String pattern;

    @Enumerated(EnumType.STRING)
    private RequestMethod method;
}
