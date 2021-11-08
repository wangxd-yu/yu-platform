package org.yu.common.web.async;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author wangxd
 * @date 2021-11-08 22:29
 */
@Data
@ConfigurationProperties(prefix = "yu.async")
public class AsyncExecutorProperties {
    /**
     * 核心线程数
     */
    private Integer corePoolSize;

    /**
     * 最大线程数
     */
    private Integer maxPoolSize;

    /**
     * 最大空闲时间(单位：秒)
     */
    private Integer keepAliveSeconds = 300;

    /**
     * 是否允许核心线程超时
     */
    private boolean allowCoreThreadTimeOut = false;

    /**
     * 线程名称前缀
     */
    private String threadNamePrefix = "Yu-Executor-";

    /**
     * 阻塞队列最大容量
     */
    private Integer queueCapacity = 1024;

    /**
     * 线程拒绝方式
     */
    private RejectEnum rejectedExecutionHandler = RejectEnum.CallerRunsPolicy;

    @PostConstruct
    public void init() {
        //获取核心线程数
        int core = Runtime.getRuntime().availableProcessors();
        //配置核心线程数：cpu核心数（cpu密集型应用）
        if (corePoolSize == null || corePoolSize <= 0) {
            corePoolSize = core;
        }

        //配置最大线程数：cpu核心数*2（IO密集型应用）
        if (maxPoolSize == null || maxPoolSize <= 0) {
            maxPoolSize = core * 2;
        }
    }

    public enum RejectEnum {

        //抛出异常的方式
        AbortPolicy(new ThreadPoolExecutor.AbortPolicy()),
        //提交任务的线程自行执行该任务
        CallerRunsPolicy(new ThreadPoolExecutor.CallerRunsPolicy()),
        //线程池会放弃当前等待队列中，最久的任务，当前被拒绝的任何放入队列
        DiscardOldestPolicy(new ThreadPoolExecutor.DiscardOldestPolicy()),
        //直接丢弃当前拒绝的任务
        DiscardPolicy(new ThreadPoolExecutor.DiscardPolicy());

        /**
         * 拒绝策略的处理器
         */
        private RejectedExecutionHandler rejectedExecutionHandler;

        RejectEnum(RejectedExecutionHandler rejectedExecutionHandler) {
            this.rejectedExecutionHandler = rejectedExecutionHandler;
        }

        public RejectedExecutionHandler getRejectedExecutionHandler() {
            return rejectedExecutionHandler;
        }
    }
}
