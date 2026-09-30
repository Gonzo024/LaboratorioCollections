package co.edu.uniquindio.poo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CatalogoEcommerce {

    // Acceso O(1) por código, preserva orden de inserción
    private LinkedHashMap<String, ProductoCatalogo> porCodigo = new LinkedHashMap<>();

    // Orden automático por precio O(log n)
    private TreeMap<Double, List<ProductoCatalogo>> porPrecio = new TreeMap<>();

    private boolean verbose = true;

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    private void log(String mensaje) {
        if (verbose) {
            System.out.println(mensaje);
        }
    }

    public void insertar(ProductoCatalogo p) {

        if (porCodigo.containsKey(p.getCodigo())) {
            log("Ya existe un producto con codigo: " + p.getCodigo());
            return;
        }

        porCodigo.put(p.getCodigo(), p);

        porPrecio
                .computeIfAbsent(p.getPrecio(), k -> new ArrayList<>())
                .add(p);

        log("Producto insertado: [" + p.getCodigo() + "] "
                + p.getNombre()
                + " | $"
                + String.format("%.2f", p.getPrecio()));
    }

    public ProductoCatalogo buscarPorCodigo(String codigo) {
        return porCodigo.getOrDefault(codigo, null);
    }

    public void eliminar(String codigo) {

        ProductoCatalogo p = porCodigo.remove(codigo);

        if (p != null) {

            List<ProductoCatalogo> lista = porPrecio.get(p.getPrecio());

            if (lista != null) {
                lista.remove(p);

                if (lista.isEmpty()) {
                    porPrecio.remove(p.getPrecio());
                }
            }

            log("Producto eliminado: [" + codigo + "] " + p.getNombre());

        } else {
            log("No existe un producto con codigo: " + codigo);
        }
    }

    public int totalProductos() {
        return porCodigo.size();
    }

    public void mostrarOrdenadosPorPrecio() {

        System.out.println("--- Catalogo ordenado por precio ---");

        for (Map.Entry<Double, List<ProductoCatalogo>> entry : porPrecio.entrySet()) {

            double precio = entry.getKey();

            for (ProductoCatalogo p : entry.getValue()) {

                System.out.printf(
                        "$%10.2f | %-25s | COD: %s%n",
                        precio,
                        p.getNombre(),
                        p.getCodigo()
                );
            }
        }
    }
}