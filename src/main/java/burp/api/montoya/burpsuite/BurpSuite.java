/*
 * Copyright (c) 2022-2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package burp.api.montoya.burpsuite;

import burp.api.montoya.core.Version;

import java.util.List;

/**
 * 提供对与Burp Suite应用程序相关的功能的访问.
 */
public interface BurpSuite
{
    /**
     * 检索运行插件的Burp版本信息。
     *
     * @return The Burp {@link Version}.
     */
    Version version();

    /**
     * 以JSON格式导出当前项目级配置。这和通过Burp用户界面保存和加载的格式相同。
     * 如果要只保存配置的某些部分，您可以选择提供要保存的配置的路径。
     * 例如：“project_options.connections”。
     * 如果没有提供路径，则将保存整个配置。
     *
     * @param paths 字符串列表。表示要保存的每个配置的路径。
     *
     * @return 以JSON格式表示当前配置的字符串。
     */
    String exportProjectOptionsAsJson(String... paths);

    /**
     * 从提供的JSON字符串中导入新的项目级配置。
     * 这和通过Burp用户界面保存和加载的格式相同。
     * 可以指定部分配置，任何未指定的配置都将保持不变。
     * <p>
     * 输入中包含的任何用户级配置选项都将被忽略。
     *
     * @param json 包含新配置的JSON字符串。
     */
    void importProjectOptionsFromJson(String json);

     /**
      * 以JSON格式导出当前用户级配置。
      * 这和通过Burp用户界面保存和加载的格式相同。
      * 如果要只包括部分配置，您可以提供要保存的配置的路劲。
      * 例如：“user_options.connections”。
      * 如果没有提供路径，则将保存整个配置。
      *
      * @param paths 字符串列表，表示应包含的配置的路径。
      *
      * @return 以JSON格式表示当前配置的字符串。
      */
    String exportUserOptionsAsJson(String... paths);

     /**
      * 从提供的JSON字符串中导入新的用户级配置。
      * 这和通过Burp用户界面保存和加载的格式相同。部分配置是可以接受的，任何未指定的设置都将保持不变。
      * <p>
      * 输入中包含的任何项目级配置选项都将被忽略
      *
      * @param json 包含新配置的JSON字符串。
      */
    void importUserOptionsFromJson(String json);

    /**
     * 启动时传递给Burp的命令行参数。
     *
     * @return 启动时传递给Burp的命令行参数。
     */
    List<String> commandLineArguments();

    /**
     * 以编程方式关闭Burp。
     *
     * @param options 以编程方式关闭Burp时的关闭选项。 例如 {@link ShutdownOptions#PROMPT_USER}
     *                将向用户显示一个对话框，询问用户确认或取消。
     */
    void shutdown(ShutdownOptions... options);

    /**
     * 访问任务执行引擎的功能。
     *
     * @return TaskExecutionEngine接口的实现，提供了任务执行引擎的功能。
     */
    TaskExecutionEngine taskExecutionEngine();
}
