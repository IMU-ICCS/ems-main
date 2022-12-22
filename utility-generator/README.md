# Utility Generator

## Communication Utility Generator (UG) <--> Performance Module (PM)
For this use case UG is currently being run as part (a library) of CP-Solver. 

At the time of writing, only evaluation requests containing metrics are sent to PM to get performance metrics. 
In the case that no metrics are present, a log entry from `UtilityFunctionEvaluator` reporting `No metrics in constraint problem - skipping PM call` can be seen.
Later a filter is planned so that it depends on the data if PM is called or not.

The communication between UG and PM is done via JMS/AMQ using the Morphemic facade (see below for configuration options).

Different topics are used for request and response. The topic names can be found in `eu.melodic.upperware.utilitygenerator.facade.pm.PMFacade` in the constants
`PM_PREDICT_REQUEST_TOPIC` and `PM_PREDICT_REPLY_TOPIC`.

### Testing
#### Preparations:

Prepare a standard MORPHEMIC environment to run the 'FCR' application test.

Enter a configuration for this application and start the process.

#### Results

For the simplest possible use case (if the setup is correct and if sending to AMQ works, independently of PM's deployment):

Check the log files for CPSolver (`dlog cpsolver`) for the patterns described below. 
If a message was sent without error and the system prints that it is listening for an answer the operation was successful.

For the complete use-case UG --> PM --> UG:

If there is any problem on the way (either UG --> PM, inside PM or PM --> UG) there will be no answer or the reasoning will fail.
When waiting for a reply to a request sent by UG, a timeout is in place that will be visible in the logs as warning: `Timeout hit after {timeout} seconds - no result received, continuing`.
In other words, if the process screen makes it past the reasoning stage while metrics are present, it works. Details can be tracked in the logs (see below).

**In application logs**

The Morphemic facade logs all communication on `INFO` level.

For sending to the request topic a log entry containing `Sending to topic:` followed by the request topic's name should be logged. 
This will be followed by `Message:` and the message's contents.

For receiving a message from the reply topic a log entry containing `Listening to topic:` followed by the reply topic's name should be logged.
When the message is sent by PM and is received by the facade, it should appear as `Message received:` followed by the message's contents (with the data PM sent).

***On the JMS/AMQ system:***

A listener/consumer should show up for the request and reply topics mentioned above.

When the communication takes place, messages should be added for the request topic and answers put into the reply topic by PM should be marked as delivered.

## Polymorphic Adaptation 
For this use case UG is being run as a stand-alone service within Morphemic.
Communication consists of two separate phases:
- Camunda <--> UG (for initialization of UG for an application)
- Polymorphic Solver <--> UG (the actual utility value calculation)

### Testing
#### Preparations:

Prepare a standard MORPHEMIC environment to run the 'FCR' application test.

Enter a configuration for this application and start the process.

### Camunda <--> UG

Camunda will call a REST-webservice exposed by UG to trigger the creation of a UG instance for a specific application-ID.

The service is available for Camunda at this relative path: `/utilityGeneratorInitialisation`.
It will return HTTP-200 (OK) on success and HTTP-406 (Not Acceptable) on errors.

This step is essential for phase #2 to succeed.

### Polymorphic Solver <--> UG

Poly-Solver sends requests for utility value calculation for an application-ID to UG.
The communication between Poly-Solver and UG is done via JMS/AMQ using the Morphemic facade.

Different topics are used for request and response. The topic names can be found in `eu.melodic.upperware.utilitygenerator.facade.solver.SolverFacadeImpl` in the constants`REQUEST_TOPIC` and `REPLY_TOPIC`.

This step can include an additional communication between UG and PM (as described above for CPSolver, but with log-locations as stated here), given that the 
evaluation requests do contain metrics. In the case that no metrics are present, a log entry from `UtilityFunctionEvaluator` reporting `No metrics in constraint problem - skipping PM call` can be found.

Note: The stand-alone version of UG is asynchronous, so log output may appear "mixed-up" as it might be coming from several threads.

### Results

Check the log files for the UG-standalone-service (`dlog ug-standalone`) for the patterns described below.

**Camunda <--> UG**

A log statement `processCamundaRequest:`followed by the data sent by Camunda marks the beginning of this phase.

Should Camunda send a request for an application-ID that already has been processed in UG, a warning `Duplicate UG registration request for app-id`+ application-ID will be logged. Note that the existing UG instance will be kept as-is.
This might happen if the process takes longer than usual and Camunda re-sends the request. This message does not necessarily mean that anything is wrong and functionality should be as usual.

After a UG instance is created a log statement `UG instance created for app-id`+ application-ID will be visible. This marks the end of the phase.

**Polymorphic Solver <--> UG**

After the facade received and accepted a message for this use case, a log statement `asynch processSolverRequest:` followed by the data received can be seen.

Should the parameters given by Poly-Solver be insufficient (no application-ID or no values for utility calculation) a warning `Incomplete parameters` will be logged and also returned to Poly-Solver via AMQ.

Should there be no UG instance ready for the given application-ID (most likely because the call from Camunda was missing or failed), `No UtilityGenerator present for app-ID ` + application-ID will be logged and also returned to Poly-Solver via AMQ.

Given all pre-conditions are satisfied, the standard UG-workflow will run (including possible UG <--> PM communication via the facade).
A good checkpoint after the end of this workflow is to look for a log statement `UG finished event processed. Result data:` followed by the data that will next be sent back to Poly-Solver (including utility value or error messages). 

For the GUI, if there is any problem on the way there will be no answer or the reasoning will fail.
When waiting for a reply to a request sent by UG, a timeout is in place that will be visible in the logs as waring: `Timeout hit after {timeout} seconds - no result received, continuing`.
In other words, if the process screen makes it past the reasoning stage, it works. Details can be tracked in the logs (see below).

**In application logs**

The Morphemic facade logs all communication on `INFO` level.

For sending to the request topic a log entry containing `Sending to topic:` followed by the request topic's name should be logged.
This will be followed by `Message:` and the message's contents.

For receiving a message from the reply topic a log entry containing `Listening to topic:` followed by the reply topic's name should be logged.
When the message is sent by the opposite side and is received by the facade, it should appear as `Message received:` followed by the message's contents (with the data sent).

***On the JMS/AMQ system:***

A listener/consumer should show up for the request and reply topics mentioned above.

When the communication takes place, messages should be added for the request topic and answers put into the reply topic by PM should be marked as delivered.

## Facade Configuration Options
The Morphemic facade offers multiple configuration options for these parameters:
- AMQ URL
- AMQ username
- AMQ password

with the URL being a required value.

Highest priority (in case of redundant input) has the Broker Client's property file for all three values.
To use the configuration from this file, a Java system property with the name `broker_properties_configuration_file_location` must be set and given a valid 
path to the file (e.g. `-Dbroker_properties_configuration_file_location=$yourPath$`).

Alternatively 1 or 3 parameters describing URL, user and pw directly can be used, with the URL being required and the other two being optional.
Should username and password not be set, the facade tries to set up a connection using empty username and password.

All three parameters are also expected as system properties, using these names:
- AMQ URL = `jmsUrl`
- AMQ username = `jmsUser`
- AMQ password = `jmsPw`

Example: `-DjmsUrl=tcp://127.0.01:61616 -DjmsUser=someUser -DjmsPw=somePassword`
