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

    public double getFullPrice() {
        return nodeCandidate.getPrice() * cardinality;
    }

    public int getTotalNumberOfCores() {
        return cardinality * nodeCandidate.getHardware().getCores();
    }

    @Override
    public String toString() {
        return String.format("Component: %s ( cardinality = %d,  %s)", id, cardinality, nodeCandidate.toString());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!ConfigurationElement.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final ConfigurationElement other = (ConfigurationElement) obj;
        if ((this.getId() == null) ? (other.getId() != null) : !this.getId().equals(other.getId())) {
            return false;
        }

        if ((this.getNodeCandidate() == null) ? (other.getNodeCandidate() != null) : !this.getNodeCandidate().equals(other.getNodeCandidate())) {
            return false;
        }

        return this.getCardinality() == other.getCardinality();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        hash = 53 * hash + this.getCardinality();
        hash = 53 * hash + (this.getNodeCandidate() != null ? this.getNodeCandidate().hashCode() : 0);
        return hash;
    }


}