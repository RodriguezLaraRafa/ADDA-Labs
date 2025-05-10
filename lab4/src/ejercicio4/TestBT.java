package ejercicio4;

import java.util.function.Predicate;

import org.jgrapht.GraphPath;


import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestBT {

	
	public static void main(String[] args) {

		DatosEstaciones.iniData("PI4_Students/resources/ejercicio4/DatosEntrada1.txt");

		EstacionesVertex vInicial = EstacionesVertex.initial();
		
		EGraph<EstacionesVertex, EstacionesEdge> graph = 
			EGraph.virtual(vInicial)
				.pathType(PathType.Sum)
				.type(Type.Min)
				.heuristic(EstacionesHeuristic::heuristic)
				.build();


		
		BT<EstacionesVertex,EstacionesEdge,SolucionEstaciones>alg_bt =BT.of(graph);

		
		var res = alg_bt.search().orElse(null);
		var outGraph = alg_bt.outGraph();
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
			System.out.println("Solucion BT: " + SolucionEstaciones.of(res) + "\n");
		else 
			System.out.println("BT no obtuvo solucion\n");
		
		
	}	
	
}
