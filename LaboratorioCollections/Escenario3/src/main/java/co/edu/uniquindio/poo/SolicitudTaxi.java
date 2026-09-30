package co.edu.uniquindio.poo;

public class SolicitudTaxi {

    private int id;
    private String usuario;
    private String origen;
    private String destino;

    public SolicitudTaxi(int id, String usuario, String origen, String destino) {
        this.id = id;
        this.usuario = usuario;
        this.origen = origen;
        this.destino = destino;
    }

    public int getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    @Override
    public String toString() {
        return "Solicitud{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                '}';
    }
}