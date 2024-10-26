/*
 * Copyright (c) 2022-2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package burp.api.montoya.repeater;

import burp.api.montoya.http.message.requests.HttpRequest;

/**
 * 提供Repeater的功能
 */
public interface Repeater
{
    /**
     * 此方法可用于向Burp Repeater工具发送HTTP请求。
     * 该请求将使用默认选项卡索引显示在用户界面中，但在用户启动此操作之前不会发送。
     *
     * @param request Http请求
     */
    void sendToRepeater(HttpRequest request);

    /**
     * 此方法可用于向Burp Repeater工具发送HTTP请求。
     * 可设置选项卡名
     *
     * @param request Http请求
     * @param name    选项卡名称
     */
    void sendToRepeater(HttpRequest request, String name);
}
