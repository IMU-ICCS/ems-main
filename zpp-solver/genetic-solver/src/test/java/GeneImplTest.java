
import jenetics_implementation.GeneImpl;
import io.jenetics.util.Seq;
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
    public void newInstanceGeneTest() {
        GeneImpl gene = new GeneImpl(1, 0, new CPGeneticWrapperA(null));

        System.out.println("What follows should be a list of randomly generated genes with value in <0, 1000>");

        for (int i = 0; i < 10; i++)
            System.out.print(gene.newInstance().getAllele() + " ");
        System.out.println();
    }

    @Test
    public void newInstanceFromValueTest() {
        GeneImpl gene = new GeneImpl(1, 0, new CPGeneticWrapperA(null));

        GeneImpl newGeneFromValue = gene.newInstance(5);
        assertEquals(newGeneFromValue.getAllele(), (Integer) 5);
    }

    @Test
    public void checkSeqMethodWithGivenWrapperTest() {
        System.out.println("What follows should be a list of 10 randomly generated genes with values in <0, 1000>");
        Seq<GeneImpl> s = GeneImpl.createSequenceOfGenes(10, new CPGeneticWrapperA(null));
        for (GeneImpl gene : s)
            System.out.print(gene.getAllele() + " ");
        System.out.println();
    }
}
