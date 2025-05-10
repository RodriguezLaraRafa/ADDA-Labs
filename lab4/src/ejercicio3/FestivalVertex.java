package ejercicio3;

import java.util.*;

import us.lsi.graphs.virtual.VirtualVertex;

public record FestivalVertex(Integer z, List<Integer> ticketsTypes, List<Integer> areasTypes) 

implements VirtualVertex<FestivalVertex, FestivalEdge, Integer>{
	
	
	public static FestivalVertex initial() {
		List<Integer> tTypes = new LinkedList<Integer>();
		List<Integer> aTypes = new LinkedList<Integer>();
		
		for(int i=0; i<DatosFestival.getNumTiposEntrada();i++) {
			tTypes.add(DatosFestival.getCuotaMinima(i));
		}
		
		for(int i=0; i<DatosFestival.getNumAreas();i++) {
			aTypes.add(0);
		}
		
		
		return FestivalVertex.of(0, tTypes, aTypes);
	}
	
	public static FestivalVertex of(Integer z, List<Integer> ticketsTypes, List<Integer> areasTypes) {
		return new FestivalVertex(z, ticketsTypes, areasTypes);
	}
	
	public Boolean goal() {
		return this.z() == DatosFestival.getNumAreas() * DatosFestival.getNumTiposEntrada();
	}
	
	public Boolean goalHasSolution() {
		for(int i=0; i<DatosFestival.getNumAreas();i++) {
			if(this.areasTypes().get(i) > DatosFestival.getAforoMaximoArea(i)) {
				return false;
			}
		}
		
		for(int i=0; i<DatosFestival.getNumTiposEntrada();i++) {
			if(this.ticketsTypes().get(i) > 0) {
				return false;
			}
		}
		return true;
	}
	

	@Override
	public List<Integer> actions() {
		
		Integer currentType = this.z() / DatosFestival.getNumAreas();
		Integer currentArea = DatosFestival.areasOfTypeSorted(currentType).get(this.z() % DatosFestival.getNumAreas());
		
		List<Integer> opciones = new LinkedList<Integer>();
		
		for(int i=0; i<= this.ticketsTypes().get(currentType);i++) {
			if (DatosFestival.getAforoMaximoArea(currentArea) >= areasTypes().get(currentArea) + i) {
			    opciones.add(i);
			}
		}
		
		return opciones;
		
	}

	@Override
	public FestivalVertex neighbor(Integer a) {
		// TODO Auto-generated method stub
		Integer nIndice = z() + 1;
		List<Integer> newTicketsTypes = new LinkedList<Integer>(ticketsTypes());
		List<Integer> newAreasTypes = new LinkedList<Integer>(areasTypes());
		Integer currentType = this.z() / DatosFestival.getNumAreas();
		Integer currentArea = DatosFestival.areasOfTypeSorted(currentType).get(this.z() % DatosFestival.getNumAreas());
		
		newTicketsTypes.set(currentType, newTicketsTypes.get(currentType) - a);
		newAreasTypes.set(currentArea, newAreasTypes.get(currentArea) + a);

		return FestivalVertex.of(nIndice, newTicketsTypes, newAreasTypes);
		
	}
	
	
	public Integer greedyAction() {
		List<Integer> actions = new LinkedList<>();
		
		Integer currentType = this.z() / DatosFestival.getNumAreas();
		Integer currentArea = DatosFestival.areasOfTypeSorted(currentType).get(this.z() % DatosFestival.getNumAreas());
		List<Integer> opciones = new LinkedList<Integer>();
		
		for(int i=0; i<= this.ticketsTypes().get(currentType);i++) {
			if (DatosFestival.getAforoMaximoArea(currentArea) >= areasTypes().get(currentArea) + i) {
			    opciones.add(i);
			}
		}
		
		return opciones.getLast();
	}
	
	
	

	@Override
	public FestivalEdge edge(Integer a) {
		// TODO Auto-generated method stub
		return FestivalEdge.of(this, this.neighbor(a), a);
	}
	
	public String toGraph() {
		return String.format("%d", z());
	}
	
	

}
