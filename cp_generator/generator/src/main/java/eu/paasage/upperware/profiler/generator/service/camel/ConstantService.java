package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.upperware.metamodel.cp.Constant;
import org.eclipse.emf.common.util.EList;

import java.util.Optional;

public interface ConstantService extends GeneratorService{

    Constant createIntegerConstant(int value);

    Constant createDoubleConstant(double value);

    Constant createFloatConstant(float value);


    Constant createIntegerConstant(int value, String name);

    Constant createDoubleConstant(double value, String name);

    Constant createFloatConstant(float value, String name);


    Optional<Constant> searchConstantByName(EList<Constant> constants, String name);

    Optional<Constant> searchConstantByValue(EList<Constant> constants, int value);

    Optional<Constant> searchConstantByValue(EList<Constant> constants, double value);

    Constant searchOrCreateConstantByValue(EList<Constant> constants, int value, String name);

    Constant searchOrCreateConstantByValue(EList<Constant> constants, double value, String name);


    String getVMProfileConstantName(String vmpId);
}
