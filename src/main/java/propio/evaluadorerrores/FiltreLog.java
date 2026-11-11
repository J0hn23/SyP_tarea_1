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

        String palabraBuscada;

        if (args.length > 0) {
            palabraBuscada = args[0];
        } else {
            palabraBuscada = "ERROR";
        }

        comparacion(palabraBuscada);
    }

    private static void comparacion(String palabraBuscada) {

        try {
            BufferedReader lector = new BufferedReader(
                    new InputStreamReader(System.in)
            );

            String linea;
            int contador = 0;
            boolean hayTexto = false;

            while ((linea = lector.readLine()) != null) {

                if (!linea.isBlank()) {
                    hayTexto = true;
                }

                // Busca únicamente la palabra recibida
                if (linea.contains(palabraBuscada)) {
                    contador++;
                }
            }

            if (!hayTexto) {
                System.err.println("No se ha recibido texto en el fichero/esta vacio");
                lector.close();
                System.exit(1);

            }

            // Devuelve un único resultado por la salida normal
            System.out.println(contador);

            lector.close();

        } catch (IOException ex) {
            System.err.println("Error en la lectura del proceso hijo");
            System.exit(1);
        }
    }
}
