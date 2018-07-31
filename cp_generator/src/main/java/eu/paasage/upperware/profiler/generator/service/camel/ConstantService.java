package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.VariableType;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import org.eclipse.emf.common.util.EList;

import java.util.Optional;

public interface ConstantService extends GeneratorService{

    String getConstantName(VariableType variableType, String vmName, String... suffixes);

    Constant createIntegerConstant(int value);

    Constant createDoubleConstant(double value);

    Constant createFloatConstant(float value);


    Constant createIntegerConstant(int value, String name);

    Constant createDoubleConstant(double value, String name);

    Constant createFloatConstant(float value, String name);

    Constant createLongConstant(long value, String name);


    Constant createIntegerConstant(NumericValueUpperware value, String name);

    Constant createDoubleConstant(NumericValueUpperware value, String name);

    Constant createFloatConstant(NumericValueUpperware value, String name);

    Constant createLongConstant(NumericValueUpperware value, String name);

    Optional<Constant> searchConstantByName(EList<Constant> constants, String name);


    Optional<Constant> searchConstantByValue(EList<Constant> constants, int value);

    Optional<Constant> searchConstantByValue(EList<Constant> constants, double value);


    Constant searchOrCreateConstantByValue(EList<Constant> constants, int value, String name);

    Constant searchOrCreateConstantByValue(EList<Constant> constants, double value, String name);

    Constant searchOrCreateConstantByValue(EList<Constant> constants, float value, String name);

    Constant searchOrCreateConstantByValue(EList<Constant> constants, long value, String name);

}
