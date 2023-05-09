package org.yu.generate.core.pojo.bo;

import lombok.Data;
import org.yu.generate.modules.api.module.domain.GenApiModuleDO;
import org.yu.generate.modules.view.domain.GenViewModuleDO;

/**
 * 标准模型
 *
 * @author wangxd
 * @date 2023-04-16 0:11
 */
@Data
public class StandardModelBO {

    private GenApiModuleDO genApiModuleDO;

    private GenViewModuleDO genViewModuleDO;
}
