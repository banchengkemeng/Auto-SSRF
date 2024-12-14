import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.extension.Extension;
import common.logger.AutoSSRFLogger;
import common.pool.CollaboratorThreadPool;
import common.pool.UIThreadPool;
import common.provider.CollaboratorProvider;
import common.provider.HttpProvider;
import common.provider.MontoyaApiProvider;
import common.provider.UIProvider;
import scanner.SSRFHttpHandler;
import scanner.SSRFScanCheck;
import ui.UIMain;

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

    private void initStartBanner() {
        logger.logToOutput("\n" +
                "                _           _____ _____ _____  ______ \n" +
                "     /\\        | |         / ____/ ____|  __ \\|  ____|\n" +
                "    /  \\  _   _| |_ ___   | (___| (___ | |__) | |__   \n" +
                "   / /\\ \\| | | | __/ _ \\   \\___ \\\\___ \\|  _  /|  __|  \n" +
                "  / ____ \\ |_| | || (_) |  ____) |___) | | \\ \\| |     \n" +
                " /_/    \\_\\__,_|\\__\\___/  |_____/_____/|_|  \\_\\_|     \n");
        logger.logToOutput("Author: 半程客梦");
        logger.logToOutput("WeChat: banc000");
        logger.logToOutput("Github: https://github.com/banchengkemeng");
        logger.logToOutput("插件正在加载, 请稍候...");
    }

    private void loading() {
        // 加载ui
        UIMain uiMain = new UIMain(uiProvider);
        uiProvider.registerSuiteTab("Auto-SSRF", uiMain);

        // 加载被动扫描任务
        SSRFScanCheck ssrfScanCheck = new SSRFScanCheck();
        montoyaApiProvider.registerScanCheck(ssrfScanCheck);

        // 加载tools扫描任务
        SSRFHttpHandler ssrfHttpHandler = new SSRFHttpHandler();
        HttpProvider.INSTANCE.registerHttpHandler(ssrfHttpHandler);

    }

    private void initEndBanner() {
        logger.logToOutput("插件加载完成");
    }

    private void unload() {
        Extension extension = montoyaApiProvider.getMontoyaApi().extension();
        extension.registerUnloadingHandler(() -> {
            CollaboratorThreadPool.INSTANCE.getPool().shutdownNow();
            UIThreadPool.INSTANCE.getPool().shutdownNow();
        });
    }
}
