package procesos.libro;


import procesos.libro.utils.LibroUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Transformador {

    public static final String CADENA_ORIGEN = "Alice";
    public static String MAYUS = "mayus";
    public static String MINUS = "minus";

    public static void main(String[] args) throws IOException {
        int lineaInicio = Integer.parseInt(args[0]);
        int lineaFin = Integer.parseInt(args[1]);
        String criterio = args[2];

        Path path = Paths.get(LibroUtils.NOMBRE_FICHERO_LIBRO);
        List<String> totalLineas = Files.readAllLines(path);

        for(int cont = lineaInicio; cont< lineaFin; cont++) {

            if(cont >= totalLineas.size())
                continue;


            String linea = totalLineas.get(cont);
            if(criterio.equalsIgnoreCase(MAYUS)) {
                System.out.println(linea.toUpperCase());
            }else if(criterio.equalsIgnoreCase(MINUS)) {
                System.out.println(linea.toLowerCase());
            }else {
                System.out.println(linea.replaceAll(CADENA_ORIGEN,criterio));
            }
        }
    }

}
