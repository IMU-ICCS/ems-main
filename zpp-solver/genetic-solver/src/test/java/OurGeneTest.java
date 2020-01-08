
import implementation.OurGene;
import io.jenetics.Gene;
import io.jenetics.util.Seq;
import org.junit.Test;
import static org.junit.Assert.*;

public class OurGeneTest {



    @Test
    public void constructorAndGettersTest() {
        OurGene gene = new OurGene(1, 0, null);
        assertEquals(gene.getAllele(), (Integer) 1);

        OurGene gen = new OurGene(2333, 13, null);
        assertEquals(gen.getAllele(), (Integer) 2333);
    }

    @Test
    public void newInstanceGeneTest() {
        OurGene gene = new OurGene(1, 0, new CPGeneticWrapperA(null));

        System.out.println("What follows should be a list of randomly generated genes with value in <0, 1000>");

        for (int i = 0; i < 10; i++)
            System.out.print(gene.newInstance().getAllele() + " ");
        System.out.println();
    }

    @Test
    public void newInstanceFromValueTest() {
        OurGene gene = new OurGene(1, 0, new CPGeneticWrapperA(null));

        OurGene newGeneFromValue = gene.newInstance(5);
        assertEquals(newGeneFromValue.getAllele(), (Integer) 5);
    }

    @Test
    public void checkSeqMethodWithGivenWrapperTest() {
        System.out.println("What follows should be a list of 10 randomly generated genes with values in <0, 1000>");
        Seq<OurGene> s = OurGene.seq(10, new CPGeneticWrapperA(null));
        for (OurGene gene : s)
            System.out.print(gene.getAllele() + " ");
        System.out.println();
    }
}
