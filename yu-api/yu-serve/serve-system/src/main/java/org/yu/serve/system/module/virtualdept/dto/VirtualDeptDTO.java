package org.yu.serve.system.module.virtualdept.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.yu.common.querydsl.api.TreeNode;
import org.yu.common.querydsl.query.annotation.YuDTO;
import org.yu.common.querydsl.query.annotation.YuDTOTransient;
import org.yu.serve.system.module.virtualdept.domain.VirtualDeptDO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wangxd
 * @date 2022-04-07 10:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@YuDTO(domain = VirtualDeptDO.class)
public class VirtualDeptDTO implements TreeNode<VirtualDeptDTO> {

    private String id;

    private String name;

    /**
     * 排序
     */
    private Integer sort;

    private String pid;

    private Boolean enabled;

    private Integer subCount;

    @YuDTOTransient
    private List<VirtualDeptDTO> children;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

}