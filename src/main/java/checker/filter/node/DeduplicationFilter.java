package checker.filter.node;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.params.HttpParameterType;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import checker.filter.IFilter;
import checker.filter.cache.DeduplicationFilterCacheManager;
import checker.filter.cache.FilterCache;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.crypto.digest.MD5;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DeduplicationFilter implements IFilter {
    private final FilterCache<String, Byte> cache;

    public DeduplicationFilter() {
        DeduplicationFilterCacheManager.initCacheManager(50000);
        cache = DeduplicationFilterCacheManager.INSTANCE.getCache();
    }

    @Override
    public boolean doFilter(HttpRequestResponse baseRequestResponse, Integer id) {
        // 1. 判断是否是重新检测 | 重新检测的任务不进行过滤，如果id不为空，代表是重新检测的任务
        if (id != null) {
            return true;
        }
        HttpRequest request = baseRequestResponse.request();

        // 2. 解析出所有符合要求的的参数并拼接字符串
        String checkString = buildCheckString(request);
        if (checkString == null) {
            return false;
        }

        // 3. 计算Hash
        String hash = MD5.create().digestHex(checkString);

        // 4. 判断hash有没有在缓存中(有没有遇到过)
        if (!cache.contains(hash)) {
            cache.put(hash, Byte.valueOf("0"));
            return true;
        }

        return false;
    }

    private String buildCheckString(HttpRequest request) {
        URL url = URLUtil.url(request.url());
        String baseURL = url.getProtocol() + "://" + url.getHost() + "/";
        String path = url.getPath();
        List<ParsedHttpParameter> parameters = request.parameters();
        // 没有参数, 直接结束
        if (parameters.isEmpty()) {
            return null;
        }

        // Key按字典序排序
        parameters.sort(Comparator.comparing(ParsedHttpParameter::name));

        ArrayList<String> queries = new ArrayList<>();
        ArrayList<String> params = new ArrayList<>();
        ArrayList<String> cookies = new ArrayList<>();
        for (ParsedHttpParameter parameter : parameters) {

            HttpParameterType type = parameter.type();
            String paramString = String.format("%s=%s", parameter.name(), parameter.value());
            // GET和POST参数: 取key和value
            // COOKIE参数: 只取key
            switch (type) {
                case URL: {
                    queries.add(paramString);
                    break;
                }
                case COOKIE: {
                    cookies.add(parameter.name());
                    break;
                }
                default: {
                    params.add(paramString);
                    break;
                }
            }
        }

        String queryString = "";
        String paramString = "";
        String cookieString = "";
        if (!queries.isEmpty()) {
            queryString = String.format("path: %s|query: %s", path, queries);
        }
        if (!params.isEmpty()) {
            paramString = String.format("path: %s|param: %s", path, params);
        }
        if (!cookies.isEmpty()) {
            cookieString = String.format("cookie: %s", cookies);
        }

        // 三种参数都没有，直接结束
        if (StrUtil.isAllBlank(queryString, paramString, cookieString)) {
            return null;
        }

        return baseURL + queryString + paramString + cookieString;
    }
}
