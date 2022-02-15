package org.yu.common.core.context;

import com.alibaba.ttl.TransmittableThreadLocal;

/**
 * @author wangxd
 * @date 2020-11-27 9:27
 */
public class YuContextHolder implements AutoCloseable {

    private static final ThreadLocal<YuContext> CTX = new TransmittableThreadLocal<>();

    public YuContextHolder(YuContext yuContext) {
        CTX.set(yuContext);
    }

    public static YuContext getYuContext() {
        return CTX.get();
    }

    public static String getTenantId() {
        return CTX.get() == null ? null : CTX.get().getTenantId();
    }

    @Override
    public void close() {
        CTX.remove();
    }

    public static void clearContext() {
        CTX.remove();
    }
}