package checker.filter;

import burp.api.montoya.http.message.HttpRequestResponse;

public class RequestResponseFilter {
    public void filter(HttpRequestResponse baseRequestResponse, Integer id) {
        // 1. 判断是否是重新检测 | 重新检测的任务不进行过滤，如果id不为空，代表是重新检测的任务
        if (id != null) {
            return;
        }

        // 2. 解析所有的参数

        // 3. 搜索key中有没有url, 搜索value中给有没有http特征，都没有直接结束

        // 4. 计算Hash

        // 5. 判断hash有没有在缓存中，在则结束

        // 6. 不在则过滤完成，进入下一阶段

    }
}
