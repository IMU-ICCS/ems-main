/**
 * CP Generator
 *  Copyright (C) 2014 INRIA, Universite Lille 1
 *
 * Contacts: daniel.romero@inria.fr & lionel.seinturier@inria.fr
 * Date: 10/2014
 */

/** REQUIREMENTS **/

- CDO Server running
- Definition of the PAASAGE_CONFIG_DIR property


/** COMPILATION AND PACKAGING **/
mvn clean install

/** USE AS STANDALONE APPLICATION - JAR FILE **/

java -jar ../cp-cloner-service-jar-with-dependencies.jar <id_of_cp_model_to_copy> <id_of_copy>