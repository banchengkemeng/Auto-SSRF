package common;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.Http;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;

public enum HttpProvider {
    INSTANCE;

    private Http http;

    public static void constructHttpProvider(MontoyaApi api) {
        HttpProvider.INSTANCE.http = api.http();
    }

    public HttpRequestResponse sendRequest(HttpRequest request) {
        return http.sendRequest(request);
    }
}
