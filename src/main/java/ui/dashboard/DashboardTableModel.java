package ui.dashboard;


import ui.common.CommonTableModel;
import ui.common.KV;

import java.util.ArrayList;
import java.util.List;

public class DashboardTableModel extends CommonTableModel<DashboardTableData> {

    public DashboardTableModel() {
        super(new ArrayList<>());
    }

    public DashboardTableModel(List<DashboardTableData> commonTableData) {
        super(commonTableData);
    }

    @Override
    protected KV[] getColumnNames() {
        return new KV[]{
                new KV("id", "#"),
                new KV("status", "Status"),
                new KV("host", "Host"),
                new KV("method", "Method"),
                new KV("url", "URL"),
                new KV("statusCode", "Status code"),
                new KV("length", "Length"),
                new KV("mime", "MIME type")
        };
    }
}
