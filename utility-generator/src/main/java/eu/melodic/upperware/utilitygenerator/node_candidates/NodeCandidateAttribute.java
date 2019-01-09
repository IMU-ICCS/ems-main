/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.node_candidates;

import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Getter
public class NodeCandidateAttribute {

    private String name;
    private String componentId;
    private CamelMetadata type;
    private boolean isList;
    @Setter
    double value;

    public NodeCandidateAttribute(String name, String componentId, CamelMetadata type, boolean isList) {
        this.name = name;
        this.componentId = componentId;
        this.type = type;
        this.isList = isList;
    }

    @Override
    public String toString() {
        return this.name + " , component = " + this.componentId + " type = " + this.type;
    }

    public static NodeCandidateAttribute findAttribute(Collection<NodeCandidateAttribute> attributes, String componentId, CamelMetadata type) {
        return attributes.stream()
                .filter(a -> componentId.equals(a.getComponentId()) && type.equals(a.getType()))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Attribute with type " + type + "for component " + componentId + " not found"));
    }

    public static String createAttributeName(String componentId, CamelMetadata type) {
        return componentId + "_" + type.camelName;
    }
}
