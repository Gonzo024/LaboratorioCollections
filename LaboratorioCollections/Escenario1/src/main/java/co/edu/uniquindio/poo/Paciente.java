package co.edu.uniquindio.poo;


public class Paciente implements Comparable<Paciente> {

    private int documento;
    private String nombre;
    private int gravedad;      // 1 = critico, 2 = urgente, 3 = normal
    private int numeroLlegada;
    private boolean atendido;

    private static int contadorLlegada = 0;

    public Paciente(int documento, String nombre, int gravedad) {
        this.documento = documento;
        this.nombre = nombre;
        this.gravedad = gravedad;
        this.numeroLlegada = contadorLlegada;
        contadorLlegada++;
        this.atendido = false;
    }

    public int getDocumento() {
        return documento;
    }

    public String getNombre() {
        return nombre;
    }

    public int getGravedad() {
        return gravedad;
    }

    public boolean isAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }

    // Metodo para saber quien va primero.
    // Menor gravedad (1) va antes. Si empatan, va primero el que llego antes.
    @Override
    public int compareTo(Paciente otro) {
        if (this.gravedad != otro.gravedad) {
            return this.gravedad - otro.gravedad;
        }
        return this.numeroLlegada - otro.numeroLlegada;
    }

    @Override
    public String toString() {
        return documento + " - " + nombre + " (gravedad " + gravedad + ")";
    }
}
