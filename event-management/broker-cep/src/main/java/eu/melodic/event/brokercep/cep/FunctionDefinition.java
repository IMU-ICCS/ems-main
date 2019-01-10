/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.cep;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import lombok.Builder;

//@Builder
@Getter
@ToString
public class FunctionDefinition implements Serializable {
    private String name;
    private String expression;
    private List<String> arguments;

    public FunctionDefinition setName(String name) {
        this.name = name;
        return this;
    }

    public FunctionDefinition setExpression(String expression) {
        this.expression = expression;
        return this;
    }

    public FunctionDefinition setArguments(List<String> arguments) {
        this.arguments = new ArrayList<>(arguments);
        return this;
    }
}
