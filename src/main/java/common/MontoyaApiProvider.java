package common;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.repeater.Repeater;
import burp.api.montoya.scanner.ScanCheck;
import burp.api.montoya.scanner.Scanner;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;

public enum MontoyaApiProvider {
    INSTANCE;

    @Getter
    private MontoyaApi montoyaApi;

    private Scanner scanner;

    private Repeater repeater;

    public static void constructInstance(MontoyaApi montoyaApi) {
        MontoyaApiProvider.INSTANCE.montoyaApi = montoyaApi;
        MontoyaApiProvider.INSTANCE.scanner = montoyaApi.scanner();
        MontoyaApiProvider.INSTANCE.repeater = montoyaApi.repeater();
    }

    public void registerScanCheck(ScanCheck scanCheck) {
        this.scanner.registerScanCheck(scanCheck);
    }

    public void sendToRepeater(HttpRequest request) {
        this.repeater.sendToRepeater(request);

    }

    public void sendToRepeater(HttpRequest request, String name) {
        this.repeater.sendToRepeater(request, name);
    }
}

