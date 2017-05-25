package _01_diferencia_texto_bin;



import java.io.*;
/**
 * Esto es un ejemplo de la diferencia entre un fichero de texto y uno binario.
 *
 */
public class DemoTextoBinario {

    static final int[] numeros;
    
    static {
        numeros = new int[]{100, 2123, 55, 199, 22};
    }
    
  /**
   * Este método genera un fichero de texto, en el que se guardan 5 números.
   * Se debe seguir una estrategia para guardarlos.
   * En este caso, cada número en una línea. Podríamos haber optado por otra
   * estrategia... por ejemplo, todos los números en una línea separados por espacios.
   * 
   * La estrategia de la lectura nos condiciona siempre la estrategia para la lectura.
   * Los ficheros de texto son secuenciales. Para leer un dato es necesario
   * recorrer los anteriores.
   * 
   * El PrintWriter tiene los métodos print, println, format (y printf) 
   */
    public void generarTexto() {
        
        PrintWriter fOut = null;
        try {
            fOut = new PrintWriter("numeros.txt");
            for (int i: numeros) {
                fOut.println(i);
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
     * Este método guarda los números en binario.
     * El DataOutputStream tiene métodos para grabar datos de los tipos
     * y también de tipo String. Guarda en el fichero una representación en
     * binario prácticamente idéntica a la representación en memoria principal
    */
     public void generarBinario() {
        DataOutputStream fOut = null;
        try {
            fOut =  new DataOutputStream(new FileOutputStream("numeros.bin"));
            for (int i: numeros) {
                fOut.writeInt(i);
            }
        } catch (IOException ex) {
            System.err.println("No se pudo grabar el fichero");
            ex.printStackTrace();
        } finally {
           try {fOut.close();} catch (Exception ex) {};
        }
                
    }
       
    public void run() {
        generarTexto();
        generarBinario();
    }
    
    public static void main(String[] args) {
        new DemoTextoBinario().run();
    }
    
}
