package model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class Evento {
    private String title;
    private String description;
    private LocalDateTime date;

    public Evento(){
        title = "";
        description = "";
        date = null;
    }

    public Evento(String t, String de, LocalDateTime da){
        title = t;
        description = de;
        date = da;
    }

    public void setTitle(String mod){
        title = mod;
    }

    public void setDescription(String mod){
        description = mod;
    }

    public void setDate(LocalDateTime mod){
        date = mod;
    }

    public String getTitle(){
        return title;
    }

    public String getDescription(){
        return description;
    }

    public LocalDateTime getDate(){
        return date;
    }

    public boolean equals(Evento x){
        return x.title.equals(this.title) &&
                x.description.equals(this.description) &&
                x.date.equals(this.date);
    }

    public String toString() {
        return title + " - " + "[" + description + "] " + date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }


}
