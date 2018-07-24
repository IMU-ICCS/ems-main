/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.model.function;

import org.mariuszgromada.math.mxparser.Argument;

public class ArgumentFactory {

    public static Argument createArgument(Element element) {
        if (element instanceof IntElement) {
            return new Argument(element.getName(), ((IntElement) element).getValue());
        } else { //RealElement
            return new Argument(element.getName(), ((RealElement) element).getValue());
        }
    }
}
