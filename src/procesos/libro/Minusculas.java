package procesos.libro;

import procesos.libro.utils.LibroUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Minusculas {

    public static void main(String[] args) throws IOException {

        int lineaInicio = Integer.parseInt(args[0]);
        int lineaFin = Integer.parseInt(args[1]);

        Path path = Paths.get(LibroUtils.NOMBRE_FICHERO_LIBRO);
        List<String> totalLineas = Files.readAllLines(path);

        for(int cont = lineaInicio; cont< lineaFin; cont++) {
            if(cont >= totalLineas.size())
                continue;

            String linea = totalLineas.get(cont);
            System.out.println(linea.toLowerCase());
        }
    }


}
