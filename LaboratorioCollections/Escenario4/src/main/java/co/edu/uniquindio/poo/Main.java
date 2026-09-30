package co.edu.uniquindio.poo;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {

        CatalogoEcommerce catalogo = new CatalogoEcommerce();

        /*
         * CASO 1
         * Inserción inicial de productos
         */
        System.out.println("=== CASO 1: Insercion de productos ===");

        catalogo.insertar(
                new ProductoCatalogo("COD001", "Laptop Lenovo", 2500000)
        );

        catalogo.insertar(
                new ProductoCatalogo("COD002", "Mouse Logitech", 85000)
        );

        catalogo.insertar(
                new ProductoCatalogo("COD003", "Teclado Mecanico", 230000)
        );

        catalogo.insertar(
                new ProductoCatalogo("COD004", "Monitor Samsung", 950000)
        );

        catalogo.insertar(
                new ProductoCatalogo("COD005", "Auriculares Sony", 310000)
        );

        catalogo.insertar(
                new ProductoCatalogo("COD006", "Webcam Logitech", 175000)
        );

        System.out.println(
                "Total productos (debe ser 6): "
                        + catalogo.totalProductos()
        );


        /*
         * CASO 2
         * Intentar insertar código duplicado
         */
        System.out.println("\n=== CASO 2: Codigo duplicado ===");

        catalogo.insertar(
                new ProductoCatalogo(
                        "COD001",
                        "Otro Laptop",
                        3000000
                )
        );

        System.out.println(
                "Total productos (debe seguir siendo 6): "
                        + catalogo.totalProductos()
        );


        /*
         * CASO 3
         * Buscar código existente
         */
        System.out.println(
                "\n=== CASO 3: Busqueda por codigo existente ==="
        );

        ProductoCatalogo encontrado =
                catalogo.buscarPorCodigo("COD004");

        System.out.println(
                "Buscar COD004 (debe ser 'Monitor Samsung'): "
                        + (encontrado != null
                        ? encontrado.getNombre()
                        : null)
        );


        /*
         * CASO 4
         * Buscar código inexistente
         */
        System.out.println(
                "\n=== CASO 4: Busqueda codigo inexistente ==="
        );

        ProductoCatalogo inexistente =
                catalogo.buscarPorCodigo("COD999");

        System.out.println(
                "Buscar COD999 (debe ser null): "
                        + inexistente
        );


        /*
         * CASO 5
         * Mostrar catálogo ordenado por precio
         */
        System.out.println(
                "\n=== CASO 5: Productos ordenados por precio ==="
        );

        catalogo.mostrarOrdenadosPorPrecio();


        /*
         * CASO 6
         * Eliminar producto
         */
        System.out.println(
                "\n=== CASO 6: Eliminar COD003 ==="
        );

        catalogo.eliminar("COD003");

        System.out.println(
                "Total productos (debe ser 5): "
                        + catalogo.totalProductos()
        );

        System.out.println(
                "Buscar COD003 tras eliminar (debe ser null): "
                        + catalogo.buscarPorCodigo("COD003")
        );


        /*
         * CASO 7
         * Ver catálogo después de eliminar
         */
        System.out.println(
                "\n=== CASO 7: Catalogo tras eliminar COD003 ==="
        );

        catalogo.mostrarOrdenadosPorPrecio();


        /*
         * CASO 10
         * Prueba de rendimiento
         */
        System.out.println(
                "\n=== CASO 10: Rendimiento con carga masiva ==="
        );

        pruebaRendimiento(100);
        pruebaRendimiento(1000);
        pruebaRendimiento(10000);
        pruebaRendimiento(100000);
    }


    private static void pruebaRendimiento(int n) {

        CatalogoEcommerce catalogo = new CatalogoEcommerce();

        // Evitamos imprimir miles de productos durante la prueba
        catalogo.setVerbose(false);

        Runtime runtime = Runtime.getRuntime();

        runtime.gc();

        long memoriaAntes =
                runtime.totalMemory() - runtime.freeMemory();


        /*
         * INSERCIÓN
         */
        long inicioInsercion = System.nanoTime();

        for (int i = 0; i < n; i++) {

            String codigo =
                    String.format("P%06d", i);

            ProductoCatalogo producto =
                    new ProductoCatalogo(
                            codigo,
                            "Producto " + i,
                            Math.random() * 1000000
                    );

            catalogo.insertar(producto);
        }

        long finInsercion = System.nanoTime();


        /*
         * BÚSQUEDA
         */
        String codigoBuscar =
                String.format("P%06d", n / 2);

        long inicioBusqueda = System.nanoTime();

        catalogo.buscarPorCodigo(codigoBuscar);

        long finBusqueda = System.nanoTime();


        /*
         * ELIMINACIÓN
         */
        long inicioEliminacion = System.nanoTime();

        catalogo.eliminar(codigoBuscar);

        long finEliminacion = System.nanoTime();


        /*
         * MEMORIA
         */
        long memoriaDespues =
                runtime.totalMemory() - runtime.freeMemory();

        long memoriaUsada =
                Math.max(
                        0,
                        (memoriaDespues - memoriaAntes) / 1024
                );


        /*
         * TIEMPOS
         */
        long tiempoInsercionMs =
                (finInsercion - inicioInsercion) / 1_000_000;

        long tiempoBusquedaNs =
                finBusqueda - inicioBusqueda;

        long tiempoEliminacionNs =
                finEliminacion - inicioEliminacion;


        System.out.printf(
                "n=%-7d | Insercion: %6dms | Busqueda: %7dns | Eliminar: %7dns | Memoria: %6dKB%n",
                n,
                tiempoInsercionMs,
                tiempoBusquedaNs,
                tiempoEliminacionNs,
                memoriaUsada
        );
    }
}