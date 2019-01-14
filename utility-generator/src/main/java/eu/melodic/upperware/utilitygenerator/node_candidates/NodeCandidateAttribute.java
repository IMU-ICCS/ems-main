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
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class NodeCandidateAttribute {

    private final String name;
    private final String componentId;
    private final CamelMetadata type;
    @ToString.Exclude
    private final boolean isList;
    @Setter
    @ToString.Exclude
    double value;

    public static String createAttributeName(String componentId, CamelMetadata type) {
        return componentId + "_" + type.camelName;
    }
}
