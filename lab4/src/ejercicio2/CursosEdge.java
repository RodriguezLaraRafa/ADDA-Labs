package ejercicio2;


import us.lsi.graphs.virtual.SimpleEdgeAction;

public record CursosEdge(CursosVertex source, CursosVertex target, Integer action, Double weight)
implements SimpleEdgeAction<CursosVertex, Integer>{

	public static CursosEdge of(CursosVertex v1, CursosVertex v2, Integer a) {	
		double peso = a==1 ? (double) DatosCursos.getRelevancia(v1.index()) : 0;
		return new CursosEdge(v1, v2, a, peso);
	}

	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}
	
}
