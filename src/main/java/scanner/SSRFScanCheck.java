package scanner;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.scanner.AuditResult;
import burp.api.montoya.scanner.ConsolidationAction;
import burp.api.montoya.scanner.ScanCheck;
import burp.api.montoya.scanner.audit.insertionpoint.AuditInsertionPoint;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import checker.SSRFChecker;
import common.logger.AutoSSRFLogger;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;

public class SSRFScanCheck implements ScanCheck {
    @Getter
    @Setter
    private static boolean enabled = true;
    private final SSRFChecker ssrfChecker = SSRFChecker.INSTANCE;
    private final AutoSSRFLogger logger = AutoSSRFLogger.INSTANCE;

    @Override
    public AuditResult activeAudit(HttpRequestResponse baseRequestResponse, AuditInsertionPoint auditInsertionPoint) {
        return Collections::emptyList;
    }

    @Override
    public AuditResult passiveAudit(HttpRequestResponse baseRequestResponse) {
        if (enabled) {
            try {
                ssrfChecker.check(baseRequestResponse, null);
            } catch (RuntimeException e) {
                logger.logToError(e);
            }
        }
        return Collections::emptyList;
    }

    @Override
    public ConsolidationAction consolidateIssues(AuditIssue newIssue, AuditIssue existingIssue) {
        return null;
    }
}
