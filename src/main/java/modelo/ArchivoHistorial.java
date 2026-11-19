
package modelo;

/**
 *
 * @author Jurbano
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ArchivoHistorial {
    public static final String NOMBRE_ARCHIVO = "historial_combate.txt";

    public void guardarRegistro(String linea) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(NOMBRE_ARCHIVO, true))) {
            bw.write(linea);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar historial: " + e.getMessage());
        }
    }

    public ArrayList<String> leerHistorial() {
        ArrayList<String> lineas = new ArrayList<>();
        File archivo = new File(NOMBRE_ARCHIVO);

        if (!archivo.exists()) {
            return lineas;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                lineas.add(linea);
            }
        } catch (IOException e) {
            System.err.println("Error al leer historial: " + e.getMessage());
        }
        return lineas;
    }
}
