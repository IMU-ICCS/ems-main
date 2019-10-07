/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.vassilis.staff;

import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ConfigurationElement {

    private String id;
    private NodeCandidate nodeCandidate;
    private int cardinality;

    @Override
    public String toString() {
        return String.format("Component: %s ( cardinality = %d,  %s)", id, cardinality, nodeCandidate.toString());
    }

}





