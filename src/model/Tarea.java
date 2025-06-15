package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class Tarea {
    private String title;
    private String description;
    private LocalDateTime limitDate;
    private boolean pending;

    public Tarea(){
        title = "";
        description = "";
        limitDate = null;
        pending = false;
    }

    public Tarea(String t, String de, LocalDateTime limit, boolean p){
        title = t;
        description = de;
        limitDate = limit;
        pending = p;
    }

    public void setTitle(String mod){
        title = mod;
    }

    public void setDescription(String mod){
        description = mod;
    }

    public void setLimitDate(LocalDateTime mod){
        limitDate = mod;
    }

    public void setPending(boolean mod){
        pending = mod;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public LocalDateTime getLimitDate(){
        return limitDate;
    }

    public boolean getPending(){
        return pending;
    }

    public boolean equals(Tarea x){
        return x.title.equals(this.title) &&
                x.description.equals(this.description) &&
                x.limitDate.equals(this.limitDate) &&
                x.pending == this.pending;
    }

    public String toString(){
        return title + " - " + "[" + description + "] " + limitDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + (pending ? " (Pendiente)" : " (Completada)");
    }
}
