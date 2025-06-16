package gui;

import java.awt.*;

public class Notificador {
    public static void mostrarNotificacion(String titulo, String mensaje){
        if(!SystemTray.isSupported()) {
            System.out.println("No hay soporte para notificaciones");
            return;
        }

        SystemTray tray = SystemTray.getSystemTray();
        Image imagen = Toolkit.getDefaultToolkit().createImage("C:\\Users\\Miquel\\IdeaProjects\\AgendaInteligente\\AgendaIcono.ico");


        TrayIcon icono = new TrayIcon(imagen, "Agenda");
        icono.setImageAutoSize(true);
        icono.setToolTip("Notificación de agenda");

        try{
            tray.add(icono);
            icono.displayMessage(titulo, mensaje, TrayIcon.MessageType.INFO);
            Thread.sleep(4000);
            tray.remove(icono);
        } catch (Exception e){
            System.out.println("Error con el tray: " + e.getMessage());
        }
    }
}
