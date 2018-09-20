package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.CpFactory;
import eu.paasage.upperware.metamodel.cp.VariableType;
import eu.paasage.upperware.metamodel.types.*;
import eu.paasage.upperware.profiler.generator.service.camel.ConstantService;
import eu.paasage.upperware.profiler.generator.service.camel.IdGenerator;
import eu.paasage.upperware.profiler.generator.service.camel.TypesFactoryService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConstantServiceImpl implements ConstantService {

    private CpFactory cpFactory;
    private TypesFactoryService typesFactoryService;
    private IdGenerator constantIdGenerator;

    @Override
    public String getConstantName(VariableType variableType, String vmName, String... suffixes) {
        return Stream.concat(Stream.of(variableType.getLiteral(), vmName), Stream.of(suffixes)).collect(Collectors.joining("_"));
    }

    @Override
    public Constant createIntegerConstant(int value) {
        return createIntegerConstant(value, constantIdGenerator.generate());
    }

    @Override
    public Constant createDoubleConstant(double value) {
        return createDoubleConstant(value, constantIdGenerator.generate());
    }

    @Override
    public Constant createFloatConstant(float value) {
        return createFloatConstant(value, constantIdGenerator.generate());
    }

    @Override
    public Constant createIntegerConstant(int value, String name) {
        return createConstant(name, BasicTypeEnum.INTEGER, typesFactoryService.getIntegerValueUpperware(value));
    }

    @Override
    public Constant createDoubleConstant(double value, String name) {
        return createConstant(name, BasicTypeEnum.DOUBLE, typesFactoryService.getDoubleValueUpperware(value));
    }

    @Override
    public Constant createFloatConstant(float value, String name) {
        return createConstant(name, BasicTypeEnum.FLOAT, typesFactoryService.getFloatValueUpperware(value));
    }

    @Override
    public Constant createLongConstant(long value, String name) {
        return createConstant(name, BasicTypeEnum.LONG, typesFactoryService.getLongValueUpperware(value));
    }

    @Override
    public Constant createIntegerConstant(NumericValueUpperware value, String name) {
        return createConstant(name, BasicTypeEnum.INTEGER, value);
    }

    @Override
    public Constant createDoubleConstant(NumericValueUpperware value, String name) {
        return createConstant(name, BasicTypeEnum.DOUBLE, value);
    }

    @Override
    public Constant createFloatConstant(NumericValueUpperware value, String name) {
        return createConstant(name, BasicTypeEnum.FLOAT, value);
    }

    @Override
    public Constant createLongConstant(NumericValueUpperware value, String name) {
        return createConstant(name, BasicTypeEnum.LONG, value);
    }

    private Constant createConstant(String name, BasicTypeEnum basicTypeEnum, NumericValueUpperware numericValueUpperware){
        Constant constant= cpFactory.createConstant();
        constant.setId(name);
        constant.setType(basicTypeEnum);
        constant.setValue(numericValueUpperware);
        return constant;
    }

    public Optional<Constant> searchConstantByName(EList<Constant> constants, String name) {
        return constants.stream().filter(constant -> name.equals(constant.getId())).findFirst();
    }

    /**
     * Searches a constant in a list with a provided value
     * @param constants The list of constants
     * @param value The constant value
     */
    @Override
    public Optional<Constant> searchConstantByValue(EList<Constant> constants, int value) {
        return searchConstantByValue(constants,
                pair -> pair.getRight().equals(Integer.class.getCanonicalName()) && Integer.parseInt(pair.getLeft()) == value);
    }

    /**
     * Searches a constant in a list with a provided value
     * @param constants The list of constants
     * @param value The constant value
     */
    @Override
    public Optional<Constant> searchConstantByValue(EList<Constant> constants, double value) {
        return searchConstantByValue(constants,
                pair -> pair.getRight().equals(Double.class.getCanonicalName()) && Double.parseDouble(pair.getLeft()) == value);
    }
//
//    @Override
//    public Constant searchOrCreateConstantByValue(EList<Constant> constants, int value, String name) {
//        Optional<Constant> constant = searchConstantByValue(constants, value);
//
//        return constant.orElseGet(() -> {
//            Constant newConstant = createIntegerConstant(value, name);
//            constants.add(newConstant);
//            return newConstant;
//        });
//    }

    @Override
    public Constant searchOrCreateConstantByValue(EList<Constant> constants, double value) {
        Optional<Constant> constant = searchConstantByValue(constants, value);

        return constant.orElseGet(() -> {
            Constant newConstant = createDoubleConstant(value);
            constants.add(newConstant);
            return newConstant;
        });
    }

//    @Override
//    public Constant searchOrCreateConstantByValue(EList<Constant> constants, float value, String name) {
//        Optional<Constant> constant = searchConstantByValue(constants, value);
//
//        return constant.orElseGet(() -> {
//            Constant newConstant = createFloatConstant(value, name);
//            constants.add(newConstant);
//            return newConstant;
//        });
//    }
//
//    @Override
//    public Constant searchOrCreateConstantByValue(EList<Constant> constants, long value, String name) {
//        Optional<Constant> constant = searchConstantByValue(constants, value);
//
//        return constant.orElseGet(() -> {
//            Constant newConstant = createLongConstant(value, name);
//            constants.add(newConstant);
//            return newConstant;
//        });
//    }

    private Optional<Constant> searchConstantByValue(EList<Constant> constants, Predicate<Pair<String, String>> predicate){
        for(Constant c: constants) {
            Optional<Pair<String, String>> valueFromNumericValue = getValueFromNumericValue(c.getValue());
            if (valueFromNumericValue.isPresent() && predicate.test(valueFromNumericValue.get())){
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }


    /**
     * Gets the value from a given Numeric Value as a string
     * @param value The value
     */
    private Optional<Pair<String, String>> getValueFromNumericValue(NumericValueUpperware value) {
        Pair<String, String> result = null;

        if(value instanceof IntegerValueUpperware) {
            result = Pair.of(((IntegerValueUpperware) value).getValue()+"", Integer.class.getCanonicalName());
        } else if(value instanceof FloatValueUpperware) {
            result = Pair.of(((FloatValueUpperware) value).getValue()+"", Float.class.getCanonicalName());
        } else if(value instanceof DoubleValueUpperware) {
            result = Pair.of(((DoubleValueUpperware) value).getValue()+"", Double.class.getCanonicalName());
        } else if(value instanceof LongValueUpperware) {
            result = Pair.of(((LongValueUpperware) value).getValue()+"", Long.class.getCanonicalName());
        }
        return Optional.ofNullable(result);
    }

    @Override
    public void reset() {
        this.constantIdGenerator.reset();
    }
}
