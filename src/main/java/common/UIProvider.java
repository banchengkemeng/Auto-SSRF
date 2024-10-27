package common;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.Registration;
import burp.api.montoya.ui.UserInterface;
import burp.api.montoya.ui.editor.EditorOptions;

import java.awt.*;

public enum UIProvider {
    INSTANCE;

    private UserInterface userInterface;

    public static void constructUIProvider(MontoyaApi api) {
        UIProvider.INSTANCE.userInterface = api.userInterface();
    }

    public Registration registerSuiteTab(String title, Component component) {
        return userInterface.registerSuiteTab(title, component);
    }

    public Component createHttpRequestEditor() {
        return userInterface
                .createHttpRequestEditor(EditorOptions.READ_ONLY)
                .uiComponent();
    }

}
