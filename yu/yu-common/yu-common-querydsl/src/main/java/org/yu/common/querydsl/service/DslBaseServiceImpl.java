package org.yu.common.querydsl.service;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.yu.common.querydsl.query.util.PageUtil;
import org.yu.common.querydsl.query.util.YuQueryHelp;
import org.yu.common.querydsl.repository.DslBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author wangxd
 * @date 2020-11-25
 */
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public abstract class DslBaseServiceImpl<M extends DslBaseRepository<DO, ID>, DO, ID> implements DslBaseService<DO, ID> {
    @Autowired(required = false)
    protected M baseRepository;

    @PersistenceContext
    protected EntityManager entityManager;

    protected JPAQueryFactory getJPAQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DO save(DO domain) {
        /// 后续参考
        /*if (domain instanceof DslBaseTenantDO) {
            ((DslBaseTenantDO<?>) domain).setTenantId(YuContextHolder.getYuContext().getTenantId());
        }*/
        return baseRepository.save(domain);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DO domain) {
        baseRepository.save(domain);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ID id) {
        baseRepository.deleteById(id);
    }

    @Override
    public DO getById(ID id) {
        return baseRepository.findById(id).orElse(null);
    }

    @Override
    public <Q> Object query(Q query, Pageable pageable) {
        JPAQuery<DO> jpaQuery = YuQueryHelp.getJPAQuery(getJPAQueryFactory(), query);
        if (pageable == null) {
            return jpaQuery.fetch();
        }
        return PageUtil.toPage(jpaQuery, pageable);
    }

    @Override
    public <Q, DTO> Object queryDTO(Q query, Pageable pageable, Class<DTO> clazz) {
        JPAQuery<DTO> jpaQuery = YuQueryHelp.getJPAQuery(getJPAQueryFactory(), query, clazz);
        if (pageable == null) {
            return jpaQuery.fetch();
        }
        return PageUtil.toPage(jpaQuery, pageable);
    }
}