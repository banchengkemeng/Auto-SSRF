package common;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.collaborator.*;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import pool.CollaboratorThreadPool;
import ui.dashboard.StatusEnum;

import java.util.List;
import java.util.concurrent.*;

public enum CollaboratorProvider {
    INSTANCE;

    private Collaborator collaborator;

    private CollaboratorClient client;

    private final ThreadPoolExecutor collaboratorReqPool = CollaboratorThreadPool.INSTANCE.getPool();
    private final HttpProvider httpProvider = HttpProvider.INSTANCE;

    // 每隔几次轮询会开始延时
    private Integer tryCountPer = 5;
    // 隔几秒进入下一轮轮询
    private Integer delaySeconds = 1;
    // 最大轮询次数
    private Integer maxTryCount = 10;

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

    public CompletableFuture<CollaboratorResult> sendReqAndWaitCollaboratorResult(
            Integer tableId,
            CollaboratorPayload payload,
            HttpRequest httpRequest
    ) {
        UIProvider.INSTANCE.getUiMain().getDashboardTab().getTable()
                .updateStatus(tableId, StatusEnum.CHECKING);

        CollaboratorResult collaboratorResult = new CollaboratorResult();
        collaboratorResult.setSuccess(false);
        collaboratorResult.setId(tableId);

        return CompletableFuture.supplyAsync(() -> {
            // 发请求
            HttpRequestResponse httpRequestResponse = httpProvider.sendRequest(httpRequest);
            HttpResponse response = httpRequestResponse.response();
            if (response == null) {
                throw new RuntimeException("请求发送失败");
            }
            collaboratorResult.setHttpRequestResponse(httpRequestResponse);

            int tryCount = 0;
            // 轮询
            while (true) {
                // 询问
                List<Interaction> interactions = client.getInteractions(
                        InteractionFilter.interactionPayloadFilter(payload.toString())
                );
                for (Interaction interaction : interactions) {
                    InteractionType type = interaction.type();
                    // 如果存在HTTP记录, 直接结束
                    if (type == InteractionType.HTTP) {
                        collaboratorResult.setSuccess(true);
                        collaboratorResult.setInteractions(interactions);
                        return collaboratorResult;
                    }
                }
                // 等待
                try {
                    if (tryCount % tryCountPer == 0) {
                        TimeUnit.MILLISECONDS.sleep(delaySeconds * 1000);
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                tryCount++;

                // 尝试次数达到上限
                if (tryCount >= maxTryCount) {
                    break;
                }
            }
            return collaboratorResult;
        }, collaboratorReqPool);
    }
}
