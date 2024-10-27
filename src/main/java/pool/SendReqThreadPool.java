package pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum SendReqThreadPool {
    INSTANCE;

    private final Integer corePoolSize = 16;
    private final Integer maxPoolSize = 500;
    private final Integer workQueueLength = 32;

    private ThreadPoolExecutor poolExecutor = null;

    private void createPool() {
        this.poolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                5L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(workQueueLength),
                new NamedThreadFactory("sendReq")
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
