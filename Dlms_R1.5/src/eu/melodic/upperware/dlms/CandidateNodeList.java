package eu.melodic.upperware.dlms;

/*
 * Copyright (C) 2018 Simula.no
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

public class CandidateNodeList {
	// This interface will accept the node candidates
	interface NodeList {
		int getItem();

		NodeList nextNode();
	}
}