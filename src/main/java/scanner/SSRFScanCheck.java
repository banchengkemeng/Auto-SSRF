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
import checker.SSRFChecker;
import cn.hutool.core.util.RandomUtil;
import common.CollaboratorProvider;
import common.MontoyaApiProvider;
import common.UIProvider;
import logger.AutoSSRFLogger;
import common.HttpProvider;
import ui.dashboard.DashboardTab;
import ui.dashboard.DashboardTable;
import ui.dashboard.DashboardTableData;
import ui.dashboard.StatusEnum;

import java.util.ArrayList;
import java.util.List;

public class SSRFScanCheck implements ScanCheck {
    private final SSRFChecker ssrfChecker = SSRFChecker.INSTANCE;

    @Override
    public AuditResult activeAudit(HttpRequestResponse baseRequestResponse, AuditInsertionPoint auditInsertionPoint) {
        return () -> null;
    }

    @Override
    public AuditResult passiveAudit(HttpRequestResponse baseRequestResponse) {
        ssrfChecker.check(baseRequestResponse, null);
        return () -> null;
    }

    @Override
    public ConsolidationAction consolidateIssues(AuditIssue newIssue, AuditIssue existingIssue) {
        return null;
    }
}
