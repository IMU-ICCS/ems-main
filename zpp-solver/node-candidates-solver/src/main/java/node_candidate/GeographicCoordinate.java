package node_candidate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class GeographicCoordinate implements Comparable<GeographicCoordinate>{
    @Getter
    private double latitude;
    @Getter
    private double longitude;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof GeographicCoordinate) {
            return latitude == ((GeographicCoordinate) obj).latitude
                    &&
                    longitude == ((GeographicCoordinate) obj).longitude;
        }
        return false;
    }

    @Override
    public int compareTo(GeographicCoordinate o) {
        if (this.equals(o)) {
            return 0;
        } else if (latitude > o.latitude || (latitude == o.latitude && longitude > o.longitude)) {
            return 1;
        } else {
            return -1;
        }
    }
}
