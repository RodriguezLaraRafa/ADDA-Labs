package ejercicio4;

import java.util.*;
import java.util.stream.IntStream;


import us.lsi.graphs.virtual.VirtualVertex;

public record EstacionesVertex(Integer index, List<Integer> path, Double accCost) 


implements VirtualVertex<EstacionesVertex, EstacionesEdge, Integer>{
	
	public static EstacionesVertex initial() {
		return EstacionesVertex.of(0, new LinkedList<Integer>(), 0.0);
	}
	
	public static EstacionesVertex of(Integer index, List<Integer> path, Double accCost) {
		return new EstacionesVertex(index, path, accCost);
	}
	
	@Override
	public Boolean goal() {

			
	    	return this.index().equals(DatosEstaciones.getNumStations());
	}
	
	public Boolean goalHasSolution() {
	    Integer consecutiveSatisfactoryStations = 0;

	    for (int i = 1; i < path.size(); i++) {
	        if (DatosEstaciones.getClientsSatisfaction(path.get(i - 1)) >= 7 &&
	            DatosEstaciones.getClientsSatisfaction(path.get(i)) >= 7) {
	            consecutiveSatisfactoryStations++;
	        }
	    }

	    if (!path.isEmpty() && DatosEstaciones.getClientsSatisfaction(path.get(path.size() - 1)) >= 7 &&
	        DatosEstaciones.getClientsSatisfaction(path.get(0)) >= 7) {
	        consecutiveSatisfactoryStations++;
	    }

	    Double totalCost = accCost + DatosEstaciones.sectionCost(path.get(path.size() - 1), path.get(0));

	    Double tiempoTotal = 0.0;
	    tiempoTotal += DatosEstaciones.getAverageTimeSection(path.get(path.size() - 1), path.get(0));

	    Boolean costCondition = totalCost <= 0.75 * DatosEstaciones.costAllSections();
	    Boolean satisfactionCondition = consecutiveSatisfactoryStations >= 2;
	    
	    Boolean firstAndLastConnected = DatosEstaciones.areConnected(path.get(0), path.getLast());

	   
	    
	    return costCondition && satisfactionCondition && firstAndLastConnected;
	}
	
	public List<Integer> actions() {

		
		
		if(index.equals(0)) {
			return IntStream.range(0, DatosEstaciones.getNumStations()).boxed().toList();
		}
		
		 
		
		return IntStream.range(0, DatosEstaciones.getNumStations())
		        .filter(x -> !path.contains(x) && DatosEstaciones.areConnected(path().getLast(), x))
		        .boxed()
		       // .sorted(Comparator.comparing(y->-DatosEstaciones.getAverageTimeSection(path.getLast(), y)))
		        .toList();
		
	}
	//a
	@Override
	public EstacionesVertex neighbor(Integer a) {
	    List<Integer> newPath = new LinkedList<>(path);
	    newPath.add(a);

	    Double nuevoCoste = accCost;
	    if (!path.isEmpty() && a < DatosEstaciones.getNumStations()) {
	        nuevoCoste += DatosEstaciones.sectionCost(path.get(path.size() - 1), a);
	    }
	    if(index().equals(DatosEstaciones.getNumStations())) {
	    	nuevoCoste+= DatosEstaciones.sectionCost(a, path().get(0));
	    }
	    

	    return EstacionesVertex.of(index + 1, newPath, nuevoCoste);
	}
	
	@Override
	public EstacionesEdge edge(Integer a) {
		// TODO Auto-generated method stub
		return EstacionesEdge.of(this,this.neighbor(a),a);

	}
	
	public String toGraph() {
		return String.format("%d", index);
	}

}
