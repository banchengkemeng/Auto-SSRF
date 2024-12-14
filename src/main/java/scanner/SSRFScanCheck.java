package scanner;

import burp.api.montoya.http.message.HttpRequestResponse;
import burp.api.montoya.scanner.AuditResult;
import burp.api.montoya.scanner.ConsolidationAction;
import burp.api.montoya.scanner.ScanCheck;
import burp.api.montoya.scanner.audit.insertionpoint.AuditInsertionPoint;
import burp.api.montoya.scanner.audit.issues.AuditIssue;
import checker.SSRFChecker;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;

public class SSRFScanCheck implements ScanCheck {
    @Getter
    @Setter
    private static boolean enabled = true;
    private final SSRFChecker ssrfChecker = SSRFChecker.INSTANCE;

    @Override
    public AuditResult activeAudit(HttpRequestResponse baseRequestResponse, AuditInsertionPoint auditInsertionPoint) {
        return Collections::emptyList;
    }

    @Override
    public AuditResult passiveAudit(HttpRequestResponse baseRequestResponse) {
        if (enabled) {
            ssrfChecker.check(baseRequestResponse, null);
        }
        return Collections::emptyList;
    }

    @Override
    public ConsolidationAction consolidateIssues(AuditIssue newIssue, AuditIssue existingIssue) {
        return null;
    }
}
