# Utility Generator

## Communication Utility Generator (UG) <--> Performance Module (PM)
For this use case UG is currently being run as part (a library) of CP-Solver. 

At the time of writing, every evaluation request to UG is sent to PM to get performance metrics, so all use cases which contain calls to CP-Solver triggering a 
utility value calculation should also trigger the UG <--> PM communication process. 
Later a filter is planned so that it depends on the data if PM is called or not.

The communication between UG and PM is done via JMS/AMQ using the Morphemic facade (see below for configuration options).

Different topics are used for request and response. The topic names can be found in `eu.melodic.upperware.utilitygenerator.facade.pm.PMFacade` in the constants
`PM_PREDICT_REQUEST_TOPIC` and `PM_PREDICT_REPLY_TOPIC`.

### Testing
#### Preparations:

Prepare a standard MORPHEMIC environment to run the 'Two Components' application test.

Enter a configuration for this application and start the process.

#### Results

For the simplest possible use case (if the setup is correct and if sending to AMQ works, independently of PM's deployment):

Check the log files for CPSolver (`logs/cpsolver.log`) for the patterns described below. 
If a message was sent without error and the system prints that it is listening for an answer the operation was successful.

For the complete use-case UG --> PM --> UG:

If there is any problem on the way (either UG --> PM, inside PM or PM --> UG) there will be no answer or the reasoning will fail. 
In other words, if the process screen makes it past the reasoning stage, it works.

**In application logs**

The Morphemic facade logs all communication on `INFO` level.

For sending to the request topic a log entry containing `Sending to topic:` followed by the request topic's name should be logged. 
This will be followed by `Message:` and the message's contents.

For receiving a message from the reply topic a log entry containing `Listening to topic:` followed by the reply topic's name should be logged.
When the message is sent by PM and is received by the facade, it should appear as `Message received:` followed by the message's contents (with the data PM sent).

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
