import java.util.Scanner;
import model.Tarea;
import model.Evento;
import service.AgendaManager;
import service.AgendaIO;
import java.time.LocalDateTime;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.io.IOException;

public class MainConsole {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        AgendaManager actual = new AgendaManager();

        mostrarMenu();

        int mov = 0;
        try{
            mov = teclado.nextInt();
            if(mov < 1 || mov > 9) throw new InputMismatchException();
        } catch(InputMismatchException e){
            System.out.println("Error, número no válido.");
            teclado.nextLine();
            return;
        }

        while(mov != 9){
            teclado.nextLine();

            if (mov == 1){
                Tarea nueva = lecturaTarea(teclado, actual);
                if(nueva == null){
                    System.out.println("Inténtelo de nuevo");
                    continue;
                }
                actual.addTarea(nueva);
                actual.showTareas();
            }

            else if (mov == 2) {
                Evento nuevo = lecturaEvento(teclado, actual);
                if(nuevo == null){
                    System.out.println("Inténtelo de nuevo");
                    continue;
                }
                actual.addEvento(nuevo);
                actual.showEventos();
            }

            else if (mov == 3) {
                if(actual.sizeTareas() == 0){
                    System.out.println("No hay tareas existentes.\n");
                }
                else{
                    System.out.println("Tarea actuales: ");
                    actual.showTareas();
                    System.out.println("Que tarea quieres eliminar? ");

                    int num = teclado.nextInt();
                    if(num < 1 || num > actual.sizeTareas()){
                        System.out.println("No existe tal tarea, inténtelo de nuevo.");
                        continue;
                    }
                    actual.getListT().remove(num-1);
                    System.out.println("Eliminado con éxito.");
                }
            }

            else if (mov == 4) {
                if(actual.sizeEventos() == 0){
                    System.out.println("No hay eventos existentes.\n");
                }
                else{
                    System.out.println("Eventos actuales: ");
                    actual.showEventos();
                    System.out.println("Que Evento quieres eliminar? ");

                    int num = teclado.nextInt();

                    if(num > actual.sizeEventos()){
                        System.out.println("No existe tal evento, inténtelo de nuevo.");
                        continue;
                    }
                    actual.getListE().remove(num-1);
                    System.out.println("Eliminado con éxito.");
                }
            }
            else if(mov == 5){
                System.out.println("Que quieres guardar? ");
                System.out.println("1. Agenda");
                System.out.println("2. Tareas");
                System.out.println("3. Eventos");

                int num = teclado.nextInt();
                teclado.nextLine();

                System.out.println("Donde quieres guardarlo (archivo json)? ");
                String file = teclado.nextLine();

                AgendaIO aux = new AgendaIO();
                if(num == 1){
                    try{
                        aux.saveAgenda(actual, file);
                        System.out.println("Se ha guardado con éxito.");
                    } catch (FileNotFoundException e){
                        System.out.println("No se ha encontrado el archivo.");
                    }
                }

                else if(num == 2){
                    try{
                        aux.saveTareas(actual, file);
                        System.out.println("Se ha guardado con éxito.");
                    } catch (FileNotFoundException e){
                        System.out.println("No se ha encontrado el archivo.");
                    }
                }

                else if(num == 3){
                    try{
                        aux.saveEventos(actual, file);
                        System.out.println("Se ha guardado con éxito.");
                    } catch (FileNotFoundException e){
                        System.out.println("No se ha encontrado el archivo.");
                    }
                }
            }

            else if(mov == 6){
                System.out.println("Que quieres cargar? ");
                System.out.println("1. Agenda");
                System.out.println("2. Tareas");
                System.out.println("3. Eventos");

                int num = teclado.nextInt();
                teclado.nextLine();

                System.out.println("De que archivo lo quieres cargar (archivo json)? ");
                String file = teclado.nextLine();

                AgendaIO aux = new AgendaIO();
                if(num == 1){
                    try{
                        actual = aux.readAgenda(file);
                        System.out.println("Se ha cargado con éxito.");
                    } catch (FileNotFoundException e){
                        System.out.println("No se ha encontrado el archivo.");
                    } catch (IOException e){
                        System.out.println("Hubo un error al cerrar el archivo, disculpe las molestias.");
                    }
                }

                else if(num == 2){
                    try{
                        actual.setListT(aux.readTareas(file));
                        System.out.println("Se ha cargado con éxito.");
                    } catch (FileNotFoundException e){
                        System.out.println("No se ha encontrado el archivo.");
                    } catch (IOException e){
                        System.out.println("Hubo un error al cerrar el archivo, disculpe las molestias.");
                    }
                }

                else if(num == 3){
                    try{
                        actual.setListE(aux.readEventos(file));
                        System.out.println("Se ha cargado con éxito.");
                    } catch (FileNotFoundException e){
                        System.out.println("No se ha encontrado el archivo.");
                    } catch (IOException e){
                        System.out.println("Hubo un error al cerrar el archivo, disculpe las molestias.");
                    }
                }

                else if(num == 7) actual.showTareas();
                else if(num == 8) actual.showEventos();
            }

            mostrarMenu();

            try{
                mov = teclado.nextInt();
                if(mov < 1 || mov > 9) throw new InputMismatchException();
            } catch(InputMismatchException e){
                System.out.println("Error, número no válido.");
                teclado.nextLine();
                return;
            }
        }
    }

    public static Tarea lecturaTarea(Scanner teclado, AgendaManager actual){
        System.out.println("Escribe el título de la tarea:");
        String titulo = teclado.nextLine();

        System.out.println("Escribe una descripción de la tarea:");
        String des = teclado.nextLine();

        System.out.println("Escribe el minuto límite de la tarea:");
        int minuto = teclado.nextInt();

        System.out.println("Escribe la hora límite de la tarea:");
        int hora = teclado.nextInt();

        System.out.println("Escribe el día límite de la tarea:");
        int dia = teclado.nextInt();

        System.out.println("Escribe el mes límite de la tarea:");
        int mes = teclado.nextInt();

        System.out.println("Escribe el año límite de la tarea:");
        int año = teclado.nextInt();

        LocalDateTime x = null;

        try{
            x = LocalDateTime.of(año,mes,dia,hora,minuto);
        } catch(java.time.DateTimeException e){
            System.out.println("Fecha introducida no válida");
            return null;
        }

        Tarea nueva = new Tarea(titulo, des, x, true);
        return nueva;
    }

    public static Evento lecturaEvento(Scanner teclado, AgendaManager actual){
        System.out.println("Escribe el título del evento:");
        String titulo = teclado.nextLine();

        System.out.println("Escribe una descripción del evento:");
        String des = teclado.nextLine();

        System.out.println("Escribe el minuto de ejecución del evento:");
        int minuto = teclado.nextInt();

        System.out.println("Escribe la hora de ejecución del evento:");
        int hora = teclado.nextInt();

        System.out.println("Escribe el día de ejecución del evento:");
        int dia = teclado.nextInt();

        System.out.println("Escribe el mes de ejecución del evento:");
        int mes = teclado.nextInt();

        System.out.println("Escribe el año de ejecución del evento:");
        int año = teclado.nextInt();

        LocalDateTime x = null;
        try{
            x = LocalDateTime.of(año,mes,dia,hora,minuto);
        } catch(java.time.DateTimeException e){
            System.out.println("Fecha introducida no válida");
            return null;
        }

        Evento nueva = new Evento(titulo, des, x);
        return nueva;
    }

    public static void mostrarMenu(){
        System.out.println("¿Qué quieres hacer?");
        System.out.println("1. Añadir una nueva Tarea.");
        System.out.println("2. Añadir un nuevo Evento.");
        System.out.println("3. Eliminar una Tarea.");
        System.out.println("4. Eliminar un Evento.");
        System.out.println("5. Guardar");
        System.out.println("6. Cargar");
        System.out.println("7. Ver Tareas");
        System.out.println("8. Ver Eventos");
        System.out.println("9. Salir");
    }

}

