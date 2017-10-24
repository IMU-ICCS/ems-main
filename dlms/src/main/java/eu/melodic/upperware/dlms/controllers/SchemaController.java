/*
 * Copyright (C) 2017 Simula.no
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.dlms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eu.melodic.upperware.dlms.store.DbSchema;
import eu.melodic.upperware.dlms.store.SchemaRepository;

@Controller
@RequestMapping(path = "/schema")
public class SchemaController {
	@Autowired
	private SchemaRepository schemaRepository;

	@GetMapping(path = "/add") // Map ONLY GET Requests
	public @ResponseBody String addNewSchema(@RequestParam String userName, @RequestParam String schema) {
		DbSchema n = new DbSchema();
		n.setUserName(userName);
		n.setSchema(schema);
		schemaRepository.save(n);
		return "Saved";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<DbSchema> getAllSchema() {
		return schemaRepository.findAll();
	}

}