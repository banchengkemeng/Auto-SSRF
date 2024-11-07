package ui.vuln;

import common.UIProvider;
import lombok.Getter;
import ui.common.HttpRequestResponseEditor;
import ui.vuln.interaction.InteractionTable;

import javax.swing.*;

@Getter
public class VulnTab extends JSplitPane {

    private final VulnTable table;
    private final HttpRequestResponseEditor httpRequestResponseEditor;
    InteractionTable interactionTable;
    private final UIProvider uiProvider = UIProvider.INSTANCE;

    public VulnTab() {
        super(JSplitPane.VERTICAL_SPLIT);

        httpRequestResponseEditor = new HttpRequestResponseEditor();

        interactionTable = new InteractionTable();

        this.table = new VulnTable(
                new VulnTableModel(),
                httpRequestResponseEditor,
                interactionTable
        );

        JScrollPane upPanel = new JScrollPane(table);
        this.add(upPanel, "left");

        this.add(httpRequestResponseEditor.uiComponent(), "right");
    }
}
