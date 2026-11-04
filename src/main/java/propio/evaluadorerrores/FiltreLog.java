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
            BufferedReader lector = new BufferedReader(
                new InputStreamReader(System.in)
            );

            String linea;
            int contadorError = 0;
            boolean hayTexto = false;

            while ((linea = lector.readLine()) != null) {

                if (!linea.isBlank()) {
                    hayTexto = true;
                }

                if (linea.contains("ERROR")) {
                    contadorError++;
                }
            }

            if (!hayTexto) {
                System.exit(1);
            }

            System.out.println(contadorError);
            lector.close();

        } catch (IOException ex) {
            System.err.println("Error en la lectura del proceso hijo");
            System.exit(1);
        }
    }
}
