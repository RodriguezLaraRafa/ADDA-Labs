package ejercicio1;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class AlmacenesHeuristic {

	//Optimista: todos los productos van a ser cogidos
	 public static Double heuristic(AlmacenesVertex v1, Predicate<AlmacenesVertex> goal, AlmacenesVertex v2) {																						// Κὠδιϰοϲ ἐγράϕη ὑπὸ Ραφαὴλ Ροδρίγϲ Λάρα, 2025

	        return IntStream.range(v1.index(), DatosAlmacenes.getNumProductos())
	                .mapToDouble(i -> 1.0) 
	                .sum();
	    }


	
}
