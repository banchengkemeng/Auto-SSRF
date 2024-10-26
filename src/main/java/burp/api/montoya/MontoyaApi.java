/*
 * Copyright (c) 2022-2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package burp.api.montoya;

import burp.api.montoya.burpsuite.BurpSuite;
import burp.api.montoya.collaborator.Collaborator;
import burp.api.montoya.comparer.Comparer;
import burp.api.montoya.decoder.Decoder;
import burp.api.montoya.extension.Extension;
import burp.api.montoya.http.Http;
import burp.api.montoya.intruder.Intruder;
import burp.api.montoya.logging.Logging;
import burp.api.montoya.persistence.Persistence;
import burp.api.montoya.proxy.Proxy;
import burp.api.montoya.repeater.Repeater;
import burp.api.montoya.scanner.Scanner;
import burp.api.montoya.scope.Scope;
import burp.api.montoya.sitemap.SiteMap;
import burp.api.montoya.ui.UserInterface;
import burp.api.montoya.utilities.Utilities;
import burp.api.montoya.websocket.WebSockets;

 /**
  * Burp Suite使用此接口将一系列方法传递给插件。
  * 当插件加载时, Burp调用它的{@link BurpExtension#initialize(MontoyaApi)} 方法并传一个 {@link MontoyaApi} 接口的实例。
  * 然后插件通过调用这些方法去使用Burp的功能
  */
public interface MontoyaApi
{
    /**
     * 访问与Burp Suite应用程序相关的功能。
     *
     * @return BurpSuite接口的实现。提供了应用程序级别的功能
     */
    BurpSuite burpSuite();

    /**
     * 访问Collaborator的功能。
     *
     * @return Collaborator接口的实现，提供了Collaborator的功能。
     */
    Collaborator collaborator();

    /**
     * 访问Comparer的功能。
     *
     * @return Comparer接口的实现，提供了Comparer的功能。
     */
    Comparer comparer();

    /**
     * 访问Decoder的功能。
     *
     * @return Decoder接口的实现，提供了Decoder的功能。
     */
    Decoder decoder();

    /**
     * 访问Extension的功能。
     *
     * @return Extension接口的实现，提供了Extension的功能。
     */
    Extension extension();

    /**
     * 访问与Http请求和响应相关的功能
     *
     * @return Http接口的实现，提供了Http的功能。
     */
    Http http();

    /**
     * 访问Intruder的功能。
     *
     * @return Intruder接口的实现，提供了Intruder的功能。
     */
    Intruder intruder();

    /**
     * 访问logging和events相关的功能。
     *
     * @return Logging接口的实现，提供了Logging的功能。
     */
    Logging logging();

    /**
     * 访问Persistence的功能。
     *
     * @return Persistence接口的实现，提供了Persistence的功能。
     */
    Persistence persistence();

    /**
     * 访问Proxy的功能。
     *
     * @return Proxy接口的实现，提供了Proxy的功能。
     */
    Proxy proxy();

    /**
     * 访问Repeater的功能。
     *
     * @return Repeater接口的实现，提供了Repeater的功能。
     */
    Repeater repeater();

    /**
     * 访问Scanner的功能。
     *
     * @return Scanner接口的实现，提供了Scanner的功能。
     */
    Scanner scanner();

    /**
     * 访问与Burp target scope相关的功能。
     *
     * @return Scope接口的实现，提供了Scope的功能。
     */
    Scope scope();

    /**
     * 访问SiteMap的功能。
     *
     * @return SiteMap接口的实现，提供了SiteMap的功能。
     */
    SiteMap siteMap();

    /**
     * 访问与UserInterface相关的功能
     *
     * @return UserInterface接口的实现，提供了UserInterface的功能。
     */
    UserInterface userInterface();

    /**
     * 访问Utilities的功能。
     *
     * @return Utilities接口的实现，提供了Utilities的功能。
     */
    Utilities utilities();

    /**
     *
     * 访问与WebSockets和messages相关的功能。
     *
     * @return WebSockets接口的实现，提供了WebSockets的功能。
     */
    WebSockets websockets();
}
