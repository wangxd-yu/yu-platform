package org.yu.common.jpa.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author wangxd
 * @date 2020-11-27
 */
public class YuIdGenerator implements IdentifierGenerator {

    static SnowFlake snowFlake;

    static {
        // TODO 配置文件中控制 workId
        snowFlake = new SnowFlake(1, 1);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return String.valueOf(snowFlake.nextId());
    }
}
