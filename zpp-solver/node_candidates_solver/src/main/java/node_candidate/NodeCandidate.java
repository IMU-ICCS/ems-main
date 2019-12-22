package node_candidate;
@AllArgsConstructor
public class NodeCandidate {
    @Getter
    private int cores;
    @Getter
    private int ram;
    @Getter
    private int disk;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (obj instanceof NodeCandidate) {
            return cores == ((NodeCandidate) obj).cores
                    &&
                    ram == ((NodeCandidate) obj).ram
                    &&
                    disk == ((NodeCandidate) obj).disk;
        }
        return false;
    }
}
