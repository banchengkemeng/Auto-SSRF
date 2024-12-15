import checker.filter.cache.FilterCache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.CacheObj;
import org.junit.Test;

import java.util.List;

public class CacheTest {

    private static final FilterCache<String, Byte> cache;

    static {
        try {
            cache = new FilterCache<>(CacheUtil.newLRUCache(3));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testCache() throws Exception{
        cache.put("a", Byte.valueOf("0"));
        cache.put("b", Byte.valueOf("0"));
        cache.put("c", Byte.valueOf("0"));
        cache.put("d", Byte.valueOf("0"));
        cache.store();
        List<CacheObj<String, Byte>> cacheObjList = cache.getCacheObjList();
        for (CacheObj<String, Byte> stringByteCacheObj : cacheObjList) {
            System.out.println(stringByteCacheObj);
        }
    }
}
