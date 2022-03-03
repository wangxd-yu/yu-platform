package org.yu.serve.system.module.dept.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.yu.common.querydsl.query.annotation.YuDTO;
import org.yu.common.querydsl.query.annotation.YuDTOTransient;
import org.yu.serve.system.module.dept.domain.DeptDO;

import java.io.Serializable;
import java.util.List;

/**
 * @author wangxd
 * @date 2020-11-30 15:44
 */
@Getter
@Setter
@YuDTO(domain = DeptDO.class)
public class DeptTreeDTO<DTO> implements Serializable {

    private String id;

    private String pid;

    /**
     * 名称
     */
    @JsonProperty("label")
    private String name;

    @YuDTOTransient
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DTO> children;

    /**
     * 标记字段：用于 从 list 转换为 tree
     */
    @YuDTOTransient
    @JsonIgnore
    private boolean hasParent;
}