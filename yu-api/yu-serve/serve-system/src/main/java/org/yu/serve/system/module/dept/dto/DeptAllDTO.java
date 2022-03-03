package org.yu.serve.system.module.dept.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 返回全部树结构（上下级全部）
 *
 * @author yu
 * @date 2020-09-09
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptAllDTO extends DeptTreeDTO<DeptAllDTO> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;
    /**
     * 排序
     */
    private Integer sort;

    @NotNull
    private Boolean enabled;

    /**
     * 上级部门
     */
    private String pid;

    private Integer subCount;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<DeptAllDTO> children;

    private boolean hasChildren;

    public boolean isHasChildren() {
        return subCount > 0;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 标记字段：用于 从 list 转换为 tree
     */
    @JsonIgnore
    private boolean hasPid;

    public DeptAllDTO(String id, String name, String pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
    }
}