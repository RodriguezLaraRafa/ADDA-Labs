package ejercicio2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public class Ejercicio2AG implements ValuesInRangeData<Integer, SolucionCursos> {
	public Ejercicio2AG(String file){
		DatosCursos.iniDatos(file);
	}

	@Override
	public Integer size() {
		// TODO Auto-generated method stub
		return DatosCursos.getNumCursos();
	}

	@Override
	public ChromosomeType type() {
		// TODO Auto-generated method stub
		return ChromosomeType.Binary;
	}

	@Override
	public Double fitnessFunction(List<Integer> value) {
		// TODO Auto-generated method stub
		Double goal = 0.0;
		Double error = 0.0;
		int nSelected = 0;
		int totalDuration = 0;
		int totalCost = 0;
		Map<Integer, Integer> coursesPerArea = new HashMap<Integer, Integer>();
		for(int i=0; i<size();i++) {
			if(value.get(i)==1) {
				goal+=DatosCursos.getRelevancia(i);
				
				//At least one course per area
				Integer currentArea = DatosCursos.getArea(i);
				
				if(coursesPerArea.containsKey(currentArea)) {
					coursesPerArea.put(currentArea, coursesPerArea.get(currentArea)+1);
				} else {
					coursesPerArea.put(currentArea, 1);
				}
				
				//Third constraint
				nSelected+=1;
				totalDuration+=DatosCursos.getDuracion(i);
				totalCost+= DatosCursos.getCoste(i);
				
				
				
			
				
			}
			
			
		}
		error+= (DatosCursos.getNumAreas() - coursesPerArea.entrySet().stream().count())*20;
		if(coursesPerArea.containsKey(0) && coursesPerArea.get(0)< coursesPerArea.entrySet().stream().filter(x->x.getKey()!=0).count()) {  //El índice 0 supuestamente es tecnología, pero no lo dejan muy claro
		error+=  coursesPerArea.entrySet().stream().filter(x->x.getKey()!=0).count() - coursesPerArea.get(0);
		}
		if(nSelected>0 && totalDuration/nSelected >20) {
			error+= 20 - totalDuration/nSelected;
		}
		if(totalCost > DatosCursos.getPresupuestoTotal()) {
			error += totalCost - DatosCursos.getPresupuestoTotal();
		}
		
		
		
		
		
        return goal - 10000 * error;

	}

	@Override
	public SolucionCursos solucion(List<Integer> value) {
		// TODO Auto-generated method stub
		return SolucionCursos.create(value);
	}

	@Override
	public Integer max(Integer i) {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public Integer min(Integer i) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
