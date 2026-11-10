/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package propio.evaluadorerrores;

import java.io.BufferedReader;
import java.io.File;
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

        String cadenaError = "ERROR";
        String cadenaWarning = "WARNING";

        try {
            ProcessBuilder procesoHijoA = new ProcessBuilder("java",
                    "-cp",
                    System.getProperty("java.class.path"),
                    "propio.evaluadorerrores.FiltreLog",
                    cadenaError);

            ProcessBuilder procesoHijoB = new ProcessBuilder("java",
                    "-cp",
                    System.getProperty("java.class.path"),
                    "propio.evaluadorerrores.FiltreLog",
                    cadenaWarning);

            //pb.inheritIO();  esta linea permite que la salida del hijo salga por la terminal del padre
            BufferedReader archivo = new BufferedReader(
                    new FileReader("C:\\Users\\John\\Documents\\NetBeansProjects\\EvaluadorErrores\\Datos\\logs.txt"));//Abro el archivo para leerlo

            //Creo el proceso y lo inicio
            Process procesoError = procesoHijoA.start();
            Process procesoWarning = procesoHijoB.start();

            PrintWriter escritorError = new PrintWriter(procesoError.getOutputStream());// con el getOutputStream se lo voy mandando a la entrada del hijo
            PrintWriter escritorWarning = new PrintWriter(procesoWarning.getOutputStream());// 

            //Lo mando al hijo FilterLog escribiendolo en él bufer del hijo
            //BufferedReader respuestaHijo = new BufferedReader(new InputStreamReader(proceso.getInputStream()));//Preparo un BR para leer el bufer de la respuesta del hijo
            //En el nivel II redirije la salida a un File
            /*
            File archivoSalidaErrores = new File("C:\\Users\\John\\Documents\\NetBeansProjects\\EvaluadorErrores\\Datos\\errors_filtre.log");
            pb.redirectError(archivoSalidaErrores);
            Process proceso = pb.start();
             */
            //Creo los hilos para diferenciar entre errores y warning y contarlos
            String linea;
            //Leo linea por linea al hijo
            while ((linea = archivo.readLine()) != null) {
                escritorError.println(linea);
                escritorWarning.println(linea);
            }

            //Primero cierro los flujos
            escritorError.flush();
            escritorWarning.flush();
            
            escritorError.close();
            escritorWarning.close();
            archivo.close();
            
            
            //Y ahora leo las respuestas d elos bufferd
              BufferedReader salidaErrores = new BufferedReader(
                    new InputStreamReader(procesoError.getInputStream())
            );

            BufferedReader salidaWarnings = new BufferedReader(
                    new InputStreamReader(procesoWarning.getInputStream())
            );

            // Leemos el número enviado por cada hijo y lo parseo a int
            int numeroErrores = Integer.parseInt(salidaErrores.readLine());
            int numeroWarnings = Integer.parseInt(salidaWarnings.readLine());


            //Leo lo que ha mandado el hijo a traves de su salida
            //String resultado = respuestaHijo.readLine();
            //System.out.println("Número de errores: "+resultado);
            //Leo la respuesta del proceso
            int codigoErrores = procesoError.waitFor();
            int codigoWarnings = procesoWarning.waitFor();

            int codigoFinal;

            if (codigoErrores == 0 && codigoWarnings == 0) {
                codigoFinal = 0;
            } else {
                codigoFinal = 1;
            }

            System.out.println( "RESULTADO: ERRORES=" + numeroErrores + " | WARNINGS=" + numeroWarnings + " | EXIT_CODE=" + codigoFinal);

        } catch (IOException ex) {
            System.out.println("Error de inicio del proceso:");
            System.out.println("Error:" + ex.getMessage());
        } catch (InterruptedException ex) {
            System.out.println("Error de inicio del proceso interrumpido:");
            System.out.println("Error:" + ex.getMessage());
        }
    }
}
