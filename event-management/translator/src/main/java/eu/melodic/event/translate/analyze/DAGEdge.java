/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.translate.analyze;

import org.jgrapht.graph.DefaultEdge;

import java.util.concurrent.atomic.AtomicLong;

public class DAGEdge extends DefaultEdge {
    private static AtomicLong edgeCounter = new AtomicLong();

    private final long id;
    private final String _toString;

    public DAGEdge() {
        id = edgeCounter.getAndIncrement();
        _toString = "EDGE #" + id;
    }

    public DAGNode getSource() {
        return (DAGNode) super.getSource();
    }

    public DAGNode getTarget() {
        return (DAGNode) super.getTarget();
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean equals(Object o) {
        return (o instanceof DAGEdge) && (toString().equals(o.toString()));
    }

    public String toString() {
        return _toString;
    }
}
