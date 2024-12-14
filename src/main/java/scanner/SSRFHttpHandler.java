package scanner;

import burp.api.montoya.core.ToolSource;
import burp.api.montoya.core.ToolType;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpRequestResponse;
import checker.SSRFChecker;
import lombok.Getter;
import lombok.Setter;

public class SSRFHttpHandler implements HttpHandler {
    private final SSRFChecker ssrfChecker = SSRFChecker.INSTANCE;

    @Getter
    @Setter
    private static boolean proxyEnabled = false;

    @Getter
    @Setter
    private static boolean repeaterEnabled = false;

    @Override
    public RequestToBeSentAction handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent) {
        return RequestToBeSentAction.continueWith(requestToBeSent);
    }

    @Override
    public ResponseReceivedAction handleHttpResponseReceived(HttpResponseReceived responseReceived) {
        ToolSource toolSource = responseReceived.toolSource();

        HttpRequestResponse httpRequestResponse = HttpRequestResponse.httpRequestResponse(
                responseReceived.initiatingRequest(),
                responseReceived
        );

        if ((toolSource.isFromTool(ToolType.PROXY) && proxyEnabled) ||
                (toolSource.isFromTool(ToolType.REPEATER) && repeaterEnabled)) {
            ssrfChecker.check(httpRequestResponse, null);
        }

        return ResponseReceivedAction.continueWith(responseReceived);
    }
}
