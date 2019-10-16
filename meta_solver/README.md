MetaSolver
==========
Metasolver is used in the Melodic Upperware module for calling and coordinating the reasoning over deployment configurations.

#### Copyright
Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)

This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
If a copy of the MPL was not distributed with this file, You can obtain one at
https://www.mozilla.org/en-US/MPL/2.0/

#### Functionality
- selects a solver
- evaluates a new solution
- updates CP model metrics with current values
- triggers application re-configuration on an SLO violation

#### Requirements
 - java 7
 - linux
 - maven

#### Building
 - mvn clean install

#### Configuration
Config file: eu.melodic.upperware.metaSolver.properties
Can be provided during application startup as a program argument:
--spring.config.location=classpath:/config/eu.melodic.upperware.metaSolver.properties

Please make sure $MELODIC_CONFIG_DIR environment variable is set and following property files are available in this Melodic config directory:
- eu.melodic.upperware.metasolver.properties - used by the Metasolver
- eu.melodic.mddb.cdo.client.properties - used by the CDO Client repository

#### Usage
Metasolver is a Spring Boot application that requires following environment variable to be set:
MELODIC_CONFIG_DIR=<path to directory where eu.paasage.mddb.cdo.client.properties file is included>

It exposes REST interface(s) (defined in MetaSolverController class according to description in the integration/interfaces project)
