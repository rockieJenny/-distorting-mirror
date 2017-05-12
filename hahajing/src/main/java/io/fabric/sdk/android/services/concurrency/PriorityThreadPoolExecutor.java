package io.fabric.sdk.android.services.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriorityThreadPoolExecutor extends ThreadPoolExecutor {
    private static final int CORE_POOL_SIZE = (CPU_COUNT + 1);
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final long KEEP_ALIVE = 1;
    private static final int MAXIMUM_POOL_SIZE = ((CPU_COUNT * 2) + 1);

    static final class PriorityThreadFactory implements ThreadFactory {
        private final int threadPriority;

        public PriorityThreadFactory(int threadPriority) {
            this.threadPriority = threadPriority;
        }

        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setPriority(this.threadPriority);
            thread.setName("Queue");
            return thread;
        }
    }

    PriorityThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, PriorityBlockingQueue<Runnable> workQueue, ThreadFactory factory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, factory);
        prestartAllCoreThreads();
    }

    public static PriorityThreadPoolExecutor create(int threadCount) {
        return new PriorityThreadPoolExecutor(threadCount, threadCount, 1, TimeUnit.SECONDS, new DependencyPriorityBlockingQueue(), new PriorityThreadFactory(10));
    }

    public static PriorityThreadPoolExecutor create() {
        return new PriorityThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, 1, TimeUnit.SECONDS, new DependencyPriorityBlockingQueue(), new PriorityThreadFactory(10));
    }

    protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
        return new PriorityFutureTask(runnable, (Object) value);
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new PriorityFutureTask(callable);
    }

    public void execute(Runnable command) {
        if (command instanceof Comparable) {
            super.execute(command);
        } else {
            super.execute(newTaskFor(command, null));
        }
    }

    protected void afterExecute(Runnable runnable, Throwable throwable) {
        if (runnable instanceof Task) {
            Task task = (Task) runnable;
            task.notifyFinished();
            task.setError(throwable);
        }
        super.afterExecute(runnable, throwable);
    }
}
