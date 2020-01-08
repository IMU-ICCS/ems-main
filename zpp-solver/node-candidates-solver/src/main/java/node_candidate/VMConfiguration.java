package node_candidate;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class VMConfiguration implements Comparable<VMConfiguration> {
    @Getter
    private long cores;
    @Getter
    private long ram;
    @Getter
    private double disk;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof VMConfiguration) {
            return cores == ((VMConfiguration) obj).cores
                    &&
                    ram == ((VMConfiguration) obj).ram
                    &&
                    disk == ((VMConfiguration) obj).disk;
        }
        return false;
    }

    @Override
    public int compareTo(VMConfiguration o) {
        if (this.equals(o)) {
            return 0;
        } else if (
                (cores > o.cores)
                || (cores == o.cores && ram > o.ram)
                || (cores == o.cores && ram == o.ram && disk > o.disk)
        ) {
            return 1;
        } else {
            return -1;
        }
    }
}
