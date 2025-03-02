package ui.settings.section.threadpool;

import ui.settings.Settings;
import ui.settings.section.SettingsSectionPanel;

import javax.swing.*;
import java.awt.*;

public class ThreadPoolSettingSection extends SettingsSectionPanel {

    private final Settings.ThreadPool threadPool = getSettings().getThreadPool();

    public ThreadPoolSettingSection() {
        super();
        Font burpBolderFont = getBolderFont();

        JLabel threadPoolSettingLabel = new JLabel("线程池配置");
        threadPoolSettingLabel.setFont(burpBolderFont);
        threadPoolSettingLabel.setBounds(
                20,
                15,
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
                String.valueOf(threadPool.getCorePoolSize())
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
                String.valueOf(threadPool.getMaxPoolSize())
        );

        JButton saveButton = new JButton("生效");
        saveButton.setBounds(
                maxPoolSizeLabel.getX(),
                maxPoolSizeLabel.getY() + maxPoolSizeInput.getHeight() + 5,
                60, 20
        );
        saveButton.addActionListener(e -> threadPool.setPoolSize(
                Integer.parseInt(corePoolSizeInput.getText()),
                Integer.parseInt(maxPoolSizeInput.getText())
        ));

        this.add(threadPoolSettingLabel);
        this.add(corePoolSizeLabel);
        this.add(corePoolSizeInput);
        this.add(maxPoolSizeLabel);
        this.add(maxPoolSizeInput);
        this.add(saveButton);
    }

    private void setInputFontAndBounds(Component positionComponent, Component currentComponent) {
        Font burpNormalFont = getNormalFont();
        currentComponent.setFont(burpNormalFont);
        currentComponent.setBounds(
                positionComponent.getX(),
                positionComponent.getY() + positionComponent.getHeight() + 5,
                70, 20
        );
    }
}
