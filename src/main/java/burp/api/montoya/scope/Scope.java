/*
 * Copyright (c) 2022-2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package burp.api.montoya.scope;

import burp.api.montoya.core.Registration;

/**
 * 提供target scope相关的功能
 */
public interface Scope
{
    /**
     * 查询url是否在scope范围内。
     *
     * @param url 要查询的url
     *
     * @return 返回url是否在scope范围内。
     */
    boolean isInScope(String url);

    /**
     * 将指定的url加入scope
     *
     * @param url 要加入scope的url
     */
    void includeInScope(String url);

    /**
     * 将指定的url从scope中排除
     *
     * @param url 要排除的url
     */
    void excludeFromScope(String url);

    /**
     * 注册一个处理器, 当scope范围更改时执行
     *
     * @param handler 实现{@link ScopeChangeHandler} 接口的对象.
     *
     * @return 这个处理器的 {@link Registration}。
     */
    Registration registerScopeChangeHandler(ScopeChangeHandler handler);
}
