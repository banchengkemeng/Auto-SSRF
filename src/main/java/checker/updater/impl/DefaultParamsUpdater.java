package checker.updater.impl;

import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import checker.updater.IParamsUpdater;
import checker.updater.UpdaterException;

import java.util.List;

public class DefaultParamsUpdater implements IParamsUpdater {
    @Override
    public HttpRequest update(HttpRequest request, List<HttpParameter> parameters) throws UpdaterException {
        return request.withUpdatedParameters(parameters);
    }
}
