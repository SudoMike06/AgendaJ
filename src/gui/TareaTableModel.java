package gui;

import javax.swing.table.AbstractTableModel;
import model.Tarea;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TareaTableModel extends AbstractTableModel {
    private List<Tarea> tareas;
    private String[] columnas = {"Título", "Descripción", "Fecha Límite", "Pendiente"};
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public TareaTableModel(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    @Override
    public int getRowCount() {
        return tareas.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tarea t = tareas.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> t.getTitle();
            case 1 -> t.getDescription();
            case 2 -> t.getLimitDate().format(formatter);
            case 3 -> t.getPending() ? "Sí" : "No";
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    public Tarea getTareaEn(int fila) {
        return tareas.get(fila);
    }

    public void actualizarDatos(List<Tarea> nuevasTareas) {
        this.tareas = nuevasTareas;
        fireTableDataChanged();
    }
}

