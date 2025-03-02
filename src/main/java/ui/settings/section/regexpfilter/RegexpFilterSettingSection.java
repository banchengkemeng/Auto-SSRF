package ui.settings.section.regexpfilter;

import ui.settings.section.SettingsSectionPanel;

import javax.swing.*;
import java.awt.*;

public class RegexpFilterSettingSection extends SettingsSectionPanel {
    public RegexpFilterSettingSection() {
        super();
        Font burpBolderFont = getBolderFont();

        JLabel regexpFilterSettingLabel = new JLabel("正则过滤器配置");
        regexpFilterSettingLabel.setFont(burpBolderFont);
        regexpFilterSettingLabel.setBounds(
                20,
                15,
                200,
                20
        );

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton remove = new JButton("Remove");
        JButton pasteURL = new JButton("Paste URL");
        JButton load = new JButton("Load...");
        buttonPanel.add(add);
        buttonPanel.add(edit);
        buttonPanel.add(remove);
        buttonPanel.add(pasteURL);
        buttonPanel.add(load);
        buttonPanel.setFont(burpBolderFont);
        buttonPanel.setBounds(
                regexpFilterSettingLabel.getX(),
                regexpFilterSettingLabel.getY() + regexpFilterSettingLabel.getHeight() + 15,
                100,
                200
        );

        this.add(regexpFilterSettingLabel);
        this.add(buttonPanel);
    }
}
