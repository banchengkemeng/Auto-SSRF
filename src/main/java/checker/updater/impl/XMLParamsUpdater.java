package checker.updater.impl;

import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import checker.updater.IParamsUpdater;
import checker.updater.UpdaterException;

import java.util.List;

public class XMLParamsUpdater implements IParamsUpdater {
    @Override
    public HttpRequest update(HttpRequest request, List<? extends HttpParameter> parameters) throws UpdaterException {
        throw new UpdaterException("暂时无法处理 XML 类型的请求体");
    }
}
