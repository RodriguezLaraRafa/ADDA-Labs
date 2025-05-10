package ejercicio4;


import java.util.List;
import java.util.stream.IntStream;

import us.lsi.graphs.virtual.SimpleEdgeAction;

public record EstacionesEdge(EstacionesVertex source, EstacionesVertex target, Integer action, Double weight)
implements SimpleEdgeAction<EstacionesVertex, Integer>{

	public static EstacionesEdge of(EstacionesVertex v1, EstacionesVertex v2, Integer a) {
		
		
		double peso = 0.0;
		
		if(v1.index()!=0) {
	    peso= DatosEstaciones.getAverageTimeSection(
	        v2.path().get(v2.path().size() - 2), 
	        a
	    );
		}
	    
	    if(v2.index().equals(DatosEstaciones.getNumStations())) {
	    	peso+= DatosEstaciones.getAverageTimeSection(v2.path().getLast(), v2.path().get(0));
	    }
	    
	    return new EstacionesEdge(v1, v2, a, peso);
	}
	
	
	@Override
	public String toString() {
		return String.format("%d; %.1f", action, weight);
	}
}
