/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 * <p>
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 * <p>
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.price.api;

import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.metamodel.cp.Variable;

public interface IProviderPriceEstimator extends IProviderPriceEstimatorFactory {

    double DEFAULT_PRICE_VM = 1;

    double estimatePrice(ProviderModel fm, Variable variable);

    void reset();

}
