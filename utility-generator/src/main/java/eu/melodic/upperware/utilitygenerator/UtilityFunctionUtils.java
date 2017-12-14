package eu.melodic.upperware.utilitygenerator;

import eu.melodic.upperware.utilitygenerator.model.VirtualMachine;

import java.util.Collection;

class UtilityFunctionUtils {

  static double normalize(double min, double max, double x){
    return (x-min)/(max-min);
  }

  static int countVirtualMachines(Collection<VirtualMachine> configuration){
    int counter = 0;
    for (VirtualMachine vm: configuration){
      counter += vm.getCount();
    }
    return counter;
  }
}
