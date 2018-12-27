/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.cdo;


import camel.core.CamelModel;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.String.format;

@Slf4j
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CDOServiceImpl implements CDOService {

    private CDOClientX clientX;

    static {
        XMIResToResFact();
    }

    private static void XMIResToResFact() {
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*",
                new XMIResourceFactoryImpl() {
                    public Resource createResource(URI uri) {
                        return new XMIResourceImpl(uri);
                    }
                }
        );
    }

    @Override
    public CamelModel getCamelModel(String resourceName, CDOView view) {
        if (!view.hasResource(resourceName)) {
            throw new IllegalArgumentException(format("Camel Model for name = %s does not exist in CDOServer", resourceName));
        }
        EList<EObject> contents = view.getResource(resourceName).getContents();

        return CdoTool.getLastCamelModel(contents)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Cannot load Camel Model for resourceName=%s. " +
                        "Check the value is valid and the model is available in CDO Server.", resourceName)));
    }

    @Override
    public ConstraintProblem getConstraintProblem(String name, CDOView view) {
        ConstraintProblem cp = null;
        log.info("Reading CP model...");

        //CDOClientX clientX = new CDOClientXImpl(Arrays.asList(TypesPackage.eINSTANCE, CpPackage.eINSTANCE));

        CDOResource resource = view.getResource(name);
        EList<EObject> contents = resource.getContents();
        for (EObject obj : contents) {
            if (obj instanceof ConstraintProblem) {
                return (ConstraintProblem) obj;
            }
        }
        throw new IllegalStateException("Constraint Problem does not exist in CDO");

    }

    @Override
    public void closeSession(CDOSessionX sessionX) {
        sessionX.closeSession();
    }

    @Override
    public CDOSessionX openSession() {
        return clientX.getSession();
    }

    @Override
    public CDOView openView(CDOSessionX sessionX) {
        return sessionX.openView();
    }

    @Override
    public CDOTransaction openTransaction(CDOSessionX sessionX) {
        return sessionX.openTransaction();
    }

}

