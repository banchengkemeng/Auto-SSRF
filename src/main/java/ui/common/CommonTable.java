package ui.common;

import lombok.Getter;
import pool.UIThreadPool;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Getter
public class CommonTable<T extends CommonTableData> extends JTable {
    protected List<T> tableData;

    private final CommonTableModel<T> tableModel;
    private final ThreadPoolExecutor uiThreadPool = UIThreadPool.INSTANCE.getPool();

    public CommonTable(TableModel tableModel) {
        super(tableModel);

        this.tableModel = (CommonTableModel<T>) tableModel;

        this.tableData = this.tableModel.getData();

        // 设置排序
        this.setAutoCreateRowSorter(true);

        // 允许行被选择
        this.setRowSelectionAllowed(true);
        this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // 设置行高
        this.setRowHeight(30);

        // 设置列
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column
            ) {
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (isSelected) {
                    setForeground(Color.BLACK);
                    setBackground(new Color(202, 218, 240));
                } else {
                    setForeground(Color.BLACK);
                    setBackground(Color.WHITE); // 设置交替行颜色
                }
                return this;
            }
        };
        this.setDefaultRenderer(String.class, defaultTableCellRenderer);
        this.setDefaultRenderer(Integer.class, defaultTableCellRenderer);
    }

    // 选择某一行时
    public void changeSelection(int row, int col, boolean toggle, boolean extend) {
        if (toggle) {
            this.addRowSelectionInterval(row, row);
        } else {
            this.setRowSelectionInterval(row, row);
        }
    }

    public void addRows(List<T> dataList) {
        dataList.forEach(this::addRow);
    }

    public void addRow(T data) {
        uiThreadPool.execute(() -> tableModel.addRow(data));
    }
}