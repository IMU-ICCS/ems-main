/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.communication;

import camel.core.CamelModel;
import org.eclipse.emf.cdo.view.CDOView;

public interface CDOService {

    CDOView openView();

    void closeView(CDOView v);

    CamelModel getCamelModel(String name, CDOView view);

}

