package procesos.libro;

import procesos.libro.utils.LibroUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EjercicioLibro {



    public static void main(String[] args) throws IOException, InterruptedException {

        Path path = Paths.get(LibroUtils.NOMBRE_FICHERO_LIBRO);
        int numTotalLineas = Files.readAllLines(path).size();

        System.out.println("Lanzando descomposicion de libro Alicia. Total de lineas a descomponer: " + numTotalLineas);

        ProcessBuilder procesos = new ProcessBuilder("");
        procesos.directory(new File(LibroUtils.RUTA_BIN));
        procesos.redirectOutput(ProcessBuilder.Redirect.appendTo(new File(LibroUtils.FICHERO_SALIDA)));
        procesos.redirectError(ProcessBuilder.Redirect.INHERIT);

        long tiempoInicio = System.currentTimeMillis();
        //Proceso 1: 0 a linea 720  -> mayus
        procesos.command("java", Mayusculas.class.getName(), String.valueOf(0), String.valueOf(720));
        Process hijo = procesos.start();

        hijo.waitFor();
        //Proceso 2: 721 a linea 1442  -> minus
        procesos.command("java", Minusculas.class.getName(), String.valueOf(721), String.valueOf(1442));
        hijo = procesos.start();

        hijo.waitFor();
        //Proceso 3: 1442 a 2163 ->mayus
        procesos.command("java", Reemplazador.class.getName(), String.valueOf(1442), String.valueOf(2163));
        hijo = procesos.start();

        hijo.waitFor();
        //Proceso 4: 2163 a 2884 -> minus
        procesos.command("java", Transformador.class.getName(), String.valueOf(2163), String.valueOf(2884),"Bruce Willis");
        hijo = procesos.start();

        hijo.waitFor();
        //Proceso 5: 2884 a 3609 -> mayus
        procesos.command("java", Mayusculas.class.getName(), String.valueOf(2884), String.valueOf(3609));
        hijo = procesos.start();

        hijo.waitFor();

        long tiempoFinal = System.currentTimeMillis();

        System.out.println("Tiempo total de proceso (ms): "  + (tiempoFinal - tiempoInicio));
        System.out.println("Procesos lanzados.Comprobar fichero de salida.");


    }

}
