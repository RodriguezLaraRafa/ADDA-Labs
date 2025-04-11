package ejercicio1;

import java.util.stream.Collectors;

import us.lsi.graphs.virtual.SimpleEdgeAction;


public record AlmacenesEdge(AlmacenesVertex source, AlmacenesVertex target, Integer action, Double weight) 
implements SimpleEdgeAction<AlmacenesVertex,Integer> {

	public static AlmacenesEdge of(AlmacenesVertex v1, AlmacenesVertex v2, Integer a) {
	    Double peso = (a == -1) ? 0.0 : 1.0; 
	    return new AlmacenesEdge(v1, v2, a, peso);
	}

	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}

}
