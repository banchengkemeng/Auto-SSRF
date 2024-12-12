package checker.updater.impl;

import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import checker.updater.UpdaterException;
import checker.updater.IParamsUpdater;

import java.util.List;

public class MultipartAttributeParamsUpdater implements IParamsUpdater {
    @Override
    public HttpRequest update(HttpRequest request, List<? extends HttpParameter> parameters) throws UpdaterException {
        throw new UpdaterException("暂时无法处理 MULTIPART_ATTRIBUTE 类型的请求体");
    }
}
