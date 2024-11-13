package pool;

import lombok.Getter;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Getter
public enum CollaboratorThreadPool {
    INSTANCE;

    private Integer corePoolSize = 8;
    private Integer maxPoolSize = 500;

    private ThreadPoolExecutor poolExecutor = null;

    private void createPool() {
        Integer workQueueLength = 80;
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

    public void setCorePoolSize(Integer corePoolSize) {
        this.poolExecutor.setCorePoolSize(corePoolSize);
        this.corePoolSize = corePoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.poolExecutor.setMaximumPoolSize(maxPoolSize);
        this.maxPoolSize = maxPoolSize;
    }

    public void setPoolSize(Integer corePoolSize, Integer maxPoolSize) {
        if (corePoolSize > this.maxPoolSize) {
            this.setMaxPoolSize(maxPoolSize);
            this.setCorePoolSize(corePoolSize);
        } else {
            this.setCorePoolSize(corePoolSize);
            this.setMaxPoolSize(maxPoolSize);
        }

    }
}
