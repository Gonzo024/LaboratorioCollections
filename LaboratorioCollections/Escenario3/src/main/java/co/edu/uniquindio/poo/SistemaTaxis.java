package co.edu.uniquindio.poo;

import java.util.LinkedHashMap;
import java.util.Map;

public class SistemaTaxis {

    private LinkedHashMap<Integer, SolicitudTaxi> solicitudes;

    public SistemaTaxis() {
        solicitudes = new LinkedHashMap<>();
    }

    // Registrar una solicitud
    public boolean registrarSolicitud(SolicitudTaxi solicitud) {

        if (solicitudes.containsKey(solicitud.getId())) {
            return false;
        }

        solicitudes.put(solicitud.getId(), solicitud);
        return true;
    }

    // Cancelar una solicitud específica
    public SolicitudTaxi cancelarSolicitud(int id) {
        return solicitudes.remove(id);
    }

    // Atender la solicitud más antigua
    public SolicitudTaxi atenderSolicitud() {

        if (solicitudes.isEmpty()) {
            return null;
        }

        Map.Entry<Integer, SolicitudTaxi> primera =
                solicitudes.entrySet().iterator().next();

        SolicitudTaxi solicitud = primera.getValue();

        solicitudes.remove(primera.getKey());

        return solicitud;
    }

    // Mostrar solicitudes pendientes
    public void mostrarSolicitudesPendientes() {

        for (SolicitudTaxi solicitud : solicitudes.values()) {
            System.out.println(solicitud);
        }
    }

    // Cantidad de solicitudes pendientes
    public int cantidadPendientes() {
        return solicitudes.size();
    }
}