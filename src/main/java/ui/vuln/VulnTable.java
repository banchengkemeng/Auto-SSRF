package ui.vuln;

import burp.api.montoya.collaborator.Interaction;
import ui.common.CommonTable;
import ui.common.HttpRequestResponseEditor;
import ui.dashboard.DashboardTableData;
import ui.vuln.interaction.InteractionTable;

import javax.swing.table.TableModel;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VulnTable extends CommonTable<VulnTableData> {

    private final VulnTableModel vulnTableModel;
    private final HttpRequestResponseEditor requestResponseEditor;
    private final AtomicInteger globalId = new AtomicInteger(0);
    private final InteractionTable interactionTable;

    public VulnTable(
            TableModel tableModel,
            HttpRequestResponseEditor httpRequestResponseEditor,
            InteractionTable interactionTable
    ) {
        super(tableModel);

        this.vulnTableModel = (VulnTableModel) tableModel;
        this.requestResponseEditor = httpRequestResponseEditor;
        this.interactionTable = interactionTable;
    }

    public Integer generateId() {
        return globalId.addAndGet(1);
    }

    @Override
    public void changeSelection(int row, int col, boolean toggle, boolean extend) {
        super.changeSelection(row, col, toggle, extend);

        row = this.getRowSorter().convertRowIndexToModel(row);
        VulnTableData vulnTableData = this.getTableData().get(row);
        requestResponseEditor.setData(
                vulnTableData.getRequestResponse()
        );
    }
}
