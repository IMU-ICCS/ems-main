/*
 * Copyright (C) 2018 Simula.no
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */
package eu.melodic.upperware.dlms.alluxio;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class DlmController {

	//TODO: Need to link to the bean of the service
    @RequestMapping("/registration")
    public String index() {
        return "File is registered!";
    }
}
