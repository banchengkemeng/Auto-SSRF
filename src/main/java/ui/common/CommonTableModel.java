package ui.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ui.dashboard.ChangeItem;

import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class CommonTableModel<T extends CommonTableData> extends AbstractTableModel {
    private final List<T> data;
    private final KV[] columns;

    public CommonTableModel() {
        this(new ArrayList<>());
    }

    public CommonTableModel(List<T> commonTableData) {
        assert commonTableData != null;
        assert !commonTableData.isEmpty();
        this.data = commonTableData;
        this.columns = getColumnNames();
    }

    @Override
    public String getColumnName(int column) {
        return columns[column].getValue();
    }

    public String getColumnKey(int column) {
        return columns[column].getKey();
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return getFieldValue(getField(column), data.get(row));
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        setFieldValue(getField(column), data.get(row), aValue);
        fireTableChanged(new TableModelEvent(this, row, row, column));
        fireTableCellUpdated(row, column);
    }

    @Override
    public int findColumn(String columnKey) {
        for (int i = 0; i < getColumnCount(); i++) {
            if (columnKey.equals(getColumnKey(i))) {
                return i;
            }
        }
        return -1;
    }

    public synchronized void changeValue(int id, ChangeItem column) {
        setValueAt(column.getValue(), id - 1, findColumn(column.getFiled()));
    }

    public synchronized void addRow(T rowData) {
        insertRow(getRowCount(), rowData);
    }

    public void insertRow(int row, T rowData) {
        data.add(rowData);
        fireTableRowsInserted(row, row);
    }

    private Field getField(int column) {
        KV item = this.columns[column];
        String key = item.getKey();
        Field field = null;
        try {
            field = this.data.get(0).getClass().getDeclaredField(key);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }

    }

    private Object getFieldValue(Field field, Object sourceValue) {
        try {
            return field.get(sourceValue);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFieldValue(Field field, Object sourceValue, Object value) {
        try {
            field.set(sourceValue, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getField(columnIndex).getType();
    }

    abstract protected KV[] getColumnNames();
}
