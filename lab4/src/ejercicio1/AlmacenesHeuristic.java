package ejercicio1;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class AlmacenesHeuristic {

	//Optimista: todos los productos van a ser cogidos
    public static Double heuristic(AlmacenesVertex v1, Predicate<AlmacenesVertex> goal, AlmacenesVertex v2) {
        return IntStream.range(v1.index(), DatosAlmacenes.getNumProductos())
                .mapToDouble(i -> mejorOpcion(i, 
                        v1.remainingSpace().stream().mapToInt(Integer::intValue).boxed().collect(Collectors.toList())))
                .sum();
    }

	private static Double mejorOpcion(Integer i, List<Integer> remaining) {
		return IntStream.range(0, DatosAlmacenes.getNumAlmacenes())
				//.filter(j -> remaining.get(j)>0)
				.mapToDouble(j -> DatosAlmacenes.getMetrosCubicosAlmacen(j)).max().orElse(0); //Igual hay que poner -1
	}	
	
}
