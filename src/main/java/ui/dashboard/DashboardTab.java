package ui.dashboard;

import common.UIProvider;
import lombok.Getter;
import ui.common.HttpRequestResponseEditor;

import javax.swing.*;


@Getter
public class DashboardTab extends JSplitPane {

    private final DashboardTable table;
    private final HttpRequestResponseEditor httpRequestResponseEditor;
    private final UIProvider uiProvider = UIProvider.INSTANCE;

    public DashboardTab() {
        super(JSplitPane.VERTICAL_SPLIT);

        this.httpRequestResponseEditor = new HttpRequestResponseEditor();

        this.table = new DashboardTable(
                new DashboardTableModel(),
                httpRequestResponseEditor
        );

        JScrollPane upPanel = new JScrollPane(table);
        this.add(upPanel, "left");

        this.add(httpRequestResponseEditor.uiComponent(), "right");
    }
}
