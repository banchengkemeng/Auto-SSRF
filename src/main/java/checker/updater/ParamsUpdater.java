package checker.updater;

import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.params.HttpParameterType;
import burp.api.montoya.http.message.requests.HttpRequest;
import checker.updater.impl.*;

import java.util.*;

public class ParamsUpdater implements IParamsUpdater {

    @Override
    public HttpRequest update(HttpRequest request, List<? extends HttpParameter> parameters) throws UpdaterException {
        // 获取参数类型
        HashMap<HttpParameterType, List<HttpParameter>> typeMap = new HashMap<>();
        for (HttpParameter parameter : parameters) {
            if (typeMap.containsKey(parameter.type())) {
                List<HttpParameter> httpParameters = typeMap.get(parameter.type());
                httpParameters.add(parameter);
            } else {
                typeMap.put(parameter.type(), Collections.singletonList(parameter));
            }
        }

        // 更新请求包
        Set<Map.Entry<HttpParameterType, List<HttpParameter>>> entries = typeMap.entrySet();
        for (Map.Entry<HttpParameterType, List<HttpParameter>> entry : entries) {
            HttpParameterType type = entry.getKey();
            List<HttpParameter> updateParams = entry.getValue();
            switch (type) {
                case XML: {
                    request = new XMLParamsUpdater().update(request, updateParams);
                    break;
                }
                case XML_ATTRIBUTE: {
                    request = new XMLAttributeParamsUpdater().update(request, updateParams);
                    break;
                }
                case MULTIPART_ATTRIBUTE: {
                    request = new MultipartAttributeParamsUpdater().update(request, updateParams);
                    break;
                }
                case JSON: {
                    request = new JSONParamsUpdater().update(request, updateParams);
                    break;
                }
                default: {
                    request = new DefaultParamsUpdater().update(request, updateParams);
                }
            }
        }

        return request;
    }
}
