package ui.settings.section.extensions;

import scanner.SSRFHttpHandler;
import scanner.SSRFScanCheck;
import ui.settings.Settings;
import ui.settings.section.SettingsSectionPanel;

import javax.swing.*;
import java.awt.*;

public class ExtensionSettingSection extends SettingsSectionPanel {

    private final Settings.Extensions extensions = getSettings().getExtensions();

    public ExtensionSettingSection() {
        super();
        Font burpBolderFont = getBolderFont();

        JLabel globalSettingLabel = new JLabel("插件配置");
        globalSettingLabel.setFont(burpBolderFont);
        globalSettingLabel.setBounds(
                20,
                20,
                200,
                20
        );

        JCheckBox passiveCheckbox = new JCheckBox();
        passiveCheckbox.setBounds(
                globalSettingLabel.getX(),
                globalSettingLabel.getY() + globalSettingLabel.getHeight() + 5,
                18,
                20
        );
        passiveCheckbox.setSelected(extensions.isPassive());
        SSRFScanCheck.setEnabled(extensions.isPassive());
        passiveCheckbox.addActionListener(e -> extensions.setPassiveReal(passiveCheckbox.isSelected()));
        JLabel passiveCheckboxLabel = new JLabel("被动扫描");
        setCheckboxFontAndBounds(passiveCheckbox, passiveCheckboxLabel);

        JCheckBox proxyCheckbox = new JCheckBox();
        proxyCheckbox.setBounds(
                passiveCheckbox.getX(),
                passiveCheckbox.getY() + passiveCheckbox.getHeight() + 5,
                18,
                20
        );
        proxyCheckbox.setSelected(extensions.isProxy());
        SSRFHttpHandler.setProxyEnabled(extensions.isProxy());
        proxyCheckbox.addActionListener(e -> extensions.setProxyReal(proxyCheckbox.isSelected()));
        JLabel proxyCheckboxLabel = new JLabel("扫描Proxy");
        setCheckboxFontAndBounds(proxyCheckbox, proxyCheckboxLabel);

        JCheckBox repeaterCheckbox = new JCheckBox();
        repeaterCheckbox.setBounds(
                proxyCheckbox.getX(),
                proxyCheckbox.getY() + proxyCheckbox.getHeight() + 5,
                18,
                20
        );
        repeaterCheckbox.setSelected(extensions.isRepeater());
        SSRFHttpHandler.setRepeaterEnabled(extensions.isRepeater());
        repeaterCheckbox.addActionListener(e -> extensions.setRepeaterReal(repeaterCheckbox.isSelected()));
        JLabel repeaterCheckboxLabel = new JLabel("扫描Repeater");
        setCheckboxFontAndBounds(repeaterCheckbox, repeaterCheckboxLabel);

        this.add(globalSettingLabel);
        this.add(passiveCheckbox);
        this.add(passiveCheckboxLabel);
        this.add(proxyCheckbox);
        this.add(proxyCheckboxLabel);
        this.add(repeaterCheckbox);
        this.add(repeaterCheckboxLabel);
    }

    private void setCheckboxFontAndBounds(Component positionComponent, Component currentComponent) {
        Font burpNormalFont = getNormalFont();

        currentComponent.setFont(burpNormalFont);
        currentComponent.setBounds(
                positionComponent.getX() + positionComponent.getWidth() + 3,
                positionComponent.getY(),
                100, 20
        );
    }
}
