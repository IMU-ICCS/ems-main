/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.model.function;

import org.mariuszgromada.math.mxparser.Constant;

public class ConstantFactory {

    public static Constant createConstant(Element element) {
        if (element instanceof RealElement) {
            return new Constant(element.getName(), ((RealElement) element).getValue());
        } else { //Integer
            return new Constant(element.getName(), ((IntElement) element).getValue());
        }
    }
}
