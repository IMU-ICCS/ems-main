package comparators;

import io.jenetics.util.RandomRegistry;
import jenetics_implementation.ChromosomeImpl;

import java.util.Random;

/*
    Compares two chromosomes using their utility and number of broken constraints.
 */
public class AssignmentComparator {
    private final static Random random = RandomRegistry.getRandom();

    public static int compare(ChromosomeImpl left, ChromosomeImpl right, double probability) {
        return compare(left.getUtility(), right.getUtility(), left.getBrokenConstraints(), right.getBrokenConstraints(), probability);
    }

    private static int compare(double leftUtility, double rightUtility, int leftBroken, int rightBroken, double probability) {
        if (leftUtility == rightUtility && leftBroken == rightBroken)
            return 0;

        if (leftBroken == rightBroken)
            return Double.compare(leftUtility, rightUtility);
        else if ((leftBroken == 0 || rightBroken == 0) && random.nextDouble() < probability)
            return Double.compare(leftUtility, rightUtility);
        else
            return Integer.compare(rightBroken, leftBroken);
    }
}
