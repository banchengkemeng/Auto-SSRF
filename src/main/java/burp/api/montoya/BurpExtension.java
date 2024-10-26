/*
 * Copyright (c) 2022-2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package burp.api.montoya;

/**
 * 所有扩展都必须实现此接口。
 * <p>
 * 实现必须声明为公共的，并且必须提供默认的（公共无参）构造函数。
 */
public interface BurpExtension
{
    /**
     * 加载扩展时调用。只有在完成此方法后，才会启用任何已注册的处理程序。
     *
     * @param api 访问Burp Suite功能的API实现。
     */
    void initialize(MontoyaApi api);
}