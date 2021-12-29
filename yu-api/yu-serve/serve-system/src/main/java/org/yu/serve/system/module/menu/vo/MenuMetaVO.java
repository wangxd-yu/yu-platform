package org.yu.serve.system.module.menu.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author yu
 * @date 2018-12-20
 */
@Data
@AllArgsConstructor
public class MenuMetaVO implements Serializable {

    private static final long serialVersionUID = 8019145643059279633L;

    private String title;

    private String icon;
}
