
import implementation.ImplGene;
import io.jenetics.util.Seq;
import org.junit.Test;
import utility.CPGeneticWrapperA;

import static org.junit.Assert.*;

public class ImplGeneTest {



    @Test
    public void constructorAndGettersTest() {
        ImplGene gene = new ImplGene(1, 0, null);
        assertEquals(gene.getAllele(), (Integer) 1);

        ImplGene gen = new ImplGene(2333, 13, null);
        assertEquals(gen.getAllele(), (Integer) 2333);
    }

    @Test
    public void newInstanceGeneTest() {
        ImplGene gene = new ImplGene(1, 0, new CPGeneticWrapperA(null));

        System.out.println("What follows should be a list of randomly generated genes with value in <0, 1000>");

        for (int i = 0; i < 10; i++)
            System.out.print(gene.newInstance().getAllele() + " ");
        System.out.println();
    }

    @Test
    public void newInstanceFromValueTest() {
        ImplGene gene = new ImplGene(1, 0, new CPGeneticWrapperA(null));

        ImplGene newGeneFromValue = gene.newInstance(5);
        assertEquals(newGeneFromValue.getAllele(), (Integer) 5);
    }

    @Test
    public void checkSeqMethodWithGivenWrapperTest() {
        System.out.println("What follows should be a list of 10 randomly generated genes with values in <0, 1000>");
        Seq<ImplGene> s = ImplGene.seq(10, new CPGeneticWrapperA(null));
        for (ImplGene gene : s)
            System.out.print(gene.getAllele() + " ");
        System.out.println();
    }
}
