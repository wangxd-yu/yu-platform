package org.yu.generate.modules.api.query.serice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.generate.modules.api.query.domain.GenApiQueryDO;
import org.yu.generate.modules.api.query.repository.GenApiQueryRepository;

/**
 * @author wangxd
 * @date 2023-04-13 23:50
 */
@Slf4j
@Service
public class GenApiQueryServiceImpl extends DslBaseServiceImpl<GenApiQueryRepository, GenApiQueryDO, String> implements GenApiQueryService {
}
