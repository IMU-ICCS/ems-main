PLAN GENERATOR 
========

The Plan Generator is a component of the Adaptation Manager.  It is not expected to be used 
on its own.  The current release is built on CAMEL 1.0 and the Adaptation Manager is expected
to provide the transitive dependencies required by CAMEL. 

The Plan Generator assumes:

1.  When removing an internal component instance, the bindings (hosting and communication instances) 
    should be removed first
2.  When removing a VMInstance, the hosted component/s should be removed first
3.  When adding an internal component, the bindings should be added afterwards.
4.  When adding an internal component, the VMInstance that provide hosting to it should be added first.
5.  If the added component instance has the same type as a removed component instance, the 
    removal should be processed first.  Essentially clean up before adding new objects.


Build
-------
To compile the application via maven:
mvn clean install 


Execute
-------
To create a plan, you need to call the generate method in the PlanGenerator class.
For a new application, provide the null current deployment model and the target deployment model as input.
For updating an existing application, provide both the current and target deployment model as input.

Contact
--------
Shirley Crompton, STFC (shirley.crompton@stfc.ac.uk)

8 April, 2015
