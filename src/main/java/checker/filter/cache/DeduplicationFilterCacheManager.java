package checker.filter.cache;

import cn.hutool.cache.CacheUtil;
import lombok.Getter;

@Getter
public enum DeduplicationFilterCacheManager {
    INSTANCE;

    private FilterCache<String, Byte> cache;

    public static void initCacheManager(int capacity) {
        try {
            DeduplicationFilterCacheManager.INSTANCE.cache = new FilterCache<>(CacheUtil.newLRUCache(capacity));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}