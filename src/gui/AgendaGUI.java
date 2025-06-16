package gui;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import service.AgendaIO;
import service.AgendaManager;
import java.time.LocalDateTime;
import model.Tarea;
import model.Evento;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.time.format.DateTimeFormatter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AgendaGUI {
    private JFrame ventana;
    private JTabbedPane pestañas;
    private JPanel panelBusquedaT;
    private JPanel panelBusquedaE;
    private JPanel panelTareas;
    private JPanel panelEventos;
    private JPanel barraTareas;
    private JPanel barraEventos;
    private JButton agregarTarea;
    private JButton guardarTareas;
    private JButton cargarTareas;
    private JTextField campoBuscarTituloT;
    private JTextField campoBuscarDescripcionT;
    private JTextField campoBuscarFechaT;
    private JTextField campoBuscarTituloE;
    private JTextField campoBuscarDescripcionE;
    private JTextField campoBuscarFechaE;
    JComboBox<String> comboPendiente;

    private JButton agregarEvento;
    private JButton guardarEventos;
    private JButton cargarEventos;
    private JButton botonBuscarT;
    private JButton botonBuscarE;
    private JButton botonRestablecerT;
    private JButton botonRestablecerE;

    private AgendaManager agenda;

    private JTable tablaTareas;
    private JTable tablaEventos;
    private TareaTableModel modeloTareas;
    private EventoTableModel modeloEventos;

    private JPopupMenu popupMenuTareas;
    private JMenuItem modificarTareaItem;
    private JMenuItem eliminarTareaItem;
    private JMenuItem marcarPendienteItem;

    private JPopupMenu popupMenuEventos;
    private JMenuItem modificarEventoItem;
    private JMenuItem eliminarEventoItem;

    private static final String ARCHIVO_TAREAS = "datos_tareas.json";
    private static final String ARCHIVO_EVENTOS = "datos_eventos.json";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");


    public AgendaGUI(){
        ventana = new JFrame();
        pestañas = new JTabbedPane();
        panelTareas = new JPanel();
        panelEventos = new JPanel();
        barraTareas = new JPanel();
        barraEventos = new JPanel();
        agenda = new AgendaManager();
        modeloTareas = new TareaTableModel(agenda.getListT());
        modeloEventos = new EventoTableModel(agenda.getListE());
        tablaTareas = new JTable(modeloTareas);
        tablaEventos = new JTable(modeloEventos);

        panelBusquedaT = new JPanel();
        panelBusquedaE = new JPanel();
        campoBuscarTituloT = new JTextField(15);
        campoBuscarDescripcionT = new JTextField(15);
        campoBuscarFechaT = new JTextField(15);
        campoBuscarTituloE = new JTextField(15);
        campoBuscarDescripcionE = new JTextField(15);
        campoBuscarFechaE = new JTextField(15);
        comboPendiente = new JComboBox<>(new String[] {"Todos", "Pendiente", "No pendiente"});
        botonBuscarT = new JButton("Buscar");
        botonBuscarE = new JButton("Buscar");
        botonRestablecerT = new JButton("Restablecer");
        botonRestablecerE = new JButton("Restablecer");

        panelBusquedaT.add(new JLabel("Título:"));
        panelBusquedaT.add(campoBuscarTituloT);
        panelBusquedaE.add(new JLabel("Título:"));
        panelBusquedaE.add(campoBuscarTituloE);

        panelBusquedaT.add(new JLabel("Descripción:"));
        panelBusquedaT.add(campoBuscarDescripcionT);
        panelBusquedaE.add(new JLabel("Descripción:"));
        panelBusquedaE.add(campoBuscarDescripcionE);

        panelBusquedaT.add(new JLabel("Fecha Límite (yyyy-MM-dd):"));
        panelBusquedaT.add(campoBuscarFechaT);
        panelBusquedaE.add(new JLabel("Fecha (yyyy-MM-dd):"));
        panelBusquedaE.add(campoBuscarFechaE);

        panelBusquedaT.add(new JLabel("Pendiente:"));
        panelBusquedaT.add(comboPendiente);

        panelBusquedaT.add(botonBuscarT);
        panelBusquedaE.add(botonBuscarE);
        panelBusquedaT.add(botonRestablecerT);
        panelBusquedaE.add(botonRestablecerE);

        botonBuscarT.addActionListener(e -> {
            String titulo = campoBuscarTituloT.getText().toLowerCase();
            String descripcion = campoBuscarDescripcionT.getText().toLowerCase();
            String fechaTexto = campoBuscarFechaT.getText();
            String estado = (String) comboPendiente.getSelectedItem();

            ArrayList<Tarea> tareasFiltradas = new ArrayList<>();

            for (Tarea t : agenda.getListT()) {
                boolean coincide = true;

                if (!titulo.isEmpty() && !t.getTitle().toLowerCase().contains(titulo))
                    coincide = false;

                if (!descripcion.isEmpty() && !t.getDescription().toLowerCase().contains(descripcion))
                    coincide = false;

                if (!fechaTexto.isEmpty()) {
                    try {
                        LocalDateTime fechaBuscada = LocalDateTime.parse(fechaTexto + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        if (t.getLimitDate().toLocalDate().isBefore(fechaBuscada.toLocalDate())) {
                            coincide = false;
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ventana, "Formato de fecha incorrecto. Usa yyyy-MM-dd");
                        return;
                    }
                }

                if (estado.equals("Pendiente") && !t.getPending()) coincide = false;
                if (estado.equals("No pendiente") && t.getPending()) coincide = false;

                if (coincide)
                    tareasFiltradas.add(t);
            }

            modeloTareas.actualizarDatos(tareasFiltradas);
        });

        botonBuscarE.addActionListener(e -> {
            String titulo = campoBuscarTituloE.getText().toLowerCase();
            String descripcion = campoBuscarDescripcionE.getText().toLowerCase();
            String fechaTexto = campoBuscarFechaE.getText();

            ArrayList<Evento> eventosFiltrados = new ArrayList<>();

            for (Evento x : agenda.getListE()) {
                boolean coincide = true;

                if (!titulo.isEmpty() && !x.getTitle().toLowerCase().contains(titulo))
                    coincide = false;

                if (!descripcion.isEmpty() && !x.getDescription().toLowerCase().contains(descripcion))
                    coincide = false;

                if (!fechaTexto.isEmpty()) {
                    try {
                        LocalDateTime fechaBuscada = LocalDateTime.parse(fechaTexto + " 00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        if (x.getDate().toLocalDate().isBefore(fechaBuscada.toLocalDate())) {
                            coincide = false;
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ventana, "Formato de fecha incorrecto. Usa yyyy-MM-dd");
                        return;
                    }
                }

                if (coincide)
                    eventosFiltrados.add(x);
            }

            modeloEventos.actualizarDatos(eventosFiltrados);
        });

        botonRestablecerT.addActionListener(e -> {
            modeloTareas.actualizarDatos(agenda.getListT());
        });

        botonRestablecerE.addActionListener(e -> {
            modeloEventos.actualizarDatos(agenda.getListE());
        });

        popupMenuTareas = new JPopupMenu();
        modificarTareaItem = new JMenuItem("Modificar tarea");
        eliminarTareaItem = new JMenuItem("Eliminar tarea");
        marcarPendienteItem = new JMenuItem("Marcar como no pendiente");

        popupMenuTareas.add(modificarTareaItem);
        popupMenuTareas.add(eliminarTareaItem);
        popupMenuTareas.add(marcarPendienteItem);

        popupMenuEventos = new JPopupMenu();
        modificarEventoItem = new JMenuItem("Modificar evento");
        eliminarEventoItem = new JMenuItem("Eliminar evento");

        popupMenuEventos.add(modificarEventoItem);
        popupMenuEventos.add(eliminarEventoItem);

        tablaTareas.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarPopupSiCorrespondeT(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarPopupSiCorrespondeT(e);
                }
            }

            private void mostrarPopupSiCorrespondeT(MouseEvent e) {
                int fila = tablaTareas.rowAtPoint(e.getPoint());
                if (fila >= 0 && fila < tablaTareas.getRowCount()) {
                    tablaTareas.setRowSelectionInterval(fila, fila);
                    Tarea tareaSeleccionada = modeloTareas.getTareaEn(fila);
                    if (tareaSeleccionada.getPending()) {
                        marcarPendienteItem.setText("Marcar como no pendiente");
                    } else {
                        marcarPendienteItem.setText("Marcar como pendiente");
                    }
                    popupMenuTareas.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        tablaEventos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarPopupSiCorrespondeE(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    mostrarPopupSiCorrespondeE(e);
                }
            }

            private void mostrarPopupSiCorrespondeE(MouseEvent e) {
                int fila = tablaEventos.rowAtPoint(e.getPoint());
                if (fila >= 0 && fila < tablaEventos.getRowCount()) {
                    tablaEventos.setRowSelectionInterval(fila, fila);
                    popupMenuEventos.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        eliminarTareaItem.addActionListener(e -> {
            int filaSeleccionada = tablaTareas.getSelectedRow();
            if (filaSeleccionada != -1) {
                Tarea tarea = modeloTareas.getTareaEn(filaSeleccionada);

                int confirm = JOptionPane.showConfirmDialog(ventana, "¿Seguro que quieres eliminar esta tarea?");
                if (confirm == JOptionPane.YES_OPTION) {
                    agenda.eliminateTarea(tarea);
                    actualizarTablaTareas();
                }
            }
        });

        eliminarEventoItem.addActionListener(e -> {
            int filaSeleccionada = tablaEventos.getSelectedRow();
            if (filaSeleccionada != -1) {
                Evento evento = modeloEventos.getEventoEn(filaSeleccionada);

                int confirm = JOptionPane.showConfirmDialog(ventana, "¿Seguro que quieres eliminar este evento?");
                if (confirm == JOptionPane.YES_OPTION) {
                    agenda.eliminateEvento(evento);
                    actualizarTablaEventos();
                }
            }
        });

        marcarPendienteItem.addActionListener(e -> {
            int filaSeleccionada = tablaTareas.getSelectedRow();
            if(filaSeleccionada != -1) {
                Tarea x = agenda.getListT().get(filaSeleccionada);
                x.setPending(!x.getPending());
                actualizarTablaTareas();
            }
        });

        modificarTareaItem.addActionListener(e -> {
            int filaSeleccionada = tablaTareas.getSelectedRow();
            if (filaSeleccionada != -1) {
                Tarea tarea = agenda.getListT().get(filaSeleccionada);

                JTextField campoTitulo = new JTextField(tarea.getTitle(), 15);
                JTextField campoDescripcion = new JTextField(tarea.getDescription(), 15);
                JTextField campoFecha = new JTextField(tarea.getLimitDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 15);

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JLabel("Título:"));
                panel.add(campoTitulo);
                panel.add(new JLabel("Descripción:"));
                panel.add(campoDescripcion);
                panel.add(new JLabel("Fecha Límite (yyyy-MM-dd HH:mm):"));
                panel.add(campoFecha);

                int resultado = JOptionPane.showConfirmDialog(ventana, panel, "Modificar Tarea", JOptionPane.OK_CANCEL_OPTION);

                if (resultado == JOptionPane.OK_OPTION) {
                    try {
                        tarea.setTitle(campoTitulo.getText());
                        tarea.setDescription(campoDescripcion.getText());
                        tarea.setLimitDate(LocalDateTime.parse(campoFecha.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                        actualizarTablaTareas();
                        JOptionPane.showMessageDialog(ventana, "Tarea modificada con éxito.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ventana, "Error al modificar la tarea: " + ex.getMessage());
                    }
                }
            }
        });

        modificarEventoItem.addActionListener(e -> {
            int filaSeleccionada = tablaEventos.getSelectedRow();
            if (filaSeleccionada != -1) {
                Evento evento = agenda.getListE().get(filaSeleccionada);

                JTextField campoTitulo = new JTextField(evento.getTitle(), 15);
                JTextField campoDescripcion = new JTextField(evento.getDescription(), 15);
                JTextField campoFecha = new JTextField(evento.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 15);

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.add(new JLabel("Título:"));
                panel.add(campoTitulo);
                panel.add(new JLabel("Descripción:"));
                panel.add(campoDescripcion);
                panel.add(new JLabel("Fecha (yyyy-MM-dd HH:mm):"));
                panel.add(campoFecha);

                int resultado = JOptionPane.showConfirmDialog(ventana, panel, "Modificar Evento", JOptionPane.OK_CANCEL_OPTION);

                if (resultado == JOptionPane.OK_OPTION) {
                    try {
                        evento.setTitle(campoTitulo.getText());
                        evento.setDescription(campoDescripcion.getText());
                        evento.setDate(LocalDateTime.parse(campoFecha.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                        actualizarTablaTareas();
                        JOptionPane.showMessageDialog(ventana, "Evento modificado con éxito.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ventana, "Error al modificar el evento: " + ex.getMessage());
                    }
                }
            }
        });


        agregarTarea = new JButton("Agregar Tarea");
        guardarTareas = new JButton("Guardar Tareas");
        cargarTareas = new JButton("Cargar Tareas");

        agregarEvento = new JButton("Agregar Evento");
        guardarEventos = new JButton("Guardar Eventos");
        cargarEventos = new JButton("Cargar Eventos");

        pestañas.addTab("Tareas", panelTareas);
        pestañas.addTab("Eventos", panelEventos);

        barraTareas.add(agregarTarea);
        barraTareas.add(guardarTareas);
        barraTareas.add(cargarTareas);

        barraEventos.add(agregarEvento);
        barraEventos.add(guardarEventos);
        barraEventos.add(cargarEventos);

        panelTareas.setLayout(new BorderLayout());
        panelEventos.setLayout(new BorderLayout());
        panelTareas.add(barraTareas, BorderLayout.NORTH);
        panelEventos.add(barraEventos, BorderLayout.NORTH);
        panelTareas.add(new JScrollPane(tablaTareas), BorderLayout.CENTER);
        panelEventos.add(new JScrollPane(tablaEventos), BorderLayout.CENTER);
        panelTareas.add(panelBusquedaT, BorderLayout.SOUTH);
        panelEventos.add(panelBusquedaE, BorderLayout.SOUTH);

        AgendaIO io = new AgendaIO();

        ventana.setTitle("Agenda");
        ventana.setSize(600,400);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    io.saveTareas(agenda, ARCHIVO_TAREAS);
                    io.saveEventos(agenda, ARCHIVO_EVENTOS);
                    System.out.println("Datos guardados automáticamente al salir.");
                } catch (IOException e) {
                    System.out.println("Error al guardar automáticamente: " + e.getMessage());
                }
            }
        });
        ventana.add(pestañas);
        ventana.setVisible(true);

        try{
            agenda.setListT(io.readTareas(ARCHIVO_TAREAS));
            actualizarTablaTareas();
        } catch(IOException e){
            System.out.println("No se pudo cargar tareas al iniciar: " + e.getMessage());
        }

        try{
            agenda.setListE(io.readEventos(ARCHIVO_EVENTOS));
            actualizarTablaEventos();
        } catch(IOException e){
            System.out.println("No se pudo cargar eventos al iniciar: " + e.getMessage());
        }

        iniciarVerificadorDeNotificaciones();

        guardarTareas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (agenda.getListT().isEmpty()) {
                    JOptionPane.showMessageDialog(ventana, "No hay tareas para guardar.");
                    return;
                }
                JFileChooser chooser = new JFileChooser();
                int resultado = chooser.showSaveDialog(ventana);

                if(resultado == JFileChooser.APPROVE_OPTION) {
                    String ruta = chooser.getSelectedFile().getAbsolutePath();
                    if (!ruta.toLowerCase().endsWith(".json")) {
                        ruta += ".json";
                    }
                    try{
                        io.saveTareas(agenda, ruta);
                    } catch (FileNotFoundException x){
                        JOptionPane.showMessageDialog(ventana, "Error al guardar el archivo: " + x.getMessage());
                    }
                    System.out.println("Archivo elegido para guardar: " +  ruta);
                }

                else{
                    System.out.println("Guardado cancelado.");
                }
            }
        });

        guardarEventos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                if (agenda.getListE().isEmpty()) {
                    JOptionPane.showMessageDialog(ventana, "No hay Eventos para guardar.");
                    return;
                }
                JFileChooser chooser = new JFileChooser();
                int resultado = chooser.showSaveDialog(ventana);

                if(resultado == JFileChooser.APPROVE_OPTION) {
                    String ruta = chooser.getSelectedFile().getAbsolutePath();
                    if (!ruta.toLowerCase().endsWith(".json")) {
                        ruta += ".json";
                    }
                    try{
                        io.saveEventos(agenda, ruta);
                    } catch (FileNotFoundException x){
                        JOptionPane.showMessageDialog(ventana, "Error al guardar el archivo: " + x.getMessage());
                    }
                    System.out.println("Archivo elegido para guardar: " +  ruta);
                }

                else{
                    System.out.println("Guardado cancelado.");
                }
            }
        });

        cargarTareas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser chooser = new JFileChooser();
                int resultado = chooser.showOpenDialog(ventana);

                if(resultado == JFileChooser.APPROVE_OPTION) {
                    String ruta = chooser.getSelectedFile().getAbsolutePath();
                    try{
                        agenda.setListT(io.readTareas(ruta));
                        actualizarTablaTareas();
                    } catch (IOException x){
                        JOptionPane.showMessageDialog(ventana, "Error al cargar el archivo: " + x.getMessage());
                    }
                    System.out.println("Archivo elegido para cargar: " +  ruta);
                }

                else{
                    System.out.println("Cargado cancelado.");
                }
            }
        });

        cargarEventos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JFileChooser chooser = new JFileChooser();
                int resultado = chooser.showOpenDialog(ventana);

                if(resultado == JFileChooser.APPROVE_OPTION) {
                    String ruta = chooser.getSelectedFile().getAbsolutePath();
                    try{
                        agenda.setListE(io.readEventos(ruta));
                        actualizarTablaEventos();
                    } catch (IOException x){
                        JOptionPane.showMessageDialog(ventana, "Error al cargar el archivo: " + x.getMessage());
                    }
                    System.out.println("Archivo elegido para cargar: " +  ruta);
                }

                else{
                    System.out.println("Cargado cancelado.");
                }
            }
        });

        agregarTarea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JTextField campoTitulo = new JTextField(15);
                JTextField campoDescripcion = new JTextField(15);
                JTextField campoFecha = new JTextField(15);

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                panel.add(new JLabel("Título:"));
                panel.add(campoTitulo);

                panel.add(new JLabel("Descripción:"));
                panel.add(campoDescripcion);

                panel.add(new JLabel("Fecha Límite (yyyy-MM-dd HH:mm):"));
                panel.add(campoFecha);

                int resultado = JOptionPane.showConfirmDialog(ventana, panel, "Agregar nueva Tarea", JOptionPane.OK_CANCEL_OPTION);
                if(resultado == JOptionPane.OK_OPTION){
                    String titulo = campoTitulo.getText();
                    String descripcion = campoDescripcion.getText();
                    String fecha = campoFecha.getText();

                    try {
                        LocalDateTime fechaLimite = LocalDateTime.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        Tarea nueva = new Tarea(titulo, descripcion, fechaLimite, true);
                        agenda.addTarea(nueva);
                        actualizarTablaTareas();
                        new Thread(() -> Notificador.mostrarNotificacion("Nueva Tarea", "Has agregado: " + titulo)).start();
                        JOptionPane.showMessageDialog(ventana, "Tarea agregada correctamente.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ventana, "Error al crear la tarea: " + ex.getMessage());
                    }
                }
            }
        });

        agregarEvento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                JTextField campoTitulo = new JTextField(15);
                JTextField campoDescripcion = new JTextField(15);
                JTextField campoFecha = new JTextField(15);

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                panel.add(new JLabel("Título:"));
                panel.add(campoTitulo);

                panel.add(new JLabel("Descripción:"));
                panel.add(campoDescripcion);

                panel.add(new JLabel("Fecha (yyyy-MM-dd HH:mm):"));
                panel.add(campoFecha);

                int resultado = JOptionPane.showConfirmDialog(ventana, panel, "Agregar nuevo Evento", JOptionPane.OK_CANCEL_OPTION);
                if(resultado == JOptionPane.OK_OPTION){
                    String titulo = campoTitulo.getText();
                    String descripcion = campoDescripcion.getText();
                    String f = campoFecha.getText();

                    try {
                        LocalDateTime fecha = LocalDateTime.parse(f, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                        Evento nueva = new Evento(titulo, descripcion, fecha);
                        agenda.addEvento(nueva);
                        actualizarTablaEventos();
                        new Thread(() -> Notificador.mostrarNotificacion("Nuevo Evento", "Has agregado: " + titulo)).start();
                        JOptionPane.showMessageDialog(ventana, "Evento agregado correctamente.");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(ventana, "Error al crear el Evento: " + ex.getMessage());
                    }
                }
            }
        });
    }
    private void actualizarTablaTareas() {
        modeloTareas.actualizarDatos(agenda.getListT());
    }

    private void actualizarTablaEventos() {
        modeloEventos.actualizarDatos(agenda.getListE());
    }

    private void iniciarVerificadorDeNotificaciones() {
        Thread verificador = new Thread(() -> {
            while (true) {
                try {
                    LocalDateTime ahora = LocalDateTime.now();

                    for (Tarea tarea : agenda.getListT()) {
                        if (tarea.getPending()) {
                            long days = java.time.Duration.between(ahora, tarea.getLimitDate()).toDays();
                            if (days >= 0 && days <= 5) {
                                Notificador.mostrarNotificacion("Tarea próxima", "Tarea: " + tarea.getTitle() + " vence pronto.");
                            }
                        }
                    }

                    for (Evento evento : agenda.getListE()) {
                        long days = java.time.Duration.between(ahora, evento.getDate()).toDays();
                        if (days >= 0 && days <= 5) {
                            Notificador.mostrarNotificacion("Evento próximo", "Evento: " + evento.getTitle() + " es en breve.");
                        }
                    }

                    Thread.sleep(172800000); // Espera 2 dias antes de volver a comprobar
                } catch (Exception e) {
                    System.out.println("Error en el verificador de notificaciones: " + e.getMessage());
                }
            }
        });

        verificador.setDaemon(true);
        verificador.start();
    }


}
