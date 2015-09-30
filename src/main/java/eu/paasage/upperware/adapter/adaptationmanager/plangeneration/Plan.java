/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.plangeneration;

import java.util.ArrayList;
import java.util.List;
import eu.paasage.upperware.adapter.adaptationmanager.actions.Action;

public class Plan {
	
	ArrayList<Action> actions;
	
	public Plan(ArrayList<Action> actions) {
		this.actions = actions;
	}
	
	public List<Action> getActions() {
		return actions;
	}

}
