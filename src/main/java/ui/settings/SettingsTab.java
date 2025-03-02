package ui.settings;

import ui.settings.section.SettingsSectionPanel;
import ui.settings.section.cache.CacheSettingSection;
import ui.settings.section.extensions.ExtensionSettingSection;
import ui.settings.section.regexpfilter.RegexpFilterSettingSection;
import ui.settings.section.threadpool.ThreadPoolSettingSection;

import javax.swing.*;

public class SettingsTab extends JPanel {

    private final Settings settings = new Settings();

    public SettingsTab() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        ExtensionSettingSection extensionSettingSection = new ExtensionSettingSection();
        ThreadPoolSettingSection threadPoolSettingSection = new ThreadPoolSettingSection();
        CacheSettingSection cacheSettingSection = new CacheSettingSection();
        RegexpFilterSettingSection regexpFilterSettingSection = new RegexpFilterSettingSection();

        // 保存所有配置按钮的UI
        SettingsSectionPanel settingSection = saveAllSettingSection();

        this.add(extensionSettingSection);
        this.add(threadPoolSettingSection);
        this.add(cacheSettingSection);
        this.add(regexpFilterSettingSection);
        this.add(settingSection);
    }

    private SettingsSectionPanel saveAllSettingSection() {
        SettingsSectionPanel jPanel = new SettingsSectionPanel();
        JButton saveAllSettingButton = new JButton("保存配置");
        saveAllSettingButton.setBounds(
                20,
                15,
                80, 20
        );
        saveAllSettingButton.addActionListener(e -> Settings.saveAutoSSRFExtensionSetting(settings));
        jPanel.add(saveAllSettingButton);
        return jPanel;
    }
}
