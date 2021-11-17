package org.yu.common.multidb.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.yu.common.core.context.YuContextHolder;
import org.yu.common.core.exception.ServiceException;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Spring 动态数据源
 *
 * @author wangxd
 * @date 2021-03-08 22:35
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    public static Map<Object, Object> dynamicTargetDataSources = new HashMap<>(8);

    @Override
    protected Object determineCurrentLookupKey() {
        String tenantId = YuContextHolder.getYuContext() != null ? YuContextHolder.getYuContext().getTenantId().toString() : null;
        if (tenantId == null) {
            HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            tenantId = req.getParameter("tenantId");
        }
        if (!super.getResolvedDataSources().containsKey(tenantId)) {
            throw new ServiceException("租户编号不存在！");
        }
        return tenantId;
    }

    /**
     * 创建数据源
     *
     * @param key
     * @param driveClass
     * @param url
     * @param username
     * @param password
     * @param databaseType
     * @return
     */
    public static boolean createDataSource(String key, String driveClass, String url, String username, String password, String databaseType) {
        //TODO
        return false;
    }

    /**
     * 删除数据源
     *
     * @param tenantId 租户id
     * @return
     */
    public static boolean deleteDataSource(String tenantId) {
        //TODO
        return false;
    }
}
