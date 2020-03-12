package eu.melodic.upperware.mcts_solver.solver.mcts_cp_wrapper;

import java.util.List;

// Interface for wrapper that makes testing single classes easier.
public interface IMCTSWrapper {
    // Generates random value for variable indexed with index.
    int generateRandomValue(int index);

    // Calculates utility for certain variables assignment.
    double getUtility(List<Integer> assignments);

    // Returns number of variables.
    int getSize();

    int domainSize(int index);

    int getMinDomainValue(int index);

    int getMaxDomainValue(int index);

    boolean isFeasible(List<Integer> assignments);
}
