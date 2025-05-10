package ejercicio4;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;


import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestAStar {

	
	public static void main(String[] args) {
		// Set up
		Locale.setDefault(Locale.of("en", "US"));

		String id_fichero = "DatosEntrada3.txt";
		DatosEstaciones.iniData("PI4_Students/resources/ejercicio4/"+id_fichero);
		System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

		// V�rtices clave

		EstacionesVertex start = EstacionesVertex.initial();

		// Grafo

		System.out.println("#### Algoritmo A* ####");

		// Algoritmo A*
		EGraph<EstacionesVertex, EstacionesEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Min)
					.edgeWeight(x -> x.weight())
					.heuristic(EstacionesHeuristic::heuristic)
					.build();
					
		AStar<EstacionesVertex, EstacionesEdge,?> aStar = AStar.of(graph);
			
		GraphPath<EstacionesVertex, EstacionesEdge> gp = aStar.search().get();
			
		List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList()); // getEdgeList();
	
		SolucionEstaciones s_as = SolucionEstaciones.of(gp);

		System.out.println(s_as);
		System.out.println(gp_as);

		GraphColors.toDot(aStar.outGraph(), "PI4_Students/resources/salida/ejercicio4/EstacionesAStarGraph1.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red,v.goal()),
					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
	}
	
	
}
