package _03_binario_aleatorio;

import java.io.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * En este ejemplo, se guardan los datos en un fomato binario
 * de acceso aleatorio.
 * 
 * Es necesario que los datos de cada objeto ocupen en el fichero exactamente
 * la misma cantidad de bytes cada uno. Eso se logra principalmente manipulando 
 * las cadenas. Escogiendo una longitud máxima para cada una de ellas y guardando
 * un buffer de bytes con los carácteres de la cadena, en lugar de la representación
 * en binario del String en memoria.
 * 
 * Un fichero de acceso aleatorio permite tanto lecturas como escrituras, y además
 * también permite saltos: ir directamente a una determinada posición dentro del fichero
 * (con el método seek)
 * 
 * Las técnicas de acceso aleatorio dieron lugar a los Sistemas Gestores de Bases
 * de Datos Relacionales (SGBD-R) y, en general, preferimos utilizar éstos a la
 * manipulación directa de ficheros.
 * 
 */
public class DemoBinarioAleatorio {

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
     * ocupando cada uno 28 bytes exactos en disco.
     * Se representan los nombres con 20 bytes.
     */
    public void generarBinarioRandom() {
        RandomAccessFile fOut = null;
        try {
            fOut = new RandomAccessFile("alumnos.rnd.bin", "rw");
            fOut.setLength(0);
            for (Alumno a : alumnos) {
                // Representar el nombre con 20 bytes
                byte[] buff = Arrays.copyOfRange(a.getNombre().getBytes(), 0, 20);
                fOut.write(buff); // Se escribe el buffer de 20 bytes
                fOut.writeDouble(a.getNota()); // 8 bytes
                System.out.format("Escribiendo Alumno: %s, %.2f%n", a.getNombre(), a.getNota());
            }
        } catch (IOException ex) {
            System.err.println("No se pudo grabar el fichero");
            ex.printStackTrace();
        } finally {
             // El cierre es un poco peculiar, porque puede lanzar IOException
            try {fOut.close();} catch (Exception ex) {};
    
        }

    }

    /**
     * Lee un dato del fichero.
     * La técnica de acceso aleatorio nos permite ir directamente a una posición
    */
    public void leerUnRegistro() {
        Scanner in = new Scanner(System.in);
        RandomAccessFile fIn = null;
        System.out.println("Vamos a leer los datos de un alumno saltando "
                + "directamente a su posición\n");
        try {
            fIn = new RandomAccessFile("alumnos.rnd.bin", "rw");
            System.out.print("Numero de registro(desde 0): ");
            int numRegistro = Integer.parseInt(in.nextLine());
            // Saltamos a la posición correspondiente
            fIn.seek(numRegistro * 28); // Cada registro ocupa 28 bytes
            System.out.format("(saltando al byte %d (%d * 28)%n", numRegistro * 28, numRegistro);
            byte[] buff = new byte[20];
            fIn.read(buff); // leer el nombre. 20 bytes
            String nombre = new String(buff); // Generar cadena a partir de los bytes
            double nota = fIn.readDouble(); // leer la nota
            Alumno a = new Alumno(nombre, nota); // Construir el objeto de tipo alumno
            System.out.format("Nombre: %s - Nota: %.2f%n", a.getNombre(), a.getNota());

        } catch (IOException ex) {
            System.err.println("No se pudo leer");
            ex.printStackTrace();
        } finally {
            // El cierre es un poco peculiar, porque puede lanzar IOException
            try {fIn.close();} catch (Exception ex) {};
        }

    }

    public void run() {
        generarBinarioRandom();
        leerUnRegistro();
    }

    public static void main(String[] args) {
        new DemoBinarioAleatorio().run();
    }

}
