package org.yu.common.querydsl.service;

import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yu.common.querydsl.api.MultiDataResult;
import org.yu.common.querydsl.api.MultiDataTypeEnum;
import org.yu.common.querydsl.api.TreeNode;
import org.yu.common.querydsl.query.util.WrapDataUtil;
import org.yu.common.querydsl.query.util.YuQueryHelp;
import org.yu.common.querydsl.repository.DslBaseRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

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
        Object jpaQueryFactoryObject = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute("JPAQueryFactory", RequestAttributes.SCOPE_REQUEST);
        if (jpaQueryFactoryObject != null) {
            return (JPAQueryFactory) jpaQueryFactoryObject;
        } else {
            JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
            RequestContextHolder.getRequestAttributes().setAttribute("JPAQueryFactory", jpaQueryFactory, RequestAttributes.SCOPE_REQUEST);
            return jpaQueryFactory;
        }
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
    public <Q> MultiDataResult<DO> query(Q query, Pageable pageable) {
        JPAQuery<DO> jpaQuery = YuQueryHelp.getJPAQuery(getJPAQueryFactory(), query);
        return queryForReturnType(jpaQuery, pageable, getMultiDataType(null, pageable));
    }

    @Override
    public <Q, DTO> MultiDataResult<DTO> queryDTO(Q query, Pageable pageable, Class<DTO> clazz, MultiDataTypeEnum typeEnum) {
        JPAQuery<DTO> jpaQuery = YuQueryHelp.getJPAQuery(getJPAQueryFactory(), query, clazz);
        return queryForReturnType(jpaQuery, pageable, getMultiDataType(typeEnum, pageable));
    }

    private MultiDataTypeEnum getMultiDataType(MultiDataTypeEnum typeEnum, Pageable pageable) {
        if (typeEnum == null) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            assert servletRequestAttributes != null;
            HttpServletRequest request = servletRequestAttributes.getRequest();
            String typeStr = request.getParameter("yuRtn");
            if (!StringUtils.isEmpty(typeStr)) {
                typeEnum = MultiDataTypeEnum.getByName(request.getParameter("yuRtn"));
            }

            if (typeEnum == null) {
                if(pageable != null) {
                    typeEnum = MultiDataTypeEnum.PAGE;
                } else {
                    typeEnum = MultiDataTypeEnum.LIST;
                }
            }
        }
        return typeEnum;
    }

    private <T> MultiDataResult<T> queryForReturnType(JPAQuery<T> jpaQuery, Pageable pageable, MultiDataTypeEnum typeEnum) {
        switch (typeEnum) {
            case LIST:
                return WrapDataUtil.toList(jpaQuery);
            case TREE:
                return (MultiDataResult<T>) WrapDataUtil.toTree((AbstractJPAQuery<TreeNode, ?>) jpaQuery);
            default:
                return WrapDataUtil.toPage(jpaQuery, pageable);
        }
    }
}