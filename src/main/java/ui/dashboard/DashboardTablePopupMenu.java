package ui.dashboard;

import checker.SSRFChecker;

import javax.swing.*;
import java.util.List;

public class DashboardTablePopupMenu extends JPopupMenu {
    private final SSRFChecker ssrfChecker = SSRFChecker.INSTANCE;

    public DashboardTablePopupMenu(List<DashboardTableData> dashboardTableDataList) {
        JMenuItem recheckMenuItem = new JMenuItem("重新检测");
        recheckMenuItem.addActionListener(
                e -> dashboardTableDataList.forEach(dashboardTableData -> ssrfChecker.check(
                        dashboardTableData.getRequestResponse(),
                        dashboardTableData.getId()
                ))
        );
        this.add(recheckMenuItem);
    }
}
