/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.communication;


import camel.core.CamelModel;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CDOServiceImpl implements CDOService {

    private CDOClient cdoClient;

    private static Map<String, Object> opts = new HashMap<>();

    static {
        XMIResToResFact();
        opts.put(XMIResource.OPTION_SCHEMA_LOCATION, true);
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
    public CDOView openView() {
        return cdoClient.openView();
    }

    @Override
    public void closeView(CDOView v) {
        cdoClient.closeView(v);
    }


    @Override
    public CamelModel getCamelModel(String name, CDOView view) {
        if (!(view.hasResource(name))) {
            throw new IllegalArgumentException(format("Camel Model for name = %s does not exist in CDOServer", name));
        }
        EList<EObject> contents = view.getResource(name).getContents();

        return Optional.ofNullable(CollectionUtils.isNotEmpty(contents) ? contents.get(contents.size() - 1) : null)
                .filter(CamelModel.class::isInstance)
                .map(CamelModel.class::cast)
                .orElseThrow(() -> new IllegalArgumentException(format("Cannot load Camel Model for resourceName=%s. " +
                        "Check the value is valid and the model is available in CDO Server.", name)));
    }

    public ConstraintProblem getConstraintProblem(String name, CDOView view) {
        cdoClient.registerPackage(TypesPackage.eINSTANCE);
        cdoClient.registerPackage(CpPackage.eINSTANCE);
        ConstraintProblem cp = null;
        CDOResource resource = view.getResource(name);
        EList<EObject> objs = resource.getContents();
        for (EObject obj: objs){
            if (obj instanceof ConstraintProblem){
                cp = (ConstraintProblem)obj;
                break;
            }
        }
        if (cp == null){
            throw new IllegalArgumentException(format("Cannot load Camel Model for name = %s", name));
        }
        return cp;
    }
}

