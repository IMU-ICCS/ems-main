package eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class IntegerNodeCandidateElementImpl implements NodeCandidateElementInterface {
    private int value;
}
