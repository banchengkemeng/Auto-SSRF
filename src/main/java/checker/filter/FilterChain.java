package checker.filter;

import burp.api.montoya.http.message.HttpRequestResponse;
import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class FilterChain {

    private final LinkedHashMap<String, IFilter> filterMap = new LinkedHashMap<>();

    public boolean doFilter(HttpRequestResponse baseRequestResponse, Integer id) {
        for (IFilter filter : filterMap.values()) {
            boolean result = filter.doFilter(baseRequestResponse, id);
            if (!result) {
                return false;
            }
        }
        return true;
    }

    public FilterChain addFilter(String key, IFilter filter) {
        if (filterMap.containsKey(key)) {
            throw new RuntimeException("创建正则过滤器存在重复的key: " + key);
        }
        filterMap.put(key, filter);
        return this;
    }

    public void removeFilter(String key) {
        filterMap.remove(key);
    }

    public void topMove(String key) {
        if (key == null) {
            return;
        }
        String lastKey = null;
        for (Map.Entry<String, IFilter> entry : filterMap.entrySet()) {
            String entryKey = entry.getKey();
            if (key.equals(entryKey)) {
                if (lastKey != null) {
                    swap(key, lastKey);
                    break;
                }
            }
            lastKey = entryKey;
        }
    }

    public void bottomMove(String key) {
        if (key == null) {
            return;
        }
        boolean isFindSource = false;
        for (Map.Entry<String, IFilter> entry : filterMap.entrySet()) {
            if (isFindSource) {
                String targetKey = entry.getKey();
                swap(key, targetKey);
                break;
            }
            String entryKey = entry.getKey();
            if (key.equals(entryKey)) {
                isFindSource = true;
            }
        }
    }

    private void swap(String sourceKey, String targetKey) {
        LinkedHashMap<String, IFilter> tempMap = new LinkedHashMap<>();
        for (Map.Entry<String, IFilter> entry : filterMap.entrySet()) {
            String key = entry.getKey();
            IFilter value = entry.getValue();
            if (key.equals(targetKey)) {
                tempMap.put(sourceKey, filterMap.get(sourceKey));
            } else if (key.equals(sourceKey)) {
                tempMap.put(targetKey, filterMap.get(targetKey));
            } else {
                tempMap.put(key, value);
            }
        }
        filterMap.clear();
        filterMap.putAll(tempMap);
    }
}
