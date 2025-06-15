package gui;

import model.Evento;
import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventoTableModel extends AbstractTableModel{
    private List<Evento> eventos;
    private String[] columnas = {"Título", "Descripción", "Fecha"};
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public EventoTableModel(List<Evento> eventos) {
        this.eventos = eventos;
    }

    @Override
    public int getRowCount() {
        return eventos.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Evento e = eventos.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> e.getTitle();
            case 1 -> e.getDescription();
            case 2 -> e.getDate().format(formatter);
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    public Evento getEventoEn(int fila) {
        return eventos.get(fila);
    }

    public void actualizarDatos(List<Evento> nuevosEventos) {
        this.eventos = nuevosEventos;
        fireTableDataChanged();
    }
}
