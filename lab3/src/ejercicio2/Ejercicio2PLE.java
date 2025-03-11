package ejercicio2;

import java.io.IOException;
import java.util.Locale;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio2PLE {
    
    public static void solucion(String file) throws IOException {
    	 DatosCursos.iniDatos(file);
		try {
 			AuxGrammar.generate(DatosCursos.class,"modelos/cursos.lsi","ficheros/cursos.lp");
 		} catch (IOException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
 		GurobiSolution solution = GurobiLp.gurobi("ficheros/cursos.lp");
 		Locale.setDefault(Locale.of("en", "US"));
 		System.out.println(solution.toString((s,d)->d>0.));
    	 
    }
    
    
    public static void main(String[] args) {
		try {
			solucion("resources/ejercicio2/DatosEntrada1.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
