/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.cdo;

import camel.core.CamelModel;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;

public interface CDOService {

    CDOSessionX openSession();

    CDOView openView(CDOSessionX sessionX);

    CDOTransaction openTransaction(CDOSessionX sessionX);

    CamelModel getCamelModel(String name, CDOView view);

    ConstraintProblem getConstraintProblem(String name, CDOView view);

    void closeSession(CDOSessionX sessionX);

}

