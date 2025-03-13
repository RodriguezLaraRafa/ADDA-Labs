package ejercicio3;

import java.io.IOException;
import java.util.Locale;

import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.solve.AuxGrammar;

public class Ejercicio3PLE {
    
    public static SolucionFestival solucion(String file) throws IOException {
    	return null;
    }
    
    public static void main(String[] args) throws IOException {
        DatosFestival.iniDatos("resources/ejercicio3/DatosEntrada3"+ ".txt");
        
        try {
        AuxGrammar.generate(DatosFestival.class,"modelos/festival.lsi","ficheros/festival.lp");
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		GurobiSolution solution = GurobiLp.gurobi("ficheros/festival.lp");
		Locale.setDefault(Locale.of("en", "US"));
		System.out.println(solution.toString((s,d)->d>0.));
        
    }
    
}
