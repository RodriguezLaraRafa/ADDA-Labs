package ejercicio1;

import java.io.IOException;
import java.util.Locale;


import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio1PLE {
	
	public static void solucion(String file) throws IOException {
		DatosAlmacenes.iniDatos(file);
		
		AuxGrammar.generate(DatosAlmacenes.class,"modelos/almacenes.lsi","ficheros/almacenes.lp");
		GurobiSolution solution = GurobiLp.gurobi("ficheros/almacenes.lp");
		Locale.setDefault(Locale.of("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
	}
	
	public static void main(String[] args) {
		try {
			solucion("resources/ejercicio1/DatosEntrada2.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
