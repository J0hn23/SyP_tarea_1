/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license


 */
package propio.evaluadorerrores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonathan M.Ortega
 */
public class AnalitzadorPrincipal {

    public static void main(String[] args) {

        try {
            ProcessBuilder pb = new ProcessBuilder("java",
                    "-cp",
                    System.getProperty("java.class.path"),
                    "propio.evaluadorerrores.FiltreLog");
            //pb.inheritIO();  esta linea permite que la salida del hijo salga por la terminal del padre
            Process proceso = pb.start();

            BufferedReader archivo = new BufferedReader(
                    new FileReader("C:\\Users\\John\\Documents\\NetBeansProjects\\EvaluadorErrores\\Datos\\logs.txt"));//Abro el archivo para leerlo
            //Lo mando al hijo FilterLog escribiendolo en él bufer del hijo
            PrintWriter escritor = new PrintWriter(proceso.getOutputStream());// con el getOutputStream se lo voy mandando a la entrada del hijo

            Thread hiloErorres;
            hiloErorres = new Thread(() -> {

                try {
                    BufferedReader lectorErrores = new BufferedReader(
                new InputStreamReader(proceso.getErrorStream())
        );
                    String linea;
                    while ((linea = lectorErrores.readLine()) != null) {
                        System.err.println(linea);
                    }
                } catch (IOException ex) {
                    System.out.println("error de lectura bufer de errores");
                }
            });
            Thread hiloWarning;
            hiloWarning = new Thread(() -> {
                try {
                    BufferedReader lectorWarnings = new BufferedReader(
                new InputStreamReader(proceso.getInputStream())
        );
                    String linea;
                    while ((linea = lectorWarnings.readLine()) != null) {
                        System.out.println(linea);
                    }
                } catch (IOException ex) {
                    System.out.println("error de lectura buffer normal");
                }
            });
            
            hiloErorres.start();
            hiloWarning.start();
            
            String linea;

            while ((linea = archivo.readLine()) != null) {
            escritor.println(linea);
            }
            
            escritor.close();
            archivo.close();
            
            hiloErorres.join();
            hiloWarning.join();

            int codigoResultado = proceso.waitFor();
        
            System.out.println(codigoResultado);
        } catch (IOException ex) {
            System.out.println("Error de inicio del proceso");
            System.out.println("Error:" + ex.getMessage());
        } catch (InterruptedException ex) {
            Logger.getLogger(AnalitzadorPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
