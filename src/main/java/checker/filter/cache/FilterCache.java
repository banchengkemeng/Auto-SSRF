package checker.filter.cache;

import cn.hutool.cache.Cache;
import cn.hutool.cache.impl.CacheObj;
import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilterCache<K, V> {
    /**
     * 缓存
     */
    private final Cache<K, V> cache;
    /**
     * 缓存文件路径
     */
    @Getter
    @Setter
    private static String path = System.getProperty("user.home") + "\\AppData\\Roaming\\BurpSuite\\AutoSSRF\\cache";
    private List<CacheObj<K, V>> cacheObjList = null;

    private void createFile(File file) throws IOException {
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            if (!parentFile.mkdirs()) {
                throw new IOException("创建文件失败: " + file.getAbsolutePath());
            }
        }

        boolean newFile = file.createNewFile();
        if (!newFile) {
            throw new IOException("创建文件失败: " + file.getAbsolutePath());
        }
    }

    private void buildCacheFromPath(Cache<K, V> cache, String path) throws IOException, ClassNotFoundException {
        File cacheFile = new File(path);
        if (!cacheFile.exists()) {
            return;
        }

        try (FileInputStream fis = new FileInputStream(cacheFile)) {
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                Object cacheObject = ois.readObject();
                List<CacheObj<K, V>> cacheObjList = (List<CacheObj<K, V>>) cacheObject;
                for (CacheObj<K, V> cacheObj : cacheObjList) {
                    cache.put(cacheObj.getKey(), cacheObj.getValue());
                }
            }
        }
    }

    public FilterCache(Cache<K, V> cache) throws IOException, ClassNotFoundException {
        this.cache = cache;
        buildCacheFromPath(cache, path);
    }

    public FilterCache(Cache<K, V> cache, String path) throws IOException, ClassNotFoundException {
        this.cache = cache;
        FilterCache.path = path;
        buildCacheFromPath(cache, path);
    }

    /**
     * 是否命中
     *
     * @param key key
     * @return 是否命中
     */
    public boolean contains(K key) {
        return cache.containsKey(key);
    }

    /**
     * 添加缓存
     *
     * @param key   key
     * @param value value
     */
    public void put(K key, V value) {
        cache.put(key, value);
    }

    /**
     * 清空缓存
     */
    public void clear() {
        cache.clear();
    }

    /**
     * 缓存文件持久化
     */
    public void store() throws IOException {
        store(path);
    }

    /**
     * 缓存文件持久化, 指定路径
     *
     * @param path 文件路径
     */
    public void store(String path) throws IOException {
        File cacheFile = new File(path);
        // 先删除
        if (cacheFile.exists()) {
            delete(path);
        }

        // 创建文件
        createFile(cacheFile);

        // 持久化
        try (FileOutputStream fos = new FileOutputStream(cacheFile)) {
            try (ObjectOutputStream oos = new ObjectOutputStream(fos)) {
                oos.writeObject(getCacheObjList());
            }
        }
    }

    /**
     * 删除缓存文件
     */
    public void delete() {
        delete(path);
    }

    /**
     * 删除缓存文件
     *
     * @param path 缓存文件路径
     * @throws RuntimeException 异常
     */
    public void delete(String path) throws RuntimeException {
        File file = new File(path);
        boolean delete = file.delete();
        if (!delete) {
            throw new RuntimeException("删除文件失败: " + file.getAbsolutePath());
        }
    }

    public List<CacheObj<K, V>> getCacheObjList() {
        if (cacheObjList != null && !cacheObjList.isEmpty()) {
            return cacheObjList;
        }

        List<CacheObj<K, V>> cacheObjs = new ArrayList<>();
        Iterator<CacheObj<K, V>> cacheObjIterator = cache.cacheObjIterator();
        while (cacheObjIterator.hasNext()) {
            CacheObj<K, V> cacheObj = cacheObjIterator.next();
            cacheObjs.add(cacheObj);
        }
        cacheObjList = cacheObjs;
        return cacheObjs;
    }

    /**
     * 获取缓存数量
     * @return  缓存数
     */
    public Integer getCacheCount() {
        return cache.size();
    }

}
