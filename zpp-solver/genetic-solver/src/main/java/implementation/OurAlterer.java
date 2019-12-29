import implementation.OurGene;
import io.jenetics.Alterer;
import io.jenetics.AltererResult;
import io.jenetics.Phenotype;
import io.jenetics.util.Seq;

public class OurAlterer implements Alterer<OurGene, Double> {
    @Override
    public AltererResult<OurGene, Double> alter(Seq<Phenotype<OurGene, Double>> seq, long l) {
        return null;
    }
}
