/*
 * Copyright (c) 2022-2023. PortSwigger Ltd. All rights reserved.
 *
 * This code may be used to extend the functionality of Burp Suite Community Edition
 * and Burp Suite Professional, provided that this usage does not violate the
 * license terms for those products.
 */

package burp.api.montoya.burpsuite;

/**
 * 对任务执行引擎的访问。
 */
public interface TaskExecutionEngine
{
    /**
     * 任务执行引擎状态
     */
    enum TaskExecutionEngineState
    {
        RUNNING, PAUSED
    }

    /**
     * 检索任务执行引擎的当前状态。
     *
     * @return current 状态
     */
    TaskExecutionEngineState getState();

    /**
     * 设置任务执行引擎的当前状态。
     *
     * @param state 新状态
     */
    void setState(TaskExecutionEngineState state);
}
