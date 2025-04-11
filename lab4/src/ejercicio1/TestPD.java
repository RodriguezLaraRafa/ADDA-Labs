package ejercicio1;

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
		DatosAlmacenes.iniDatos("PI4_Students/resources/ejercicio1/DatosEntrada3.txt");

		AlmacenesVertex vInicial = AlmacenesVertex.initial();
		
		EGraph<AlmacenesVertex, AlmacenesEdge> graph = //(AlmacenesVertex v_inicial, Predicate<AlmacenesVertex> es_terminal) { 
			EGraph.virtual(vInicial)
					.pathType(PathType.Sum)
					.type(Type.Max)
					.heuristic(AlmacenesHeuristic::heuristic)
					.build();

		GreedyOnGraph<AlmacenesVertex, AlmacenesEdge> alg_voraz = GreedyOnGraph.of(graph);		
		GraphPath<AlmacenesVertex, AlmacenesEdge> path = alg_voraz.path();
		path = alg_voraz.isSolution(path)? path: null;

		PDR<AlmacenesVertex,AlmacenesEdge,SolucionAlmacen> alg_pdr = path==null? PDR.of(graph):
			PDR.of(graph, null, path.getWeight(), path, true);
		
		var res = alg_pdr.search().orElse(null);
		var outGraph = alg_pdr.outGraph();
		if(outGraph!=null) {
			Predicate<AlmacenesVertex> vs = v -> res.getVertexList().contains(v);
			Predicate<AlmacenesEdge> es = e -> res.getEdgeList().contains(e);
			GraphColors.toDot(outGraph, "PI4_Students/resources/salida/ejercicio1/DatosSalida1.gv", 
					v -> v.toGraph(),
					e -> e.action().toString(), 
					v -> GraphColors.colorIf(Color.red, vs.test(v)),
					e -> GraphColors.colorIf(Color.red, es.test(e)));

		}	

		
		if(res!=null)
			System.out.println("Solucion PDR: " + SolucionAlmacen.of(res) + "\n");
		else 
			System.out.println("PDR no obtuvo solucion\n");
		
		
	}

}

