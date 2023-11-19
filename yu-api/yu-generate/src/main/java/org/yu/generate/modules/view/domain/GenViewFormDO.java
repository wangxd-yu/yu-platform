package org.yu.generate.modules.view.domain;

import lombok.Data;

import java.util.List;

/**
 * 前端表单信息
 *
 * @author wangxd
 * @date 2023-10-02 10:26
 */
@Data
public class GenViewFormDO {

    private List<GenViewFormColumnDO> columns;
}
