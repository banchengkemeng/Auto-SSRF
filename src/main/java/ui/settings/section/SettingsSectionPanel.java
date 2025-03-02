package ui.settings.section;

import common.provider.UIProvider;
import ui.settings.Settings;

import javax.swing.*;
import java.awt.*;

public class SettingsSectionPanel extends JPanel {
    private final UIProvider uiProvider = UIProvider.INSTANCE;
    private final Settings settings = new Settings();

    public SettingsSectionPanel() {
        this.setLayout(null);
    }

    protected Font getBurpFont() {
        return uiProvider.currentDisplayFont();
    }

    protected Settings getSettings() {
        return settings;
    }

    protected Font getBolderFont() {
        Font burpFont = getBurpFont();
        return new Font(burpFont.getName(), Font.PLAIN, burpFont.getSize() + 7);
    }

    protected Font getNormalFont() {
        Font burpFont = getBurpFont();
        return new Font(burpFont.getName(), Font.PLAIN, burpFont.getSize() + 1);
    }
}
