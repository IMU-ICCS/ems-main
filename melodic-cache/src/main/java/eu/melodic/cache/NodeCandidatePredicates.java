package eu.melodic.cache;

import eu.melodic.cloudiator.client.model.NodeCandidate;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by pszkup on 04.01.18.
 */
public final class NodeCandidatePredicates {

    public static Predicate<NodeCandidate> getRamPredicate(Long value) {
        Objects.requireNonNull(value);
        return nodeCandidate -> value.equals(nodeCandidate.getHardware().getRam());
    }

    public static Predicate<NodeCandidate> getCoresPredicate(Integer value) {
        Objects.requireNonNull(value);
        return nodeCandidate -> value.equals(nodeCandidate.getHardware().getCores());
    }

    public static Predicate<NodeCandidate> getStoragePredicate(Float value) {
        Objects.requireNonNull(value);
        return nodeCandidate -> value.equals(nodeCandidate.getHardware().getDisk());
    }

    public static Predicate<NodeCandidate> getOsPredicate(int value) {
        return nodeCandidate -> value == nodeCandidate.getImage().getOperatingSystem().getOperatingSystemFamily().ordinal();
    }
}
