/*
 * Copyright (c) 2022-2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package burp.api.montoya.ui;

import burp.api.montoya.core.Registration;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;
import burp.api.montoya.ui.editor.EditorOptions;
import burp.api.montoya.ui.editor.HttpRequestEditor;
import burp.api.montoya.ui.editor.HttpResponseEditor;
import burp.api.montoya.ui.editor.RawEditor;
import burp.api.montoya.ui.editor.WebSocketMessageEditor;
import burp.api.montoya.ui.editor.extension.HttpRequestEditorProvider;
import burp.api.montoya.ui.editor.extension.HttpResponseEditorProvider;
import burp.api.montoya.ui.editor.extension.WebSocketMessageEditorProvider;
import burp.api.montoya.ui.menu.MenuBar;
import burp.api.montoya.ui.swing.SwingUtils;

import java.awt.Component;
import java.awt.Font;

/**
 * 通过此界面，您可以访问各种与用户界面相关的功能。
 * 例如注册您自己的用户界面提供程序，创建Burps各种编辑器的实例，以及将主题应用于自定义组件。
 */
public interface UserInterface
{
    /**
     * @return The Burp Suite {@link MenuBar}.
     */
    MenuBar menuBar();

    /**
     * 添加一个自定义的Tab到Burp Suite的主页面
     *
     * @param title     Tab 的标题
     * @param component 自定义组件实例
     *
     * @return 自定义Tab的 {@link Registration}
     */
    Registration registerSuiteTab(String title, Component component);

    /**
     * This method can be used to register a provider of custom context menu items.
     *
     * @param provider The provider to register.
     *
     * @return A {@link Registration} of the context menu item provider.
     */
    Registration registerContextMenuItemsProvider(ContextMenuItemsProvider provider);

    /**
     * This method can be used to register a provider of custom HTTP request editors.
     *
     * @param provider The provider to register.
     *
     * @return A {@link Registration} of the HTTP request editor provider.
     */
    Registration registerHttpRequestEditorProvider(HttpRequestEditorProvider provider);

    /**
     * This method can be used to register a provider of custom HTTP response editors.
     *
     * @param provider The provider to register.
     *
     * @return A {@link Registration} of the HTTP response editor provider.
     */
    Registration registerHttpResponseEditorProvider(HttpResponseEditorProvider provider);

    /**
     * This method can be used to register a provider of custom Web Socket message editors.
     *
     * @param provider The provider to register.
     *
     * @return A {@link Registration} of the Web Socket message editor provider.
     */
    Registration registerWebSocketMessageEditorProvider(WebSocketMessageEditorProvider provider);

    /**
     * Create a new instance of Burp's plain text editor, for the extension to use in its own UI.
     *
     * @param options Optional options to apply to the editor.
     *
     * @return An instance of the {@link RawEditor} interface.
     */
    RawEditor createRawEditor(EditorOptions... options);

    /**
     * Create a new instance of Burp's WebSocket message editor, for the extension to use in its own UI.
     *
     * @param options Optional options to apply to the editor.
     *
     * @return An instance of the {@link WebSocketMessageEditor} interface.
     */
    WebSocketMessageEditor createWebSocketMessageEditor(EditorOptions... options);

    /**
     * Create a new instance of Burp's HTTP request editor, for the extension to use in its own UI.
     *
     * @param options Optional options to apply to the editor.
     *
     * @return An instance of the {@link HttpRequestEditor} interface.
     */
    HttpRequestEditor createHttpRequestEditor(EditorOptions... options);

    /**
     * Create a new instance of Burp's HTTP response editor, for the extension to use in its own UI.
     *
     * @param options Optional options to apply to the editor.
     *
     * @return An instance of the {@link HttpResponseEditor} interface.
     */
    HttpResponseEditor createHttpResponseEditor(EditorOptions... options);

    /**
     * Customize UI components in line with Burp's UI style, including font size, colors, table line spacing, etc.
     * The action is performed recursively on any child components of the passed-in component.
     *
     * @param component The component to be customized.
     */
    void applyThemeToComponent(Component component);

    /**
     * Identify the theme currently being used.
     *
     * @return The current {@link Theme}
     */
    Theme currentTheme();

    /**
     * Access the message editor's font type and size.
     *
     * @return The current {@link java.awt.Font}, as specified in the <strong>Settings</strong> dialog under the <strong>HTTP message display</strong> setting.
     */
    Font currentEditorFont();

    /**
     * Access Burp's font size. 
     *
     * @return The current {@link java.awt.Font}, as specified in the <strong>Settings</strong> dialog under the <strong>Appearance</strong> setting.
     */
    Font currentDisplayFont();

    /**
     * @return An instance of {@link SwingUtils}
     */
    SwingUtils swingUtils();
}
