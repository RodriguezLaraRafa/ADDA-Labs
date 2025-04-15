package ejercicio2;

import java.util.function.Predicate;

import org.jgrapht.GraphPath;


import ejercicio1.SolucionAlmacen;
import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.graphs.alg.GreedyOnGraph;
import us.lsi.graphs.alg.PDR;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.EGraph.Type;
import us.lsi.path.EGraphPath.PathType;

public class TestPD {

	
	public static void main(String[] args) {
		DatosCursos.iniDatos("PI4_Students/resources/ejercicio2/DatosEntrada3.txt");

		CursosVertex vInicial = CursosVertex.initial();
		
		EGraph<CursosVertex, CursosEdge> graph = //(CursosVertex v_inicial, Predicate<CursosVertex> es_terminal) { 
			EGraph.virtual(vInicial)
					.pathType(PathType.Sum)
					.type(Type.Max)
					.heuristic(CursosHeuristic::heuristic)
					.build();

		GreedyOnGraph<CursosVertex, CursosEdge> alg_voraz = GreedyOnGraph.of(graph);		
		GraphPath<CursosVertex, CursosEdge> path = alg_voraz.path();
		path = alg_voraz.isSolution(path)? path: null;

		PDR<CursosVertex,CursosEdge,SolucionAlmacen> alg_pdr = path==null? PDR.of(graph):
			PDR.of(graph, null, path.getWeight(), path, true);
		
		var res = alg_pdr.search().orElse(null);
		var outGraph = alg_pdr.outGraph();
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
			System.out.println("Solucion PDR: " + SolucionCursos.of(res) + "\n");
		else 
			System.out.println("PDR no obtuvo solucion\n");
		
		
	}

	
	
}
