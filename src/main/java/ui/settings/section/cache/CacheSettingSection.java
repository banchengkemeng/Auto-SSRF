package ui.settings.section.cache;

import checker.filter.cache.DeduplicationFilterCacheManager;
import checker.filter.cache.FilterCache;
import common.logger.AutoSSRFLogger;
import ui.settings.Settings;
import ui.settings.section.SettingsSectionPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.IOException;
import java.util.function.Consumer;

public class CacheSettingSection extends SettingsSectionPanel {

    private final Settings.Cache cacheSetting = getSettings().getCache();

    public CacheSettingSection() {
        super();
        Font burpBolderFont = getBolderFont();
        Font burpNormalFont = getNormalFont();

        JLabel cacheSettingLabel = new JLabel("缓存配置");
        cacheSettingLabel.setFont(burpBolderFont);
        cacheSettingLabel.setBounds(
                20,
                15,
                200,
                20
        );

        // 缓存配置
        JLabel cacheFilePathChooserLabel = new JLabel("缓存文件路径:");
        cacheFilePathChooserLabel.setFont(burpNormalFont);
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
        cacheFileNameLabel.setFont(burpNormalFont);
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
        cacheObjCountLabel.setFont(burpNormalFont);
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

            FilterCache<String, Byte> cache = DeduplicationFilterCacheManager.INSTANCE.getCache();
            Integer cacheCount = cache.getCacheCount();
            cacheObjCountInput.setText(String.valueOf(cacheCount));
        });

        JButton clearCacheFileButton = new JButton("清理缓存");
        setButtonBounds(cacheCountButton, clearCacheFileButton);
        clearCacheFileButton.addActionListener(e -> {
            FilterCache<String, Byte> cache = DeduplicationFilterCacheManager.INSTANCE.getCache();
            cache.clear();
        });

        JButton deleteCacheFileButton = new JButton("删除缓存文件");
        setButtonBounds(clearCacheFileButton, deleteCacheFileButton);
        deleteCacheFileButton.addActionListener(e -> {
            FilterCache<String, Byte> cache = DeduplicationFilterCacheManager.INSTANCE.getCache();
            cache.delete();
        });

        JButton saveCacheFileButton = new JButton("保存缓存文件");
        setButtonBounds(deleteCacheFileButton, saveCacheFileButton);
        saveCacheFileButton.addActionListener(e -> {
            FilterCache<String, Byte> cache = DeduplicationFilterCacheManager.INSTANCE.getCache();
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
    }

    private void setButtonBounds(Component positionComponent, Component currentComponent) {
        currentComponent.setBounds(
                positionComponent.getX() + positionComponent.getWidth() + 3,
                positionComponent.getY(),
                100, 20
        );
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
