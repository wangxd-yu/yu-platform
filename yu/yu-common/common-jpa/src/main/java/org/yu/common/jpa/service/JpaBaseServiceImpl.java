package org.yu.common.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.yu.common.jpa.domain.JpaBaseDO;

/**
 * @author wangxd
 * @date 2020-11-09
 */
public abstract class JpaBaseServiceImpl<M extends JpaRepository<DO, ID>, DO extends JpaBaseDO, ID> implements BaseService<DO, ID> {

    @Autowired
    protected M baseRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DO save(DO domain) {
        return baseRepository.save(domain);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DO domain) {
        domain.setUpdateTime(null);
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
}
