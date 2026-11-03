/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package propio.evaluadorerrores;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Jonathan M.Ortega
 */
public class FiltreLog {

    public static void main(String[] args) {

        try {
            BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));
            String linea;
            int contadorError=0;
            while ((linea = lector.readLine()) != null) {
                
                if (linea.contains("ERROR")) {
                    //System.err.println(linea);
                    ++contadorError;
                    
                } else if (linea.contains("WARNING")) {
                    System.out.println(linea);
                }
            }
            System.err.println(contadorError);
            lector.close();

        } catch (IOException ex) {
            System.out.println("Error en la lectura del fichero recibido en el proceso Filtro");
        }

    }
}
