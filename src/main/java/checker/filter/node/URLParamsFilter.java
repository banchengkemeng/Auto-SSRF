package checker.filter.node;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import checker.filter.IFilter;
import checker.updater.ParamsUpdater;

/**
 * 存在URL相关的参数的才通过过滤
 */
public class URLParamsFilter implements IFilter {
    @Override
    public boolean doFilter(HttpRequestResponse baseRequestResponse, Integer id) {
        for (ParsedHttpParameter parameter : baseRequestResponse.request().parameters()) {
            if (ParamsUpdater.checkParameter(parameter)) {
                return true;
            }
        }
        return false;
    }
}
