package cn.succy.bio.pool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Echo服务器处理类线程池
 *
 * @author Succy
 * @date 2017-01-24 23:35
 **/

public class EchoServerHandlerExecutePool {
    private ExecutorService executor;

    /**
     * Echo服务器处理类线程池构造函数
     * @param maxPoolSize 最大线程数
     * @param queueSize 队列大小
     */
    public EchoServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize, 120L,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(queueSize));
    }

    /**
     * 执行任务
     * @param task 要执行的任务
     */
    public void execute(Runnable task) {
        executor.execute(task);
    }
}
