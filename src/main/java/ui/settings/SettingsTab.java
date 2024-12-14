package ui.settings;

import common.pool.CollaboratorThreadPool;
import common.provider.UIProvider;
import scanner.SSRFHttpHandler;
import scanner.SSRFScanCheck;

import javax.swing.*;
import java.awt.*;

public class SettingsTab extends JPanel {

    UIProvider uiProvider = UIProvider.INSTANCE;
    CollaboratorThreadPool collaboratorThreadPool = CollaboratorThreadPool.INSTANCE;

    public SettingsTab() {
        this.setLayout(null);

        int rootX = 0;
        int rootY = 0;

        Component endComponent = createExtensionsSettingUI(rootX, rootY);
        createThreadPoolSettingUI(
                endComponent.getX(),
                endComponent.getY() + endComponent.getHeight()
        );
    }

    private Component createExtensionsSettingUI(int rootX, int rootY) {
        Font burpFont = uiProvider.currentDisplayFont();
        JLabel globalSettingLabel = new JLabel("插件配置");
        globalSettingLabel.setFont(createBolderFont(burpFont));
        globalSettingLabel.setBounds(
                rootX + 20,
                rootY + 20,
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
        passiveCheckbox.setSelected(SSRFScanCheck.isEnabled());
        SSRFScanCheck.setEnabled(SSRFScanCheck.isEnabled());
        passiveCheckbox.addActionListener(e -> SSRFScanCheck.setEnabled(passiveCheckbox.isSelected()));
        JLabel passiveCheckboxLabel = new JLabel("被动扫描");
        setCheckboxFontAndBounds(passiveCheckbox, passiveCheckboxLabel);

        JCheckBox proxyCheckbox = new JCheckBox();
        proxyCheckbox.setBounds(
                passiveCheckbox.getX(),
                passiveCheckbox.getY() + passiveCheckbox.getHeight() + 5,
                18,
                20
        );
        proxyCheckbox.setSelected(SSRFHttpHandler.isProxyEnabled());
        SSRFHttpHandler.setProxyEnabled(SSRFHttpHandler.isProxyEnabled());
        proxyCheckbox.addActionListener(e -> SSRFHttpHandler.setProxyEnabled(proxyCheckbox.isSelected()));
        JLabel proxyCheckboxLabel = new JLabel("扫描Proxy");
        setCheckboxFontAndBounds(proxyCheckbox, proxyCheckboxLabel);

        JCheckBox repeaterCheckbox = new JCheckBox();
        repeaterCheckbox.setBounds(
                proxyCheckbox.getX(),
                proxyCheckbox.getY() + proxyCheckbox.getHeight() + 5,
                18,
                20
        );
        repeaterCheckbox.setSelected(SSRFHttpHandler.isRepeaterEnabled());
        SSRFHttpHandler.setRepeaterEnabled(SSRFHttpHandler.isRepeaterEnabled());
        repeaterCheckbox.addActionListener(e -> SSRFHttpHandler.setRepeaterEnabled(repeaterCheckbox.isSelected()));
        JLabel repeaterCheckboxLabel = new JLabel("扫描Repeater");
        setCheckboxFontAndBounds(repeaterCheckbox, repeaterCheckboxLabel);

        this.add(globalSettingLabel);
        this.add(passiveCheckbox);
        this.add(passiveCheckboxLabel);
        this.add(proxyCheckbox);
        this.add(proxyCheckboxLabel);
        this.add(repeaterCheckbox);
        this.add(repeaterCheckboxLabel);

        return repeaterCheckbox;
    }

    private void createThreadPoolSettingUI(int rootX, int rootY) {
        Font burpFont = uiProvider.currentDisplayFont();
        JLabel threadPoolSettingLabel = new JLabel("线程池配置");
        threadPoolSettingLabel.setFont(createBolderFont(burpFont));
        threadPoolSettingLabel.setBounds(
                rootX,
                rootY + 10,
                200,
                20
        );

        // 核心线程数输入框
        JLabel corePoolSizeLabel = new JLabel("核心线程数:");
        setInputFontAndBounds(threadPoolSettingLabel, corePoolSizeLabel);
        JTextField corePoolSizeInput = new JTextField();
        corePoolSizeInput.setBounds(
                corePoolSizeLabel.getX() + corePoolSizeLabel.getWidth() + 5,
                corePoolSizeLabel.getY(),
                200, 20
        );
        corePoolSizeInput.setText(
                String.valueOf(collaboratorThreadPool.getCorePoolSize())
        );

        // 最大线程数输入框
        JLabel maxPoolSizeLabel = new JLabel("最大线程数:");
        setInputFontAndBounds(corePoolSizeLabel, maxPoolSizeLabel);
        JTextField maxPoolSizeInput = new JTextField();
        maxPoolSizeInput.setBounds(
                maxPoolSizeLabel.getX() + maxPoolSizeLabel.getWidth() + 5,
                maxPoolSizeLabel.getY(),
                200, 20
        );
        maxPoolSizeInput.setText(
                String.valueOf(collaboratorThreadPool.getMaxPoolSize())
        );

        JButton saveButton = new JButton("保存");
        saveButton.setBounds(
                maxPoolSizeLabel.getX(),
                maxPoolSizeLabel.getY() + maxPoolSizeInput.getHeight() + 5,
                60, 20
        );
        saveButton.addActionListener(e -> collaboratorThreadPool.setPoolSize(
                Integer.valueOf(corePoolSizeInput.getText()),
                Integer.valueOf(maxPoolSizeInput.getText())
        ));

        this.add(threadPoolSettingLabel);
        this.add(corePoolSizeLabel);
        this.add(corePoolSizeInput);
        this.add(maxPoolSizeLabel);
        this.add(maxPoolSizeInput);
        this.add(saveButton);
    }

    private void setInputFontAndBounds(Component positionComponent, Component currentComponent) {
        Font burpFont = uiProvider.currentDisplayFont();
        currentComponent.setFont(createNormalFont(burpFont));
        currentComponent.setBounds(
                positionComponent.getX(),
                positionComponent.getY() + positionComponent.getHeight() + 5,
                70, 20
        );
    }

    private void setCheckboxFontAndBounds(Component positionComponent, Component currentComponent) {
        Font burpFont = uiProvider.currentDisplayFont();
        currentComponent.setFont(createNormalFont(burpFont));
        currentComponent.setBounds(
                positionComponent.getX() + positionComponent.getWidth() + 3,
                positionComponent.getY(),
                100, 20
        );
    }

    private Font createBolderFont(Font burpFont) {
        return new Font(burpFont.getName(), Font.PLAIN, burpFont.getSize() + 7);
    }

    private Font createNormalFont(Font burpFont) {
        return new Font(burpFont.getName(), Font.PLAIN, burpFont.getSize() + 1);
    }
}
