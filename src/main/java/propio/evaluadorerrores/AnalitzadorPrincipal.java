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
            
            BufferedReader respuestaHijo = new BufferedReader(new InputStreamReader(proceso.getInputStream()));//Preparo un BR para leer el bufer de la respuesta del hijo

         
            String linea;
            //Leo linea por linea al hijo
            while ((linea = archivo.readLine()) != null) {
            escritor.println(linea);
            }
            escritor.flush();
            escritor.close();
            archivo.close();
            
            //Leo lo que ha mandado el hijo a traves de su salida
            String resultado = respuestaHijo.readLine();

            System.out.println("Número de errores: "+resultado);
            
            //Leo la respuesta del proceso
            int codigoResultado = proceso.waitFor();
        
            System.out.println("Codigo del waitFor: "+codigoResultado);
            
        } catch (IOException ex) {
            System.out.println("Error de inicio del proceso");
            System.out.println("Error:" + ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println("Error de inicio del proceso interrumpido");
            System.out.println("Error:" + ex.getMessage());
        }
    }
}
