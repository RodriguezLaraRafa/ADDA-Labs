package ejercicio4;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;

public class SolucionEstaciones {

    public static SolucionEstaciones of(List<Integer> ls) {
        return new SolucionEstaciones(ls);
    }

    public static SolucionEstaciones of(GraphPath<EstacionesVertex, EstacionesEdge> path) {
        List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
        SolucionEstaciones res = of(ls);
        res.path = ls;
        return res;
    }

    private List<Integer> path;
    private Integer numEstaciones;
    private List<Estacion> camino;
    private Double tiempoTotal;

    private SolucionEstaciones(List<Integer> ls) {
        if (ls.isEmpty()) {
            throw new IllegalArgumentException("The path cannot be empty.");
        }

        numEstaciones = DatosEstaciones.getNumStations();
        camino = new LinkedList<>();
        tiempoTotal = 0.0;

        for (int i = 0; i < ls.size(); i++) {
            camino.add(DatosEstaciones.getStation(ls.get(i)));
            if (i > 0) {
                try {
                    tiempoTotal += DatosEstaciones.getAverageTimeSection(ls.get(i - 1), ls.get(i));
                } catch (Exception e) {
                    throw new IllegalStateException("Error calculating time between stations " + ls.get(i - 1) + " and " + ls.get(i), e);
                }
            }
        }

        // Add the time to return to the first station
        tiempoTotal += DatosEstaciones.getAverageTimeSection(ls.get(ls.size() - 1), ls.get(0));
        camino.add(camino.get(0));
        
        
        GraphColors.toDot(DatosEstaciones.getGraph(),"PI4_Students/resources/ejercicio4/DatosEntrada1.gv",
				x->x.nombre() + " - " + camino.indexOf(x),
				x->x.toString(),
				v->GraphColors.colorIf(Color.red, Color.green, v.satisfaccionClientes()>=7),
				e->GraphColors.style(Style.bold));
        
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Resumen del recorrido:\n");

        result.append("Camino seguido: ").append(camino.stream()
                .map(Estacion::nombre)
                .collect(Collectors.joining(" -> "))).append("\n");

        result.append(String.format("Tiempo total: %.2f min\n", tiempoTotal));

        return result.toString();
    }

    public Integer getNumEstaciones() {
        return numEstaciones;
    }

    public List<Estacion> getCamino() {
        return camino;
    }

    public Double getTiempoTotal() {
        return tiempoTotal;
    }
}
