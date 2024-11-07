package scanner;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.scanner.AuditResult;
import burp.api.montoya.scanner.ConsolidationAction;
import burp.api.montoya.scanner.ScanCheck;
import burp.api.montoya.scanner.audit.insertionpoint.AuditInsertionPoint;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import checker.SSRFChecker;

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
