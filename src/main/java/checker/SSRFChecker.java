package checker;

import burp.api.montoya.collaborator.CollaboratorPayload;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import checker.filter.RequestResponseFilter;
import checker.updater.ParamsUpdater;
import cn.hutool.core.util.RandomUtil;
import common.logger.AutoSSRFLogger;
import common.provider.CollaboratorProvider;
import common.provider.UIProvider;
import lombok.Getter;
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
    @Getter
    private final RequestResponseFilter filter = new RequestResponseFilter();
    private final ParamsUpdater updater = new ParamsUpdater();

    public void check(HttpRequestResponse baseRequestResponse, Integer id) {

        HttpRequest request = baseRequestResponse.request();
        CollaboratorPayload payload = collaboratorProvider.generatePayload();

        // 过滤
        if (!filter.filter(baseRequestResponse, id)) {
            return;
        }

        // 参数填充&构建请求
        HttpRequest newRequest = updateParameterAndBuildRequest(
                request,
                filter.getParameters(),
                payload
        );
        if (newRequest == null) {
            return;
        }

        if (id == null) {
            // 防止 并发 生成重复ID
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

    private HttpRequest updateParameterAndBuildRequest(
            HttpRequest request,
            List<ParsedHttpParameter> parameters,
            CollaboratorPayload payload
    ) {
        // 更新参数
        List<HttpParameter> updateParameters = new ArrayList<>();
        for (HttpParameter parameter : parameters) {
            HttpParameter newParameter = HttpParameter.parameter(
                    parameter.name(),
                    "http://" + payload.toString() + "/" + RandomUtil.randomString(6),
                    parameter.type()
            );
            updateParameters.add(newParameter);
        }

        // 更新请求
        return updater.update(request, updateParameters);
    }
}
