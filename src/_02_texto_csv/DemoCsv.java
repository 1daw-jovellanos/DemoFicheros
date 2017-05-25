package _02_texto_csv;

import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
/**
 * En este ejemplos se guardan los datos de un array de objetos de tipo
 * Alumno en un formato de texto posicional: el CSV.
 */
public class DemoCsv {

    static final Alumno[] alumnos;

    static {
        alumnos = new Alumno[]{
            new Alumno("Pepe", 5.23),
            new Alumno("Lucas", 6.60),
            new Alumno("Carmela", 8.80),
            new Alumno("Lucrecia", 4.43),
            new Alumno("Marco Aurelio Encarnación de todos los Santos", 2.23),
            new Alumno("Margarita María", 4.2)
        };
    }

    /**
     * Para guardar los datos como texto necesitamos una estrategia que nos
     * permita luego leer el fichero y reconstruir los datos originales. Una de
     * las más sencillas es CSV
     * (https://es.wikipedia.org/wiki/Valores_separados_por_comas)
     * 
     * Ojo: Si el carácter coma (,) formase parte de las cadenas a grabar habría
     * que programar una estrategia de escapado/desescapado.
     *
     */
    public void generarTexto() {
        PrintWriter fOut = null;
        try {
            fOut = new PrintWriter("alumnos.csv");
            for (Alumno a : alumnos) {
                // OJO: Doubles en cultura neutra, o saldrán con una coma e
                // interferirán con la coma del CSV
                fOut.format(Locale.ROOT, "%s, %.2f%n", a.getNombre(), a.getNota());
                System.out.format("Escribiendo Alumno: %s, %.2f%n", a.getNombre(), a.getNota());

            }
        } catch (IOException ex) {
            System.err.println("No se pudo grabar el fichero");
            ex.printStackTrace();
        } finally {
            if (fOut != null) {
                fOut.close();
            }
        }

    }

    /**
     * Lee un dato del fichero. Al ser secuenciales, para leer un dato del
     * fichero no hay más remedio que leer todos los anteriores.
     */
    public void leerUnRegistro() {
        Scanner in = new Scanner(System.in);
        Scanner fIn = null;
        System.out.println("Vamos obtener un alumno por su posición, leyendo e "
                + " ignorando las líneas anteriores \n");
        try {
            fIn = new Scanner(new BufferedReader(new FileReader("alumnos.csv")));
            System.out.print("Numero de registro(desde 0): ");
            int numRegistro = Integer.parseInt(in.nextLine());
            // Leemos e ignoramos las líneas.
            for (int i = 0; i < numRegistro; i++) {
                fIn.nextLine();
            }
            String linea = fIn.nextLine();
            Scanner tokenizer = new Scanner(linea)
                    .useLocale(Locale.ROOT)
                    .useDelimiter("\\s*,\\s*"); // Regex: 0 o más espacios, coma y 0 o más espacios
            String nombre = tokenizer.next();
            double nota = tokenizer.nextDouble(); // leer la nota
            Alumno a = new Alumno(nombre, nota); // Construir el objeto de tipo alumno
            System.out.format("Nombre: %s - Nota: %.2f%n", a.getNombre(), a.getNota());

        } catch (Exception ex) {
            System.err.println("No se pudo leer");
            ex.printStackTrace();
        } finally {
            // El cierre es un poco peculiar, porque puede lanzar IOException
            try {
                fIn.close();
            } catch (Exception ex) {
            };
        }

    }

    public void run() {
        generarTexto();
        leerUnRegistro();
    }

    public static void main(String[] args) {
        new DemoCsv().run();
    }

}
