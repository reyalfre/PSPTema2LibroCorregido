package procesos.libro;

import procesos.libro.utils.LibroUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;
public class EjercicioLibroVariable {


    private static int MINUS = 0;
    private static int MAYUS = 1;
    private static int REEMPLAZADOR = 2;

    public static void main(String[] args) throws IOException, InterruptedException {

        Path path = Paths.get(LibroUtils.NOMBRE_FICHERO_LIBRO);
        int numTotalLineas = Files.readAllLines(path).size();

        System.out.println("Lanzando descomposicion de libro Alicia. Total de lineas a descomponer: " + numTotalLineas);
        System.out.println("¿Cuántos procesos usamos para ello?");

        //Leemos de teclado y sacamos cu�ntos procesos y cu�ntas l�neas por proceso
        Scanner teclado = new Scanner(System.in);
        int numProcesos = teclado.nextInt();
        int numLineasCadaProceso = numTotalLineas / numProcesos;

        System.out.println(
                String.format("Se usarán %d procesos, teniendo %d lineas cada uno", numProcesos, numLineasCadaProceso));

        //Construimos el processBuilder con la salida que queremos
        ProcessBuilder procesos = new ProcessBuilder("");
        procesos.directory(new File(LibroUtils.RUTA_BIN));
        procesos.redirectOutput(ProcessBuilder.Redirect.appendTo(new File(LibroUtils.FICHERO_SALIDA_VARIABLE)));

        procesos.redirectError(Redirect.INHERIT);

        int lineaInicio = 0;
        int lineaFin = numLineasCadaProceso;
        int procesamientoAleatorio = -1;

        for (int cont = 1; cont <= numProcesos; cont++) {

            procesamientoAleatorio = obtenerOperacion();
            System.out.println(String.format("Proceso %d, lineas %d a %d. Se usará el metodo %d ", cont, lineaInicio, lineaFin, procesamientoAleatorio));

            //Esta estructura if se podría optimizar más para no repetir tanto código, ¿cómo lo harías?
            if(procesamientoAleatorio == MAYUS) {
                procesos.command("java", Mayusculas.class.getName(), String.valueOf(lineaInicio), String.valueOf(lineaFin));
            }else if(procesamientoAleatorio == MINUS) {
                procesos.command("java", Minusculas.class.getName(), String.valueOf(lineaInicio), String.valueOf(lineaFin));
            }else {
                procesos.command("java", Reemplazador.class.getName(), String.valueOf(lineaInicio), String.valueOf(lineaFin));
            }

            //Lanzamos y esperamos a que termine
            Process hijo = procesos.start();
            hijo.waitFor();

            // Recalculamos linea de inicio y linea de fin para el siguiente proceso,
            // bas�ndonos en la lineaFin anterior y sumando el numLineasCadaProceso
            lineaInicio = lineaFin;

            //Si el proximo proceso es el ultimo, cogemos todas las lineas restantes
            if((cont + 1) == numProcesos) {
                numLineasCadaProceso = numTotalLineas - lineaInicio;
            }
            lineaFin = lineaInicio + numLineasCadaProceso;

        }

        System.out.println("Procesos lanzados.Comprobar fichero de salida.");

    }

    /**
     * Genera aleatoriamente una operacion. Utilizamos por ejemplo el modulo 3, para que nos de valores 0, 1 o 2.
     * @return
     */
    private static int obtenerOperacion() {
        Random aleatorio = new Random();

        int operacion = -1;
        switch(aleatorio.nextInt() % 3) {
            case 0:
                operacion = MINUS;
                break;
            case 1:
                operacion = MAYUS;
                break;
            case 2:
                operacion = REEMPLAZADOR;
                break;
            default:
                //Si algun valor se sale de los que hemos especificado, que se haga mayusculas por defecto
                operacion = MAYUS;
        }

        return operacion;
    }

}
