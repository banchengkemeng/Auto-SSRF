import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.extension.Extension;
import common.CollaboratorProvider;
import common.HttpProvider;
import common.MontoyaApiProvider;
import common.UIProvider;
import logger.AutoSSRFLogger;
import pool.CollaboratorThreadPool;
import pool.SendReqThreadPool;
import scanner.SSRFScanCheck;
import ui.UIMain;

import java.awt.*;

public class AutoSSRFBurpExtension implements BurpExtension {

    private final MontoyaApiProvider montoyaApiProvider = MontoyaApiProvider.INSTANCE;

    private AutoSSRFLogger logger;
    private final UIProvider uiProvider = UIProvider.INSTANCE;

    @Override
    public void initialize(MontoyaApi api) {
        // 将montoyaApiProvider
        initMontoyaApiProvider(api);

        // 加载一些工具
        initTools(api);

        // StartBanner
        initStartBanner();

        // 加载程序
        loading();

        // EndBanner
        initEndBanner();

        // 插件被卸载时执行
        unload();
    }

    private void initMontoyaApiProvider(MontoyaApi montoyaApi) {
        MontoyaApiProvider.constructInstance(montoyaApi);
    }

    private void initTools(MontoyaApi api) {
        // Logger
        AutoSSRFLogger.constructAutoSSRFLogger(api);
        logger = AutoSSRFLogger.INSTANCE;

        // HttpProvider
        HttpProvider.constructHttpProvider(api);

        // CollaboratorProvider
        CollaboratorProvider.constructCollaboratorProvider(api);

        // UIProvider
        UIProvider.constructUIProvider(api);
    }

    private void loading() {
        // 加载被动扫描任务
        SSRFScanCheck ssrfScanCheck = new SSRFScanCheck();
        montoyaApiProvider.registerScanCheck(ssrfScanCheck);

        // 加载ui
        uiProvider.registerSuiteTab("Auto-SSRF", new UIMain());
    }

    private void initStartBanner() {
        logger.logToOutput("Auto-SSRF");
        logger.logToOutput("Author: 半程客梦");
        logger.logToOutput("插件正在加载, 请稍候...");
    }

    private void initEndBanner() {
        logger.logToOutput("插件加载完成");
    }

    private void unload() {
        Extension extension = montoyaApiProvider.getMontoyaApi().extension();
        extension.registerUnloadingHandler(() -> {
            SendReqThreadPool.INSTANCE.getPool().shutdownNow();
            CollaboratorThreadPool.INSTANCE.getPool().shutdownNow();
        });
    }
}
