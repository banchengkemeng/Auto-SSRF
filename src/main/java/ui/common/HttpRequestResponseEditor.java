package ui.common;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import common.provider.UIProvider;
import lombok.Data;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

@Data
public class HttpRequestResponseEditor {
    private final HttpRequestEditor httpRequestEditor;
    private final HttpResponseEditor httpResponseEditor;
    private final UIProvider uiProvider = UIProvider.INSTANCE;

    public HttpRequestResponseEditor() {
        this.httpRequestEditor = uiProvider.createHttpRequestEditor();
        this.httpResponseEditor = uiProvider.createHttpResponseEditor();


    }

    public void setData(HttpRequestResponse httpRequestResponse) {
        httpRequestEditor.setRequest(httpRequestResponse.request());
        httpResponseEditor.setResponse(httpRequestResponse.response());
    }

    public Component uiComponent() {
        JSplitPane requestResponsePane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        requestResponsePane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                requestResponsePane.setDividerLocation(0.4);
            }
        });
        JTabbedPane requestPane = new JTabbedPane();
        JTabbedPane responsePane = new JTabbedPane();
        requestPane.addTab("Request", httpRequestEditor.uiComponent());
        responsePane.addTab("Response", httpResponseEditor.uiComponent());
        requestResponsePane.add(requestPane, "left");
        requestResponsePane.add(responsePane, "right");
        return requestResponsePane;
    }
}
