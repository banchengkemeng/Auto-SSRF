package common.pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum UIThreadPool {
    INSTANCE;

    private Integer corePoolSize = 5;
    private Integer maxPoolSize = 500;
    private Integer workQueueLength = 100;

    private ThreadPoolExecutor poolExecutor = null;

    private void createPool() {
        this.poolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                5L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(workQueueLength),
                new NamedThreadFactory("ui")
        );
    }

    public ThreadPoolExecutor getPool() {
        if (poolExecutor == null) {
            this.createPool();
            return poolExecutor;
        }

        return poolExecutor;
    }

}
