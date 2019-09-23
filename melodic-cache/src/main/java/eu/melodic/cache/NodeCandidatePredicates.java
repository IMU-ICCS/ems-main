package eu.melodic.cache;

import io.github.cloudiator.rest.model.GeoLocation;
import io.github.cloudiator.rest.model.Location;
import io.github.cloudiator.rest.model.NodeCandidate;
import io.github.cloudiator.rest.model.OperatingSystemFamily;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Created by pszkup on 04.01.18.
 */
@Slf4j
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

    public static Predicate<NodeCandidate> getStoragePredicate(Integer value) {
        Objects.requireNonNull(value);
        return new Predicate<NodeCandidate>() {
            @Override
            public boolean test(NodeCandidate nodeCandidate) {
                return value.equals(nodeCandidate.getHardware().getDisk().intValue());
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

    public static Predicate<NodeCandidate> getLatitudePredicate(int value) {
        return new Predicate<NodeCandidate>() {
            @Override
            public boolean test(NodeCandidate nodeCandidate) {

                Double latitude = null;
                Location tempLocation = nodeCandidate.getLocation();
                do {
                    final GeoLocation geoLocation = tempLocation.getGeoLocation();
                    if (geoLocation != null) {
                        latitude = geoLocation.getLatitude();
                    }
                    tempLocation = tempLocation.getParent();
                } while (latitude == null || tempLocation != null);

                log.debug("Comparing Latitude {} with {} result: {}", value, (latitude * 100), Objects.equals(value, (latitude * 100)));
                return Objects.equals(value, (latitude * 100));
            }

            @Override
            public String toString() {
                return "{Latitude Predicate with value: " + value + "}";
            }
        };
    }

    public static Predicate<NodeCandidate> getLongitudePredicate(int value) {
        return new Predicate<NodeCandidate>() {
            @Override
            public boolean test(NodeCandidate nodeCandidate) {

                Double longitude = null;
                Location tempLocation = nodeCandidate.getLocation();
                do {
                    final GeoLocation geoLocation = tempLocation.getGeoLocation();
                    if (geoLocation != null) {
                        longitude = geoLocation.getLongitude();
                    }
                    tempLocation = tempLocation.getParent();
                } while (longitude == null || tempLocation != null);

                log.debug("Comparing Longitude {} with {} result: {}", value, (longitude * 100), Objects.equals(value, (longitude * 100)));
                return Objects.equals(value, (longitude * 100));
            }

            @Override
            public String toString() {
                return "{Longitude Predicate with value: " + value + "}";
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
