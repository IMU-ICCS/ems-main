/* * Copyright (C) 2018 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.utilitygenerator.converter;

import eu.melodic.upperware.utilitygenerator.model.function.ArgumentFactory;
import eu.melodic.upperware.utilitygenerator.model.function.ConstantFactory;
import eu.melodic.upperware.utilitygenerator.model.function.Element;
import lombok.extern.slf4j.Slf4j;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Constant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class ConvertingUtils {

    public static Collection<Argument> convertToArgument(Collection<Element> elements){

        return elements.stream().map(ArgumentFactory::createArgument).collect(Collectors.toList());

    }

    public static Collection<Constant> convertToConstants(Collection<Element> elements){

        return elements.stream().map(ConstantFactory::createConstant).collect(Collectors.toList());

    }

    private static Collection<Constant[]> convertToConstantsArray(Collection<Collection<Element>> elements){
        Collection<Collection<Constant>>constants = new ArrayList<>();
        elements.forEach(
                list -> constants.add(
                        list.stream().map(ConstantFactory::createConstant).collect(Collectors.toList())));
        return constants.stream().map(list ->list.toArray(new Constant[list.size()])).collect(Collectors.toList());
    }

    public static Collection<Constant> convertArrayToConstants(Collection<Collection<Element>> elements){
        Collection<Constant[]> constants = convertToConstantsArray(elements);
        return constants.stream().map(c -> new Constant(c[0].getConstantName(), c)).collect(Collectors.toList());
    }


}
