package org.yu.common.querydsl.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author wangxd
 * @date 2021-08-11 9:44
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultiDataResult<T> {
    /**
     * 默认分页
     */
    private MultiDataTypeEnum type;

    private List<T> data;

    private Boolean success;

    private Integer current;

    private Integer pageSize;

    private Long total;
}
