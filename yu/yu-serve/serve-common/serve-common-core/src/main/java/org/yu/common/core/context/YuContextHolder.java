package org.yu.common.core.context;

/**
 * @author wangxd
 * @date 2020-11-27 9:27
 */
public class YuContextHolder implements AutoCloseable {

    private static final ThreadLocal<YuContext> ctx = new ThreadLocal<>();

    public YuContextHolder(YuContext yuContext) {
        ctx.set(yuContext);
    }

    public static YuContext getYuContext() {
        return ctx.get();
    }

    public static Integer getTenantId() {
        return ctx.get() == null ? null : ctx.get().getTenantId();
    }

    @Override
    public void close() {
        ctx.remove();
    }
}
