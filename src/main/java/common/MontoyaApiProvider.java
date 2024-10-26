package common;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.logging.Logging;
import lombok.Getter;

public enum MontoyaApiProvider {
    INSTANCE;

    @Getter
    private MontoyaApi montoyaApi;

    private Logging logging;

    public static void constructInstance(MontoyaApi montoyaApi) {
        MontoyaApiProvider.INSTANCE.montoyaApi = montoyaApi;
        MontoyaApiProvider.INSTANCE.logging = montoyaApi.logging();
    }

    public void logToOutput(String message) {
        logging.logToOutput(message);
    }
}

