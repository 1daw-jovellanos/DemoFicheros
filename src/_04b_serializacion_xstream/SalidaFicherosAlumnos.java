package _04b_serializacion_xstream;

import com.thoughtworks.xstream.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * En este ejemplo, se guardan una colección entera en un stream de texto XML.
 * El XML es un formato de texto como CSV, pero a diferencia de éste:
 *  -Tiene un estándar respaldado por el W3C y otras organizaciones
 *  -Está basado en marcas, en lugar de posiciones.
 * 
 * En esta ocasión, vamos a utilizar la librería de terceros XStream.
 */
public class SalidaFicherosAlumnos {

    static List<Alumno> alumnos;

    static {
        alumnos = new ArrayList<>();
        alumnos.add(new Alumno("Pepe", 5.23));
        alumnos.add(new Alumno("Lucas", 6.60));
        alumnos.add(new Alumno("Carmela", 8.80));
        alumnos.add(new Alumno("Lucrecia", 4.43));
        alumnos.add(new Alumno("Marco Aurelio Encarnación de todos los Santos", 2.23));
        alumnos.add(new Alumno("Margarita María", 4.2));
    }

    /**
     * Serializar la lista entera utilizando XStream
     */
    public void generarXMLStream() {
        XStream xstream = new XStream();
        xstream.alias("alumno", Alumno.class); // Escoger la etiqueta que representa a un alumno
        try (PrintWriter fOut = new PrintWriter("alumnos.xml")) {
            xstream.toXML(alumnos, fOut);
        } catch (IOException ex) {
            System.err.println("No se pudo escribir fichero");
        }  
    }

    /**
     * Para obtener los datos del fichero, leemos la colección entera.
     * 
     * Es necesario hacer un typecast, que puede generar una
     * ClassCastException
     */
    public void leerUnRegistro() {
        Scanner in = new Scanner(System.in);
                System.out.println("Vamos a leer los datos de un alumno saltando "
                + "recuperando entera la colección\n");
        try (FileReader fIn = new FileReader("alumnos.xml")) {
            XStream xstream = new XStream();
            xstream.alias("alumno", Alumno.class);
            alumnos = (List<Alumno>)xstream.fromXML(fIn);
        } catch (Exception ex) {
            System.err.println("No se pudo leer");
            ex.printStackTrace();
        } 
        
        System.out.print("Numero de registro(desde 0): ");
        int numRegistro = Integer.parseInt(in.nextLine());
        Alumno a = alumnos.get(numRegistro);
        System.out.format("Nombre: %s - Nota: %.2f%n", a.getNombre(), a.getNota());
    }

    public void run() {
        generarXMLStream();
        alumnos = null; // Eliminamos la coleccion para continuar con
        //la demo
        leerUnRegistro();
    }

    public static void main(String[] args) {
        new SalidaFicherosAlumnos().run();
    }

}
