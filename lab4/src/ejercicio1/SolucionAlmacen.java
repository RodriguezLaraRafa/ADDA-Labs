package ejercicio1;

import java.util.*;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import ejercicio1.DatosAlmacenes.Producto;


public class SolucionAlmacen {
	
	public static SolucionAlmacen of(List<Integer> ls) {
		return new SolucionAlmacen(ls);
	}

	private Integer numproductos;
	private Map<Producto, Integer> solucion;
	private List<Integer> path;


	private SolucionAlmacen(List<Integer> ls) {
		numproductos = 0;
		solucion = new HashMap<>();
		
		for(int i=0; i<ls.size();i++) {
			
			if(ls.get(i)>-1) {
				numproductos++;
				solucion.put(DatosAlmacenes.getProducto(i), ls.get(i));
			}
			
			
		}
	}
	
	public static SolucionAlmacen of(GraphPath<AlmacenesVertex, AlmacenesEdge> path) {
		List<Integer> ls = path.getEdgeList().stream().map(e -> e.action()).toList();
		SolucionAlmacen res = of(ls);
		res.path = ls;
		return res;
	}
	

	
	@Override
	public String toString() {
		return solucion.entrySet().stream()
		.map(p -> p.getKey().producto()+": Almacen "+p.getValue())
		.collect(Collectors.joining("\n", "Reparto de productos y almacen en el que se coloca cada uno de ellos:\n", String.format("\nProductos colocados: %d", numproductos)));
	}
	
	public Integer getNumProductos() {
    	return solucion.size();
    }

}
