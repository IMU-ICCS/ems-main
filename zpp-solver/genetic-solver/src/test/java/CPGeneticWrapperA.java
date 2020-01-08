import cPGeneticWrapper.ACPGeneticWrapper;
import cp_wrapper.CPWrapper;
import implementation.OurGene;
import io.jenetics.util.ISeq;

import java.util.List;

public class CPGeneticWrapperA extends ACPGeneticWrapper {
    public CPGeneticWrapperA(CPWrapper cpWrapper) {
        super(cpWrapper);
    }

    @Override
    public int calculateHeuristicBest(ISeq<OurGene> values) {
        return 0;
    }

    @Override
    public int generateRandomValue(int index) {
        return random.nextInt(1001);
    }

    @Override
    public double calculateUtility(ISeq<OurGene> genes) {
        double res = 0.;
        List<Integer> list = genesToIntegerList(genes);
        for (int i = 0; i < list.size() - 1; i+=2)
            res += list.get(i) - list.get(i + 1);
        return res;
    }

    @Override
    public int countViolatedConstraints(ISeq<OurGene> genes) {
        return 0;
    }

    @Override
    public boolean isValid(int value, int index) {
        return value <= 1000 && value >= 0;
    }

    @Override
    public boolean getIsFeasible(ISeq<OurGene> genes) {
        return true;
    }

    @Override
    public int getSize() {
        return 10;
    }
}
