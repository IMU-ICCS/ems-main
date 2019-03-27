package eu.melodic.dlms.algorithms.utility;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Bound edges for molecular structure Bounds between atoms are represented as
 * edges in graph structure. It is just storing bound type, later on it might be
 * extended.
 */
@Getter
@AllArgsConstructor
public class BoundEdge implements Serializable {
	private int bondType;

	@Override
	public String toString() {
		return "" + bondType;
	}

}
