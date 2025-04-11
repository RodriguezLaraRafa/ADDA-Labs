package ejercicio1;

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
		DatosAlmacenes.iniDatos("PI4_Students/resources/ejercicio1/"+id_fichero);
		System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");

		// V�rtices clave

		AlmacenesVertex start = AlmacenesVertex.initial();

		// Grafo

		System.out.println("#### Algoritmo A* ####");

		// Algoritmo A*
		EGraph<AlmacenesVertex, AlmacenesEdge> graph =
					EGraph.virtual(start)
					.pathType(PathType.Sum)
					.type(Type.Max)
					.edgeWeight(x -> x.weight())
					.heuristic(AlmacenesHeuristic::heuristic)
					.build();
					
		AStar<AlmacenesVertex, AlmacenesEdge,?> aStar = AStar.ofGreedy(graph);
			
		GraphPath<AlmacenesVertex, AlmacenesEdge> gp = aStar.search().get();
			
		List<Integer> gp_as = gp.getEdgeList().stream().map(x -> x.action())
					.collect(Collectors.toList()); // getEdgeList();
	
		SolucionAlmacen s_as = SolucionAlmacen.of(gp);

		System.out.println(s_as);
		System.out.println(gp_as);

		GraphColors.toDot(aStar.outGraph(), "PI4_Students/resources/salida/ejercicio1/AlmacenesAStarGraph1.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red,v.goal()),
					e -> GraphColors.colorIf(Color.red, gp.getEdgeList().contains(e)));
	}
	

}

