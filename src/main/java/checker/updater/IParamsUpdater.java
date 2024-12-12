package checker.updater;

import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;

import java.util.List;

public interface IParamsUpdater {
    HttpRequest update(HttpRequest request, List<? extends HttpParameter> parameters) throws UpdaterException;
}
