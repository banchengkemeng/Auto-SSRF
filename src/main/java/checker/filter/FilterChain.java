package checker.filter;

import burp.api.montoya.http.message.HttpRequestResponse;

import java.util.ArrayList;
import java.util.List;

public class FilterChain{

    private final List<IFilter> filterList = new ArrayList<>();

    public boolean doFilter(HttpRequestResponse baseRequestResponse, Integer id) {
        for (IFilter filter : filterList) {
            boolean result = filter.doFilter(baseRequestResponse, id);
            if (!result) {
                return false;
            }
        }
        return true;
    }

    public FilterChain addFilter(IFilter filter) {
        filterList.add(filter);
        return this;
    }
}
