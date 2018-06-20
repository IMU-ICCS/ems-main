/*
 * Copyright (c) 2014-2016 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.passage.upperware.commons.model.tools;


import eu.paasage.camel.CamelModel;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.ecore.EObject;

import java.util.List;
import java.util.Optional;

/**
 * A utility to help interaction with the CDO server.
 * <p>
 *
 * @author Shirley Crompton
 *         org UK Science and Technology Facilities Council
 */
public final class CdoTool {

    public static Optional<CamelModel> getLastCamelModel(List<EObject> contentsCM){
        return getLastElement(contentsCM)
                .filter(CamelModel.class::isInstance)
                .map(CamelModel.class::cast);
    }


    private static <T extends EObject> Optional<T> getLastElement(List<T> collection) {
        return Optional.ofNullable(CollectionUtils.isNotEmpty(collection) ? collection.get(collection.size()-1) : null);
    }
}
