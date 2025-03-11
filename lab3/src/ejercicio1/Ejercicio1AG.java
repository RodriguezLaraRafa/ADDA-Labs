package ejercicio1;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
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
        LinkedList<Integer> constraintOne = new LinkedList<>(); //One product cannot be in 2 warehouses
        LinkedList<Integer> constraintTwo = new LinkedList<>(); //Products' sum of sizes cannot exceed maximum capability of warehouse
        int incompatibilitiesCounter = 0;

        Integer nProductos = DatosAlmacenes.getNumProductos();
        Integer nAlmacenes = DatosAlmacenes.getNumAlmacenes();

        for (int i = 0; i < size(); i++) {
            Integer currentWarehouse = i / nProductos;
            Integer currentProduct = i % nProductos;
            if (ls.get(i) > 0) {
                goal += ls.get(i);
                //first constraint
                if (constraintOne.size() <= currentProduct) {
                    constraintOne.add(ls.get(i));
                } else {
                    constraintOne.set(currentProduct, constraintOne.get(currentProduct) + ls.get(i));
                }
                //second constraint
                Integer currentWeight = DatosAlmacenes.getMetrosCubicosProducto(currentProduct);
                if (constraintTwo.size() <= currentWarehouse) {
                    constraintTwo.add(currentWeight);
                } else {
                    constraintTwo.set(currentWarehouse, currentWeight + constraintTwo.get(currentWarehouse));
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
        error += constraintOne.stream().filter(x -> x > 1).mapToInt(y -> y).sum() * 20;
        error += IntStream.range(0, constraintTwo.size())
                .filter(index -> constraintTwo.get(index) > DatosAlmacenes.getMetrosCubicosAlmacen(index))
                .map(index -> constraintTwo.get(index) * 20)
                .sum();     
        
        error += incompatibilitiesCounter*20;

        return goal - 10000 * error;
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