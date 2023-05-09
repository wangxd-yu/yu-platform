package org.yu.generate.modules.api.dto.serice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.yu.common.querydsl.service.DslBaseServiceImpl;
import org.yu.generate.modules.api.dto.domain.GenApiDtoDO;
import org.yu.generate.modules.api.dto.repository.GenApiDtoRepository;

/**
 * @author wangxd
 * @date 2023-04-13 23:41
 */
@Slf4j
@Service
public class GenApiDtoServiceImpl extends DslBaseServiceImpl<GenApiDtoRepository, GenApiDtoDO, String> implements GenApiDtoService {
}
