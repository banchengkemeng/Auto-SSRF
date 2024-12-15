package ui.settings;

import checker.SSRFChecker;
import checker.filter.cache.FilterCache;
import cn.hutool.core.lang.func.VoidFunc;
import common.logger.AutoSSRFLogger;
import common.provider.UIProvider;
import scanner.SSRFHttpHandler;
import scanner.SSRFScanCheck;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class SettingsTab extends JPanel {

    private final UIProvider uiProvider = UIProvider.INSTANCE;
    private final Settings settings = new Settings();

    public SettingsTab() {
        this.setLayout(null);

        int rootX = 0;
        int rootY = 0;

        // 插件配置
        Component extensionsSettingUIEndComponent = createExtensionsSettingUI(rootX, rootY);

        // 线程池配置
        Component threadPoolSettingUIEndComponent = createThreadPoolSettingUI(
                extensionsSettingUIEndComponent.getX(),
                extensionsSettingUIEndComponent.getY() + extensionsSettingUIEndComponent.getHeight()
        );

        // 缓存配置
        Component cacheSettingUIEndComponent = createCacheSettingUI(
                threadPoolSettingUIEndComponent.getX(),
                threadPoolSettingUIEndComponent.getY() + threadPoolSettingUIEndComponent.getHeight()
        );

        // 保存所有配置
        saveAllSettingUI(
                cacheSettingUIEndComponent.getX(),
                cacheSettingUIEndComponent.getY() + cacheSettingUIEndComponent.getHeight()
        );
    }

    private Component createExtensionsSettingUI(int rootX, int rootY) {
        Font burpFont = uiProvider.currentDisplayFont();
        Settings.Extensions extensions = settings.getExtensions();

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
        return repeaterCheckbox;
    }

    private Component createThreadPoolSettingUI(int rootX, int rootY) {
        Font burpFont = uiProvider.currentDisplayFont();
        Settings.ThreadPool threadPool = settings.getThreadPool();

        JLabel threadPoolSettingLabel = new JLabel("线程池配置");
        threadPoolSettingLabel.setFont(createBolderFont(burpFont));
        threadPoolSettingLabel.setBounds(
                rootX,
                rootY + 15,
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
        return saveButton;
    }

    private Component createCacheSettingUI(int rootX, int rootY) {
        Font burpFont = uiProvider.currentDisplayFont();
        Settings.Cache cacheSetting = settings.getCache();

        JLabel cacheSettingLabel = new JLabel("缓存配置");
        cacheSettingLabel.setFont(createBolderFont(burpFont));
        cacheSettingLabel.setBounds(
                rootX,
                rootY + 15,
                200,
                20
        );

        // 缓存配置
        JLabel cacheFilePathChooserLabel = new JLabel("缓存文件路径:");
        cacheFilePathChooserLabel.setFont(createNormalFont(burpFont));
        cacheFilePathChooserLabel.setBounds(
                cacheSettingLabel.getX(),
                cacheSettingLabel.getY() + cacheSettingLabel.getHeight() + 5,
                80, 20
        );

        JTextField cacheFilePathInput = new JTextField();
        cacheFilePathInput.setBounds(
                cacheFilePathChooserLabel.getX() + cacheFilePathChooserLabel.getWidth() + 5,
                cacheFilePathChooserLabel.getY(),
                200, 20
        );
        cacheFilePathInput.setText(cacheSetting.getCacheFilePath());
        cacheFilePathInput.getDocument().addDocumentListener(
                buildDocumentListener(e -> cacheSetting.setCacheFilePath(cacheFilePathInput.getText()))
        );

        JButton fileChooserButton = new JButton("选择文件夹");
        fileChooserButton.setBounds(
                cacheFilePathInput.getX() + cacheFilePathInput.getWidth() + 5,
                cacheFilePathInput.getY(),
                90, 20
        );
        fileChooserButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.showDialog(null, "确认");
            cacheFilePathInput.setText(fileChooser.getSelectedFile().getAbsolutePath());
        });

        JLabel cacheFileNameLabel = new JLabel("缓存文件名:");
        cacheFileNameLabel.setFont(createNormalFont(burpFont));
        cacheFileNameLabel.setBounds(
                cacheFilePathChooserLabel.getX(),
                cacheFilePathChooserLabel.getY() + cacheFilePathChooserLabel.getHeight() + 5,
                80, 20
        );

        JTextField cacheFileNameInput = new JTextField();
        cacheFileNameInput.setBounds(
                cacheFileNameLabel.getX() + cacheFileNameLabel.getWidth() + 5,
                cacheFileNameLabel.getY(),
                200, 20
        );
        cacheFileNameInput.setText(cacheSetting.getCacheFileName());
        cacheFileNameInput.getDocument().addDocumentListener(
                buildDocumentListener(e -> cacheSetting.setCacheFileName(cacheFileNameInput.getText()))
        );

        JLabel cacheObjCountLabel = new JLabel("缓存对象数:");
        cacheObjCountLabel.setFont(createNormalFont(burpFont));
        cacheObjCountLabel.setBounds(
                cacheFileNameLabel.getX(),
                cacheFileNameLabel.getY() + cacheFileNameLabel.getHeight() + 5,
                80, 20
        );

        JTextField cacheObjCountInput = new JTextField();
        cacheObjCountInput.setBounds(
                cacheObjCountLabel.getX() + cacheObjCountLabel.getWidth() + 5,
                cacheObjCountLabel.getY(),
                200, 20
        );
        cacheObjCountInput.setText("点击下方按钮获取");
        cacheObjCountInput.setEditable(false);
        cacheObjCountInput.setFocusable(false);

        JButton cacheCountButton = new JButton("刷新缓存数");
        cacheCountButton.setBounds(
                cacheObjCountLabel.getX(),
                cacheObjCountLabel.getY() + cacheObjCountLabel.getHeight() + 5,
                90, 20
        );
        cacheCountButton.addActionListener(e -> {
            FilterCache<String, Byte> cache = SSRFChecker.INSTANCE.getFilter().getCache();
            Integer cacheCount = cache.getCacheCount();
            cacheObjCountInput.setText(String.valueOf(cacheCount));
        });

        JButton clearCacheFileButton = new JButton("清理缓存");
        setButtonBounds(cacheCountButton, clearCacheFileButton);
        clearCacheFileButton.addActionListener(e -> {
            FilterCache<String, Byte> cache = SSRFChecker.INSTANCE.getFilter().getCache();
            cache.clear();
        });

        JButton deleteCacheFileButton = new JButton("删除缓存文件");
        setButtonBounds(clearCacheFileButton, deleteCacheFileButton);
        deleteCacheFileButton.addActionListener(e -> {
            FilterCache<String, Byte> cache = SSRFChecker.INSTANCE.getFilter().getCache();
            cache.delete();
        });

        JButton saveCacheFileButton = new JButton("保存缓存文件");
        setButtonBounds(deleteCacheFileButton, saveCacheFileButton);
        saveCacheFileButton.addActionListener(e -> {
            FilterCache<String, Byte> cache = SSRFChecker.INSTANCE.getFilter().getCache();
            try {
                FilterCache.setPath(cacheSetting.getCacheFilePathHaveName());
                cache.store();
            } catch (IOException exception) {
                AutoSSRFLogger.INSTANCE.logToError(exception);
            }
        });

        this.add(cacheSettingLabel);
        this.add(cacheFilePathChooserLabel);
        this.add(cacheFilePathInput);
        this.add(fileChooserButton);
        this.add(cacheFileNameLabel);
        this.add(cacheObjCountLabel);
        this.add(cacheObjCountInput);
        this.add(cacheCountButton);
        this.add(cacheFileNameInput);
        this.add(clearCacheFileButton);
        this.add(deleteCacheFileButton);
        this.add(saveCacheFileButton);
        return cacheCountButton;
    }

    private void saveAllSettingUI(int rootX, int rootY) {
        JButton saveAllSettingButton = new JButton("保存配置");
        saveAllSettingButton.setBounds(
                rootX,
                rootY + 15,
                80, 20
        );
        saveAllSettingButton.addActionListener(e -> Settings.saveAutoSSRFExtensionSetting(settings));
        this.add(saveAllSettingButton);
    }

    private void setButtonBounds(Component positionComponent, Component currentComponent) {
        currentComponent.setBounds(
                positionComponent.getX() + positionComponent.getWidth() + 3,
                positionComponent.getY(),
                100, 20
        );
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

    private DocumentListener buildDocumentListener(Consumer<DocumentEvent> function) {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                function.accept(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                function.accept(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                function.accept(e);
            }
        };
    }
}
