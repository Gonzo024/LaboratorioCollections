package co.edu.uniquindio.poo;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.*;

public class Main {

    static class Producto {
        private String codigo;
        private String nombre;
        private String categoria;
        private double precio;

        public Producto(String codigo, String nombre, String categoria, double precio) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.categoria = categoria;
            this.precio = precio;
        }

        public String getCodigo() { return codigo; }
        public String getNombre() { return nombre; }
        public String getCategoria() { return categoria; }
        public double getPrecio() { return precio; }

        @Override
        public String toString() {
            return "Codigo: " + codigo + " | Nombre: " + nombre + " | Categoria: " + categoria + " | Precio: $" + precio;
        }
    }

    static class PlataformaVentas {
        private HashMap<String, Producto> productosPorCodigo;
        private LinkedList<Producto> productos;
        private HashMap<String, ArrayList<Producto>> productosPorCategoria;

        public PlataformaVentas() {
            productosPorCodigo = new HashMap<>(60000);
            productos = new LinkedList<>();
            productosPorCategoria = new HashMap<>();
        }

        public boolean registrarProducto(Producto producto) {
            if (productosPorCodigo.containsKey(producto.getCodigo())) {
                return false;
            }

            productosPorCodigo.put(producto.getCodigo(), producto);
            productos.addFirst(producto);

            String categoria = producto.getCategoria();
            productosPorCategoria.putIfAbsent(categoria, new ArrayList<>());
            productosPorCategoria.get(categoria).add(producto);

            return true;
        }

        public Producto buscarPorCodigo(String codigo) {
            return productosPorCodigo.get(codigo);
        }

        public void mostrarOrdenadosPorPrecio() {
            ArrayList<Producto> listaOrdenada = new ArrayList<>(productos);
            listaOrdenada.sort(Comparator.comparingDouble(Producto::getPrecio));

            System.out.println("\n--- PRODUCTOS ORDENADOS POR PRECIO ---");

            for (Producto p : listaOrdenada) {
                System.out.println(p);
            }
        }

        public void filtrarPorCategoria(String categoria) {
            ArrayList<Producto> lista = productosPorCategoria.get(categoria);

            System.out.println("\n--- PRODUCTOS DE LA CATEGORIA: " + categoria + " ---");

            if (lista == null || lista.isEmpty()) {
                System.out.println("No hay productos en esta categoria.");
                return;
            }

            for (Producto p : lista) {
                System.out.println(p);
            }
        }

        public int cantidadProductos() {
            return productos.size();
        }

        public void medirRendimiento(int cantidad) {

            System.gc();

            long memoriaAntes = Runtime.getRuntime().totalMemory()
                    - Runtime.getRuntime().freeMemory();

            System.out.println("\n==================================");
            System.out.println("PRUEBA CON " + cantidad + " PRODUCTOS");
            System.out.println("==================================");

            long inicioInsercion = System.nanoTime();

            for (int i = 0; i < cantidad; i++) {
                String codigo = "P" + i;
                String nombre = "Producto " + i;
                String categoria = "Categoria " + (i % 5);
                double precio = 1000 + (i % 10000);

                Producto p = new Producto(codigo, nombre, categoria, precio);
                registrarProducto(p);
            }

            long finInsercion = System.nanoTime();
            long tiempoInsercion = finInsercion - inicioInsercion;

            long memoriaDespues = Runtime.getRuntime().totalMemory()
                    - Runtime.getRuntime().freeMemory();

            long memoriaUsada = memoriaDespues - memoriaAntes;

            long inicioBusqueda = System.nanoTime();

            for (int i = 0; i < 1000; i++) {
                buscarPorCodigo("P" + (i % cantidad));
            }

            long finBusqueda = System.nanoTime();
            long tiempoBusqueda = finBusqueda - inicioBusqueda;

            long inicioOrdenamiento = System.nanoTime();

            ArrayList<Producto> listaOrdenada = new ArrayList<>(productos);
            listaOrdenada.sort(Comparator.comparingDouble(Producto::getPrecio));

            long finOrdenamiento = System.nanoTime();
            long tiempoOrdenamiento = finOrdenamiento - inicioOrdenamiento;

            long inicioFiltrado = System.nanoTime();

            ArrayList<Producto> filtrados = productosPorCategoria.get("Categoria 1");
            int cantidadFiltrada = filtrados.size();

            long finFiltrado = System.nanoTime();
            long tiempoFiltrado = finFiltrado - inicioFiltrado;

            System.out.println("Productos registrados: " + cantidadProductos());
            System.out.println("Tiempo de insercion: " + tiempoInsercion / 1_000_000.0 + " ms");
            System.out.println("Tiempo de 1000 busquedas: " + tiempoBusqueda / 1_000_000.0 + " ms");
            System.out.println("Tiempo de ordenamiento: " + tiempoOrdenamiento / 1_000_000.0 + " ms");
            System.out.println("Tiempo de acceso a categoria: " + tiempoFiltrado / 1_000_000.0 + " ms");
            System.out.println("Memoria aproximada: " + memoriaUsada / 1024.0 / 1024.0 + " MB");
            System.out.println("Productos en Categoria 1: " + cantidadFiltrada);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PlataformaVentas plataforma = new PlataformaVentas();
        int opcion;

        do {
            System.out.println("\n===== PLATAFORMA DE VENTAS =====");
            System.out.println("1. Registrar producto");
            System.out.println("2. Buscar por codigo");
            System.out.println("3. Mostrar productos por precio");
            System.out.println("4. Filtrar por categoria");
            System.out.println("5. Pruebas de rendimiento");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {

                case 1:
                    System.out.print("Codigo: ");
                    String codigo = sc.nextLine();

                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();

                    System.out.print("Categoria: ");
                    String categoria = sc.nextLine();

                    System.out.print("Precio: ");
                    double precio = sc.nextDouble();
                    sc.nextLine();

                    Producto nuevo = new Producto(codigo, nombre, categoria, precio);

                    if (plataforma.registrarProducto(nuevo)) {
                        System.out.println("Producto registrado al inicio.");
                    } else {
                        System.out.println("Error: el codigo ya existe.");
                    }
                    break;

                case 2:
                    System.out.print("Ingrese el codigo a buscar: ");
                    String buscar = sc.nextLine();

                    Producto encontrado = plataforma.buscarPorCodigo(buscar);

                    if (encontrado != null) {
                        System.out.println(encontrado);
                    } else {
                        System.out.println("Producto no encontrado.");
                    }
                    break;

                case 3:
                    plataforma.mostrarOrdenadosPorPrecio();
                    break;

                case 4:
                    System.out.print("Ingrese la categoria: ");
                    String cat = sc.nextLine();
                    plataforma.filtrarPorCategoria(cat);
                    break;

                case 5:
                    int[] cantidades = {100, 1000, 10000, 100000};

                    for (int cantidad : cantidades) {
                        PlataformaVentas prueba = new PlataformaVentas();
                        prueba.medirRendimiento(cantidad);
                    }
                    break;

                case 0:
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("Opcion no valida.");
            }

        } while (opcion != 0);

        sc.close();
    }
}