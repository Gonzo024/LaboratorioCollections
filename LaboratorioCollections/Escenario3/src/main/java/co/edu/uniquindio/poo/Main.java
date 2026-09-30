package co.edu.uniquindio.poo;

public class Main {

    public static void main(String[] args) {

        // ==========================================
        // DEMOSTRACIÓN NORMAL DEL PROGRAMA
        // ==========================================

        SistemaTaxis sistema = new SistemaTaxis();

        sistema.registrarSolicitud(
                new SolicitudTaxi(1, "Ana", "Centro", "Universidad")
        );

        sistema.registrarSolicitud(
                new SolicitudTaxi(2, "Carlos", "Norte", "Centro")
        );

        sistema.registrarSolicitud(
                new SolicitudTaxi(3, "Laura", "Sur", "Centro")
        );

        sistema.registrarSolicitud(
                new SolicitudTaxi(4, "Pedro", "Centro", "Norte")
        );

        System.out.println("Solicitudes pendientes:");
        sistema.mostrarSolicitudesPendientes();

        System.out.println("\nCancelando solicitud 3:");
        sistema.cancelarSolicitud(3);

        System.out.println("\nSolicitudes pendientes:");
        sistema.mostrarSolicitudesPendientes();

        System.out.println("\nAtendiendo solicitud más antigua:");
        System.out.println(sistema.atenderSolicitud());

        System.out.println("\nSolicitudes pendientes:");
        sistema.mostrarSolicitudesPendientes();


        // ==========================================
        // FASE 4 - MEDICIÓN DE RENDIMIENTO
        // ==========================================

        System.out.println("\n\n==========================================");
        System.out.println("FASE 4 - MEDICIÓN DE RENDIMIENTO");
        System.out.println("==========================================");

        int[] tamanos = {
                100,
                1000,
                10000,
                100000
        };

        for (int cantidad : tamanos) {
            medirRendimiento(cantidad);
        }
    }


    // ==========================================
    // MÉTODO PARA MEDIR EL RENDIMIENTO
    // ==========================================

    public static void medirRendimiento(int cantidad) {

        SistemaTaxis sistema = new SistemaTaxis();

        Runtime runtime = Runtime.getRuntime();

        // ==========================================
        // CREAR LAS SOLICITUDES
        // ==========================================

        SolicitudTaxi[] solicitudes = new SolicitudTaxi[cantidad];

        for (int i = 0; i < cantidad; i++) {

            solicitudes[i] = new SolicitudTaxi(
                    i,
                    "Usuario" + i,
                    "Origen" + i,
                    "Destino" + i
            );
        }


        // ==========================================
        // MEDICIÓN DE MEMORIA INICIAL
        // ==========================================

        System.gc();

        long memoriaInicial =
                runtime.totalMemory() - runtime.freeMemory();


        // ==========================================
        // MEDICIÓN DEL TIEMPO DE REGISTRO
        // ==========================================

        long inicioRegistro = System.nanoTime();

        for (SolicitudTaxi solicitud : solicitudes) {
            sistema.registrarSolicitud(solicitud);
        }

        long finRegistro = System.nanoTime();


        // ==========================================
        // MEDICIÓN DE MEMORIA FINAL
        // ==========================================

        long memoriaFinal =
                runtime.totalMemory() - runtime.freeMemory();


        // ==========================================
        // CÁLCULO DEL TIEMPO
        // ==========================================

        double tiempoRegistro =
                (finRegistro - inicioRegistro) / 1_000_000.0;


        // ==========================================
        // CÁLCULO DE MEMORIA
        // ==========================================

        double memoriaMB =
                (memoriaFinal - memoriaInicial)
                        / (1024.0 * 1024.0);

        // Evitamos valores negativos
        if (memoriaMB < 0) {
            memoriaMB = 0;
        }


        // ==========================================
        // MEDICIÓN DEL TIEMPO DE ATENCIÓN
        // ==========================================

        long inicioAtencion = System.nanoTime();

        while (sistema.cantidadPendientes() > 0) {
            sistema.atenderSolicitud();
        }

        long finAtencion = System.nanoTime();

        double tiempoAtencion =
                (finAtencion - inicioAtencion) / 1_000_000.0;


        // ==========================================
        // MOSTRAR RESULTADOS
        // ==========================================

        System.out.println("\n------------------------------------------");
        System.out.println("Cantidad de solicitudes: " + cantidad);
        System.out.println("------------------------------------------");

        System.out.printf(
                "Tiempo de registro: %.4f ms%n",
                tiempoRegistro
        );

        System.out.printf(
                "Tiempo de atención: %.4f ms%n",
                tiempoAtencion
        );

        System.out.printf(
                "Memoria aproximada: %.4f MB%n",
                memoriaMB
        );
    }
}