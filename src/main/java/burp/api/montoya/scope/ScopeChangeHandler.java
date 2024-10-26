/*
 * Copyright (c) 2022-2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package burp.api.montoya.scope;

/**
 * 插件可以实现此接口然后调用 {@link Scope#registerScopeChangeHandler(ScopeChangeHandler)}
 * 去注册一个处理器。这个处理器在scope的范围被改变时执行
 */
public interface ScopeChangeHandler
{
    /**
     * 当scope的范围被改变时执行此方法
     *
     * @param scopeChange 表示对scope范围进行更改的对象，。
     */
    void scopeChanged(ScopeChange scopeChange);
}
