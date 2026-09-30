package co.edu.uniquindio.poo;

public class ProductoCatalogo {

    private String codigo;
    private String nombre;
    private double precio;

    public ProductoCatalogo(String codigo, String nombre, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }
}