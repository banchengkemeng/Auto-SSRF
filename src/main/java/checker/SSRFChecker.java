package checker;

import burp.api.montoya.collaborator.CollaboratorPayload;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import cn.hutool.core.util.RandomUtil;
import common.CollaboratorProvider;
import common.UIProvider;
import logger.AutoSSRFLogger;
import ui.dashboard.DashboardTable;
import ui.dashboard.DashboardTableData;
import ui.dashboard.StatusEnum;
import ui.vuln.VulnTable;
import ui.vuln.VulnTableData;

import java.util.ArrayList;
import java.util.List;

public enum SSRFChecker {
    INSTANCE;

    private final AutoSSRFLogger logger = AutoSSRFLogger.INSTANCE;
    private final CollaboratorProvider collaboratorProvider = CollaboratorProvider.INSTANCE;
    private final DashboardTable dashboardTable = UIProvider.INSTANCE.getUiMain().getDashboardTab().getTable();
    private final VulnTable vulnTable = UIProvider.INSTANCE.getUiMain().getVulnTab().getTable();

    public void check(HttpRequestResponse baseRequestResponse, Integer id) {

        HttpRequest request = baseRequestResponse.request();
        CollaboratorPayload payload = collaboratorProvider.generatePayload();

        if (id == null) {
            // 防止重复扫描
        }

        // 解析参数, 是否存在URL参数以及替换 url -> dnslog
        HttpRequest newRequest = parseURLParameterAndReplaceToPayload(request, payload);
        if (newRequest == null) {
            return;
        }

        if (id == null) {
            // 防止并发生成重复ID
            id = dashboardTable.generateId();
            dashboardTable.addRow(DashboardTableData.buildDashboardTableData(
                    id,
                    baseRequestResponse
            ));
        } else {
            dashboardTable.updateStatus(id, StatusEnum.CHECKING);
        }

        // 构造HTTP请求，测试功能点是否发出请求
        collaboratorProvider.sendReqAndWaitCollaboratorResult(
                id,
                payload,
                newRequest
        ).whenComplete((result, err) -> {
            if (err != null) {
                dashboardTable.updateStatus(result.getId(), StatusEnum.ERROR);
                logger.logToError(err);
                return;
            }

            // 一定不存在SSRF结束
            if (!result.getSuccess()) {
                dashboardTable.updateStatus(result.getId(), StatusEnum.FAILED);
                return;
            }

            dashboardTable.updateStatus(result.getId(), StatusEnum.SUCCESS);
            vulnTable.addRow(
                    VulnTableData.buildVulnTableData(
                            vulnTable.generateId(),
                            result.getHttpRequestResponse(),
                            result.getInteractions()
                    )
            );

            logger.logToOutput("可能存在SSRF: " + request.url());
        });
    }

    private boolean checkParameter(ParsedHttpParameter parameter) {
        String name = parameter.name().toLowerCase();
        String value = parameter.value().toLowerCase();
        return name.contains("url") || value.contains("http") || value.contains("https");
    }

    private HttpRequest parseURLParameterAndReplaceToPayload(
            HttpRequest request,
            CollaboratorPayload payload
    ) {
        List<ParsedHttpParameter> parameters = request.parameters();
        ArrayList<HttpParameter> updateParameters = new ArrayList<>();
        for (ParsedHttpParameter parameter : parameters) {
            if (checkParameter(parameter)) {
                // 替换成collaborator
                HttpParameter newParameter = HttpParameter.parameter(
                        parameter.name(),
                        "http://" + payload.toString() + "/" + RandomUtil.randomString(6),
                        parameter.type()
                );
                updateParameters.add(newParameter);
            }
        }

        // 无url相关参数,直接停止
        if (updateParameters.isEmpty()) {
            return null;
        }

        // 更新请求
        // TODO 只能处理URL BODY COOKIE, 其他情况需要自己处理
        return request.withUpdatedParameters(updateParameters);
    }
}
