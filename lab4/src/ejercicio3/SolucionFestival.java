package ejercicio3;

import java.util.List;
import java.util.Map;

import org.jgrapht.GraphPath;

import ejercicio2.CursosEdge;
import ejercicio2.CursosVertex;
import ejercicio2.SolucionCursos;

import java.util.HashMap;



    public class SolucionFestival {
    	
    	public static SolucionFestival of(List<Integer> ls) {
    		return new SolucionFestival(ls);
    	}

        public static SolucionFestival create(List<Integer> ls) {
            return new SolucionFestival(ls);
        }

        private int numAsignaciones;
        private Map<Integer, Integer> solucion;
        private Double costeTotal;
        private int unidadesTotales;
    	private List<Integer> path;

        private SolucionFestival(List<Integer> ls) {
        	numAsignaciones = ls.size();
        	solucion = new HashMap<>();
        	costeTotal = 0.0;
        	unidadesTotales = 0;
        	
        	
        	for(int i =0;i<ls.size();i++) {
    			Integer aforoAreaTipo = ls.get(i);
    			Integer currentType = i / DatosFestival.getNumAreas();
    			Integer currentArea = DatosFestival.getAreasOfTypeSortedByCost(currentType).get(i % DatosFestival.getNumAreas());
        		costeTotal += DatosFestival.getCosteAsignacion(currentType, currentArea) * ls.get(i);
        		unidadesTotales += aforoAreaTipo;
        		solucion.put(i, aforoAreaTipo);
        	}
        	
        }

        @Override
        public String toString() {
            StringBuilder result = new StringBuilder("Resumen de asignaciones:\n");

            Map<Integer, Integer> aforoOcupadoPorArea = new HashMap<>();
            Map<Integer, Map<Integer, Integer>> entradasPorArea = new HashMap<>();

            for (Map.Entry<Integer, Integer> entry : solucion.entrySet()) {
                Integer tipoEntrada = entry.getKey() / DatosFestival.getNumAreas();
                Integer sortedIndex = entry.getKey() % DatosFestival.getNumAreas();
                Integer area = DatosFestival.getAreasOfTypeSortedByCost(tipoEntrada).get(sortedIndex);
                Integer unidades = entry.getValue();

                if (unidades > 0) {
                    aforoOcupadoPorArea.put(area, aforoOcupadoPorArea.getOrDefault(area, 0) + unidades);
                    entradasPorArea.computeIfAbsent(area, k -> new HashMap<>())
                            .put(tipoEntrada, entradasPorArea.get(area).getOrDefault(tipoEntrada, 0) + unidades);
                }
            }

            for (int i = 0; i < DatosFestival.getNumAreas(); i++) {
                Integer aforoOcupado = aforoOcupadoPorArea.getOrDefault(i, 0);
                if (aforoOcupado > 0) {
                    result.append(String.format("Aforo área %s: %d/%d\n",
                            DatosFestival.getArea(i).nombre(),
                            aforoOcupado,
                            DatosFestival.getAforoMaximoArea(i)));

                    entradasPorArea.getOrDefault(i, new HashMap<>()).forEach((tipoEntrada, unidades) ->
                            result.append(String.format("Tipo de entrada %s asignadas: %d unidades\n",
                                    DatosFestival.getTipoEntrada(tipoEntrada).tipo(), unidades)
                    ));
                }
            }

            result.append(String.format("\nCoste total: %.2f\nUnidades totales: %d\n", costeTotal, unidadesTotales));

            return result.toString();
        }

        public Integer getNumAsignaciones() {
            return numAsignaciones;
        }

        public Map<Integer, Integer> getSolucion() {
            return solucion;
        }

        public Double getCosteTotal() {
            return costeTotal;
        }

        public Integer getUnidadesTotales() {
            return unidadesTotales;
        }
    	public static SolucionFestival of(GraphPath<FestivalVertex, FestivalEdge> path) {
    		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
    		SolucionFestival res = of(ls);
    		res.path = ls;
    		return res;
    	}
        
       
    }
