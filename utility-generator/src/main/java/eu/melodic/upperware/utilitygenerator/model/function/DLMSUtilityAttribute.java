/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.model.function;

import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DLMSUtilityAttribute {

    private String name;
    private String componentId;
    private CamelMetadata type;
    @Setter
    double value;


    public DLMSUtilityAttribute(String name, String componentId, CamelMetadata type) {
        this.name = name;
        this.componentId = componentId;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.name + " , component = " + this.componentId + " type = " + this.type;
    }

}
