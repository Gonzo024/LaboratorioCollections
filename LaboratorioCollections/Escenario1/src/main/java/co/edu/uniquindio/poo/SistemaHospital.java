package co.edu.uniquindio.poo;



import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class SistemaHospital {

    // Busqueda rapida por documento y control de duplicados
    private HashMap<Integer, Paciente> pacientes = new HashMap<>();

    // Orden de llegada (primero en llegar, primero en salir)
    private Queue<Paciente> colaLlegada = new LinkedList<>();

    // Prioridad por gravedad
    private PriorityQueue<Paciente> colaPrioridad = new PriorityQueue<>();

    // Devuelve true si se registro, false si el documento ya existia
    public boolean registrar(Paciente p) {
        if (pacientes.containsKey(p.getDocumento())) {
            return false;
        }
        pacientes.put(p.getDocumento(), p);
        colaLlegada.add(p);
        colaPrioridad.add(p);
        return true;
    }

    // Devuelve el paciente o null si no existe
    public Paciente buscar(int documento) {
        return pacientes.get(documento);
    }

    // Atiende al que llego primero
    public Paciente atenderPorLlegada() {
        while (!colaLlegada.isEmpty()) {
            Paciente p = colaLlegada.poll();
            // Si ya fue atendido por la otra cola, lo saltamos
            if (!p.isAtendido()) {
                p.setAtendido(true);
                pacientes.remove(p.getDocumento());
                return p;
            }
        }
        return null;
    }

    // Atiende al mas grave
    public Paciente atenderPorGravedad() {
        while (!colaPrioridad.isEmpty()) {
            Paciente p = colaPrioridad.poll();
            // Si ya fue atendido por la otra cola, lo saltamos
            if (!p.isAtendido()) {
                p.setAtendido(true);
                pacientes.remove(p.getDocumento());
                return p;
            }
        }
        return null;
    }

    // Cantidad de pacientes esperando
    public int cantidadEnEspera() {
        return pacientes.size();
    }
}