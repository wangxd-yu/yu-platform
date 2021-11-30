package org.yu.serve.system.module.menu.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.yu.common.querydsl.api.TreeNode;
import org.yu.common.querydsl.query.annotation.YuDTO;
import org.yu.common.querydsl.query.annotation.YuDTOTransient;
import org.yu.serve.system.module.menu.domain.MenuDO;

import java.util.List;

/**
 * @author wangxd
 * @date 2021-08-15 16:25
 */
@JsonIgnoreProperties({"id", "pid"})
@Data
@YuDTO(domain = MenuDO.class)
public class MenuBuildDTO implements TreeNode<MenuBuildDTO> {

    private String id;

    private String pid;
    /**
     * 菜单名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路由路径
     */
    private String path;

    /**
     * 组件
     */
    private String component;

    /**
     * 子菜单
     */
    @JsonProperty("routes")
    @YuDTOTransient
    private List<MenuBuildDTO> children;
}
