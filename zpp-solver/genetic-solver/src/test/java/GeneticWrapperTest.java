import cp_genetic_wrapper.ACPGeneticWrapper;
import cp_genetic_wrapper.CPGeneticWrapper;
import cp_wrapper.CPWrapper;
import cp_wrapper.utility_provider.UtilityProvider;
import eu.paasage.upperware.metamodel.cp.*;

import org.junit.Test;
import utility.Methods;


import java.util.Map;

public class GeneticWrapperTest {

    @Test
    public void testConstructorAndGetters() {
        // TODO
        Map<ConstraintProblem, UtilityProvider> problem = Methods.prepareSimpleConstraintProblem();
        CPWrapper cpWrapper = new CPWrapper();
        cpWrapper.parse(problem.keySet().iterator().next(), problem.values().iterator().next());

        ACPGeneticWrapper geneticWrapper = new CPGeneticWrapper(cpWrapper);
        System.out.println(geneticWrapper.getSize());
        System.out.println(geneticWrapper.generateRandomValue(2));
    }
}
