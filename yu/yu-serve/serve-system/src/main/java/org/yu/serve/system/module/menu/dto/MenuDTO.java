package org.yu.serve.system.module.menu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuDTO;
import org.yu.common.querydsl.query.annotation.YuDTOTransient;
import org.yu.serve.system.module.menu.domain.MenuDO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wangxd
 * @date 2020-11-26 15:20
 */
@Data
@YuDTO(domain = MenuDO.class)
public class MenuDTO {

    private Long id;
    /**
     * 上级菜单ID
     */
    private Long pid;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 组件
     */
    private String component;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否外链
     */
    private Boolean frame;

    /**
     * 链接地址
     */
    private String path;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 子菜单
     */
    @YuDTOTransient
    private List<MenuDTO> children;
}
