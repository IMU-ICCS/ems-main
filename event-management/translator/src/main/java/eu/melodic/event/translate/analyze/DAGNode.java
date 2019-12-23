/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.translate.analyze;

import camel.core.NamedElement;

import java.util.concurrent.atomic.AtomicLong;

public class DAGNode {
    private static AtomicLong counter = new AtomicLong();
    final NamedElement element;
    final String elementName;
    private final long _id = counter.getAndIncrement();
    private final String _name;
    private final String _toString;
    private Grouping grouping;

    DAGNode() {
        element = null;
        elementName = null;
        _name = null;
        _toString = "NODE <ROOT>";
    }

    public DAGNode(NamedElement elem, String fullName) {
        if (elem == null) throw new IllegalArgumentException("Argument #1 cannot be null");
        if (fullName == null) throw new IllegalArgumentException("Argument #2 cannot be null");
        element = elem;
        elementName = element.getName();
        _name = fullName;
        _toString = String.format("NODE %s", _name);
    }

    public long getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public Grouping getGrouping() {
        return grouping;
    }

    public DAGNode setGrouping(Grouping g) {
        grouping = g;
        return this;
    }

    public NamedElement getElement() {
        return element;
    }

    public String getElementName() {
        //return element!=null? element.getName() : null;
        return elementName;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public boolean equals(Object o) {
        return (o instanceof DAGNode) && (toString().equals(o.toString()));
    }

    public String toString() {
        return _toString;
    }
}
