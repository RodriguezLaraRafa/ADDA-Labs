package ejercicio2;

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

		DatosCursos.iniDatos("PI4_Students/resources/ejercicio2/DatosEntrada1.txt");

		CursosVertex vInicial = CursosVertex.initial();
		
		EGraph<CursosVertex, CursosEdge> graph = 
			EGraph.virtual(vInicial)
				.pathType(PathType.Sum)
				.type(Type.Max)
				.heuristic(CursosHeuristic::heuristic)
				.build();

		GreedyOnGraph<CursosVertex, CursosEdge> alg_voraz = GreedyOnGraph.of(graph);		
		GraphPath<CursosVertex, CursosEdge> path = alg_voraz.path();
		path = alg_voraz.isSolution(path)? path: null;

		path = null;
		
		BT<CursosVertex,CursosEdge,SolucionCursos>alg_bt = path==null? BT.of(graph):
			BT.of(graph, null, path.getWeight(), path, true);
		
		var res = alg_bt.search().orElse(null);
		var outGraph = alg_bt.outGraph();
		if(outGraph!=null) {
			Predicate<CursosVertex> vs = v -> res.getVertexList().contains(v);
			Predicate<CursosEdge> es = e -> res.getEdgeList().contains(e);
			GraphColors.toDot(outGraph, "PI4_Students/resources/salida/ejercicio2/DatosSalida1.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, vs.test(v)),
					e -> GraphColors.colorIf(Color.red, es.test(e)));

		}	

		if(res!=null)
			System.out.println("Solucion BT: " + SolucionCursos.of(res) + "\n");
		else 
			System.out.println("BT no obtuvo solucion\n");
		
		
	}	
	

}
