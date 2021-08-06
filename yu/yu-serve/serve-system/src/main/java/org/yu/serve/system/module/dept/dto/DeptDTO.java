package org.yu.serve.system.module.dept.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.*;
import org.yu.common.querydsl.query.annotation.YuDTO;
import org.yu.common.querydsl.query.annotation.YuDTOTransient;
import org.yu.serve.system.module.dept.domain.DeptDO;

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

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @JsonProperty("name")
    private String name;

    private String code;

    /**
     * 类型编码
     */
    private String typeCode;

    private String pno;

    private Boolean enabled;

    private Integer subCount;

    @YuDTOTransient
    private List<DeptDTO> children;

    @YuDTOTransient
    private boolean hasChildren;

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
    private boolean hasPid;

}