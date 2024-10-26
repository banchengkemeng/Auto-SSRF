import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import common.MontoyaApiProvider;

public class AutoSSRFBurpExtension implements BurpExtension {

    private final MontoyaApiProvider montoyaApiProvider = MontoyaApiProvider.INSTANCE;

    @Override
    public void initialize(MontoyaApi api) {
        // 将montoyaApiProvider
        initMontoyaApiProvider(api);

        // StartBanner
        initStartBanner();

        // EndBanner
        initEndBanner();
    }

    private void initMontoyaApiProvider(MontoyaApi montoyaApi) {
        MontoyaApiProvider.constructInstance(montoyaApi);
    }

    private void initStartBanner() {
        montoyaApiProvider.logToOutput("Auto-SSRF");
        montoyaApiProvider.logToOutput("Author: 半程客梦");
        montoyaApiProvider.logToOutput("插件正在加载, 请稍候...");
    }

    private void initEndBanner() {
        montoyaApiProvider.logToOutput("插件加载完成");
    }
}
