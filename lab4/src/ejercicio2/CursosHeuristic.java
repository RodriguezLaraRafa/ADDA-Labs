package ejercicio2;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;



public class CursosHeuristic {


	
	public static Double heuristic(CursosVertex v1, Predicate<CursosVertex> goal, CursosVertex v2) {
	    return IntStream.range(v1.index(), DatosCursos.getNumCursos())
	        .mapToDouble(DatosCursos::getRelevancia) 
	        .sum();
	}

}
