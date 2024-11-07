package ui.vuln;

import ui.common.CommonTableModel;
import ui.common.KV;

import java.util.ArrayList;
import java.util.List;

public class VulnTableModel extends CommonTableModel<VulnTableData> {
    public VulnTableModel() {
        super(new ArrayList<>());
    }

    public VulnTableModel(List<VulnTableData> commonTableData) {
        super(commonTableData);
    }

    @Override
    protected KV[] getColumnNames() {
        return new KV[]{
                new KV("id", "#"),
                new KV("host", "Host"),
                new KV("method", "Method"),
                new KV("url", "URL"),
                new KV("statusCode", "Status code"),
                new KV("length", "Length"),
                new KV("mime", "MIME type")
        };
    }
}
