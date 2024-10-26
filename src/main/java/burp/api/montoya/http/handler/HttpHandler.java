/*
 * Copyright (c) 2022-2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package burp.api.montoya.http.handler;

import burp.api.montoya.http.Http;

/**
 * 插件可以实现这个接口然后调用 {@link Http#registerHttpHandler} 去注册一个HTTP处理器. Burp的任何模块收到或发出HTTP请求和响应时都会执行
 * 此处理器中的方法
 */
public interface HttpHandler
{
    /**
     * 即将发送HTTP请求时调用
     *
     * @param requestToBeSent 将要被发送的HTTP请求信息
     *
     * @return {@link RequestToBeSentAction} 对象实例
     */
    RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent);

    /**
     * 当收到HTTP响应时调用
     *
     * @param responseReceived 收到的HTTP请求信息
     *
     * @return {@link ResponseReceivedAction} 对象实例
     */
    ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived);
}
