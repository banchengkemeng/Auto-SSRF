package pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum UIThreadPool {
    INSTANCE;

    private final Integer corePoolSize = 5;
    private final Integer maxPoolSize = 500;
    private final Integer workQueueLength = 100;

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
