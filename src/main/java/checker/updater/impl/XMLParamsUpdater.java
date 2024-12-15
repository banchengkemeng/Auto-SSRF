package checker.updater.impl;

import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import checker.updater.IParamsUpdater;
import checker.updater.UpdaterException;
import cn.hutool.core.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLParamsUpdater implements IParamsUpdater {
    @Override
    public HttpRequest update(HttpRequest request, List<HttpParameter> parameters) throws UpdaterException {
        Document document = XmlUtil.readXML(request.bodyToString());
        updateXMLDocument(document, parametersToMap(parameters));
        return request.withBody(XmlUtil.toStr(document));
    }

    private Map<String, String> parametersToMap(List<? extends HttpParameter> parameters) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        for (HttpParameter parameter : parameters) {
            stringStringHashMap.put(parameter.name(), parameter.value());
        }
        return stringStringHashMap;
    }

    private void updateXMLDocument(Node node, Map<String, String> parameterMap) {
        if (node.getNodeType() == Node.TEXT_NODE) {
            String key = node.getParentNode().getNodeName().toLowerCase();
            if (parameterMap.containsKey(key)) {
                node.setNodeValue(parameterMap.get(key));
            }
            return;
        }

        NodeList childNodes = node.getChildNodes();
        int length = childNodes.getLength();
        for (int i = 0; i < length; i++) {
            Node item = childNodes.item(i);
            updateXMLDocument(item, parameterMap);
        }
    }
}
