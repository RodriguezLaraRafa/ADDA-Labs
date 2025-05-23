package ejercicio3;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Map2;
import us.lsi.common.String2;

public class DatosFestival {
    
    public static record Area(String nombre, Integer aforoMaximo) {        
        
        public static Area create(String s) {
            String[] v0 = s.split(":");
            String[] v1 = v0[1].split(";");
            String a = v1[0].split("=")[1].trim();
            return new Area(v0[0].trim(), Integer.parseInt(a));
        }    
        
        @Override
        public String toString() {        
            return nombre + ": " + aforoMaximo + "; ";
        }
    }
    
    public static record TipoEntrada(String tipo, Integer cuotaMinima, Map<Integer,Integer> costeAsignacion) {
        
        public static TipoEntrada create(String s) {
            String[] v0 = s.split("->");
            String[] v1 = v0[1].trim().split(";");
            Integer a = Integer.parseInt(v1[0].split("=")[1].trim());
            
            Map<Integer,Integer> b = Map2.empty();
            String[] v2 = v1[1].split("=")[1].trim().split(",");
            for(String e: v2) {
                String[] v3 = e.trim().split("[:()]");
                b.put(Integer.parseInt(v3[1].trim()), Integer.parseInt(v3[2].trim()));
            }
            return new TipoEntrada(v0[0].trim(), a, b);
        }    
        
        @Override
        public String toString() {        
            return tipo + ": " + cuotaMinima + "; " + costeAsignacion + "; ";
        }
    }

    public static Boolean tests = false;
    private static List<Area> areas;
    private static List<TipoEntrada> tiposEntrada;
    private static List<List<Integer>> areasOfTypesSortedByCost;
    
    
    public static List<Integer> getAreasOfTypeSortedByCost(int i) {
        Map<Integer, Integer> mapa = new HashMap<>();
        for (int j = 0; j < DatosFestival.getNumAreas(); j++) {
            mapa.put(j, DatosFestival.getCosteAsignacion(i, j));
        }
        List<Integer> output = new LinkedList<>(mapa.keySet());
        output.sort((a, b) -> mapa.get(a).compareTo(mapa.get(b)));
        return output;
    }
    
    public static List<Integer> areasOfTypeSorted(int i){
    	return areasOfTypesSortedByCost.get(i);
    }
    
    
    public static void iniDatos(String fichero) {
        areas = List2.empty();
        tiposEntrada = List2.empty();
        for(String s: Files2.linesFromFile(fichero)) {
            if(s.startsWith("//"))
                continue;
            else if(s.startsWith("A"))
                areas.add(Area.create(s));
            else if (s.startsWith("T"))
                tiposEntrada.add(TipoEntrada.create(s));
        }
        if(!tests)
            toConsole();
        
        areasOfTypesSortedByCost = new LinkedList<List<Integer>>();
        for(int i=0;i<DatosFestival.getNumTiposEntrada();i++) {
        	List<Integer> areasOfThisType = DatosFestival.getAreasOfTypeSortedByCost(i);
        	areasOfTypesSortedByCost.add(areasOfThisType);
        }
        
    }
    
    public static Integer getNumTiposEntrada() {
        return tiposEntrada.size();
    }
    
    public static Integer getNumAreas() {
        return areas.size();
    }
    
    public static Integer getCosteAsignacion(Integer i, Integer j) {
        return tiposEntrada.get(i).costeAsignacion().get(j);
    }
    
    public static Integer getAforoMaximoArea(Integer i) {
        return areas.get(i).aforoMaximo();
    }
    
   
    
    public static TipoEntrada getTipoEntrada(Integer i) {
        return tiposEntrada.get(i);
    }    
    
    public static Area getArea(Integer j) {
        return areas.get(j);
    }
    
    public static Integer getCuotaMinima(Integer i) {
        return DatosFestival.getTipoEntrada(i).cuotaMinima();
    }
    
    public static void toConsole() {
        String2.toConsole(areas, "Áreas");
        String2.toConsole(tiposEntrada, "Tipos de Entrada");
        String2.toConsole(String2.linea());
    }

    public static void main(String[] args) throws IOException {
        iniDatos("resources/ejercicio3/DatosEntrada1.txt");
    }
}