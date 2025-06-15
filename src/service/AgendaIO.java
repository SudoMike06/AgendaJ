package service;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.GsonBuilder;
import model.Tarea;
import model.Evento;
import java.util.ArrayList;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

public class AgendaIO {
    private static final Gson gs = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();;

    public void saveAgenda(AgendaManager actual, String file) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(file)) {
            gs.toJson(actual, pw);
        }
    }

    public void saveTareas(AgendaManager actual, String file) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(file)) {
            gs.toJson(actual.getListT(), pw);
        }
    }

    public void saveEventos(AgendaManager actual, String file) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(file)) {
            gs.toJson(actual.getListE(), pw);
        }
    }

    public AgendaManager readAgenda(String file) throws FileNotFoundException, IOException{
        try (FileReader fl = new FileReader(file)) {
            AgendaManager fin = gs.fromJson(fl, AgendaManager.class);
            return fin != null ? fin : new AgendaManager();
        }
    }

    public ArrayList<Tarea> readTareas(String file) throws FileNotFoundException, IOException{
        try (FileReader fl = new FileReader(file)) {
            Type tareaListType = new TypeToken<ArrayList<Tarea>>() {}.getType();
            ArrayList<Tarea> res = gs.fromJson(fl, tareaListType);
            return res != null ? res : new ArrayList<>();
        }
    }

    public ArrayList<Evento> readEventos(String file) throws FileNotFoundException, IOException{
        try (FileReader fl = new FileReader(file)) {
            Type eventoListType = new TypeToken<ArrayList<Evento>>() {}.getType();
            ArrayList<Evento> res = gs.fromJson(fl, eventoListType);
            return res != null ? res : new ArrayList<>();
        }
    }
}
