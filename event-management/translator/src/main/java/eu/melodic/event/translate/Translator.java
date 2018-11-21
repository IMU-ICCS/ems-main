/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.translate;

import camel.core.CamelModel;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

public interface Translator {
	public TranslationContext translate(String camelId);
	public TranslationContext translate(CamelModel camelModel);
}