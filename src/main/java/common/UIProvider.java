package common;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.Registration;
import burp.api.montoya.ui.UserInterface;
import burp.api.montoya.ui.editor.EditorOptions;
import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import lombok.Getter;
import ui.UIMain;

import java.awt.*;

public enum UIProvider {
    INSTANCE;

    private UserInterface userInterface;

    @Getter
    private UIMain uiMain;

    public static void constructUIProvider(MontoyaApi api) {
        UIProvider.INSTANCE.userInterface = api.userInterface();
    }

    public Registration registerSuiteTab(String title, Component component) {
        uiMain = (UIMain) component;
        return userInterface.registerSuiteTab(title, component);
    }

    public void applyThemeToComponent(Component component) {
        userInterface.applyThemeToComponent(component);
    }


    public HttpRequestEditor createHttpRequestEditor(EditorOptions... options) {
        return userInterface.createHttpRequestEditor(options);
    }

    public HttpResponseEditor createHttpResponseEditor(EditorOptions... options) {
        return userInterface.createHttpResponseEditor(options);
    }

    public Font currentEditorFont() {
        return userInterface.currentEditorFont();
    }
}
