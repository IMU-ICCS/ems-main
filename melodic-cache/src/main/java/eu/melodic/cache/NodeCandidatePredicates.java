package eu.melodic.cache;

import io.github.cloudiator.rest.model.NodeCandidate;
import io.github.cloudiator.rest.model.OperatingSystemFamily;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by pszkup on 04.01.18.
 */
public final class NodeCandidatePredicates {

    public static Predicate<NodeCandidate> getRamPredicate(Long value) {
        Objects.requireNonNull(value);
        return new Predicate<NodeCandidate>() {
            @Override
            public boolean test(NodeCandidate nodeCandidate) {
                return value.equals(nodeCandidate.getHardware().getRam());
            }

            @Override
            public String toString() {
                return "{Ram Predicate with value: " + value + "}";
            }
        };
    }

    public static Predicate<NodeCandidate> getCoresPredicate(Integer value) {
        Objects.requireNonNull(value);
        return new Predicate<NodeCandidate>() {
            @Override
            public boolean test(NodeCandidate nodeCandidate) {
                return value.equals(nodeCandidate.getHardware().getCores());
            }

            @Override
            public String toString() {
                return "{Cores Predicate with value: " + value + "}";
            }
        };
    }

    public static Predicate<NodeCandidate> getStoragePredicate(Double value) {
        Objects.requireNonNull(value);
        return new Predicate<NodeCandidate>() {
            @Override
            public boolean test(NodeCandidate nodeCandidate) {
                return value.equals(nodeCandidate.getHardware().getDisk());
            }

            @Override
            public String toString() {
                return "{Storage Predicate with value: " + value + "}";
            }

        };
    }

    public static Predicate<NodeCandidate> getOsPredicate(int value) {
        return new Predicate<NodeCandidate>() {
            @Override
            public boolean test(NodeCandidate nodeCandidate) {
                return value == nodeCandidate.getImage().getOperatingSystem().getOperatingSystemFamily().ordinal();
            }

            @Override
            public String toString() {
                return "{Os Predicate with value: " + value + " (" + getOperatingSystemFamilyByOrdinal(value) + ")}";
            }
        };
    }

    private static OperatingSystemFamily getOperatingSystemFamilyByOrdinal(int ordinal){
        return Arrays.stream(OperatingSystemFamily.values())
                .filter(operatingSystemFamily -> operatingSystemFamily.ordinal() == ordinal)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find value for " + ordinal));
    }
}
