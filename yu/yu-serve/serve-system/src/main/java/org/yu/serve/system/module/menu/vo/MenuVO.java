package org.yu.serve.system.module.menu.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 构建前端路由时用到
 *
 * @author yu
 * @date 2018-12-20
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuVO implements Serializable {

    private static final long serialVersionUID = 6011646772300763261L;

    private String name;

    private String path;

    private String redirect;

    private String component;

    /**
     * 图标
     */
    private String icon;

    private Boolean alwaysShow;

    private MenuMetaVO meta;

    @JsonProperty("routes")
    private List<MenuVO> children;
}
