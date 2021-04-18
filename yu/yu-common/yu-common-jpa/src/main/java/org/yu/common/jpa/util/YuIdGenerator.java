package org.yu.common.jpa.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;

/**
 * @author wangxd
 * @date 2020-11-27 9:13
 */
public class YuIdGenerator implements IdentifierGenerator {

    static Snowflake snowflake;
    static {
        // TODO 配置文件中控制 workId
        snowflake = IdUtil.getSnowflake(1, 1);
    }

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        return snowflake.nextId();
    }
}
