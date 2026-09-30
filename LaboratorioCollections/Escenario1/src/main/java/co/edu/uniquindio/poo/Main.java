package co.edu.uniquindio.poo;


import java.util.ArrayList;

public class Main {

    // Memoria usada aproximada en MB
    public static double memoriaUsadaMB() {
        Runtime r = Runtime.getRuntime();
        System.gc();
        long usada = r.totalMemory() - r.freeMemory();
        return usada / 1024.0 / 1024.0;
    }

    public static void demostracion() {
        System.out.println("=== DEMOSTRACION ===");
        SistemaHospital sistema = new SistemaHospital();

        sistema.registrar(new Paciente(111, "Ana", 3));
        sistema.registrar(new Paciente(222, "Luis", 1));
        sistema.registrar(new Paciente(333, "Marta", 2));

        boolean repetido = sistema.registrar(new Paciente(111, "Ana otra vez", 3));
        System.out.println("Se registro el documento 111 repetido? " + repetido);

        System.out.println("Buscar 222: " + sistema.buscar(222));
        System.out.println("Buscar 999: " + sistema.buscar(999));

        System.out.println("Atender por llegada: " + sistema.atenderPorLlegada());
        System.out.println("Atender por gravedad: " + sistema.atenderPorGravedad());
        System.out.println("Atender por gravedad: " + sistema.atenderPorGravedad());
        System.out.println();
    }

    public static void medicion() {
        int[] tamanos = {100, 1000, 10000, 100000};

        System.out.println("=== MEDICION ===");
        System.out.println("n\tRegistro(ms)\tBuscar(ms)\tAtender(ms)\tMemoria(MB)");

        for (int k = 0; k < tamanos.length; k++) {
            int n = tamanos[k];

            double memAntes = memoriaUsadaMB();
            SistemaHospital sistema = new SistemaHospital();

            // 1. Registrar n pacientes
            long inicio = System.nanoTime();
            for (int i = 0; i < n; i++) {
                sistema.registrar(new Paciente(1000000 + i, "Paciente" + i, (i % 3) + 1));
            }
            long fin = System.nanoTime();
            double tRegistro = (fin - inicio) / 1000000.0;

            // Memoria con todos los pacientes guardados
            double memDespues = memoriaUsadaMB();
            double memoria = memDespues - memAntes;

            // 2. Buscar los n pacientes
            inicio = System.nanoTime();
            for (int i = 0; i < n; i++) {
                sistema.buscar(1000000 + i);
            }
            fin = System.nanoTime();
            double tBuscar = (fin - inicio) / 1000000.0;

            // 3. Atender todos por gravedad
            inicio = System.nanoTime();
            for (int i = 0; i < n; i++) {
                sistema.atenderPorGravedad();
            }
            fin = System.nanoTime();
            double tAtender = (fin - inicio) / 1000000.0;

            System.out.printf("%d\t%.2f\t\t%.2f\t\t%.2f\t\t%.2f%n",
                    n, tRegistro, tBuscar, tAtender, memoria);
        }
        System.out.println();
    }


    public static void comparacionArrayList() {
        int[] tamanos = {100, 1000, 10000, 100000};

        System.out.println("=== COMPARACION: 1000 busquedas (HashMap vs ArrayList) ===");
        System.out.println("n\tHashMap(ms)\tArrayList(ms)");

        for (int k = 0; k < tamanos.length; k++) {
            int n = tamanos[k];

            SistemaHospital sistema = new SistemaHospital();
            ArrayList<Paciente> lista = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                Paciente p = new Paciente(1000000 + i, "Paciente" + i, (i % 3) + 1);
                sistema.registrar(p);
                lista.add(p);
            }

            int busquedas = 1000;

            // Buscamos los ultimos pacientes (los mas lejanos para la lista)
            long inicio = System.nanoTime();
            for (int i = 0; i < busquedas; i++) {
                sistema.buscar(1000000 + (n - 1 - (i % n)));
            }
            long fin = System.nanoTime();
            double tHash = (fin - inicio) / 1000000.0;

            inicio = System.nanoTime();
            for (int i = 0; i < busquedas; i++) {
                int documento = 1000000 + (n - 1 - (i % n));
                for (int j = 0; j < lista.size(); j++) {
                    if (lista.get(j).getDocumento() == documento) {
                        break;
                    }
                }
            }
            fin = System.nanoTime();
            double tLista = (fin - inicio) / 1000000.0;

            System.out.printf("%d\t%.2f\t\t%.2f%n", n, tHash, tLista);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        demostracion();
        medicion();
        comparacionArrayList();
    }
}