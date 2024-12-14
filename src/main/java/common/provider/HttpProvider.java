package common.provider;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.Registration;
import burp.api.montoya.http.Http;
import burp.api.montoya.http.handler.HttpHandler;
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

    public Registration registerHttpHandler(HttpHandler handler) {
        return http.registerHttpHandler(handler);
    }
}
