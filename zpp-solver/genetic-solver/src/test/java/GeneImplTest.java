
import eu.melodic.upperware.genetic_solver.jenetics_implementation.GeneImpl;
import org.junit.Test;
import utility.CPGeneticWrapperA;

import static org.junit.Assert.*;

public class GeneImplTest {
    @Test
    public void constructorAndGettersTest() {
        GeneImpl gene = new GeneImpl(1, 0, null);
        assertEquals(gene.getAllele(), (Integer) 1);

        GeneImpl gen = new GeneImpl(2333, 13, null);
        assertEquals(gen.getAllele(), (Integer) 2333);
    }

    @Test
    public void newInstanceFromValueTest() {
        GeneImpl gene = new GeneImpl(1, 0, new CPGeneticWrapperA(null));

        GeneImpl newGeneFromValue = gene.newInstance(5);
        assertEquals(newGeneFromValue.getAllele(), (Integer) 5);
    }


}
