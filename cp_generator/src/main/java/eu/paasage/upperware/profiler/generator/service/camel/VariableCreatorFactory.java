package eu.paasage.upperware.profiler.generator.service.camel;

import camel.type.PrimitiveType;
import eu.paasage.upperware.profiler.generator.error.GeneratorException;
import eu.paasage.upperware.profiler.generator.service.camel.creator.VariableCreator;
import eu.paasage.upperware.profiler.generator.service.camel.creator.impl.DoubleVariableCreator;
import eu.paasage.upperware.profiler.generator.service.camel.creator.impl.FloatVariableCreator;
import eu.paasage.upperware.profiler.generator.service.camel.creator.impl.IntegerVariableCreator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VariableCreatorFactory {

    private IntegerVariableCreator integerVariableCreator;
    private FloatVariableCreator floatVariableCreator;
    private DoubleVariableCreator doubleVariableCreator;

    public VariableCreator getCreator(PrimitiveType primitiveType){
        Objects.requireNonNull(primitiveType, "PrimitiveType could not be null");
        switch (primitiveType) {
            case INT_TYPE:
                return integerVariableCreator;
            case FLOAT_TYPE:
                return floatVariableCreator;
            case DOUBLE_TYPE:
                return doubleVariableCreator;
            case STRING_TYPE:
            case BOOLEAN_TYPE:
        }
        throw new GeneratorException("Could not get variableCreator. Unsupported primitiveType: " + primitiveType);
    }

}
