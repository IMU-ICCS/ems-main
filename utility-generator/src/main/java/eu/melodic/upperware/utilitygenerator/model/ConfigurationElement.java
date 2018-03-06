/* * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.model;

import io.github.cloudiator.rest.model.NodeCandidate;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConfigurationElement {

    private String id;
    private NodeCandidate nodeCandidate;
    private int cardinality;

    public double getFullPrice(){
        return nodeCandidate.getPrice() * cardinality;
    }

    public long getFullRam(){
        return nodeCandidate.getHardware().getRam() * cardinality;
    }

    public double getTotalNumberOfCores(){
        return cardinality * nodeCandidate.getHardware().getCores();
    }

    @Override
    public String toString(){
        return String.format("Component: %s ( cardinality = %d,  %s)", id, cardinality, nodeCandidate.toString());
    }
}