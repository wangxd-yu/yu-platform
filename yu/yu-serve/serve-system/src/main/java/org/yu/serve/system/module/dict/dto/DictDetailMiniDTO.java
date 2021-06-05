package org.yu.serve.system.module.dict.dto;

import lombok.Data;
import org.yu.common.querydsl.query.annotation.YuDTO;
import org.yu.serve.system.module.dict.domain.DictDetailDO;

/**
 * 对应Query : DictDetailMiniQuery
 *
 * @author wangxd
 * @date 2020-12-02 19:21
 */
@Data
@YuDTO(domain = DictDetailDO.class)
public class DictDetailMiniDTO {

    private String code;

    private String name;
}
