package ejercicio3;

import java.util.function.Predicate;
import java.util.stream.IntStream;




public class FestivalHeuristic {

	
	public static Double heuristic(FestivalVertex v1, Predicate<FestivalVertex> goal, FestivalVertex v2) {
	    Double costeMinimoOptimista = 0.0;

	    for (int i = 0; i < DatosFestival.getNumTiposEntrada(); i++) {
	        int remainingTicketsToCoverOfType = v1.ticketsTypes().get(i);

	        for (int j = 0; j < DatosFestival.getNumAreas() && remainingTicketsToCoverOfType > 0; j++) {
	            Integer currentArea = DatosFestival.areasOfTypeSorted(i).get(j);
	            int availableCapacity = DatosFestival.getAforoMaximoArea(currentArea) - v1.areasTypes().get(currentArea);

	            if (availableCapacity > 0) {
	                int ticketsToAssign = Math.min(remainingTicketsToCoverOfType, availableCapacity);
	                costeMinimoOptimista += ticketsToAssign * DatosFestival.getCosteAsignacion(i, currentArea);
	                remainingTicketsToCoverOfType -= ticketsToAssign;
	            }
	        }

	        if (remainingTicketsToCoverOfType > 0) {
	            return Double.MAX_VALUE; 
	        }
	    }

	    return costeMinimoOptimista;
	}
	
}
