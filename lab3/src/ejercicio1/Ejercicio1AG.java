package ejercicio1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ejercicio1.DatosAlmacenes.Almacen;
import ejercicio1.DatosAlmacenes.Producto;
import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.AlgoritmoAG;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.agstopping.StoppingConditionFactory;
import us.lsi.common.Files2;
import us.lsi.common.List2;

public class Ejercicio1AG implements ValuesInRangeData<Integer, SolucionAlmacen> {

    public Ejercicio1AG(String file) {
        DatosAlmacenes.iniDatos(file);
    }

    @Override
    public Integer size() {
        return DatosAlmacenes.getNumAlmacenes() * DatosAlmacenes.getNumProductos();
    }

    @Override
    public ChromosomeType type() {
        return ChromosomeType.Binary;
    }

    @Override
    public Double fitnessFunction(List<Integer> ls) {
        double goal = 0, error = 0;
        Map<Integer, Integer> constraintOne = new HashMap<>(); //One product cannot be in 2 warehouses
        Map<Integer, Integer> constraintTwo = new HashMap<>(); //Products' sum of sizes cannot exceed maximum capability of warehouse
        int incompatibilitiesCounter = 0;

        Integer nProductos = DatosAlmacenes.getNumProductos();
        Integer nAlmacenes = DatosAlmacenes.getNumAlmacenes();

        for (int i = 0; i < size(); i++) {
            Integer currentWarehouse = i / nProductos;
            Integer currentProduct = i % nProductos;
            if (ls.get(i) > 0) {
                goal += 1;
                //first constraint
                if (constraintOne.containsKey(currentProduct)) {
                    constraintOne.put(constraintOne.get(currentProduct), constraintOne.get(currentProduct)+1);
                } else {
                	constraintOne.put(constraintOne.get(currentProduct), 1);
                }
                //second constraint
                Integer currentWeight = DatosAlmacenes.getMetrosCubicosProducto(currentProduct);
                if (constraintTwo.containsKey(currentWarehouse)) {
                    constraintTwo.put(constraintTwo.get(currentWarehouse), constraintTwo.get(currentWarehouse)+currentWeight);
                } else {
                    constraintTwo.put(constraintTwo.get(currentWarehouse), currentWeight);
                }
                //Third constraint
                for (int j = 0; j < nProductos; j++) {
                    if (j != currentProduct && ls.get(currentWarehouse * nProductos + j) > 0) {
                        if (DatosAlmacenes.sonIncompatibles(currentProduct, j)) {
                            incompatibilitiesCounter += 1;
                        }
                    }
                }
            }
            
        }
        error += constraintOne.keySet().stream().filter(x -> constraintOne.get(x) > 1).mapToInt(y -> constraintOne.get(y)).sum() * 20;
        error += IntStream.range(0, nAlmacenes)
                .filter(index -> constraintTwo.getOrDefault(index, 0) > DatosAlmacenes.getMetrosCubicosAlmacen(index))
                .map(index -> constraintTwo.getOrDefault(index, 0) - DatosAlmacenes.getMetrosCubicosAlmacen(index))
                .sum();
        error += incompatibilitiesCounter*20;

        return goal - 10000 * Math.pow(error, 2);
    }

    @Override
    public SolucionAlmacen solucion(List<Integer> ls) {
        return SolucionAlmacen.create(ls);
    }

    @Override
    public Integer max(Integer i) {
        return 2;
    }

    @Override
    public Integer min(Integer i) {
        return 0;
    }
}