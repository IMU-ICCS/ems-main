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
import org.eclipse.emf.cdo.view.CDOView;

public class CDOServiceFromFile implements CDOService {


    @Override
    public CDOView openView() {
        return null;
    }

    @Override
    public void closeView(CDOView v) {

    }

    @Override
    public CamelModel getCamelModel(String name, CDOView view) {
        return (CamelModel) CDOClient.loadModel(name);
    }
}
