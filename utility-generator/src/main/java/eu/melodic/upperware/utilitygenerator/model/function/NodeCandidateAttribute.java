/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.model.function;

import lombok.Getter;
import lombok.Setter;

@Getter
public class NodeCandidateAttribute {

    String name;
    String componentId;
    final String type = "cost"; //todo - different types - for now only cost
    @Setter
    double value;


    public NodeCandidateAttribute(String name, String componentId){

        this.name = name;
        this.componentId = componentId;
    }
}
