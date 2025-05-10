package ejercicio3;

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
		DatosFestival.iniDatos("PI4_Students/resources/ejercicio3/DatosEntrada1.txt");

		FestivalVertex vInicial = FestivalVertex.initial();
		
		EGraph<FestivalVertex, FestivalEdge> graph = //(FestivalVertex v_inicial, Predicate<FestivalVertex> es_terminal) { 
			EGraph.virtual(vInicial)
					.pathType(PathType.Sum)
					.type(Type.Max)
					.heuristic(FestivalHeuristic::heuristic)
					.build();

		GreedyOnGraph<FestivalVertex, FestivalEdge> alg_voraz = GreedyOnGraph.of(graph);		
		GraphPath<FestivalVertex, FestivalEdge> path = alg_voraz.path();
		path = alg_voraz.isSolution(path)? path: null;

		PDR<FestivalVertex,FestivalEdge,SolucionFestival> alg_pdr = path==null? PDR.of(graph):
			PDR.of(graph, null, path.getWeight(), path, true);
		
		var res = alg_pdr.search().orElse(null);
		var outGraph = alg_pdr.outGraph();
		if(outGraph!=null) {
			Predicate<FestivalVertex> vs = v -> res.getVertexList().contains(v);
			Predicate<FestivalEdge> es = e -> res.getEdgeList().contains(e);
			GraphColors.toDot(outGraph, "PI4_Students/resources/salida/ejercicio2/DatosSalida1.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, vs.test(v)),
					e -> GraphColors.colorIf(Color.red, es.test(e)));

		}	

		
		if(res!=null)
			System.out.println("Solucion PDR: " + SolucionFestival.of(res) + "\n");
		else 
			System.out.println("PDR no obtuvo solucion\n");
		
		
	}
	
	
	
}
