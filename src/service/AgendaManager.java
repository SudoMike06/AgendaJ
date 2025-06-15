package service;
import java.util.ArrayList;
import model.Tarea;
import model.Evento;

public class AgendaManager {
    private ArrayList<Tarea> Works;
    private ArrayList<Evento> Events;

    public AgendaManager(){
        Works = new ArrayList<Tarea>();
        Events = new ArrayList<Evento>();
    }

    public AgendaManager(ArrayList<Tarea> t, ArrayList<Evento> e){
        Works = new ArrayList<Tarea>(t);
        Events = new ArrayList<Evento>(e);
    }


    public ArrayList<Tarea> getListT(){
        return Works;
    }

    public ArrayList<Evento> getListE(){
        return Events;
    }

    public void setListT(ArrayList<Tarea> x){
        Works = new ArrayList<Tarea>(x);
    }

    public void setListE(ArrayList<Evento> x){
        Events = new ArrayList<Evento>(x);
    }

    public void addTarea(Tarea x){
        for(int i = 0;i < Works.size();i++){
            if(x.getLimitDate().isBefore(Works.get(i).getLimitDate())){
                Works.add(i,x);
                return;
            }
        }
        Works.add(x);
    }

    public void addEvento(Evento x){
        for(int i = 0;i < Events.size();i++){
            if(x.getDate().isBefore(Events.get(i).getDate())){
                Events.add(i,x);
                return;
            }
        }
        Events.add(x);
    }

    public long sizeTareas(){
        return Works.size();
    }

    public long sizeEventos(){
        return Events.size();
    }

    public void showTareas(){
        for(int i = 0;i<Works.size();i++){
            System.out.println((i+1) + ". " + Works.get(i).toString());
        }
        System.out.println();
    }

    public void showEventos(){
        for(int i = 0;i<Events.size();i++){
            System.out.println((i+1) + ". " + Events.get(i).toString());
        }
        System.out.println();
    }

    public void eliminateTarea(Tarea x) {
        for(int i = 0;i<Works.size();i++){
            if(Works.get(i).getLimitDate().isAfter(x.getLimitDate())){
                System.out.println("No se ha podido encontar la tarea a eliminar.");
                return;
            }
            if(Works.get(i).equals(x)){
                Works.remove(i);
                System.out.println("Tarea eliminada correctamente.");
                return;
            }
        }
    }

    public void eliminateEvento(Evento x) {
        for(int i = 0;i<Events.size();i++){
            if(Events.get(i).getDate().isAfter(x.getDate())){
                System.out.println("No se ha podido encontar el evento a eliminar.");
                return;
            }
            if(Events.get(i).equals(x)){
                Events.remove(i);
                System.out.println("Evento eliminada correctamente.");
                return;
            }
        }
    }

    public String modificateTarea(Tarea nuevo, Tarea old){
        for(int i = 0;i<Works.size();i++){
            if(Works.get(i).equals(old)){
                Works.set(i, nuevo);
                return "Tarea modificada correctamente.";
            }
        }
        return "No se ha podido encontrar la tarea a modificar.";
    }

    public String modificateEvento(Evento nuevo, Evento old){
        for(int i = 0;i<Events.size();i++){
            if(Events.get(i).equals(old)){
                Events.set(i, nuevo);
                return "Evento modificado correctamente.";
            }
        }
        return "No se ha podido encontrar el evento a modificar.";
    }

    public Tarea searchTareaTitle(String x){
        for(int i = 0;i<Works.size();i++){
            if(Works.get(i).getTitle().equals(x)){
                return Works.get(i);
            }
        }
        return null;
    }

    public Evento searchEventoTitle(String x){
        for(int i = 0;i<Events.size();i++){
            if(Events.get(i).getTitle().equals(x)){
                return Events.get(i);
            }
        }
        return null;
    }
}