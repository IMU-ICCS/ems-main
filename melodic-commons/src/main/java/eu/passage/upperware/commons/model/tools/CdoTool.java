/*
 * Copyright (c) 2014-2016 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.passage.upperware.commons.model.tools;


import camel.core.CamelModel;
import camel.deployment.DeploymentInstanceModel;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
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
        return getLastElementAsOptional(contentsCM)
                .filter(CamelModel.class::isInstance)
                .map(CamelModel.class::cast);
    }

    private static <T extends EObject> Optional<T> getLastElementAsOptional(List<T> collection) {
        return Optional.ofNullable(getLastElement(collection));
    }

    public static <T extends EObject> T getLastElement(List<T> collection) {
        return CollectionUtils.isNotEmpty(collection) ? collection.get(collection.size() - 1) : null;
    }

    public static <T extends EObject> T getFirstElement(List<T> collection) {
        if (CollectionUtils.isEmpty(collection)) {
            throw new IllegalStateException("Could not find first element - collection is empty");
        }
        return collection.get(0);
    }

    public static CamelModel getCamelModelById(CDOTransaction transaction, String camelModelID) {
        return CdoTool.getLastCamelModel(transaction.getResource(camelModelID).getContents())
                .orElseThrow(() -> new IllegalStateException("Could not find camel model from camelModelID: " + camelModelID));
    }

    public static Optional<DeploymentInstanceModel> getLastDeployedInstanceModel(CamelModel camelModel) {
        return getLastElementAsOptional(camelModel.getDeploymentModels())
                .filter(DeploymentInstanceModel.class::isInstance)
                .map(DeploymentInstanceModel.class::cast);
    }
}