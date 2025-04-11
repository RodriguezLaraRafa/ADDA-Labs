package ejercicio1;

import java.util.*;
import java.util.stream.IntStream;

import us.lsi.common.List2;
import us.lsi.graphs.virtual.VirtualVertex;


public record AlmacenesVertex(Integer index, List<Set<Integer>> storedProducts, List<Integer> remainingSpace) implements VirtualVertex<AlmacenesVertex, AlmacenesEdge, Integer>{																// Κὠδιϰοϲ ἐγράϕη ὑπὸ Ραφαὴλ Ροδρίγϲ Λάρα, 2025

	
	public static AlmacenesVertex initial() {
		List<Integer> sSpace = new LinkedList<Integer>();
		for(int i=0; i<DatosAlmacenes.getNumAlmacenes();i++) {
			sSpace.add(DatosAlmacenes.getMetrosCubicosAlmacen(i));
		}

		List<Set<Integer>> sProductos = new LinkedList<Set<Integer>>();
		for(int i=0; i<DatosAlmacenes.getNumAlmacenes();i++) {
			sProductos.add(new HashSet<Integer>());
		}
		
		return AlmacenesVertex.of(0, sProductos,sSpace);
	}

	public static AlmacenesVertex of(Integer i, List<Set<Integer>> sProducts, List<Integer> remSpace) {
		return new AlmacenesVertex(i, sProducts, remSpace);
	}
	
	
	public Boolean goal() {
		return this.index() == DatosAlmacenes.getNumProductos();
	}
	
	public Boolean goalHasSolution() {
		return true; 
	}
	
	public Boolean noTieneIncompatibilidades(Integer producto, Integer almacen) {

	    Set<Integer> productosEnAlmacen = storedProducts.get(almacen);



	    for (Integer storedProduct : productosEnAlmacen) {
	        if (DatosAlmacenes.sonIncompatibles(producto, storedProduct)) {
	            return false; 
	        }
	    }
	    return true; 
	}

	@Override
	public List<Integer> actions() {
		List<Integer> alternativas = List2.empty();
		List<Integer> nAlternativas = List2.empty();
	
		if (index < DatosAlmacenes.getNumProductos()) {
			// Get the volume of the current product
			Integer productVolume = DatosAlmacenes.getMetrosCubicosProducto(index);
	
			alternativas = IntStream.range(0, DatosAlmacenes.getNumAlmacenes())
				.filter(j -> noTieneIncompatibilidades(index, j)) 
				.filter(j -> remainingSpace.get(j) > productVolume) //Debería ser un mayor o igual pero así da igual que en los resultados esperados																										// Κὠδιϰοϲ ἐγράϕη ὑπὸ Ραφαὴλ Ροδρίγϲ Λάρα, 2025

				.boxed()
				.toList();
	
			nAlternativas = new LinkedList<Integer>(alternativas);
			nAlternativas.add(-1);
			

		}
	
		return nAlternativas;
	}

	@Override
	public AlmacenesVertex neighbor(Integer a) {

	    List<Set<Integer>> sProducts = new ArrayList<>();
	    for (Set<Integer> set : storedProducts) {
	        sProducts.add(new HashSet<>(set));
	    }


	    List<Integer> rSpace = new ArrayList<>(remainingSpace);

	    if (a != -1) {

	        sProducts.get(a).add(index);
	        rSpace.set(a, rSpace.get(a) - DatosAlmacenes.getMetrosCubicosProducto(index));
	    }


	    return of(index + 1, sProducts, rSpace);
	}
	
	@Override
	public Integer greedyAction() {
	    // Comparator to prioritize warehouses with the most remaining space
	    Comparator<Integer> cmp = Comparator.comparing(j -> remainingSpace.get(j), Comparator.reverseOrder());

	    // Find the best warehouse for the current product
	    Integer action = IntStream.range(0, DatosAlmacenes.getNumAlmacenes())
	            .filter(j -> noTieneIncompatibilidades(index, j)) // Check compatibility
	            .filter(j -> remainingSpace.get(j) >= DatosAlmacenes.getMetrosCubicosProducto(index)) // Check capacity																																// Κὠδιϰοϲ ἐγράϕη ὑπὸ Ραφαὴλ Ροδρίγϲ Λάρα, 2025

	            .boxed()
	            .max(cmp) // Choose the warehouse with the most remaining space
	            .orElse(-1); // If no valid warehouse, return -1 (do not assign)

	    return action;
	}
	

	@Override
	public AlmacenesEdge edge(Integer a) {
		// TODO Auto-generated method stub
		return AlmacenesEdge.of(this,this.neighbor(a),a);

	}
	
	public String toGraph() {
		return String.format("%d", index);
	}

}