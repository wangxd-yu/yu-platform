package org.yu.generate.modules.api.module.serice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.generate.modules.api.module.domain.GenApiModuleDO;
import org.yu.generate.modules.api.module.repository.GenApiModuleRepository;

/**
 * @author wangxd
 * @date 2023-04-13 23:55
 */
@Slf4j
@Service
public class GenApiModuleServiceImpl extends DslBaseServiceImpl<GenApiModuleRepository, GenApiModuleDO, String> implements GenApiModuleService {
}
