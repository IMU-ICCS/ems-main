package cp_wrapper.utility_provider;

import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO.VariableValueDTO;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class ParallelUtilityProviderImpl implements UtilityProvider {
    final private List<UtilityGeneratorApplication> utility;
    final private List<Boolean> occupied;
    final private ReentrantLock mutex = new ReentrantLock();

    public ParallelUtilityProviderImpl(List<UtilityGeneratorApplication> utility) {
        this.utility = utility;
        occupied = utility.stream().map(util -> false).collect(Collectors.toList());
    }

    @Override
    public double evaluate(List<VariableValueDTO> result) {
        int utilityIndex = chooseUtility();
        double evaluation = utility.get(utilityIndex).evaluate(result);
        freeUtility(utilityIndex);
        return evaluation;
    }

    private int chooseUtility() {
        mutex.lock();
        int index = occupied.indexOf(false);
        occupied.set(index, true);
        mutex.unlock();
        return index;
    }

    synchronized private void freeUtility(int index) {
        mutex.lock();
        occupied.set(index, false);
        mutex.unlock();
    }
}
