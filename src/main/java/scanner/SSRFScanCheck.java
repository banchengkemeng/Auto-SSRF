package scanner;

import burp.api.montoya.collaborator.CollaboratorPayload;
import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.http.message.params.HttpParameter;
import burp.api.montoya.http.message.params.ParsedHttpParameter;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.scanner.AuditResult;
import burp.api.montoya.scanner.ConsolidationAction;
import burp.api.montoya.scanner.ScanCheck;
import burp.api.montoya.scanner.audit.insertionpoint.AuditInsertionPoint;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import cn.hutool.core.util.RandomUtil;
import common.CollaboratorProvider;
import common.MontoyaApiProvider;
import logger.AutoSSRFLogger;
import common.HttpProvider;
import java.util.ArrayList;
import java.util.List;

public class SSRFScanCheck implements ScanCheck {

    private final AutoSSRFLogger logger = AutoSSRFLogger.INSTANCE;
    private final MontoyaApiProvider montoyaApiProvider = MontoyaApiProvider.INSTANCE;
    private final HttpProvider httpProvider = HttpProvider.INSTANCE;
    private final CollaboratorProvider collaboratorProvider = CollaboratorProvider.INSTANCE;

    @Override
    public AuditResult activeAudit(HttpRequestResponse baseRequestResponse, AuditInsertionPoint auditInsertionPoint) {
        return () -> null;
    }

    @Override
    public AuditResult passiveAudit(HttpRequestResponse baseRequestResponse) {
        HttpRequest request = baseRequestResponse.request();
        // 防止重复扫描

        CollaboratorPayload payload = collaboratorProvider.generatePayload();

        // 解析参数, 是否存在URL参数
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
            return () -> null;
        }

        // 更新请求
        // TODO 只能处理URL BODY COOKIE, 其他情况需要自己处理
        HttpRequest newRequest = request.withUpdatedParameters(updateParameters);

        // 构造HTTP请求，测试功能点是否发出请求
        collaboratorProvider.sendReqAndWaitCollaboratorResult(
                payload,
                newRequest
        ).whenComplete((success, err) -> {
            if (err != null) {
                logger.logToError(err);
                return;
            }

            // 一定不存在SSRF结束
            if (!success) {
                return;
            }

            logger.logToOutput("可能存在SSRF: " + request.url());
        });

        return () -> null;
    }

    @Override
    public ConsolidationAction consolidateIssues(AuditIssue newIssue, AuditIssue existingIssue) {
        return null;
    }

    private boolean checkParameter(ParsedHttpParameter parameter) {
        String name = parameter.name().toLowerCase();
        String value = parameter.value().toLowerCase();
        return name.contains("url") || value.contains("http") || value.contains("https");
    }
}
