package checker.filter;

import burp.api.montoya.http.message.HttpRequestResponse;

public interface IFilter {
    boolean doFilter(HttpRequestResponse baseRequestResponse, Integer id);
}
