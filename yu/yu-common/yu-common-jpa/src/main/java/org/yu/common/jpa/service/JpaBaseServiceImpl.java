package org.yu.common.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wangxd
 * @date 2020-11-09 09:36
 */
public abstract class JpaBaseServiceImpl<M extends JpaRepository<DO, ID>, DO, ID> implements BaseService<DO, ID> {

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
