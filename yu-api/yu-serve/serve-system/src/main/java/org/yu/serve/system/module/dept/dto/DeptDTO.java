package org.yu.serve.system.module.dept.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.yu.common.querydsl.query.annotation.YuDTO;
import org.yu.common.querydsl.query.annotation.YuDTOField;
import org.yu.common.querydsl.query.annotation.YuDTOTransient;
import org.yu.serve.system.module.dept.domain.DeptDO;
import org.yu.serve.system.module.dept.domain.DeptTypeDO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yu
 * @date 2019-03-25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@YuDTO(domain = DeptDO.class)
public class DeptDTO extends DeptTreeDTO<DeptDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    @JsonProperty("name")
    private String name;

    private String code;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 类型id
     */
    private String typeId;
    /**
     * 类型编码
     */
    private String typeCode;

    /**
     * 类型名称
     */
    @YuDTOField(domain = DeptTypeDO.class, propName = "name")
    private String typeName;

    private String pid;

    private Boolean enabled;

    private Integer subCount;

    @YuDTOTransient
    private List<DeptDTO> children;

    @YuDTOTransient
    private Boolean hasChildren;

    public boolean isHasChildren() {
        return subCount > 0;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 标记字段：用于 从 list 转换为 tree
     */
    @YuDTOTransient
    @JsonIgnore
    private Boolean hasPid;

}