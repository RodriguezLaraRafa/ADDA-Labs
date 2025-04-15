package ejercicio2;
import java.util.*;
import java.util.stream.Collectors;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;

public record CursosVertex(Integer index, List<Integer> selectedCourses, Integer remainingBudget, Set<Integer> coveredAreas)																																									// Κὠδιϰοϲ ἐγράϕη ὑπὸ Ραφαὴλ Ροδρίγϲ Λάρα, 2025

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
			
		
		
			averageGreaterTwenty = selectedCourses.stream().collect(Collectors.summingInt(x->DatosCursos.getDuracion(x))) / selectedCourses.size() >= 20;																																									// Κὠδιϰοϲ ἐγράϕη ὑπὸ Ραφαὴλ Ροδρίγϲ Λάρα, 2025

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
	
	public Integer greedyAction() {

	    if (index >= DatosCursos.getNumCursos()) {
	        return 0;
	    }


	    int courseIndex = this.index();
	    int courseCost = DatosCursos.getCoste(courseIndex);
	    int courseDuration = DatosCursos.getDuracion(courseIndex);
	    int courseArea = DatosCursos.getArea(courseIndex);


	    boolean exceedsBudget = this.remainingBudget() - courseCost < 0;
	    boolean invalidDuration = (this.selectedCourses().size() + 1) > 0 &&
	        (this.selectedCourses().stream()
	            .mapToInt(DatosCursos::getDuracion)
	            .sum() + courseDuration) / (this.selectedCourses().size() + 1) < 20;
	    boolean areaAlreadyCovered = this.coveredAreas().contains(courseArea);
	    boolean maintainsTechnologyDominance = this.selectedCourses().stream()
	        .filter(x -> DatosCursos.getArea(x) == 0).count() + (courseArea == 0 ? 1 : 0) >=
	        this.selectedCourses().stream()
	            .filter(x -> DatosCursos.getArea(x) != 0)
	            .collect(Collectors.groupingBy(DatosCursos::getArea, Collectors.counting()))
	            .values()
	            .stream()
	            .max(Long::compare)
	            .orElse(0L);


	    if (!exceedsBudget && !invalidDuration && !areaAlreadyCovered && maintainsTechnologyDominance) {
	        return 1;
	    }


	    return 0;
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
