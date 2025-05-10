package ejercicio3;


import us.lsi.graphs.virtual.SimpleEdgeAction;

public record FestivalEdge (FestivalVertex source, FestivalVertex target, Integer action, Double weight)
implements SimpleEdgeAction<FestivalVertex, Integer>{
	
	public static FestivalEdge of(FestivalVertex v1, FestivalVertex v2, Integer a) {
		Integer currentType = v1.z() / DatosFestival.getNumAreas();
		Integer currentArea = DatosFestival.areasOfTypeSorted(currentType).get(v1.z() % DatosFestival.getNumAreas());
		
		double peso = DatosFestival.getCosteAsignacion(currentType, currentArea) * a;
		return new FestivalEdge(v1, v2, a, peso);
	}
	
	

}
