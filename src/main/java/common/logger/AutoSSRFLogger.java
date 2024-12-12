package common.logger;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;

public enum AutoSSRFLogger {
    INSTANCE;

    private Logging logging;

    public static void constructAutoSSRFLogger(MontoyaApi api) {
        AutoSSRFLogger.INSTANCE.logging = api.logging();
    }

    public void logToOutput(String message) {
        logging.logToOutput(message);
    }

    public void logToError(Throwable err) {
        logging.logToError(err);
    }
}
