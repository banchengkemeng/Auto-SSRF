package ui;

import common.provider.UIProvider;
import lombok.Getter;
import ui.dashboard.DashboardTab;
import ui.settings.SettingsTab;
import ui.vuln.VulnTab;

import javax.swing.*;
import java.awt.*;


public class UIMain extends JTabbedPane {

    private final UIProvider provider;

    @Getter
    private final DashboardTab dashboardTab = new DashboardTab();

    @Getter
    private final VulnTab vulnTab = new VulnTab();

    @Getter
    private final SettingsTab settingsTab = new SettingsTab();

    public UIMain(UIProvider provider) {
        super();
        this.provider = provider;

        this.addTab("扫描概览", dashboardTab);
        this.addTab("疑似漏洞", vulnTab);
        this.addTab("插件配置", settingsTab);

        // 主题适配
        applyTheme(
                this,
                dashboardTab,
                dashboardTab.getTable()
        );
    }

    private void applyTheme(Component... components) {
        for (Component component : components) {
            provider.applyThemeToComponent(component);
        }
    }
}
