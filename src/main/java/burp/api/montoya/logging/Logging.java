/*
 * Copyright (c) 2022-2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package burp.api.montoya.logging;

import java.io.PrintStream;

/**
 * 提供logging和events相关的功能的功能.
 */
public interface Logging
{
    /**
     * Obtain the current extension's standard output
     * stream. Extensions should write all output to this stream, allowing the
     * Burp user to configure how that output is handled from within the UI.
     *
     * @return The extension's standard output stream.
     *
     * @deprecated Use {@link burp.api.montoya.logging.Logging#logToOutput} instead.
     */
    @Deprecated
    PrintStream output();

    /**
     * Obtain the current extension's standard error
     * stream. Extensions should write all error messages to this stream,
     * allowing the Burp user to configure how that output is handled from
     * within the UI.
     *
     * @return The extension's standard error stream.
     *
     * @deprecated Use {@link burp.api.montoya.logging.Logging#logToError} instead.
     */
    @Deprecated
    PrintStream error();

    /**
     * 此方法将一行输出打印到当前插件的标准输出流。
     *
     * @param message 打印的信息
     */
    void logToOutput(String message);

    /**
     * 此方法打印一行输出到当前插件的标准错误流
     *
     * @param message 打印的信息。
     */
    void logToError(String message);

    /**
     * 此方法将信息和堆栈跟踪打印到当前插件的标准错误流。
     *
     * @param message 打印的信息。
     * @param cause 抛出的异常
     */
    void logToError(String message, Throwable cause);

    /**
     * 此方法将堆栈跟踪打印到当前插件的标准错误流。
     *
     * @param cause 抛出的异常
     */
    void logToError(Throwable cause);

    /**
     * 此方法可用于在Burp Suite事件日志中显示调试事件。
     *
     * @param message 显示的调试事件信息。
     */
    void raiseDebugEvent(String message);

    /**
     * 此方法可用于在Burp Suite事件日志中显示信息性事件。
     *
     * @param message 显示的信息性事件信息。
     */
    void raiseInfoEvent(String message);

    /**
     * 此方法可用于在Burp Suite事件日志中显示错误事件。
     *
     * @param message 显示的错误事件信息。
     */
    void raiseErrorEvent(String message);

     /**
     * 此方法可用于在Burp Suite事件日志中显示严重事件。
     *
     * @param message 显示的严重事件信息。
     */
    void raiseCriticalEvent(String message);
}
