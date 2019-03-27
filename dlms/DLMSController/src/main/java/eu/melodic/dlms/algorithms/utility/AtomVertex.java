package eu.melodic.dlms.algorithms.utility;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * Atom vertices for molecular structure Atoms are represented as vertices in
 * graph representation of molecules.
 */
@Getter
@Setter
public class AtomVertex implements Serializable {
	private String name;
	private int position;
	private int atomId;

	public AtomVertex(String name) {
		this.name = name;
	}

	/**
	 * Position does not effect the hash of Atom. The same atom chain can be located
	 * different positions.
	 * @return atomId
	 */
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * Equal to method based on only the name
	 */
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		AtomVertex that = (AtomVertex) o;
		return name.equals(that.name);
	}
}
