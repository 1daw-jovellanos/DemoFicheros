package _04a_serializacion_nativa;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 * En este ejemplo, se guardan una colección entera en un stream de objetos
 * 
 * En un stream de objetos, el intérprete es capaz de guardar cualquier objeto
 * en fichero en un formato binario, sustituyendo las referencias en memoria principal por
 * referencias en memoria secundaria.
 * 
 * A este mecanismo se le denomina <strong>Serialización</strong>.
 * Para serializar es necesario que las clases que se van a guardar implementen la
 * interface java.io.Serializable.
 * Esa interface está vacía de métodos. No comporta ninguna obligación.
 */
public class DemoSerializaciónNativa {

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
     * Serialización de la lista entera.
     */
    public void generarOutputStream() {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("alumnos.ser"));
            oos.writeObject(alumnos);
        } catch (IOException ex) {
            System.err.println("No se pudo grabar el fichero");
            ex.printStackTrace();
        } finally {
            // El cierre es un poco peculiar, porque puede lanzar IOException
            try {
                oos.close();
            } catch (Exception ex) {
            };

        }

    }

    /**
     * Para obtener los datos del fichero, leemos la colección entera.
     * 
     * Es necesario hacer un typecast, que puede generar una
     * ClassCastException
     */
    @SuppressWarnings("unchecked")
    public void leerUnRegistro() {
        Scanner in = new Scanner(System.in);
        ObjectInputStream ois = null;
        System.out.println("Vamos a leer los datos de un alumno saltando "
                + "recuperando entera la colección\n");
        try {
            ois = new ObjectInputStream(new FileInputStream("alumnos.ser"));
            alumnos = (List<Alumno>)ois.readObject();

        } catch (Exception ex) {
            System.err.println("No se pudo leer");
            ex.printStackTrace();
        } finally {
            // El cierre es un poco peculiar, porque puede lanzar IOException
            try {
                ois.close();
            } catch (Exception ex) {
            };
        }
        
        System.out.print("Numero de registro(desde 0): ");
        int numRegistro = Integer.parseInt(in.nextLine());
        Alumno a = alumnos.get(numRegistro);
        System.out.format("Nombre: %s - Nota: %.2f%n", a.getNombre(), a.getNota());
    }

    public void run() {
        generarOutputStream();
        alumnos = null; // Eliminamos la coleccion para continuar con
        //la demo
        leerUnRegistro();
    }

    public static void main(String[] args) {
        new DemoSerializaciónNativa().run();
    }

}
