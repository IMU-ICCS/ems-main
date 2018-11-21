/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.translate.analyze;

import java.util.concurrent.atomic.AtomicLong;
import org.jgrapht.graph.DefaultEdge;

public enum Grouping {
	UNSPECIFIED(-1, false, false),
	PER_INSTANCE(0, true, false),
	PER_HOST(1, true, false),
	PER_ZONE(2, false, true),
	PER_REGION(3, false, true),
	PER_CLOUD(4, false, true),
	GLOBAL(5, false, false);
	
	private int order;
	private boolean sameHost;
	private boolean sameCloud;
	
	Grouping(int n, boolean sh, boolean sc) {
		order = n;
		sameHost = sh;
		sameCloud = sc;
	}
	
	boolean equals(Grouping g) { return this.order == g.order; }
	boolean lowerThan(Grouping g) { return this.order < g.order; }
	boolean greaterThan(Grouping g) { return this.order > g.order; }
}