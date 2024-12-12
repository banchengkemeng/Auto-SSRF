package checker.updater.impl;

import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import checker.updater.IParamsUpdater;
import checker.updater.UpdaterException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.List;

public class JSONParamsUpdater implements IParamsUpdater {
    @Override
    public HttpRequest update(HttpRequest request, List<? extends HttpParameter> parameters) throws UpdaterException {
        String body = request.bodyToString();
        JSONObject params = JSONUtil.parseObj(body);
        for (HttpParameter parameter : parameters) {
            params.set(parameter.name(), parameter.value());
        }
        return request.withBody(params.toString());
    }
}
