package ejercicio2;
import java.util.*;
import java.util.stream.Collectors;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CursosVertex(Integer index, List<Integer> selectedCourses, Integer remainingBudget, Set<Integer> coveredAreas)
implements VirtualVertex<CursosVertex, CursosEdge, Integer>{
	
	
	public static CursosVertex initial() {
		
		return CursosVertex.of(0, new LinkedList<Integer>(),DatosCursos.getPresupuestoTotal() , new HashSet<Integer>());
		
	}
	
	public static CursosVertex of(Integer index, List<Integer> selectedCourses, Integer remainingBudget, Set<Integer> coveredAreas) {
		return new CursosVertex(index, selectedCourses, remainingBudget, coveredAreas);
	}
	
	public Boolean goal() {
		return this.index() == DatosCursos.getNumCursos();
	}
	
	public Boolean goalHasSolution() {
		Boolean allAreasAreCovered = coveredAreas.size() == DatosCursos.getNumAreas();
		Boolean moreTechnologyCourses = selectedCourses.stream()
			    .filter(x -> DatosCursos.getArea(x) == 0)
			    .count() >=
			    selectedCourses.stream()
			    .filter(x -> DatosCursos.getArea(x) != 0)
			    .collect(Collectors.groupingBy(DatosCursos::getArea, Collectors.counting()))
			    .values()
			    .stream()
			    .max(Long::compare)
			    .orElse(0L);			
			    boolean averageGreaterTwenty;
		try {
			
		
		
			averageGreaterTwenty = selectedCourses.stream().collect(Collectors.summingInt(x->DatosCursos.getDuracion(x))) / selectedCourses.size() >= 20;
		} catch (ArithmeticException e){
			averageGreaterTwenty = false;
		}
			Boolean doesNotExceedBudget = remainingBudget >=0;
		return allAreasAreCovered && moreTechnologyCourses && averageGreaterTwenty && doesNotExceedBudget;
	}
	
	public List<Integer> actions () {
		
		List<Integer> alternativas = new LinkedList<>();

		if (index< DatosCursos.getNumCursos()) {
			alternativas.add(0);
			alternativas.add(1);
			
			
		}
		
		return alternativas;
		
	}
	
	public CursosVertex neighbor(Integer a) {
		Integer indice = index+1;
		List<Integer> sCourses= new LinkedList<Integer>(selectedCourses);
		int rBudget = remainingBudget;
		Set<Integer> cAreas = new HashSet<Integer>(coveredAreas);

		if(a==1) {
			sCourses.add(index);
			cAreas.add(DatosCursos.getArea(index));
			rBudget = rBudget - DatosCursos.getCoste(index);
		}
		return of(indice, sCourses, rBudget, cAreas);
		
	}
	
	public CursosEdge edge(Integer a) {
		// TODO Auto-generated method stub
		return CursosEdge.of(this,this.neighbor(a),a);

	}
	
	public String toGraph() {
		return String.format("%d", index);
	}
	
	

}
