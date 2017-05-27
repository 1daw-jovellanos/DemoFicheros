package _03a_binario_secuencial;

import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * En este ejemplo, se guardan los datos en un fomato binario de acceso
 * secuencial.
 *
 * Básicamente, los datos van uno detrás de otro en formato binario. Los
 * primitivos tienen un tamaño fijo, y las cadenas no, pero se guarda su tamaño
 * en binario sin signo con 2 bytes antes de los bytes que representan el texto.
 *
 * Se parece mucho al CSV, y tiene muchas de sus características, solo que es
 * binario: no es facil manipularlo por un humano, y no necesita
 * escapado/desescapado.
 *
 */
public class DemoBinarioSecuencial {

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
     * Se guardan los datos de una serie de objetos de la clase alumno,
     */
    public void generarBinarioSecuencial() {
        DataOutputStream fOut = null;
        try {
            fOut = new DataOutputStream(new FileOutputStream("alumnos.seq.bin"));
            for (Alumno a : alumnos) {
                fOut.writeUTF(a.getNombre());
                fOut.writeDouble(a.getNota()); // 8 bytes
                System.out.format("Escribiendo Alumno: %s, %.2f%n", a.getNombre(), a.getNota());
            }
        } catch (IOException ex) {
            System.err.println("No se pudo grabar el fichero");
            ex.printStackTrace();
        } finally {
            // El cierre es un poco peculiar, porque puede lanzar IOException
            try {
                fOut.close();
            } catch (Exception ex) {
            };
        }

    }

    /**
     * Lee un alumno del fichero. Como es un acceso secuencial es necesario
     * recorrer todos los anteriores
     *
     */
    public void leerUnRegistro() {
        Scanner in = new Scanner(System.in);
        DataInputStream fIn = null;
        System.out.println("Vamos a leer los datos de un alumno saltando "
                + "directamente a su posición\n");
        try {
            fIn = new DataInputStream(new FileInputStream("alumnos.seq.bin"));
            System.out.print("Numero de registro(desde 0): ");
            int numRegistro = Integer.parseInt(in.nextLine());
            // leer e ignorar datos al principio del fichero.
            for (int i = 0; i < numRegistro - 1; i++) {
                fIn.readUTF(); // lee el nombre y no hace nada con él
                fIn.readDouble(); // lee la nota y no hace nada con ella
            }
            String nombre = fIn.readUTF(); // leer nombre que nos interesa
            double nota = fIn.readDouble(); // leer la nota
            Alumno a = new Alumno(nombre, nota); // Construir el objeto de tipo alumno
            System.out.format("Nombre: %s - Nota: %.2f%n", a.getNombre(), a.getNota());

        } catch (IOException ex) {
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
        generarBinarioSecuencial();
        leerUnRegistro();
    }

    public static void main(String[] args) {
        new DemoBinarioSecuencial().run();
    }

}
