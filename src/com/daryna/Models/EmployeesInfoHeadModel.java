package com.daryna.Models;

import com.daryna.Models.Data.EmployeeInfoHead;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EmployeesInfoHeadModel implements TableModel {

    private Set<TableModelListener> listeners = new HashSet<TableModelListener>();
    private List<EmployeeInfoHead> data;

    public EmployeesInfoHeadModel(List<EmployeeInfoHead> data)
    {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Project name";
            case 1:
                return "Start date";
            case 2:
                return "End date";
            case 3:
                return "Salary";
        }
        return "";
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        EmployeeInfoHead current = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return current.getProjectName();
            case 1:
                return current.getStartDate();
            case 2:
                return current.getEndDate();
            case 3:
                return current.getSalary();
        }
        return "";
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }
}
