package common;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.collaborator.*;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import pool.CollaboratorThreadPool;
import pool.SendReqThreadPool;

import java.util.List;
import java.util.concurrent.*;

public enum CollaboratorProvider {
    INSTANCE;

    private Collaborator collaborator;

    private CollaboratorClient client;

    private final ThreadPoolExecutor sendReqPool = SendReqThreadPool.INSTANCE.getPool();
    private final ThreadPoolExecutor collaboratorReqPool = CollaboratorThreadPool.INSTANCE.getPool();
    private final HttpProvider httpProvider = HttpProvider.INSTANCE;

    // 几秒轮询一次
    private Integer delaySeconds = 1;
    // 请求已完成后最大轮询次数
    private Integer receivedResponseMaxTryCount = 30;
    // 总最大轮询次数
    private Integer totalMaxTryCount = 5 * 60;
    // 标识请求是否已完成
    private final ConcurrentHashMap<String, Boolean> reqFinishFlagMap = new ConcurrentHashMap<>();

    public static void constructCollaboratorProvider(MontoyaApi api) {
        Collaborator collaboratorInstance = api.collaborator();
        CollaboratorProvider.INSTANCE.collaborator = collaboratorInstance;
        CollaboratorProvider.INSTANCE.client = collaboratorInstance.createClient();
    }

    public void recreateClient() {
        this.client = collaborator.createClient();
    }

    public CollaboratorPayload generatePayload() {
        return client.generatePayload();
    }

    public CompletableFuture<Boolean> sendReqAndWaitCollaboratorResult(
            CollaboratorPayload payload,
            HttpRequest httpRequest
    ) {
        String id = payload.id().toString();

        // 发送请求
        CompletableFuture.runAsync(() -> {
            httpProvider.sendRequest(httpRequest);
            reqFinishFlagMap.put(id, true);
        }, sendReqPool);

        // 轮询
        return CompletableFuture.supplyAsync(() -> {
            int tryCount = 0;
            while (true) {
                // 询问
                List<Interaction> interactions = client.getInteractions(
                        InteractionFilter.interactionPayloadFilter(payload.toString())
                );
                for (Interaction interaction : interactions) {
                    InteractionType type = interaction.type();
                    // 如果存在HTTP记录, 直接结束
                    if (type == InteractionType.HTTP) {
                        return true;
                    }
                }
                // 等待
                try {
                    TimeUnit.MILLISECONDS.sleep(delaySeconds * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                tryCount ++;
                if (tryCount >= totalMaxTryCount) {
                    break;
                }
                Boolean isReqFinished = reqFinishFlagMap.get(id);
                if (isReqFinished != null && isReqFinished && tryCount >= receivedResponseMaxTryCount) {
                    break;
                }

            }

            return false;
        }, collaboratorReqPool);
    }

}
