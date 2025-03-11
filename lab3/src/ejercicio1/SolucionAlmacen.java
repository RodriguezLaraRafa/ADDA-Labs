package ejercicio1;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import ejercicio1.DatosAlmacenes.Producto;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agstopping.StoppingConditionFactory;

public class SolucionAlmacen {

	public static SolucionAlmacen create(List<Integer> ls) {
		return new SolucionAlmacen(ls);
	}

	private int numproductos;
	private Map<Producto, Integer> solucion;

	private SolucionAlmacen() {
		numproductos = 0;
		solucion = new HashMap<>();
	}

	private SolucionAlmacen(List<Integer> ls) {
		numproductos = 0;
		solucion = new HashMap<>();
		for (int i = 0; i < ls.size(); i++) {
			if (ls.get(i) > 0) {
				Integer e = ls.get(i);
				Producto product = DatosAlmacenes.getProducto(i % DatosAlmacenes.getNumProductos());
				numproductos += e;
				solucion.put(product, i / DatosAlmacenes.getNumProductos());
			}
		}
	}

	@Override
	public String toString() {
		return solucion.entrySet().stream()
				.map(p -> p.getKey().producto() + ": Almacen " + p.getValue())
				.collect(Collectors.joining("\n", "Reparto de productos y almacen en el que se coloca cada uno de ellos:\n", String.format("\nProductos colocados: %d", numproductos)));
	}

	public Integer getNumProductos() {
		return solucion.size();
	}

	public static void main(String[] args) {
		Locale.setDefault(Locale.of("en", "US"));

		AlgoritmoAG.ELITISM_RATE = 0.10;
		AlgoritmoAG.CROSSOVER_RATE = 0.95;
		AlgoritmoAG.MUTATION_RATE = 0.8;
		AlgoritmoAG.POPULATION_SIZE = 1000;

		//StoppingConditionFactory.NUM_GENERATIONS = 10;
		StoppingConditionFactory.MAX_ELAPSEDTIME = 50;
		StoppingConditionFactory.stoppingConditionType = StoppingConditionFactory.StoppingConditionType.ElapsedTime;

		Ejercicio1AG p = new Ejercicio1AG("resources/ejercicio1/DatosEntrada1.txt");

		AlgoritmoAG<List<Integer>, SolucionAlmacen> ap = AlgoritmoAG.of(p);
		ap.ejecuta();

		System.out.println("================================");
		System.out.println(ap.bestSolution());
		System.out.println("================================");
	}
}
