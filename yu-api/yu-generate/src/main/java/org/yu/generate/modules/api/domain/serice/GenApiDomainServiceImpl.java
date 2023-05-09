package org.yu.generate.modules.api.domain.serice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.generate.modules.api.domain.domain.GenApiDomainDO;
import org.yu.generate.modules.api.domain.repository.GenApiDomainRepository;

/**
 * @author wangxd
 * @date 2023-04-13 23:29
 */
@Slf4j
@Service
public class GenApiDomainServiceImpl extends DslBaseServiceImpl<GenApiDomainRepository, GenApiDomainDO, String> implements GenApiDomainService {
}
