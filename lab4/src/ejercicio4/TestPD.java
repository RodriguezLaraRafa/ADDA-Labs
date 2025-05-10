package ejercicio4;

import java.util.function.Predicate;

import org.jgrapht.GraphPath;


import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestPD {

	public static void main(String[] args) {
		DatosEstaciones.iniData("PI4_Students/resources/ejercicio4/DatosEntrada3.txt");

		EstacionesVertex vInicial = EstacionesVertex.initial();
		
		EGraph<EstacionesVertex, EstacionesEdge> graph = //(EstacionesVertex v_inicial, Predicate<EstacionesVertex> es_terminal) { 
			EGraph.virtual(vInicial)
					.pathType(PathType.Sum)
					.type(Type.Min)
					.heuristic(EstacionesHeuristic::heuristic)
					.build();

		GreedyOnGraph<EstacionesVertex, EstacionesEdge> alg_voraz = GreedyOnGraph.of(graph);		
		GraphPath<EstacionesVertex, EstacionesEdge> path = alg_voraz.path();
		path = alg_voraz.isSolution(path)? path: null;

		PDR<EstacionesVertex,EstacionesEdge,SolucionEstaciones> alg_pdr = path==null? PDR.of(graph):
			PDR.of(graph, null, path.getWeight(), path, true);
		
		var res = alg_pdr.search().orElse(null);
		var outGraph = alg_pdr.outGraph();
		if(outGraph!=null) {
			Predicate<EstacionesVertex> vs = v -> res.getVertexList().contains(v);
			Predicate<EstacionesEdge> es = e -> res.getEdgeList().contains(e);
			GraphColors.toDot(outGraph, "PI4_Students/resources/salida/ejercicio4/DatosSalida1.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, vs.test(v)),
					e -> GraphColors.colorIf(Color.red, es.test(e)));

		}	

		
		if(res!=null)
			System.out.println("Solucion PDR: " + SolucionEstaciones.of(res) + "\n");
		else 
			System.out.println("PDR no obtuvo solucion\n");
		
		
	}
	
	
	
}
