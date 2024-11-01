package pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum CollaboratorThreadPool {
    INSTANCE;

    private final Integer corePoolSize = 8;
    private final Integer maxPoolSize = 500;
    private final Integer workQueueLength = 80;

    private ThreadPoolExecutor poolExecutor = null;

    private void createPool() {
        this.poolExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maxPoolSize,
                5L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(workQueueLength),
                new NamedThreadFactory("collaborator")
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
