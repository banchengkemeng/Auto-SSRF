/*
 * Copyright (c) 2022-2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package burp.api.montoya.burpsuite;

/**
 * 调用时可以使用的关闭选项 {@link BurpSuite#shutdown(ShutdownOptions...)}.
 */
public enum ShutdownOptions
{
    /**
     * 向用户显示一个对话框，询问用户确认或取消。
     */
    PROMPT_USER
}
